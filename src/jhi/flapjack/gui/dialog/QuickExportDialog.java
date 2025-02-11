// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jhi.flapjack.data.*;
import jhi.flapjack.gui.*;

import scri.commons.gui.*;
import scri.commons.gui.matisse.*;

public class QuickExportDialog extends JDialog implements ActionListener
{
	private JButton bOK, bCancel, bHelp;
	private boolean isOK = false;
	private QuickExportPanelNB nbPanel;

	public QuickExportDialog()
	{
		super(
			Flapjack.winMain,
			RB.getString("gui.dialog.QuickExportDialog.title"),
			true
		);

		nbPanel = new QuickExportPanelNB();

		add(nbPanel);
		add(createButtons(), BorderLayout.SOUTH);

		FlapjackUtils.initDialog(this, bOK, bCancel, true, getContentPane());
	}

	private JPanel createButtons()
	{
		bOK = new JButton(RB.getString("gui.text.ok"));
		bOK.addActionListener(this);
		bCancel = new JButton(RB.getString("gui.text.cancel"));
		bCancel.addActionListener(this);
		bHelp = new JButton(RB.getString("gui.text.help"));
		RB.setText(bHelp, "gui.text.help");
		FlapjackUtils.setHelp(bHelp, "quick_export_data.html");

		JPanel p1 = new DialogPanel();
		p1.add(bOK);
		p1.add(bCancel);
		p1.add(bHelp);

		return p1;
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bCancel)
			setVisible(false);

		else if (e.getSource() == bOK && nbPanel.isOK())
		{
			isOK = true;
			setVisible(false);
		}
	}

	public boolean isOK()
	{
		return isOK;
	}

	public String getOutputDir()
	{
		return nbPanel.folder.getText();
	}
}