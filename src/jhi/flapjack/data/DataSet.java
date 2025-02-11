// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.data;

import java.util.*;

import jhi.flapjack.data.pedigree.*;

public class DataSet extends XMLRoot
{
	private String name;

	// Data information
	private ArrayList<ChromosomeMap> chromosomes = new ArrayList<>();
	private ArrayList<Line> lines = new ArrayList<>();

	// Dummy line data (can be used whenever we need a 'placeholder' line
	private Line dummyLine;

	// Sort splitter, used to separate lines which shouldn't be sorted from
	// those that should
	private Line splitter;

	// Trait information
	private ArrayList<Trait> traits = new ArrayList<>();

	// View information
	private StateTable stateTable = new StateTable(0);
	private ArrayList<GTViewSet> viewSets = new ArrayList<>();

	// DB-link/association data
	private DBAssociation dbAssociation = new DBAssociation();

	// Some "global" (to the dataset) UI variables
	// Total counts for matrices, dendrograms, etc
	private HashMap<String, Integer> navPanelCounts = new HashMap<>();

	private boolean colorTraitTable = true;

	private BinnedData bins = new BinnedData();

	public static final int DUMMY_COUNT = 5;

	// Pedigree information
	private PedManager pm = new PedManager();
	// Infomation on favourable alleles
	private FavAlleleManager fm = new FavAlleleManager();

	public DataSet()
	{
	}

	void validate()
		throws NullPointerException
	{
		if (name == null)
			throw new NullPointerException();

		for (ChromosomeMap map: chromosomes)
			map.validate();
		for (Line line: lines)
			line.validate();
		for (Trait trait: traits)
			trait.validate();

		// Rebuild dummy and splitter as they are not part of the saved project
		dummyLine = lines.get(0).createDummy();
		splitter = lines.get(0).createDummy();

		stateTable.validate();
		for (GTViewSet viewSet: viewSets)
			viewSet.validate();
	}


	// Methods required for XML serialization

	public String getName()
		{ return name; }

	public void setName(String name)
		{ this.name = name; }

	public ArrayList<ChromosomeMap> getChromosomeMaps()
		{ return chromosomes; }

	public void setChromosomeMaps(ArrayList<ChromosomeMap> chromosomes)
		{ this.chromosomes = chromosomes; }

	public ArrayList<Line> getLines()
		{ return lines; }

	public void setLines(ArrayList<Line> lines)
		{ this.lines = lines; }

	public Line getDummyLine()
		{ return dummyLine; }

	public void setDummyLine(Line dummyLine)
		{ this.dummyLine = dummyLine; }

	public Line getSplitter()
		{ return splitter; }

	public void setSplitter(Line splitter)
		{ this.splitter = splitter; }

	public ArrayList<Trait> getTraits()
		{ return traits; }

	public void setTraits(ArrayList<Trait> traits)
		{ this.traits = traits; }

	public StateTable getStateTable()
		{ return stateTable; }

	public void setStateTable(StateTable stateTable)
		{ this.stateTable = stateTable; }

	public ArrayList<GTViewSet> getViewSets()
		{ return viewSets; }

	public void setViewSets(ArrayList<GTViewSet> viewSets)
		{ this.viewSets = viewSets; }

	public DBAssociation getDbAssociation()
		{ return dbAssociation; }

	public void setDbAssociation(DBAssociation dbAssociation)
		{ this.dbAssociation = dbAssociation; }

	public HashMap<String, Integer> getNavPanelCounts()
		{ return navPanelCounts; }

	public void setNavPanelCounts(HashMap<String, Integer> navPanelCounts)
		{ this.navPanelCounts = navPanelCounts; }

	public boolean getColorTraitTable()
		{ return colorTraitTable; }

	public void setColorTraitTable(boolean colorTraitTable)
		{ this.colorTraitTable = colorTraitTable; }

	public BinnedData getBinnedData()
		{ return bins; }

	public void setBinnedData(BinnedData binnedData)
		{ this.bins = binnedData; }

	public PedManager getPedManager()
		{ return pm; }

