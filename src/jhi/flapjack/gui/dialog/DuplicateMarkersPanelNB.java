// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.dialog;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

import jhi.flapjack.gui.*;

import scri.commons.gui.*;

public class DuplicateMarkersPanelNB extends javax.swing.JPanel
{
	/** Creates new form NBDuplicateMarkersPanel */
	public DuplicateMarkersPanelNB(LinkedList<String> duplicates)
	{
		initComponents();

		setBackground((Color)UIManager.get("fjDialogBG"));

		// Column headers
		String[] columnNames = {
			RB.getString("gui.dialog.NBDuplicateMarkersDialog.column1"),
			RB.getString("gui.dialog.NBDuplicateMarkersDialog.column2"),
			RB.getString("gui.dialog.NBDuplicateMarkersDialog.column3") };

		// Fill the data array with the string values from the list
		Object[][] data = new Object[duplicates.size()][3];

		for (int i = 0; i < duplicates.size(); i++)
		{
			String[] str = duplicates.get(i).split("\t");
			data[i][0] = str[0];
			data[i][1] = str[1];
			data[i][2] = str[2];
		}

		table.setModel(new DefaultTableModel(data, columnNames) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
        		return false;
        }});

        RB.setText(label1, "gui.dialog.NBDuplicateMarkersDialog.label1");
		RB.setText(label2, "gui.dialog.NBDuplicateMarkersDialog.label2");
		RB.setText(checkWarn, "gui.dialog.NBDuplicateMarkersDialog.checkWarn");
	}

	boolean isCheckboxSelected()
		{ return checkWarn.isSelected(); }

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label1 = new javax.swing.JLabel();
        label2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        checkWarn = new javax.swing.JCheckBox();

        label1.setText("The imported data contains one or more duplicated markers.");

        label2.setText("Only the first instance of each duplicated marker has been imported into Flapjack.");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table.setEnabled(false);
        jScrollPane2.setViewportView(table);

        checkWarn.setText("Don't warn me about duplicate markers again");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(label1)
                        .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(checkWarn))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkWarn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkWarn;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

}