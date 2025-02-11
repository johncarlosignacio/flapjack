// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.navpanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

import jhi.flapjack.gui.*;

import scri.commons.gui.*;

public class TreeRenderer extends JLabel implements TreeCellRenderer
{
	private static Color bColor = UIManager.getColor("Tree.selectionBackground");

	private boolean selected = false;

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean isExpanded, boolean leaf, int row,
			boolean hasFocus)
	{
		setText(value.toString());

		if (value instanceof DataSetNode)
			setIcon(isExpanded ? Icons.getIcon("FOLDEROPEN") : Icons.getIcon("FOLDER"));
		else if (value instanceof VisualizationNode)
			setIcon(Icons.getIcon("VISUALIZATION"));
		else if (value instanceof TraitsNode)
			setIcon(Icons.getIcon("TRAITS"));
		else if (value instanceof BookmarkNode)
			setIcon(Icons.getIcon("BOOKMARK"));
		else if (value instanceof SimMatrixNode)
			setIcon(Icons.getIcon(((SimMatrixNode)value).getIconName()));
		else if (value instanceof DendrogramNode)
			setIcon(Icons.getIcon("DENDROGRAM"));
		else if (value instanceof ForwardBreedingNode ||
				 value instanceof MabcNode ||
				 value instanceof PedVerF1sNode ||
				 value instanceof PedVerLinesNode)
			setIcon(Icons.getIcon("LINKEDTABLE"));
		else
			setIcon(Icons.getIcon("TREEBLANK"));

		this.selected = selected;

		if (selected)
			setForeground((Color) UIManager.get("Tree.selectionForeground"));
		else
			setForeground((Color) UIManager.get("Tree.foreground"));

		return this;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		Icon icon = getIcon();

		int offset = 0;
		if (icon != null && getText() != null)
			offset = (icon.getIconWidth() + getIconTextGap());

		if (selected)
		{
			g.setColor(bColor);
			g.fillRect(offset, 0, 500 - offset, getHeight() - 1);
		}

		super.paintComponent(g);
	}
}