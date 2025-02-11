// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.analysis;

import java.util.stream.*;
import jhi.flapjack.data.*;
import jhi.flapjack.data.pedigree.*;
import jhi.flapjack.data.results.*;
import jhi.flapjack.gui.visualization.colors.*;

import scri.commons.gui.*;

public class PedVerF1sAnalysis extends SimpleJob
{
	private GTViewSet viewSet;
	private AnalysisSet as;
	private StateTable stateTable;

	private int parent1Index;
	private int parent2Index;
	private boolean simulateF1;
	private int f1Index;
	private boolean[] selectedChromosomes;
	private String name;
	private boolean excludeAdditionalParents;
	private PedVerF1sThresholds thresholds;

	private int f1HetCount = 0;
	private int usableMarkerCount = 0;
	private double f1PercentCount = 0;

	public PedVerF1sAnalysis(GTViewSet viewSet, boolean[] selectedChromosomes, int parent1Index, int parent2Index, boolean simulateF1, int f1Index, boolean excludeAdditionalParents, String name, PedVerF1sThresholds thresholds)
	{
		this.viewSet =  viewSet.createClone("", true);
		this.selectedChromosomes = selectedChromosomes;
		this.stateTable = viewSet.getDataSet().getStateTable();
		this.parent1Index = parent1Index;
		this.parent2Index = parent2Index;
		this.simulateF1 = simulateF1;
		this.f1Index = f1Index;
		this.excludeAdditionalParents = excludeAdditionalParents;
		this.name = name;
		this.thresholds = thresholds;
	}

	public PedVerF1sAnalysis(GTViewSet viewSet, boolean[] selectedChromosomes, int parent1Index, int parent2Index, boolean simulateF1, int f1Index, boolean excludeAdditionalParents, String name)
	{
		this.viewSet =  viewSet.createClone("", true);
		this.selectedChromosomes = selectedChromosomes;
		this.stateTable = viewSet.getDataSet().getStateTable();
		this.parent1Index = parent1Index;
		this.parent2Index = parent2Index;
		this.simulateF1 = simulateF1;
		this.f1Index = f1Index;
		this.excludeAdditionalParents = excludeAdditionalParents;
		this.name = name;
		// TODO: longer term provide constructor which lets users specify custom thresholds from the command line
		this.thresholds = PedVerF1sThresholds.fromUserDefaults();
	}

	private void setupAnalysis()
	{
		if (simulateF1)
		{
			SimulateF1 f1Sim = new SimulateF1(viewSet, parent1Index, parent2Index);
			// TODO: have the F1 simulation track as part of this SimpleJob
			f1Sim.runJob(0);
			f1Index = f1Sim.getF1Index();
		}

		// If the user has specified that only the parents used for the analysis
		// should be included in the results and the view
		if (excludeAdditionalParents)
		{
			PedManager pedMan = viewSet.getDataSet().getPedManager();

			// Iterate backward over the viewSet so we can remove any parents
			// that we need to
			for (int i = viewSet.getLines().size() - 1; i >= 0; i--)
			{
				// Don't remove the selected rp and dp
				if (i == parent1Index || i == parent2Index || i == f1Index)
					continue;

				if (pedMan.isParent(viewSet.getLines().get(i)))
				{
					viewSet.getLines().remove(i);

					// If the removed parent is before rp, or dp in the viewSet
					// we need to adjust the rpIndex and dpIndex
					if (i < parent1Index)
						parent1Index--;

					if (i < parent2Index)
						parent2Index--;

					if (i < f1Index)
						f1Index--;
				}
			}
		}
	}

	public void runJob(int index)
	{
		long s = System.currentTimeMillis();

		setupAnalysis();

		as = new AnalysisSet(viewSet)
			.withViews(selectedChromosomes)
			.withSelectedLines()
			.withSelectedMarkers();

		calculateExpectedF1Stats();

//		for (int l=0; l < as.lineCount(); l++)
		IntStream.range(0, as.lineCount()).parallel().forEach((l) -> {
			calculateStatsForLine(l);
		});

		prepareForVisualization();

		long e = System.currentTimeMillis();
		System.out.println("TIME: " + (e-s) + "ms");
	}

	private void calculateStatsForLine(int lineIndex)
	{
		LineInfo lineInfo = as.getLine(lineIndex);
		PedVerF1sResult lineStat = new PedVerF1sResult();
		lineInfo.getResults().setPedVerF1sResult(lineStat);
		lineInfo.getResults().setName(name);

		int totalCount = 0;
		for (int view = 0; view < as.viewCount(); view++)
			totalCount += as.markerCount(view);

		int missingMarkerCount = as.missingMarkerCount(lineIndex);

		int foundMarkers = totalCount - missingMarkerCount;
		int hetMarkers = as.hetCount(lineIndex);
		double similarityToP1 = similarityToLine(lineIndex, parent1Index);
		double similarityToP2 = similarityToLine(lineIndex, parent2Index);
		int matchesExpF1 = matchesExpF1(lineIndex);

		lineStat.setDataCount(foundMarkers);
		lineStat.setPercentData((foundMarkers / (double) totalCount) * 100);
		lineStat.setHeterozygousCount(hetMarkers);
		lineStat.setPercentHeterozygous((hetMarkers / (double)foundMarkers) * 100);
		lineStat.setPercentDeviationFromExpected(f1PercentCount - ((hetMarkers / (double)foundMarkers) * 100));
		lineStat.setSimilarityToP1(similarityToP1 * 100);
		lineStat.setSimilarityToP2(similarityToP2 * 100);
		lineStat.setPercentAlleleMatchExpected((matchesExpF1 / (double)foundMarkers) * 100);
		lineStat.setThresholds(thresholds);
		lineStat.setParent1Heterozygosity((as.hetCount(parent1Index) / (double)foundMarkers) * 100);
		lineStat.setParent2Heterozygosity((as.hetCount(parent2Index) / (double)foundMarkers) * 100);
		lineStat.setF1Heterozygosity((as.hetCount(f1Index) / (double)foundMarkers) * 100);
	}

