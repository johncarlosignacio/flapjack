// Copyright 2009-2016 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.pedver;

import java.util.*;

import jhi.flapjack.data.*;
import jhi.flapjack.data.results.*;
import jhi.flapjack.gui.table.*;

class PedVerTableModel extends LineDataTableModel
{
	private PedVerKnownParentsResults results;

	PedVerTableModel(DataSet dataSet, PedVerKnownParentsResults results)
	{
		this.dataSet = dataSet;
		this.results = results;

		initModel();
	}

	void initModel()
	{
		columnNames = new String[] { "Line", "Marker count", "% missing",
			"Het count", "% het", "% deviation from expected", "Count P1 contained",
			"% P1 contained", "Count P2 contained", "% P2 contained",
			"Count allele match to expected", "% allele match to expected" };
	}

	public int getRowCount()
	{
		return results.size();
	}

	public Object getValueAt(int row, int col)
	{
		PedVerKnownParentsLineStats stats = results.getLineStats().get(row);

		if (col == 0)
			return stats.getLine();
		else if (col == 1)
			return stats.getMarkerCount();
		else if (col == 2)
			return stats.getPercentMissing();
		else if (col == 3)
			return stats.getHeterozygousCount();
		else if (col == 4)
			return stats.getPercentHeterozygous();
		else if (col == 5)
			return stats.getPercentDeviationFromExpected();
		else if (col == 6)
			return stats.getCountP1Contained();
		else if (col == 7)
			return stats.getPercentP1Contained();
		else if (col == 8)
			return stats.getCountP2Contained();
		else if (col == 9)
			return stats.getPercentP2Contained();
		else if (col == 10)
			return stats.getCountAlleleMatchExpected();
		else if (col == 11)
			return stats.getPercentAlleleMatchExpected();

		return -1;
	}

	@Override
	public Class getColumnClass(int col)
	{
		if (col == 0)
			return Line.class;
		else
			return Double.class;
	}
}