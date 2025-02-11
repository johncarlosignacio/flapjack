// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.dialog.analysis;

import java.awt.event.*;
import javax.swing.*;
import jhi.flapjack.analysis.*;

import jhi.flapjack.data.*;
import jhi.flapjack.gui.*;
import jhi.flapjack.gui.visualization.*;

import scri.commons.gui.*;

/*
 This class is used for filtering both MISSING markers and HET markers
*/
public class FilterMarkersByLineDialog extends JDialog implements ActionListener
{
	private GTViewSet viewSet;
	private ChromosomeSelectionDialog csd;

	private GenotypePanel gPanel;
	private boolean isOK;

	private String label;

	public FilterMarkersByLineDialog(GenotypePanel gPanel, GTViewSet viewSet, String title, String label)
	{
		super(
			Flapjack.winMain,
			title,
			true
		);

		this.gPanel = gPanel;
		this.viewSet = viewSet;
		this.label = label;
		isOK = false;

		initComponents();
		initComponents2();

		FlapjackUtils.initDialog(this, bFilter, bCancel, true,
			getContentPane(), linePanel, dataPanel);
	}

	private void initComponents2()
	{
//		RB.setText(bOK, "gui.text.ok");
		bFilter.addActionListener(this);

		RB.setText(bCancel, "gui.text.cancel");
		bCancel.addActionListener(this);

		RB.setText(bHelp, "gui.text.help");
		FlapjackUtils.setHelp(bHelp, "filtering_markers.html#filter-missing-markers-by-line");

		lineLabel.setText(label);

		csd = new ChromosomeSelectionDialog(viewSet, false, true);
		csd.hideLineSummary();
		csdLabel.addActionListener(e -> { csd.setVisible(true); } );


		// Set up the combo box
		GTView view = viewSet.getView(viewSet.getViewIndex());

		DefaultComboBoxModel<LineInfo> lineModel = new DefaultComboBoxModel<>();

		AnalysisSet as = new AnalysisSet(viewSet)
			.withViews(null)
			.withAllLines()
			.withSelectedMarkers();

		for (int i = 0; i < as.lineCount(); i++)
			lineModel.addElement(as.getLine(i));
		selectedLine.setModel(lineModel);

		if (view.mouseOverLine >= 0 && view.mouseOverLine < view.lineCount())
			selectedLine.setSelectedIndex(view.mouseOverLine);

		if (viewSet.getView(0).countSelectedLines() == 0)
			bFilter.setEnabled(false);
	}

	// Generates a boolean array with a true/false selected state for each of
	// the possible chromosomes that could be used in the sort
	public boolean[] getSelectedChromosomes()
	{
		return csd.getSelectedChromosomes();
	}

	public LineInfo getSelectedLine()
	{
		return (LineInfo)selectedLine.getSelectedItem();
	}

	public boolean isOK()
		{ return isOK; }

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bFilter)
		{
			isOK = true;
			setVisible(false);
		}

		else if (e.getSource() == bCancel)
			setVisible(false);
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        dialogPanel1 = new scri.commons.gui.matisse.DialogPanel();
        bFilter = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();
        bHelp = new javax.swing.JButton();
        linePanel = new javax.swing.JPanel();
        selectedLine = new javax.swing.JComboBox<>();
        lineLabel = new javax.swing.JLabel();
        dataPanel = new javax.swing.JPanel();
        csdLabel = new scri.commons.gui.matisse.HyperLinkLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        bFilter.setText("Filter");
        dialogPanel1.add(bFilter);

        bCancel.setText("Cancel");
        dialogPanel1.add(bCancel);

        bHelp.setText("Help");
        dialogPanel1.add(bHelp);

        linePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Reference line:"));

        lineLabel.setText("Remove all markers with missing data in this line:");

        javax.swing.GroupLayout linePanelLayout = new javax.swing.GroupLayout(linePanel);
        linePanel.setLayout(linePanelLayout);
        linePanelLayout.setHorizontalGroup(
            linePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(linePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(linePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(linePanelLayout.createSequentialGroup()
                        .addComponent(lineLabel)
                        .addGap(0, 66, Short.MAX_VALUE))
                    .addComponent(selectedLine, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        linePanelLayout.setVerticalGroup(
            linePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(linePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lineLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectedLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dataPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Data selection settings:"));

        csdLabel.setText("Select chromosomes to filter across");

        javax.swing.GroupLayout dataPanelLayout = new javax.swing.GroupLayout(dataPanel);
        dataPanel.setLayout(dataPanelLayout);
        dataPanelLayout.setHorizontalGroup(
            dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(csdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dataPanelLayout.setVerticalGroup(
            dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(csdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dialogPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(linePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(linePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dialogPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bFilter;
    private javax.swing.JButton bHelp;
    private scri.commons.gui.matisse.HyperLinkLabel csdLabel;
    private javax.swing.JPanel dataPanel;
    private scri.commons.gui.matisse.DialogPanel dialogPanel1;
    private javax.swing.JLabel lineLabel;
    private javax.swing.JPanel linePanel;
    javax.swing.JComboBox<LineInfo> selectedLine;
    // End of variables declaration//GEN-END:variables

}