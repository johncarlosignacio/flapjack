// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.io.brapi;

import java.io.*;
import java.net.*;
import java.util.*;

import jhi.flapjack.data.*;
import jhi.flapjack.gui.*;
import jhi.flapjack.io.*;

import jhi.brapi.api.markerprofiles.*;
import okhttp3.*;
import scri.commons.gui.*;

public class BrapiGenotypeImporter implements IGenotypeImporter
{
	private DataSet dataSet;
	private StateTable stateTable;

	// Each marker's name is stored (only while loading) in a hashmap, along
	// with the index of the chromosome it is associated with
	private HashMap<String, MarkerIndex> markers;
	private HashMap<String, MarkerIndex> markersByName;

	// Stores known states as we find them so we don't have to keep working them
	// out for each allele
	private HashMap<String, Integer> states = new HashMap<>();

	private String ioMissingData;
	private String ioHeteroSeparator;

	private long alleleCount;

	private boolean useByteStorage = true;

	private boolean isOK = true;

	private BrapiClient client;
	private boolean mapWasProvided;
	private boolean fakeMapCreated = false;

	public BrapiGenotypeImporter(BrapiClient client, DataSet dataSet, HashMap<String, MarkerIndex> markers,
		 HashMap<String, MarkerIndex> markersByName, String ioMissingData, String ioHeteroSeparator)
	{
		this.client = client;
		this.dataSet = dataSet;
		this.markers = markers;
		this.markersByName = markersByName;
		this.ioMissingData = ioMissingData;
		this.ioHeteroSeparator = ioHeteroSeparator;

		this.ioMissingData = "N";
		this.ioHeteroSeparator = "/";

		stateTable = dataSet.getStateTable();

		mapWasProvided = markersByName.size() > 0;
	}

	@Override
	public void cleanUp()
	{
		markers.clear();
	}

	@Override
	public void cancelImport()
	{
		isOK = false;
		client.cancel();
	}

	@Override
	public long getLineCount()
		{ return dataSet.getLines().size(); }

	@Override
	public long getMarkerCount()
		{ return alleleCount; }

	@Override
	// Returns false if storing the data using byte arrays failed
	public boolean importGenotypeDataAsBytes()
		throws Exception
	{
		return readData();
	}

	@Override
	public void importGenotypeDataAsInts()
		throws Exception
	{
		useByteStorage = false;

		readData();
	}

	private boolean readData()
		throws Exception
	{
		// Simpler (v2) use case where we just need to ask for the allelematrix
		// which will return a Flapjack formatted genotypes file
		if (client.hasAlleleMatrices())
			return readFlapjackAlleleMatrix(true, null);


		// Call /markerprofiles for list of all profile IDs so those parameters
		// can be fed into the /allelematrix call
		List<BrapiMarkerProfile> profiles = client.getMarkerProfiles();

		HashMap<String, Line> linesByProfileID = new HashMap<>();

		if (client.hasAlleleMatrixSearchFlapjack())
			return readFlapjackAlleleMatrix(false, profiles);

		else if (client.hasAlleleMatrixSearchTSV())
		{
			if (mapWasProvided)
			{
				for (BrapiMarkerProfile mp : profiles)
				{
					String name = mp.getUniqueDisplayName();

					// TODO: Call specifies unique name but should we check for duplicates just in case???
					Line line = dataSet.createLine(name, useByteStorage);

					linesByProfileID.put(mp.getMarkerprofileDbId(), line);
				}
			}

			return readTSVAlleleMatrix(linesByProfileID, profiles);
		}

		else
			return readJSONAlleleMatrix(linesByProfileID, profiles);
	}

	@Override
	public long getBytesRead()
		{ return 0; }

