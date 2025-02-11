// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jhi.flapjack.gui.*;
import jhi.flapjack.gui.visualization.*;

import scri.commons.gui.*;
import scri.commons.gui.matisse.*;

public class ToggleCanvasDialog extends JDialog implements ActionListener
{
	private JButton bClose;

	private ToggleCanvasPanelNB nbPanel;

	public ToggleCanvasDialog(GenotypePanel gPanel)
	{
		super(
			Flapjack.winMain,
			RB.getString("gui.dialog.ToggleCanvasDialog.title"),
			true
		);

		nbPanel = new ToggleCanvasPanelNB(gPanel);

		add(nbPanel);
		add(createButtons(), BorderLayout.SOUTH);

		FlapjackUtils.initDialog(this, bClose, bClose, true, getContentPane());
	}

	private JPanel createButtons()
	{
		bClose = new JButton(RB.getString("gui.text.close"));
		bClose.addActionListener(this);

		JPanel p1 = new DialogPanel();
		p1.add(bClose);

		return p1;
	}

	public void actionPerformed(ActionEvent e)
	{
		setVisible(false);
	}
}