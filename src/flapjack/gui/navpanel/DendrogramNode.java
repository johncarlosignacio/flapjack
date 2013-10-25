package flapjack.gui.navpanel;

import java.awt.image.*;
import javax.swing.*;

import flapjack.data.*;
import flapjack.gui.dendrogram.*;

public class DendrogramNode extends BaseNode
{
	private DendrogramPanel panel;
	private String title;

	public DendrogramNode(DataSet dataSet, Dendrogram dendrogram)
	{
		super(dataSet);

		panel = new DendrogramPanel(dendrogram);
		title = dendrogram.getTitle();
	}

	public String toString()
	{
		return title;
	}

	public void setActions()
	{

	}

	public JPanel getPanel()
	{
		return panel;
	}
}
