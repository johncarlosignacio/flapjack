// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.dialog.importer;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;

import jhi.flapjack.gui.*;
import jhi.flapjack.io.*;

import scri.commons.gui.*;
import scri.commons.gui.matisse.*;

public class ImportDialog extends JDialog implements ActionListener, ChangeListener
{
	private JButton bImport, bCancel, bHelp;
	private boolean isOK = false;

	private JTabbedPane tabs;
	private boolean secondaryOptions;

	private ImportBrapiTabNB brapiTab;
	private ImportGenoTabNB genoTab;
	private ImportTraitsTabNB traitsTab;
	private ImportFeaturesTabNB featuresTab;
	private ImportGraphTabNB graphsTab;
	private ImportSampleTabNB sampleTab;

	public ImportDialog(int tabIndex, boolean secondaryOptions)
	{
		super(
			Flapjack.winMain,
			RB.getString("gui.dialog.DataImportDialog.title"),
			true
		);

		this.secondaryOptions = secondaryOptions;

		add(createButtons(tabIndex), BorderLayout.SOUTH);

		// Create and add the panels/tabs
		tabs = new JTabbedPane();

		brapiTab = new ImportBrapiTabNB();
		tabs.addTab(RB.getString("gui.dialog.DataImportDialog.brapiTab"),
			Icons.getIcon("BRAPI16"), brapiTab);


		genoTab = new ImportGenoTabNB(this);
		tabs.addTab(RB.getString("gui.dialog.DataImportDialog.dataTab"),
			Icons.getIcon("DATATAB"), genoTab);

		traitsTab = new ImportTraitsTabNB(secondaryOptions);
		tabs.addTab(RB.getString("gui.dialog.DataImportDialog.phenotypesTab"),
			Icons.getIcon("PHENOTYPETAB"), traitsTab);

		featuresTab = new ImportFeaturesTabNB(secondaryOptions);
		tabs.addTab(RB.getString("gui.dialog.DataImportDialog.featuresTab"),
			Icons.getIcon("QTLTAB"), featuresTab);

		graphsTab = new ImportGraphTabNB(secondaryOptions);
		tabs.addTab(RB.getString("gui.dialog.DataImportDialog.graphTab"),
			Icons.getIcon("GRAPHTAB"), graphsTab);

		sampleTab = new ImportSampleTabNB(bImport);
		tabs.addTab(RB.getString("gui.dialog.DataImportDialog.sampleTab"),
			Icons.getIcon("HELPTAB"), sampleTab);

		tabs.addChangeListener(this);
		tabs.setSelectedIndex(tabIndex);
		add(tabs);

		FlapjackUtils.initDialog(this, bImport, bCancel, true, getContentPane());
	}

	private JPanel createButtons(int tabIndex)
	{
		if (tabIndex == 0)
			bImport = new JButton(RB.getString("gui.dialog.DataImportDialog.importBrapi"));
		else
			bImport = new JButton(RB.getString("gui.dialog.DataImportDialog.import"));
		bImport.addActionListener(this);
		bCancel = new JButton(RB.getString("gui.text.cancel"));
		bCancel.addActionListener(this);
		bHelp = new JButton(RB.getString("gui.text.help"));
		RB.setText(bHelp, "gui.text.help");
		FlapjackUtils.setHelp(bHelp, "import_data.html");

		JPanel p1 = new DialogPanel();
		p1.add(bImport);
		p1.add(bCancel);
		p1.add(bHelp);

		return p1;
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bImport)
		{
			switch (tabs.getSelectedIndex())
			{
				case 1: if (genoTab.isOK() == false)
							return;
				break;

				case 2: if (traitsTab.isOK() == false)
							return;
				break;

				case 3: if (featuresTab.isOK() == false)
							return;
				break;

				case 4: if (graphsTab.isOK() == false)
							return;
				break;
			}

			isOK = true;
			setVisible(false);
		}

		else if (e.getSource() == bCancel)
			setVisible(false);
	}

	public void stateChanged(ChangeEvent e)
	{
		// Change the Import button's text based on the tab we're viewing
		switch (tabs.getSelectedIndex())
		{
			case 0 :
				RB.setText(bImport, "gui.dialog.DataImportDialog.importBrapi");
				bImport.setEnabled(true);
				break;

			case 1 :
				RB.setText(bImport, "gui.dialog.DataImportDialog.import");
				bImport.setEnabled(true);
				break;

			case 2 :
				RB.setText(bImport, "gui.dialog.DataImportDialog.importPhenotypes");
				bImport.setEnabled(secondaryOptions);
				break;

			case 3 :
				RB.setText(bImport, "gui.dialog.DataImportDialog.importFeatures");
				bImport.setEnabled(secondaryOptions);
				break;

			case 4 :
				RB.setText(bImport, "gui.dialog.DataImportDialog.importGraphs");
				bImport.setEnabled(secondaryOptions);
				break;

			case 5 :
				RB.setText(bImport, "gui.dialog.DataImportDialog.importSample");
				bImport.setEnabled(sampleTab.isOK());
				break;
		}
	}

	public boolean isOK()
		{ return isOK; }

	public int getSelectedAction()
		{ return tabs.getSelectedIndex(); }


	public File getMapFile()
		{ return genoTab.getMapFile(); }

	public File getGenotypeFile()
		{ return genoTab.getGenotypeFile(); }

	public File getTraitsFile()
		{ return traitsTab.getFile(); }

	public File getFeaturesFile()
		{ return featuresTab.getFile(); }

	public File getGraphsFile()
		{ return graphsTab.getFile(); }

	public FlapjackFile getSampleProject()
		{ return sampleTab.getProject(); }
}