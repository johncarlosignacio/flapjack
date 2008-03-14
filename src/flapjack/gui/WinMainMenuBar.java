package flapjack.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class WinMainMenuBar extends JMenuBar
{
	private WinMain winMain;
	private int menuShortcut;

	private JMenu mFile;
	private JMenu mFileRecent;
	private JMenuItem mFileNew;
	private JMenuItem mFileOpen;
	private JMenuItem mFileSave;
	private JMenuItem mFileSaveAs;
	private JMenuItem mFileImport;
	private JMenuItem mFileExit;

	private JMenu mView;
	private JCheckBoxMenuItem mViewOverview;

	private JMenu mData;
	private JMenuItem mDataSortLines;
	private JMenu mDataColor;
	private JMenuItem mDataColorRandom;
	private JMenuItem mDataColorNucleotide;
	private JMenuItem mDataColorNucleotideSim;
	private JMenuItem mDataColorNucleotideSimGS;

	private JMenu mHelp;
	private JMenuItem mHelpAbout;

	WinMainMenuBar(WinMain winMain)
	{
		this.winMain = winMain;

		// Returns value for "CTRL" under most OSs, and the "apple" key for OS X
		menuShortcut = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

		new Actions(winMain);

		setBorderPainted(false);

		createFileMenu();
		createViewMenu();
		createDataMenu();
		createHelpMenu();
	}

	private void createFileMenu()
	{
		mFile = new JMenu(RB.getString("gui.WinMainMenuBar.mFile"));
		mFile.setMnemonic(KeyEvent.VK_F);

		mFileNew = getItem(Actions.fileNew, KeyEvent.VK_N, KeyEvent.VK_N, menuShortcut);
		mFileOpen = getItem(Actions.fileOpen, KeyEvent.VK_O, KeyEvent.VK_O, menuShortcut);
		mFileSave = getItem(Actions.fileSave, KeyEvent.VK_S, KeyEvent.VK_S, menuShortcut);
		mFileSaveAs = getItem(Actions.fileSaveAs, KeyEvent.VK_A, 0, 0);
		mFileImport = getItem(Actions.fileImport, KeyEvent.VK_I, 0, 0);
		mFileExit = getItem(Actions.fileExit, KeyEvent.VK_X, 0, 0);

		mFileRecent = new JMenu(RB.getString("gui.WinMainMenuBar.mFileRecent"));
		mFileRecent.setMnemonic(KeyEvent.VK_R);
		createRecentMenu(null);

		mFile.add(mFileNew);
		mFile.add(mFileOpen);
		mFile.addSeparator();
		mFile.add(mFileSave);
		mFile.add(mFileSaveAs);
		mFile.addSeparator();
		mFile.add(mFileImport);
		mFile.addSeparator();
		mFile.add(mFileRecent);
		mFile.addSeparator();
		mFile.add(mFileExit);

		add(mFile);
	}

	private void createViewMenu()
	{
		mView = new JMenu(RB.getString("gui.WinMainMenuBar.mView"));
		mView.setMnemonic(KeyEvent.VK_V);

		mViewOverview = getCheckedItem(Actions.viewOverview, KeyEvent.VK_O, KeyEvent.VK_F7, 0, Prefs.guiOverviewDialog);

		mView.add(mViewOverview);

		add(mView);
	}

	private void createDataMenu()
	{
		mData = new JMenu(RB.getString("gui.WinMainMenuBar.mData"));
		mData.setMnemonic(KeyEvent.VK_D);
		mDataColor = new JMenu(RB.getString("gui.WinMainMenuBar.mDataColor"));
		mDataColor.setMnemonic(KeyEvent.VK_C);

		mDataSortLines = getItem(Actions.dataSortLines, KeyEvent.VK_S, 0, 0);
		mDataColorRandom = getItem(Actions.dataColorRandom, KeyEvent.VK_R, 0, 0);
		mDataColorNucleotide = getItem(Actions.dataColorNucleotide, KeyEvent.VK_N, 0, 0);
		mDataColorNucleotideSim = getItem(Actions.dataColorNucleotideSim, KeyEvent.VK_2, 0, 0);
		mDataColorNucleotideSimGS = getItem(Actions.dataColorNucleotideSimGS, KeyEvent.VK_G, 0, 0);

		mDataColor.add(mDataColorNucleotide);
		mDataColor.add(mDataColorNucleotideSim);
		mDataColor.add(mDataColorNucleotideSimGS);
		mDataColor.addSeparator();
		mDataColor.add(mDataColorRandom);

		mData.add(mDataSortLines);
		mData.addSeparator();
		mData.add(mDataColor);

		add(mData);
	}

	private void createHelpMenu()
	{
		mHelp = new JMenu(RB.getString("gui.WinMainMenuBar.mHelp"));
		mHelp.setMnemonic(KeyEvent.VK_H);

		mHelpAbout = getItem(Actions.helpAbout, KeyEvent.VK_A, 0, 0);

		mHelp.add(mHelpAbout);

		add(mHelp);
	}

	public static JMenuItem getItem(Action action, int mnemonic, int accelerator, int keymask)
	{
		JMenuItem item = new JMenuItem(action);
		item.setMnemonic(mnemonic);

		if (accelerator != 0)
			item.setAccelerator(KeyStroke.getKeyStroke(accelerator, keymask));

		return item;
	}

	public static JCheckBoxMenuItem getCheckedItem(Action action, int mnemonic, int accelerator, int keymask, boolean state)
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem(action);
		item.setMnemonic(mnemonic);
		item.setState(state);

		if (accelerator != 0)
			item.setAccelerator(KeyStroke.getKeyStroke(accelerator, keymask));

		return item;
	}

	public static JRadioButtonMenuItem getRadioItem(Action action, int mnemonic, int accelerator, int keymask)
	{
		JRadioButtonMenuItem item = new JRadioButtonMenuItem(action);
		item.setMnemonic(mnemonic);

		if (accelerator != 0)
			item.setAccelerator(KeyStroke.getKeyStroke(accelerator, keymask));

		return item;
	}

	// Maintains and creates the Recent Projects file menu, adding new entries
	// as previously unseen projects are opened or saved, and ensuring that:
	//   a) the most recently accessed file is always at the start of the list
	//   b) the list never grows bigger than four entries
	void createRecentMenu(File file)
	{
		// Begin by making a list of the recent file locations
		LinkedList<String> entries = new LinkedList<String>();
		entries.add(Prefs.guiRecentProject1);
		entries.add(Prefs.guiRecentProject2);
		entries.add(Prefs.guiRecentProject3);
		entries.add(Prefs.guiRecentProject4);

		// See if any of the items on that list match the file being accessed,
		// moving (or adding) the entry to the first location
		if (file != null)
		{
			int location = -1;
			for (int i = 0; i < entries.size(); i++)
				if (entries.get(i) != null)
					if (entries.get(i).equals(file.getPath()))
						location = i;

			if (location != -1)
				entries.remove(location);

			entries.addFirst(file.getPath());

			if (entries.size() > 4)
				entries.removeLast();
		}

		// The menu can then be built up, one item per entry
		mFileRecent.removeAll();

		int vk = 0;
		for (final String entry: entries)
		{
			if (entry != null)
			{
				JMenuItem item = new JMenuItem((++vk) + " " + entry);
				item.setMnemonic(KeyEvent.VK_0 + vk);
				item.addActionListener(new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						winMain.fileOpen(new File(entry));
					}
				});

				mFileRecent.add(item);
			}
		}

		mFileRecent.setEnabled(mFileRecent.getItemCount() > 0);

		// Finally, update the preference strings with the new ordering
		Prefs.guiRecentProject1 = entries.get(0);
		Prefs.guiRecentProject2 = entries.get(1);
		Prefs.guiRecentProject3 = entries.get(2);
		Prefs.guiRecentProject4 = entries.get(3);
	}
}