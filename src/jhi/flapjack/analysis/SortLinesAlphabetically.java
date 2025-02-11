// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.analysis;

import java.util.*;

import jhi.flapjack.data.*;

public class SortLinesAlphabetically extends SortLines
{
	public SortLinesAlphabetically(GTViewSet viewSet)
	{
		super(viewSet);
	}

	@Override
	protected ArrayList<LineInfo> doSort(GTView view)
	{
		int numLines = view.lineCount();
		ArrayList<LineScore> scores = new ArrayList<>(numLines);
		// Create line scores for each LineInfo
		for (int i = 0; i < numLines && okToRun; i++, linesScored++)
		{
			LineInfo line = view.getLineInfo(i);
			scores.add(new LineScore(line));
		}

		// Sort the array based on those scores
		Collections.sort(scores);

		ArrayList<LineInfo> lineOrder = new ArrayList<>(numLines);
		// Create a new line ordering for the view based on the scores
		for (int i = 0; i < scores.size() && okToRun; i++)
			lineOrder.add(scores.get(i).lineInfo);

		return lineOrder;
	}

	private class LineScore implements Comparable<LineScore>
	{
		LineInfo lineInfo;

		LineScore(LineInfo lineInfo)
		{
			this.lineInfo = lineInfo;
		}

		public int compareTo(LineScore other)
		{
			return lineInfo.getLine().getName().compareToIgnoreCase(
				other.lineInfo.getLine().getName());
		}
	}
}