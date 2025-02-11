// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.navpanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jhi.flapjack.gui.*;

import scri.commons.gui.*;
import scri.commons.gui.matisse.*;

/**
* Panel used for display when no other tree components have been selected.
*/
public class IntroPanel extends JPanel
{
   public IntroPanel()
   {
	   setLayout(new BorderLayout());
	   setBorder(BorderFactory.createLineBorder(new Color(119, 126, 143), 3));

	   JPanel panel = new LogoPanel(new BorderLayout(0, 0));

	   JPanel welcomePanel = new JPanel(new BorderLayout());
	   welcomePanel.setOpaque(false);
	   welcomePanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 8, 2));
	   welcomePanel.add(new TitlePanel3(
		   RB.getString("gui.navpanel.NBStartWelcomePanel.title")), BorderLayout.NORTH);
	   welcomePanel.add(new StartPanelWelcomeNB());

	   JPanel filePanel = new JPanel(new BorderLayout());
	   filePanel.setOpaque(false);
	   filePanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
	   filePanel.add(new TitlePanel3(
		   RB.getString("gui.navpanel.NBStartFilePanel.title")), BorderLayout.NORTH);
	   filePanel.add(new StartPanelFileNB());

	   JPanel helpPanel = new JPanel(new BorderLayout());
	   helpPanel.setOpaque(false);
	   helpPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
	   helpPanel.add(new TitlePanel3(
		   RB.getString("gui.navpanel.NBStartHelpPanel.title")), BorderLayout.NORTH);
	   helpPanel.add(new StartPanelHelpNB());

	   JPanel citationPanel = new JPanel(new BorderLayout());
	   citationPanel.setOpaque(false);
	   citationPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
	   citationPanel.add(new TitlePanel3(
		   RB.getString("gui.navpanel.StartPanelPublicationNB.title")), BorderLayout.NORTH);
	   citationPanel.add(new StartPanelPublicationNB());

	   JPanel huttonPanel = new JPanel(new BorderLayout());
	   huttonPanel.setOpaque(false);
	   huttonPanel.add(citationPanel);
	   JPanel logoPanel = new JPanel(new BorderLayout());
	   logoPanel.setOpaque(false);
	   logoPanel.add(getHuttonLabel(), BorderLayout.WEST);
	   huttonPanel.add(logoPanel, BorderLayout.EAST);


	   JPanel centrePanel = new JPanel(new GridLayout(1, 2, 0, 0));
	   centrePanel.setOpaque(false);
	   centrePanel.add(filePanel);
	   centrePanel.add(helpPanel);

	   panel.add(welcomePanel, BorderLayout.NORTH);
	   panel.add(centrePanel, BorderLayout.CENTER);
	   panel.add(huttonPanel, BorderLayout.SOUTH);

//			add(new JScrollPane(panel));
	   add(panel);
   }

	private static JLabel getHuttonLabel()
	{
	   HyperLinkLabel huttonLabel = new HyperLinkLabel();
	   huttonLabel.setIcon(Icons.getIcon("HUTTON"));
	   huttonLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

	   huttonLabel.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   FlapjackUtils.visitURL("http://www.hutton.ac.uk");
		   }
	   });

	   return huttonLabel;
	}

	private static class LogoPanel extends JPanel
	{
	   private static ImageIcon logo = Icons.getIcon("HUTTONLARGE");

	   LogoPanel(LayoutManager lm)
	   {
		   super(lm);
		   setBackground(Color.white);
	   }

	   public void paintComponent(Graphics graphics)
	   {
		   super.paintComponent(graphics);

		   Graphics2D g = (Graphics2D) graphics;

		   int w = getWidth();
		   int h = getHeight();

		   g.drawImage(logo.getImage(), 0, 0, w, h, null);
	   }
   }
}