	// Loops over all the alleles in the expected F1 as identified by f1Index
	// and counts the total number of usable markers and the total number of
	// heterozygous alleles. Finally it calculates the percentage of alleles in
	//the (expected) F1 line that are heterozygous.
	private void calculateExpectedF1Stats()
	{
		for (int c = 0; c < as.viewCount(); c++)
		{
			for (int m = 0; m < as.markerCount(c); m++)
			{
				if (as.getState(c, f1Index, m) != 0)
				{
					usableMarkerCount++;

					int stateCode = as.getState(c, f1Index, m);
					if (stateTable.isHet(stateCode))
						f1HetCount++;
				}
			}
		}
		f1PercentCount = (f1HetCount / (double) usableMarkerCount) * 100;
	}

	// Checks to see if this allele is usable. It first checks that the allele
	// itself isn't unknown, then checks that the parental and f1 alleles at
	// this location aren't known. Finally it checks that the parental alleles
	// aren't hets at this location.
	private boolean isUsableMarker(int chr, int line, int marker)
	{
		return as.getState(chr, line, marker) != 0
			&& as.getState(chr, parent1Index, marker) != 0
			&& as.getState(chr, parent2Index, marker) != 0
			&& as.getState(chr, f1Index, marker) != 0
			&& stateTable.isHom(as.getState(chr, parent1Index, marker))
			&& stateTable.isHom(as.getState(chr, parent2Index, marker));
	}

	private double similarityToLine(int line, int comparisonLine)
	{
		double score = 0;
		int nComps = 0;

		for (int c = 0; c < as.viewCount(); c++)
		{
			for (int m = 0; m < as.markerCount(c); m++)
			{
				nComps++;

				AlleleState s1 = stateTable.getAlleleState(as.getState(c, comparisonLine, m));
				AlleleState s2 = stateTable.getAlleleState(as.getState(c, line, m));

				if (s1.matches(s2))
					score += 1.0d;

					// TODO: This is only really correct for diploid data, as
					// A/T/A vs A/T/G should really score 0.6666
				else if (s1.matchesAnyAllele(s2))
					score += 0.5d;
				else
					score += 0;
			}
		}

		return nComps > 0 ? (score / (double)nComps) : 0;
	}

	private int matchesExpF1(int lineIndex)
	{
		int matchesExpF1 = 0;

		for (int c = 0; c < as.viewCount(); c++)
		{
			for (int m = 0; m < as.markerCount(c); m++)
			{
				if (isUsableMarker(c, lineIndex, m))
				{
					// Compare state code of the current line with the equivalent in test line
					AlleleState testState = stateTable.getAlleleState(as.getState(c, f1Index, m));
					AlleleState currState = stateTable.getAlleleState(as.getState(c, lineIndex, m));

					if (currState.matches(testState))
						matchesExpF1++;
				}
			}
		}
		return matchesExpF1;
	}

	private void prepareForVisualization()
	{
		prepareParentsForVisualization();
		changeColourScheme();
		addViewSetToDataSet();
	}

	private void prepareParentsForVisualization()
	{
		LineInfo p1 = viewSet.getLines().get(parent1Index);
		LineInfo p2 = viewSet.getLines().get(parent2Index);
		LineInfo f1 = viewSet.getLines().get(f1Index);

		// Mark the parents lines as sortToTop special cases
		p1.getResults().setSortToTop(true);
		p2.getResults().setSortToTop(true);
		f1.getResults().setSortToTop(true);

		// Mark parents and f1s for later retrieval
		p1.getResults().getPedVerF1sResult().setP1(true);
		p2.getResults().getPedVerF1sResult().setP2(true);
		f1.getResults().getPedVerF1sResult().setF1(true);

		// Remove them from the list
		viewSet.getLines().remove(p1);
		viewSet.getLines().remove(p2);
		viewSet.getLines().remove(f1);

		// Then put them back in at the top
		viewSet.getLines().add(0, p1);
		viewSet.getLines().add(1, p2);
		// Move the f1 to just below the parents
		viewSet.getLines().add(2, f1);

		// Reset our indexes as we've moved the lines in the dataset
		parent1Index = 0;
		parent2Index = 1;
		f1Index = 2;
	}

	private void changeColourScheme()
	{
		// Set the colour scheme to the similarity to line exact match scheme
		// and set the comparison line equal to the F1
		viewSet.setColorScheme(ColorScheme.LINE_SIMILARITY_EXACT_MATCH);
		viewSet.setComparisonLineIndex(f1Index);
		viewSet.setComparisonLine(viewSet.getLines().get(f1Index).getLine());
	}

	private void addViewSetToDataSet()
	{
		DataSet dataSet = viewSet.getDataSet();

		int id = dataSet.getNavPanelCounts().getOrDefault("pedVerF1sCount", 0) + 1;
		dataSet.getNavPanelCounts().put("pedVerF1sCount", id);
		viewSet.setName(RB.format("gui.MenuAnalysis.pedVerF1s.view", id));

		// Create new NavPanel components to hold the results
		dataSet.getViewSets().add(viewSet);
	}

	public GTViewSet getViewSet()
		{ return viewSet; }
}