	public void setPedManager(PedManager pm)
		{ this.pm = pm; }

	public FavAlleleManager getFavAlleleManager()
		{ return fm; }

	public void setFavAlleleManager(FavAlleleManager fm)
		{ this.fm = fm; }

	
	// Other methods

	public Line createLine(String name, boolean useByteStorage)
	{
		Line line = new Line(name);

		// This ensures each line has a set of (empty) loci data for each map
		for (ChromosomeMap map: chromosomes)
			line.initializeMap(map, useByteStorage);

		lines.add(line);

		return line;
	}

	public ChromosomeMap getMapByIndex(int index)
		throws ArrayIndexOutOfBoundsException
	{
		return chromosomes.get(index);
	}

	public Line getLineByIndex(int index)
		throws ArrayIndexOutOfBoundsException
	{
		return lines.get(index);
	}

	/**
	 * Returns the chromosome map (and its index within the dataset) with the
	 * given name, or creates a new map with this name if it could not be found.
	 */
	public ChromosomeMap.Wrapper getMapByName(String mapName, boolean create)
	{
		for (int i = 0; i < chromosomes.size(); i++)
			if (chromosomes.get(i).getName().equals(mapName))
				return new ChromosomeMap.Wrapper(chromosomes.get(i), i);

		// If there's no chromosome with this name, don't make a new one
		if (create == false)
			return null;

		ChromosomeMap map = new ChromosomeMap(mapName);
		chromosomes.add(map);

		return new ChromosomeMap.Wrapper(map, chromosomes.size()-1);
	}

	public int countChromosomeMaps()
	{
		return chromosomes.size();
	}

	public int countLines()
	{
		return lines.size();
	}

	/**
	 * Returns the total number of markers (minus dummies) across all maps.
	 */
	public int countGenuineMarkers()
	{
		int count = 0;
		for (ChromosomeMap map: chromosomes)
			if (map.isSpecialChromosome() == false)
				count += map.countLoci();

		return count;
	}

	/**
	 * Sorts the markers within each chromosome map so that they are held in
	 * ascending position order.
	 */
	public void orderMarkersWithinMaps()
	{
		for (ChromosomeMap map: chromosomes)
			map.sort();
	}

	public void createSuperChromosome(String chromosomeName)
	{
		boolean useByteStorage = true;

		// Query the current data to determine useByteStorage value
		if (lines.size() > 0)
			useByteStorage = lines.get(0).useByteStorage();

		// Create a new "super" chromosome to hold every marker
		ChromosomeMap allMap = new ChromosomeMap(chromosomeName);
		allMap.setSpecialChromosome(true);

		double mapOffset = 0;

		// Traverse each existing chromsome...
		for (int i = 0; i < chromosomes.size(); i++)
		{
			ChromosomeMap map = chromosomes.get(i);

			// Adding clones of its markers to the super chromosome
			for (Marker marker: map)
			{
				// Offset each marker's position
				double position = mapOffset + marker.getPosition();
				double realPosition = marker.getPosition();

				Marker m = new Marker(marker.getName(), position, realPosition);
				allMap.addMarker(m);
			}

			mapOffset += map.getLength();

			// Add some dummy markers
			if (i < chromosomes.size()-1)
				for (int d = 0; d < DUMMY_COUNT; d++)
					allMap.addMarker(new Marker(true, mapOffset));
		}

		allMap.sort();

		int allMapIndex = chromosomes.size();

		// Now force the lines to duplicate their info too
		for (Line line: lines)
		{
			line.initializeMap(allMap, useByteStorage);
			int loci = 0;

			for (int i = 0; i < chromosomes.size(); i++)
			{
				ChromosomeMap map = chromosomes.get(i);

				for (int j = 0, num = map.countLoci(); j < num; j++, loci++)
					line.setLoci(allMapIndex, loci, line.getState(i, j));

				// Add dummy loci information
				if (i < chromosomes.size()-1)
					for (int d = 0; d < DUMMY_COUNT; d++, loci++)
						line.setLoci(allMapIndex, loci, 0);
			}
		}

		chromosomes.add(allMap);
	}
}