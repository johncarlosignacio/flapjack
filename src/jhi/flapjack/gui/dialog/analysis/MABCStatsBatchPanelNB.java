// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.dialog.analysis;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import jhi.flapjack.data.*;
import jhi.flapjack.gui.*;

public class MABCStatsBatchPanelNB extends JPanel implements ActionListener
{
	private ArrayList<GTViewSet> viewSets;

	public MABCStatsBatchPanelNB(ArrayList<GTViewSet> viewSets)
	{
		this.viewSets = viewSets;

		System.out.println("Batch analysis could run on " + viewSets.size() + " datasets");

		initComponents();
		initComponents2();
	}

	private void initComponents2()
	{
		maxMrkrCoverage.setValue(Prefs.mabcMaxMrkrCoverage);

		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(bWeighted);
		bGroup.add(bUnweighted);

		bWeighted.addActionListener(this);
		bUnweighted.addActionListener(this);

		// Set the selection state for the weighted / unweighted radio buttons
		// based on the last choice the user made (assumes Weighted by default)
		bWeighted.setSelected(!Prefs.guiUseSimpleMabcStats);
		bUnweighted.setSelected(Prefs.guiUseSimpleMabcStats);

		// Set the enabled state of the max marker coverage spinner and its
		// label based on whether or not we are using the weighted calculation
		jLabel3.setEnabled(!Prefs.guiUseSimpleMabcStats);
		maxMrkrCoverage.setEnabled(!Prefs.guiUseSimpleMabcStats);

		FlapjackUtils.initPanel(settingsPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bWeighted)
		{
			jLabel3.setEnabled(true);
			maxMrkrCoverage.setEnabled(true);
		}

		else if (e.getSource() == bUnweighted)
		{
			jLabel3.setEnabled(false);
			maxMrkrCoverage.setEnabled(false);
		}
	}

	public boolean isOK()
	{
		Prefs.guiUseSimpleMabcStats = bUnweighted.isSelected();

		return true;
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

        settingsPanel = new javax.swing.JPanel();
        bWeighted = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        bUnweighted = new javax.swing.JRadioButton();
        maxMrkrCoverage = new javax.swing.JSpinner();

        settingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("General settings:"));

        bWeighted.setText("Weighted model");

        jLabel3.setText("Maximum coverage per marker (cM): ");

        bUnweighted.setText("Unweighted model");

        maxMrkrCoverage.setModel(new javax.swing.SpinnerNumberModel(10.0d, 0.0d, null, 0.25d));

        javax.swing.GroupLayout settingsPanelLayout = new javax.swing.GroupLayout(settingsPanel);
        settingsPanel.setLayout(settingsPanelLayout);
        settingsPanelLayout.setHorizontalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bWeighted)
                    .addComponent(bUnweighted)
                    .addGroup(settingsPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(maxMrkrCoverage, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        settingsPanelLayout.setVerticalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bWeighted)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(maxMrkrCoverage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bUnweighted)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(settingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(settingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton bUnweighted;
    private javax.swing.JRadioButton bWeighted;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSpinner maxMrkrCoverage;
    private javax.swing.JPanel settingsPanel;
    // End of variables declaration//GEN-END:variables
}