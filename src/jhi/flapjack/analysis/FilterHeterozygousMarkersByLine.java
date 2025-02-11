// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.analysis;

import jhi.flapjack.data.*;

import scri.commons.gui.*;

public class FilterHeterozygousMarkersByLine extends SimpleJob
{
	private GTViewSet viewSet;
	private StateTable stateTable;
	private boolean[] selectedChromosomes;
	private LineInfo line;
	private int count;

	public FilterHeterozygousMarkersByLine(GTViewSet viewSet, boolean[] selectedChromosomes, LineInfo line)
	{
		this.viewSet = viewSet;
		this.selectedChromosomes = selectedChromosomes;
		this.line = line;

		stateTable = viewSet.getDataSet().getStateTable();
	}

	public int getCount()
		{ return count; }

	public void runJob(int index)
		throws Exception
	{
		AnalysisSet as = new AnalysisSet(viewSet)
			.withViews(selectedChromosomes)
			.withAllLines()
			.withSelectedMarkers();

		for (int i = 0; i < as.viewCount(); i++)
			maximum += as.markerCount(i);

		int lineIndex = as.getLines().indexOf(line);

		for (int view = 0; view < as.viewCount(); view++)
		{
			boolean isSpecialChromosome = as.getGTView(view).getChromosomeMap().isSpecialChromosome();

			// For each marker...
			for (int marker = as.markerCount(view)-1; marker >= 0 && okToRun; marker--)
			{
				if (stateTable.isHet(as.getState(view, lineIndex, marker)))
				{
					MarkerInfo mi = as.getMarker(view, marker);
					as.getGTView(view).hideMarker(mi);

					if (isSpecialChromosome == false)
						count++;
				}

				progress++;
			}
		}
	}
}