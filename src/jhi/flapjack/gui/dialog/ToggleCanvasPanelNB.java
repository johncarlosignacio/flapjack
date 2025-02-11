// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jhi.flapjack.gui.*;
import jhi.flapjack.gui.visualization.*;

import scri.commons.gui.*;

class ToggleCanvasPanelNB extends JPanel implements ActionListener
{
	private GenotypePanel gPanel;

	public ToggleCanvasPanelNB(GenotypePanel gPanel)
	{
		this.gPanel = gPanel;

		initComponents();

		setBackground((Color)UIManager.get("fjDialogBG"));
		panel.setBackground((Color)UIManager.get("fjDialogBG"));

		// i18n
		panel.setBorder(BorderFactory.createTitledBorder(RB.getString("gui.dialog.NBToggleCanvasPanel.panel.title")));
		RB.setText(checkMiniMapCanvas, "gui.dialog.NBToggleCanvasPanel.checkMiniMapCanvas");
		RB.setText(checkLinePanel, "gui.dialog.NBToggleCanvasPanel.checkLinePanel");
		RB.setText(checkMapCanvas, "gui.dialog.NBToggleCanvasPanel.checkMapCanvas");
		RB.setText(checkQTLCanvas, "gui.dialog.NBToggleCanvasPanel.checkQTLCanvas");
		RB.setText(checkGraphCanvas, "gui.dialog.NBToggleCanvasPanel.checkGraphCanvas");
		RB.setText(checkRowCanvas, "gui.dialog.NBToggleCanvasPanel.checkRowCanvas");
		RB.setText(checkColCanvas, "gui.dialog.NBToggleCanvasPanel.checkColCanvas");
		RB.setText(checkTraitCanvas, "gui.dialog.NBToggleCanvasPanel.checkTraitCanvas");
		RB.setText(checkStatusPanel, "gui.dialog.NBToggleCanvasPanel.checkStatusPanel");

		checkMiniMapCanvas.addActionListener(this);
		checkLinePanel.addActionListener(this);
		checkMapCanvas.addActionListener(this);
		checkQTLCanvas.addActionListener(this);
		checkGraphCanvas.addActionListener(this);
		checkRowCanvas.addActionListener(this);
		checkColCanvas.addActionListener(this);
		checkTraitCanvas.addActionListener(this);
		checkStatusPanel.addActionListener(this);

		checkMiniMapCanvas.setSelected(Prefs.visShowMiniMapCanvas);
		checkLinePanel.setSelected(Prefs.visShowLinePanel);
		checkMapCanvas.setSelected(Prefs.visShowMapCanvas);
		checkQTLCanvas.setSelected(Prefs.visShowQTLCanvas);
		checkGraphCanvas.setSelected(Prefs.visShowGraphCanvas);
		checkRowCanvas.setSelected(Prefs.visShowRowCanvas);
		checkColCanvas.setSelected(Prefs.visShowColCanvas);
		checkTraitCanvas.setSelected(Prefs.visShowTraitCanvas);
		checkStatusPanel.setSelected(Prefs.visShowStatusPanel);
	}

	public void actionPerformed(ActionEvent e)
	{
		Prefs.visShowMiniMapCanvas = checkMiniMapCanvas.isSelected();
		Prefs.visShowLinePanel = checkLinePanel.isSelected();
		Prefs.visShowMapCanvas = checkMapCanvas.isSelected();
		Prefs.visShowQTLCanvas = checkQTLCanvas.isSelected();
		Prefs.visShowGraphCanvas = checkGraphCanvas.isSelected();
		Prefs.visShowRowCanvas = checkRowCanvas.isSelected();
		Prefs.visShowColCanvas = checkColCanvas.isSelected();
		Prefs.visShowTraitCanvas = checkTraitCanvas.isSelected();
		Prefs.visShowStatusPanel = checkStatusPanel.isSelected();

		gPanel.setVisibleStates();
	}


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        checkMiniMapCanvas = new javax.swing.JCheckBox();
        checkLinePanel = new javax.swing.JCheckBox();
        checkMapCanvas = new javax.swing.JCheckBox();
        checkQTLCanvas = new javax.swing.JCheckBox();
        checkGraphCanvas = new javax.swing.JCheckBox();
        checkRowCanvas = new javax.swing.JCheckBox();
        checkColCanvas = new javax.swing.JCheckBox();
        checkTraitCanvas = new javax.swing.JCheckBox();
        checkStatusPanel = new javax.swing.JCheckBox();

        panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Only display:"));

        checkMiniMapCanvas.setText("The mini chromosome map overview");

        checkLinePanel.setText("The list of line names");

        checkMapCanvas.setText("The main chromosome map panel");

        checkQTLCanvas.setText("The QTL panel");

        checkGraphCanvas.setText("The graph/histogram panel");

        checkRowCanvas.setText("The mouse-over line overview panel");

        checkColCanvas.setText("The mouse-over marker overview panel");

        checkTraitCanvas.setText("The phenotypic traits heatmap panel");

        checkStatusPanel.setText("The status bar and zoom controls");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkMiniMapCanvas)
                            .addComponent(checkLinePanel)
                            .addComponent(checkMapCanvas)
                            .addComponent(checkQTLCanvas))
                        .addGap(18, 18, 18)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkRowCanvas)
                            .addComponent(checkColCanvas)
                            .addComponent(checkTraitCanvas)
                            .addComponent(checkStatusPanel)))
                    .addComponent(checkGraphCanvas))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(checkRowCanvas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkColCanvas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkTraitCanvas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkStatusPanel))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(checkMiniMapCanvas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkLinePanel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkMapCanvas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkQTLCanvas)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkGraphCanvas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkColCanvas;
    private javax.swing.JCheckBox checkGraphCanvas;
    private javax.swing.JCheckBox checkLinePanel;
    private javax.swing.JCheckBox checkMapCanvas;
    private javax.swing.JCheckBox checkMiniMapCanvas;
    private javax.swing.JCheckBox checkQTLCanvas;
    private javax.swing.JCheckBox checkRowCanvas;
    private javax.swing.JCheckBox checkStatusPanel;
    private javax.swing.JCheckBox checkTraitCanvas;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}