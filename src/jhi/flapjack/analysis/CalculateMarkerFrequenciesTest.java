// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.analysis;

import java.io.*;
import java.util.*;

import junit.framework.*;

import jhi.flapjack.data.*;
import jhi.flapjack.io.*;

public class CalculateMarkerFrequenciesTest extends TestCase
{
	private DataSet dataSet = new DataSet();

	private void load()
		throws Exception
	{
		File mapFile = new File("tests\\tiny.map");
		File genoFile = new File("tests\\tiny.data");

		ChromosomeMapImporter mapImporter
			= new ChromosomeMapImporter(mapFile, dataSet);
		GenotypeDataImporter genoImporter = new GenotypeDataImporter(genoFile,
			dataSet, mapImporter.getMarkersHashMap(), "", "/", false, false);

		mapImporter.importMap();
		genoImporter.importGenotypeDataAsBytes();
	}

	public void testGenotypeFrequencies()
		throws Exception
	{
		load();

		CalculateMarkerFrequencies c = new CalculateMarkerFrequencies(
			dataSet, CalculateMarkerFrequencies.GENOTYPE_METHOD);


		// State table should be:
		//  UNKNOWN, A, G, C, T, A/G, F, A/T


		// 1st marker: A, C, A, G, T
		float[] freqs = c.getGenotypeFrequencies(0, 0);
		assertEquals("A in 1st marker", 0.4f, freqs[1]);
		assertEquals("C in 1st marker", 0.2f, freqs[3]);
		assertEquals("G in 1st marker", 0.2f, freqs[2]);
		assertEquals("T in 1st marker", 0.2f, freqs[4]);


		// 2nd marker: G, G, G, G, T
		freqs = c.getGenotypeFrequencies(0, 1);
		assertEquals("G in 2nd marker", 0.8f, freqs[2]);
		assertEquals("T in 2nd marker", 0.2f, freqs[4]);


		// 3rd marker: C, G, F, C, A
		freqs = c.getGenotypeFrequencies(0, 2);
		assertEquals("C in 3rd marker", 0.4f, freqs[3]);
		assertEquals("G in 3rd marker", 0.2f, freqs[2]);
		assertEquals("G in 3rd marker", 0.2f, freqs[6]);
		assertEquals("A in 3rd marker", 0.2f, freqs[1]);


		// 4th marker: T, A, G, G, G
		freqs = c.getGenotypeFrequencies(0, 3);
		assertEquals("T in 4th marker", 0.2f, freqs[4]);
		assertEquals("A in 4th marker", 0.2f, freqs[1]);
		assertEquals("G in 4th marker", 0.6f, freqs[2]);


		// 5th marker: A/G, A/G, A/T, A/G, A/G
		freqs = c.getGenotypeFrequencies(0, 4);
		assertEquals("A/G in 5th marker", 0.8f, freqs[5]);
		assertEquals("A/T in 5th marker", 0.2f, freqs[7]);
	}
}