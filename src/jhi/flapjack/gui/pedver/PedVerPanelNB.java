package jhi.flapjack.gui.pedver;

import javax.swing.*;

import jhi.flapjack.gui.table.*;

import scri.commons.gui.*;

class PedVerPanelNB extends JPanel
{
	PedVerPanelNB(PedVerPanel panel)
	{
		initComponents();

		bFilter.addActionListener(panel);
		bFilter.setIcon(Icons.getIcon("FILTER"));

		bSort.addActionListener(panel);
		bSort.setIcon(Icons.getIcon("SORT"));

		bExport.addActionListener(panel);
		bExport.setIcon(Icons.getIcon("EXPORTTRAITS"));

//		lblF1MarkerCount.setText("Expected F1 marker count: " + results.getF1MarkerCount());
//		lblF1HetCount.setText("Expected F1 heterozygous allele count: " + results.getF1HeterozygousCount());
//		lblF1HetPercentage.setText("Expected F1 heterozygous allele percentage: " + results.getF1PercentHeterozygous());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new LineDataTable();
        bExport = new javax.swing.JButton();
        bSort = new javax.swing.JButton();
        bFilter = new javax.swing.JButton();
        lblF1MarkerCount = new javax.swing.JLabel();
        lblF1HetCount = new javax.swing.JLabel();
        lblF1HetPercentage = new javax.swing.JLabel();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {

            }
        ));
        jScrollPane1.setViewportView(table);

        bExport.setText("Export");

        bSort.setText("Sort...");

        bFilter.setText("Filter...");

        lblF1MarkerCount.setText("Expected F1 marker count: {0}");

        lblF1HetCount.setText("Expected F1 heterozygous allele count: {0}");

        lblF1HetPercentage.setText("Expected F1 heterozygous allele percentage: {0}");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bFilter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bSort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bExport))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblF1MarkerCount)
                            .addComponent(lblF1HetCount)
                            .addComponent(lblF1HetPercentage))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblF1MarkerCount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblF1HetCount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblF1HetPercentage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bExport)
                    .addComponent(bSort)
                    .addComponent(bFilter))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton bExport;
    javax.swing.JButton bFilter;
    javax.swing.JButton bSort;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblF1HetCount;
    private javax.swing.JLabel lblF1HetPercentage;
    private javax.swing.JLabel lblF1MarkerCount;
    javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}