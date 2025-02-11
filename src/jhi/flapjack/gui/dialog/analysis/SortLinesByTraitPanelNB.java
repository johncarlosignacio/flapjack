// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.dialog.analysis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import jhi.flapjack.data.*;
import jhi.flapjack.gui.*;
import jhi.flapjack.gui.visualization.*;

import scri.commons.gui.*;

class SortLinesByTraitPanelNB extends JPanel implements ActionListener, ListSelectionListener
{
	SortLinesByTraitTableModel model;

	public SortLinesByTraitPanelNB(GenotypePanel gPanel)
	{
		initComponents();

		setBackground((Color)UIManager.get("fjDialogBG"));

		RB.setText(bAdd, "gui.dialog.analysis.NBSortLinesByTraitPanel.bAdd");
		RB.setText(bDelete, "gui.dialog.analysis.NBSortLinesByTraitPanel.bDelete");
		RB.setText(checkAssign, "gui.dialog.analysis.NBSortLinesByTraitPanel.checkAssign");

		bAdd.addActionListener(this);
		bDelete.addActionListener(this);
		checkAssign.setSelected(Prefs.guiAssignTraits);

		updateModel(gPanel.getViewSet().getDataSet());

		checkButtonStates();
	}

	public void updateModel(DataSet dataSet)
	{
		model = new SortLinesByTraitTableModel(dataSet);

		table.setModel(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.getSelectionModel().addListSelectionListener(this);

		TableColumn c0 = table.getColumnModel().getColumn(0);
		c0.setCellEditor(new DefaultCellEditor(model.getTraitComboBox()));

		TableColumn c1 = table.getColumnModel().getColumn(1);
		c1.setPreferredWidth(60);

		// Needed when combo box editors lose focus (pressing Delete) and they
		// can end up calling setValueAt after the row has been removed
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
	}

	boolean isOK()
	{
		Prefs.guiAssignTraits = checkAssign.isSelected();

		return true;
	}

	private void checkButtonStates()
	{
		bAdd.setEnabled(model.getRowCount() > 0);

		// Only enable the delete button if something is selected and there's
		// 2 or more items in the table
		if (model.getRowCount() > 1 && table.getSelectedRow() != -1)
			bDelete.setEnabled(true);
		else
			bDelete.setEnabled(false);
	}

	public void valueChanged(ListSelectionEvent e)
	{
		if (e.getValueIsAdjusting())
			return;

		checkButtonStates();
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bAdd)
			model.addRow();
		else if (e.getSource() == bDelete)
			model.deleteRow(table.getSelectedRow());

		checkButtonStates();
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        checkAssign = new javax.swing.JCheckBox();
        bAdd = new javax.swing.JButton();
        bDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        checkAssign.setText("Auto assign these traits to the traits heatmap once the sort is completed");

        bAdd.setText("Add sort level");

        bDelete.setText("Delete sort level");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkAssign)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bDelete)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bAdd)
                    .addComponent(bDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkAssign)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAdd;
    private javax.swing.JButton bDelete;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JCheckBox checkAssign;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}