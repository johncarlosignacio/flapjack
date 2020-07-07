// Copyright 2009-2020 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.navpanel;

import java.awt.event.*;

import jhi.flapjack.gui.*;
import jhi.flapjack.io.*;

import scri.commons.gui.*;
import scri.commons.gui.matisse.HyperLinkLabel;

public class StartPanelFileNB extends javax.swing.JPanel implements ActionListener
{
	private HyperLinkLabel[] labels = new HyperLinkLabel[10];
	private String[] files = new String[10];

	public StartPanelFileNB()
	{
		initComponents();
		setOpaque(false);

		RB.setText(importLabel, "gui.navpanel.NBStartFilePanel.importLabel");
		RB.setText(sampleLabel, "gui.navpanel.NBStartFilePanel.sampleLabel");
		RB.setText(openLabel, "gui.navpanel.NBStartFilePanel.openLabel");


		importLabel.setIcon(Icons.getIcon("FILEIMPORT"));
		importLabel.addActionListener(this);

		sampleLabel.setIcon(Icons.getIcon("OPENSAMPLE"));
		sampleLabel.addActionListener(this);

		// Create the labels array
		labels[0] = project0; labels[1] = project1;
		labels[2] = project2; labels[3] = project3;
		labels[4] = project4; labels[5] = project5;
		labels[6] = project6; labels[7] = project7;
		labels[8] = project8; labels[9] = project9;

		// Create the files array
		files[0] = Prefs.guiRecentProject1; files[1] = Prefs.guiRecentProject2;
		files[2] = Prefs.guiRecentProject3; files[3] = Prefs.guiRecentProject4;
		files[4] = Prefs.guiRecentProject5; files[5] = Prefs.guiRecentProject6;
		files[6] = Prefs.guiRecentProject7; files[7] = Prefs.guiRecentProject8;
		files[8] = Prefs.guiRecentProject9; files[9] = Prefs.guiRecentProject10;

		for (int i = 0; i < labels.length; i++)
		{
			if (files[i] != null)
			{
				labels[i].addActionListener(this);
				labels[i].setText(files[i]);
			}
			else
				labels[i].setVisible(false);
		}

		openLabel.setVisible(labels[0].isVisible());
    }

	@Override
	public void actionPerformed(ActionEvent e)
	{
		WinMain wm = Flapjack.winMain;

		if (e.getSource() == importLabel)
			wm.mFile.fileImport(1);

		else if (e.getSource() == sampleLabel)
			wm.mFile.fileImport(5);

		else
		{
			for (int i = 0; i < labels.length; i++)
				if (e.getSource() == labels[i])
					wm.mFile.fileOpen(new FlapjackFile(files[i]));
		}
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

        openLabel = new javax.swing.JLabel();
        importLabel = new scri.commons.gui.matisse.HyperLinkLabel();
        sampleLabel = new scri.commons.gui.matisse.HyperLinkLabel();
        project0 = new scri.commons.gui.matisse.HyperLinkLabel();
        project1 = new scri.commons.gui.matisse.HyperLinkLabel();
        project2 = new scri.commons.gui.matisse.HyperLinkLabel();
        project3 = new scri.commons.gui.matisse.HyperLinkLabel();
        project4 = new scri.commons.gui.matisse.HyperLinkLabel();
        project5 = new scri.commons.gui.matisse.HyperLinkLabel();
        project6 = new scri.commons.gui.matisse.HyperLinkLabel();
        project7 = new scri.commons.gui.matisse.HyperLinkLabel();
        project8 = new scri.commons.gui.matisse.HyperLinkLabel();
        project9 = new scri.commons.gui.matisse.HyperLinkLabel();

        openLabel.setText("Open an previously accessed Flapjack project:");

        importLabel.setForeground(new java.awt.Color(68, 106, 156));
        importLabel.setText("Import data into Flapjack");

        sampleLabel.setForeground(new java.awt.Color(68, 106, 156));
        sampleLabel.setText("Download an example Project");

        project0.setForeground(new java.awt.Color(68, 106, 156));
        project0.setText("p0");

        project1.setForeground(new java.awt.Color(68, 106, 156));
        project1.setText("p1");

        project2.setForeground(new java.awt.Color(68, 106, 156));
        project2.setText("p2");

        project3.setForeground(new java.awt.Color(68, 106, 156));
        project3.setText("p3");

        project4.setForeground(new java.awt.Color(68, 106, 156));
        project4.setText("p4");

        project5.setForeground(new java.awt.Color(68, 106, 156));
        project5.setText("p5");

        project6.setForeground(new java.awt.Color(68, 106, 156));
        project6.setText("p6");

        project7.setForeground(new java.awt.Color(68, 106, 156));
        project7.setText("p7");

        project8.setForeground(new java.awt.Color(68, 106, 156));
        project8.setText("p8");

        project9.setForeground(new java.awt.Color(68, 106, 156));
        project9.setText("p9");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(project0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(project1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(project2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(project3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(project4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(project5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(project6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(project7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(project8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(project9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(importLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sampleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(importLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(sampleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(openLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(project0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(project1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(project2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(project3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(project4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(project5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(project6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(project7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(project8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(project9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private scri.commons.gui.matisse.HyperLinkLabel importLabel;
    private javax.swing.JLabel openLabel;
    private scri.commons.gui.matisse.HyperLinkLabel project0;
    private scri.commons.gui.matisse.HyperLinkLabel project1;
    private scri.commons.gui.matisse.HyperLinkLabel project2;
    private scri.commons.gui.matisse.HyperLinkLabel project3;
    private scri.commons.gui.matisse.HyperLinkLabel project4;
    private scri.commons.gui.matisse.HyperLinkLabel project5;
    private scri.commons.gui.matisse.HyperLinkLabel project6;
    private scri.commons.gui.matisse.HyperLinkLabel project7;
    private scri.commons.gui.matisse.HyperLinkLabel project8;
    private scri.commons.gui.matisse.HyperLinkLabel project9;
    private scri.commons.gui.matisse.HyperLinkLabel sampleLabel;
    // End of variables declaration//GEN-END:variables

}