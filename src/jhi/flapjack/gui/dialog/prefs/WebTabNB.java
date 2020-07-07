// Copyright 2009-2020 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.dialog.prefs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jhi.flapjack.gui.*;

import scri.commons.gui.*;

class WebTabNB extends JPanel implements IPrefsTab, ActionListener
{
	public WebTabNB()
    {
        initComponents();

        setBackground((Color)UIManager.get("fjDialogBG"));
        panel.setBackground((Color)UIManager.get("fjDialogBG"));

		panel.setBorder(BorderFactory.createTitledBorder(RB.getString("gui.dialog.prefs.NBWebPanel.panelTitle")));
		RB.setText(checkProxy, "gui.dialog.prefs.NBWebPanel.checkProxy");
		RB.setText(labelAddress, "gui.dialog.prefs.NBWebPanel.labelAddress");
		RB.setText(labelPort, "gui.dialog.prefs.NBWebPanel.labelPort");
		RB.setText(labelUsername, "gui.dialog.prefs.NBWebPanel.labelUsername");
		RB.setText(labelPassword, "gui.dialog.prefs.NBWebPanel.labelPassword");

		checkProxy.addActionListener(this);

		initSettings();
		actionPerformed(null);
    }

    private void initSettings()
    {
    	checkProxy.setSelected(Prefs.proxyUse);
		checkSocks.setSelected(Prefs.proxySocks);

		proxyAddress.setText(Prefs.proxyAddress);
		proxyPort.setValue(Prefs.proxyPort);
		proxyUsername.setText(Prefs.proxyUsername);
		proxyPassword.setText(Prefs.proxyPassword);

/*		try
		{
			java.net.URL url = new java.net.URL("http://www.bbc.co.uk");
			java.net.HttpURLConnection c = (java.net.HttpURLConnection) url.openConnection();

			int code = c.getResponseCode();
			c.disconnect();

			System.out.println("response: " + code);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
*/
    }

	public void applySettings()
	{
		Prefs.proxyUse = checkProxy.isSelected();
		Prefs.proxySocks = checkSocks.isSelected();

		Prefs.proxyAddress = proxyAddress.getText();
		Prefs.proxyPort = (Integer) proxyPort.getValue();
		Prefs.proxyUsername = proxyUsername.getText();
		Prefs.proxyPassword = new String(proxyPassword.getPassword());

		Flapjack.setProxy();
	}

	public void setDefaults()
	{
	}

	public void actionPerformed(ActionEvent e)
	{
		boolean enabled = checkProxy.isSelected();

		checkSocks.setEnabled(enabled);
		labelAddress.setEnabled(enabled);
		proxyAddress.setEnabled(enabled);
		labelPort.setEnabled(enabled);
		proxyPort.setEnabled(enabled);
		labelUsername.setEnabled(enabled);
		proxyUsername.setEnabled(enabled);
		labelPassword.setEnabled(enabled);
		proxyPassword.setEnabled(enabled);
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        panel = new javax.swing.JPanel();
        checkProxy = new javax.swing.JCheckBox();
        labelAddress = new javax.swing.JLabel();
        proxyAddress = new javax.swing.JTextField();
        labelUsername = new javax.swing.JLabel();
        proxyUsername = new javax.swing.JTextField();
        labelPort = new javax.swing.JLabel();
        proxyPort = new javax.swing.JSpinner();
        labelPassword = new javax.swing.JLabel();
        proxyPassword = new javax.swing.JPasswordField();
        checkSocks = new javax.swing.JCheckBox();

        panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Internet settings:"));

        checkProxy.setText("Use a proxy server for internet connectivity");

        labelAddress.setLabelFor(proxyAddress);
        labelAddress.setText("Address:");

        labelUsername.setLabelFor(proxyUsername);
        labelUsername.setText("Username:");

        labelPort.setLabelFor(proxyPort);
        labelPort.setText("Port number:");

        proxyPort.setModel(new javax.swing.SpinnerNumberModel(8080, 1, 65535, 1));

        labelPassword.setLabelFor(proxyPassword);
        labelPassword.setText("Password:");

        checkSocks.setText("Proxy is SOCKS rather than HTTP/S");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelPassword)
                            .addComponent(labelUsername)
                            .addComponent(labelAddress))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(proxyUsername)
                            .addComponent(proxyAddress)
                            .addComponent(proxyPassword))
                        .addGap(18, 18, 18)
                        .addComponent(labelPort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proxyPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(checkSocks)
                            .addComponent(checkProxy))))
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkProxy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkSocks)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAddress)
                    .addComponent(proxyAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proxyPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPort))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUsername)
                    .addComponent(proxyUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPassword)
                    .addComponent(proxyPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkProxy;
    private javax.swing.JCheckBox checkSocks;
    private javax.swing.JLabel labelAddress;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelPort;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JPanel panel;
    private javax.swing.JTextField proxyAddress;
    private javax.swing.JPasswordField proxyPassword;
    private javax.swing.JSpinner proxyPort;
    private javax.swing.JTextField proxyUsername;
    // End of variables declaration//GEN-END:variables
}