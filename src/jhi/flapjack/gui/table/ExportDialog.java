// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.table;

import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import jhi.flapjack.gui.*;

import scri.commons.gui.*;

public class ExportDialog extends JDialog implements ActionListener
{
	private boolean isOK = false;

	public ExportDialog()
	{
		super(Flapjack.winMain, RB.getString("gui.table.ExportDialog.title"), true);

		initComponents();
		initComponents2();

		RB.setText(bCancel, "gui.text.cancel");
		RB.setText(bExport, "gui.table.ExportDialog.bExport");
		RB.setText(bHelp, "gui.text.help");
		FlapjackUtils.setHelp(bHelp, "analysis_results_tables.html#exporting-to-a-file");

		initOptions();

		bBrowse.addActionListener(this);
		bExport.addActionListener(this);
		bCancel.addActionListener(this);

		FlapjackUtils.initDialog(this, bExport, bCancel, true, getContentPane());
	}

	private void initComponents2()
	{
		RB.setText(fileLabel, "gui.table.ExportDialog.fileLabel");
		RB.setText(bBrowse, "gui.text.browse");

		RB.setText(selectLabel, "gui.table.ExportDialog.selectLabel");
		RB.setText(bAllLines, "gui.table.ExportDialog.bAllLines");
		RB.setText(bVisLines, "gui.table.ExportDialog.bVisLines");
		RB.setText(bVisSelLines, "gui.table.ExportDialog.bVisSelLines");

		RB.setText(checkHeaders, "gui.table.ExportDialog.checkHeaders");
		RB.setText(checkTraits, "gui.table.ExportDialog.checkTraits");
	}

	private void initOptions()
	{
		filename.setHistory(Prefs.guiLDTableExportHistory);

		bGroup.add(bAllLines);
		bGroup.add(bVisLines);
		bGroup.add(bVisSelLines);

		switch (Prefs.guiLDTableExportType)
		{
			case 0: bAllLines.setSelected(true);
				break;
			case 1: bVisLines.setSelected(true);
				break;
			case 2: bVisSelLines.setSelected(true);
				break;
		}

		checkHeaders.setSelected(Prefs.guiLDTableExportHeaders);
		checkTraits.setSelected(Prefs.guiLDTableExportTraits);
	}

	private boolean saveOptions()
	{
		if (filename.getText().isEmpty())
		{
			TaskDialog.warning(
				"You must supply a file name to write the exported data to.",
				RB.getString("gui.text.ok"));
			return false;
		}

		Prefs.guiLDTableExportHistory = filename.getHistory();

		if (bAllLines.isSelected())
			Prefs.guiLDTableExportType = 0;
		else if (bVisLines.isSelected())
			Prefs.guiLDTableExportType = 1;
		else if (bVisSelLines.isSelected())
			Prefs.guiLDTableExportType = 2;

		Prefs.guiLDTableExportHeaders = checkHeaders.isSelected();
		Prefs.guiLDTableExportTraits = checkTraits.isSelected();

		return true;
	}

	File getFilename()
		{ return new File(filename.getText()); }

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bBrowse)
			browseForFile();

		else if (e.getSource() == bExport)
		{
			if (saveOptions() == false)
				return;

			isOK = true;
			setVisible(false);
		}
		else if (e.getSource() == bCancel)
			setVisible(false);
	}

	public boolean isOK()
		{ return isOK; }

	private void browseForFile()
	{
		File saveAs = new File(Prefs.guiCurrentDir, "table-data.txt");
		if (!filename.getText().isEmpty())
			saveAs = new File(filename.getText());

		FileNameExtensionFilter filter = new FileNameExtensionFilter(
			RB.getString("other.Filters.txt"), "txt");

		// Ask the user for a filename to save the current view as
		String file = FlapjackUtils.getSaveFilename("Save table data as", saveAs, filter);

		// Quit if the user cancelled the file selection
		if (file != null)
			filename.updateComboBox(file);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        bGroup = new javax.swing.ButtonGroup();
        dialogPanel1 = new scri.commons.gui.matisse.DialogPanel();
        bExport = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();
        bHelp = new javax.swing.JButton();
        fileLabel = new javax.swing.JLabel();
        filename = new scri.commons.gui.matisse.HistoryComboBox();
        bBrowse = new javax.swing.JButton();
        selectLabel = new javax.swing.JLabel();
        bAllLines = new javax.swing.JRadioButton();
        bVisLines = new javax.swing.JRadioButton();
        bVisSelLines = new javax.swing.JRadioButton();
        checkHeaders = new javax.swing.JCheckBox();
        checkTraits = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        bExport.setText("Export");
        dialogPanel1.add(bExport);

        bCancel.setText("Cancel");
        dialogPanel1.add(bCancel);

        bHelp.setText("Help");
        dialogPanel1.add(bHelp);

        fileLabel.setText("File name:");

        bBrowse.setText("Browse...");

        selectLabel.setText("Select which lines to include in the output:");

        bAllLines.setText("All lines (incuding any which may have been filtered)");

        bVisLines.setText("Only visible lines");

        bVisSelLines.setText("Only visible and selected lines");

        checkHeaders.setText("Include header rows with details of any active filter, sort or selection parameters");

        checkTraits.setText("Include additional columns with details of any phenotypic trait information");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dialogPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fileLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filename, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bBrowse))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(selectLabel)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bVisLines)
                            .addComponent(bAllLines)
                            .addComponent(bVisSelLines))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkTraits)
                            .addComponent(checkHeaders))
                        .addGap(0, 69, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileLabel)
                    .addComponent(filename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bBrowse))
                .addGap(18, 18, 18)
                .addComponent(selectLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bAllLines)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bVisLines)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bVisSelLines)
                .addGap(18, 18, 18)
                .addComponent(checkHeaders)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkTraits)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dialogPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton bAllLines;
    private javax.swing.JButton bBrowse;
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bExport;
    private javax.swing.ButtonGroup bGroup;
    private javax.swing.JButton bHelp;
    private javax.swing.JRadioButton bVisLines;
    private javax.swing.JRadioButton bVisSelLines;
    private javax.swing.JCheckBox checkHeaders;
    private javax.swing.JCheckBox checkTraits;
    private scri.commons.gui.matisse.DialogPanel dialogPanel1;
    private javax.swing.JLabel fileLabel;
    private scri.commons.gui.matisse.HistoryComboBox filename;
    private javax.swing.JLabel selectLabel;
    // End of variables declaration//GEN-END:variables
}