	private boolean readTSVAlleleMatrix(HashMap<String, Line> linesByProfileID, List<BrapiMarkerProfile> profiles)
		throws Exception
	{
		URI uri = client.getAlleleMatrixFileByProfiles(profiles, "tsv");
		// We need to add the authorization token to the headers of requests from this client
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(uri)));

		// The first line is a list of marker profile IDs
		String str = in.readLine();

		// A list of IDs that need to be mapped back to the original list of marker profile IDs that
		// were asked for
		String[] tmpstr = str.split("\t");
		List<String> markerprofileIds = Arrays.asList(tmpstr);


		if (mapWasProvided == false)
		{
			List<String> markerNames = new ArrayList<>();
			while ((str = in.readLine()) != null)
			{
				String[] tokens = str.split("\t");
				String markerID = tokens[0].trim();
				markerNames.add(markerID);
			}
			markerNames.forEach(this::queryMarker);

			for (BrapiMarkerProfile mp : profiles)
			{
				String name = mp.getUniqueDisplayName();

				// TODO: Call specifies unique name but should we check for duplicates just in case???
				Line line = dataSet.createLine(name, useByteStorage);

				linesByProfileID.put(mp.getMarkerprofileDbId(), line);
			}
			fakeMapCreated = true;
		}
		in.close();

		in = new BufferedReader(new InputStreamReader(client.getInputStream(uri)));

		// The first line is a list of marker profile IDs
		in.readLine();

		while ((str = in.readLine()) != null && !str.isEmpty())
		{
			String[] tokens = str.split("\t");
			String markerID = tokens[0].trim();

			MarkerIndex index = queryMarker(markerID);

			for (int j = 1; j < tokens.length; j++)
			{
				// Retrieve the line matching this
				String mpID = markerprofileIds.get(j);
				Line line = linesByProfileID.get(mpID);

				if (line == null || index == null)
					break;

				String allele = tokens[j];

				// Determine its various states
				Integer stateCode = states.computeIfAbsent(allele,
					a -> stateTable.getStateCode(a, true, ioMissingData, ioHeteroSeparator));

				// Then apply them to the marker data
				line.setLoci(index.mapIndex, index.mkrIndex, stateCode);

				alleleCount++;
			}

			if (useByteStorage && stateTable.size() > 127)
			{
				in.close();
				return false;
			}

			if (isOK == false)
				break;
		}

		in.close();

		return true;
	}

	private boolean readFlapjackAlleleMatrix(boolean createLines, List<BrapiMarkerProfile> profiles)
		throws Exception
	{
		BufferedReader in = null;

		if (createLines)
		{
			URI uri = client.getAlleleMatrixFileById();

			if (isOK && uri != null)
			{
				Response response = client.getResponse(uri);
				String cl = response.header("Content-Length");

				// If the file is 500MB in size or larger
				if (cl != null && Long.parseLong(cl) >= 524288000)
				{
					String size = FlapjackUtils.getSizeString(Long.parseLong(cl));
					String msg = RB.format("io.BrapiGenotypeImporter.largeFileMsg", size);
					String[] options = new String[]{RB.getString("gui.text.ok"), RB.getString("gui.text.cancel")};

					if (TaskDialog.show(msg, TaskDialog.QST, 1, options) != 0)
					{
						response.close();
						cancelImport();
						return false;
					}
				}

				in = new BufferedReader(new InputStreamReader(response.body().byteStream()));
			}
			else
			{
				return false;
			}
		}
		else
		{
			URI uri = client.getAlleleMatrixFileByProfiles(profiles, "flapjack");
			in = new BufferedReader(new InputStreamReader(client.getInputStream(uri)));
		}

		// The first line is a list of marker profile IDs
		String str;

		while ((str = in.readLine()) != null && !str.isEmpty() && isOK)
		{
			if (str.startsWith("#"))
				continue;

			// Split the first line up into an array of marker names (we ignore the
			// first element as it's a redundant column header: i=1 in loop below)
			String[] markerNames = str.split("\t");

			// Now work out the map indices of these markers and the indices within
			// the map itself. This speeds up loading by pre-caching this data so we
			// don't need to search per line, rather just once per file
			int[] mapIndex = new int[markerNames.length];
			int[] mkrIndex = new int[markerNames.length];

			for (int i = 1; i < markerNames.length && isOK; i++)
			{
				MarkerIndex index = queryMarker(markerNames[i].trim());

				// Check that the marker does exists on map
				if (index != null)
				{
					mapIndex[i] = index.mapIndex;
					mkrIndex[i] = index.mkrIndex;
				}
				// This ensures a marker - in the .dat file - that isn't in the .map
				// file will be ignored
				else
					mapIndex[i] = mkrIndex[i] = -1;
			}

			while ((str = in.readLine()) != null && isOK)
			{
				if (str.length() == 0)
					continue;

				String[] values = str.split("\t");

				if (values.length == 0)
					continue;

				String name = values[0].trim();
				Line line = dataSet.createLine(name, useByteStorage);

				for (int i = 1; i < values.length && isOK; i++)
				{
					// Assuming a map was found that contains this marker...
					if (mapIndex[i] != -1)
					{
						// Determine its various states
						Integer stateCode = states.computeIfAbsent(values[i],
							a -> stateTable.getStateCode(a, true, ioMissingData, ioHeteroSeparator));

						// Then apply them to the marker data
						line.setLoci(mapIndex[i], mkrIndex[i], stateCode);
					}
				}

				if (useByteStorage && stateTable.size() > 127)
					return false;
			}
		}

		in.close();

		return true;
	}

	private boolean readJSONAlleleMatrix(HashMap<String, Line> linesByProfileID, List<BrapiMarkerProfile> profiles)
		throws Exception
	{
		// Now retrieve the allele data using the /brapi/allelematrix call
		List<BrapiAlleleMatrix> matrixList = client.getAlleleMatrix(profiles);

		for (int m = 0; m < matrixList.size(); m++)
		{
			BrapiAlleleMatrix matrix = matrixList.get(m);

			for (int call = 0; call < matrix.getData().size(); call++)
			{
				String markerDbId = matrix.markerId(call);
				String markerprofileDbId = matrix.markerProfileId(call);
				String allele = matrix.allele(call);

				Line line = linesByProfileID.get(markerprofileDbId);
				MarkerIndex index = markers.get(markerDbId);
				if (line != null && index != null)
				{
					Integer stateCode = states.computeIfAbsent(allele,
						a -> stateTable.getStateCode(a, true, ioMissingData, ioHeteroSeparator));

					// Then apply them to the marker data
					line.setLoci(index.mapIndex, index.mkrIndex, stateCode);

					alleleCount++;

					if (useByteStorage && stateTable.size() > 127)
						return false;
				}
			}

			if (isOK == false)
				break;
		}

		return true;
	}

	private MarkerIndex queryMarker(String name)
	{
		// If a map was provided, then just use the hashtable
		if (mapWasProvided || fakeMapCreated)
			return markersByName.get(name);

		// Otherwise, we're into the special case for no map

		// Make sure it's not a duplicate marker
		if (markersByName.get(name) != null)
			return null;

		// Its position will just be based no how many we've added so far
		int position = markersByName.size();
		Marker marker = new Marker(name, position);

		// There's only one map, so just grab it and add the marker to it
		ChromosomeMap map = dataSet.getChromosomeMaps().get(0);
		map.addMarker(marker);
		// Updating its length as we go
		map.setLength(position);

		// We can set the ChrIndex to 0 as we know there's only one
		MarkerIndex mi = new MarkerIndex(0, markersByName.size());
		markersByName.put(marker.getName(), mi);

		return mi;
	}

	public boolean isOK()
		{ return isOK; }

	public String currentAsyncStatusMessage()
	{
		return client.currentAsyncStatusMessage();
	}
}