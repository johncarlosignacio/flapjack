// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.data;

import java.util.*;

public class GenotypeData extends XMLRoot
{
	// A reference to the chromsome this data applies to
	private ChromosomeMap map;

	// Store the data EITHER in a byte array OR a int array
	private byte[] loci;
	private int[] lociInt;

	public GenotypeData()
	{
	}

	public GenotypeData(ChromosomeMap map, boolean useByteStorage)
	{
		this.map = map;

		// How many markers do we need to hold data for?
		int size = map.countLoci();

		// Initialize the array to the correct size
		if (useByteStorage)
			loci = new byte[size];
		else
			lociInt = new int[size];
	}

	void validate()
		throws NullPointerException
	{
		if (map == null || (loci == null && lociInt == null))
			throw new NullPointerException();
	}


	// Methods required for XML serialization

	public ChromosomeMap getChromosomeMap()
		{ return map; }

	public void setChromosomeMap(ChromosomeMap map)
		{ this.map = map; }

	public byte[] getLoci()
		{ return loci; }

	public void setLoci(byte[] loci)
		{ this.loci = loci; }

	public int[] getLociInt()
		{ return lociInt; }

	public void setLociInt(int[] lociInt)
		{ this.lociInt = lociInt; }


	// Other methods

	void setLoci(int index, int stateCode)
	{
		if (loci != null)
			loci[index] = (byte) stateCode;
		else
			lociInt[index] = stateCode;
	}

	/** Returns true if byte (rather than int) storage is in use. */
	boolean useByteStorage()
	{
		return loci != null;
	}

	public int getState(int index)
		throws ArrayIndexOutOfBoundsException
	{
		if (loci != null)
			return loci[index];
		else
			return lociInt[index];
	}

	boolean isGenotypeDataForMap(ChromosomeMap map)
	{
		return this.map == map;
	}

	public int countLoci()
	{
		if (loci != null)
			return loci.length;
		else
			return lociInt.length;
	}

	// Collapses all instances of s2 to have the same value of s1 (basically
	// overwrites all s2 values to be the same as s1)
	void collapseStates(ArrayList<Integer> remap)
	{
		if (loci != null)
		{
			for (int i = 0; i < loci.length; i++)
				loci[i] = (byte) (int) remap.get((int)loci[i]);
		}
		else
		{
			for (int i = 0; i < lociInt.length; i++)
				lociInt[i] = remap.get(lociInt[i]);
		}
	}

	// Returns true if the data is stored in bytes rather than ints
	boolean usingByteStorage()
	{
		return loci != null;
	}
}