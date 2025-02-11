// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import jhi.flapjack.io.*;

import scri.commons.gui.*;

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
	private JMenuItem mFileOptimize;
	private JMenuItem mFileImport;
	private JMenuItem mFileImportBrapi;
	private JMenuItem mFileExport;
	private JMenuItem mFileExit;

	private JMenu mEdit;
	public static JMenuItem mEditUndo;
	public static JMenuItem mEditRedo;
	private JRadioButtonMenuItem mEditModeNavigation;
	private JRadioButtonMenuItem mEditModeMarker;
	private JRadioButtonMenuItem mEditModeLine;
	private JMenu mEditSelectMarkers;
	private JMenuItem mEditSelectMarkersAll;
	private JMenuItem mEditSelectMarkersNone;
	private JMenuItem mEditSelectMarkersInvert;
	private JMenuItem mEditSelectMarkersMonomorphic;
	private JMenuItem mEditSelectMarkersImport;
	private JMenuItem mEditHideMarkers;
	private JMenu mEditFilterMarkers;
	private JMenuItem mEditFilterMissingMarkers;
	private JMenuItem mEditFilterMissingMarkersByLine;
	private JMenuItem mEditFilterHeterozygousMarkers;
	private JMenuItem mEditFilterHeterozygousMarkersByLine;
	private JMenuItem mEditFilterMonomorphicMarkers;
	private JMenu mEditSelectLines;
	private JMenuItem mEditSelectLinesAll;
	private JMenuItem mEditSelectLinesNone;
	private JMenuItem mEditSelectLinesInvert;
	private JMenuItem mEditSelectLinesImport;
	private JMenuItem mEditHideLines;
	private JMenu mEditFilterLines;
	private JMenuItem mEditFilterMissingLines;
	private JMenuItem mEditFilterHeterozygousLines;
	private JMenuItem mEditFilterHomozygousLines;
	private JMenuItem mEditCustomMap;

	private JMenu mView;
	private JMenuItem mViewNewView;
	private JMenuItem mViewRenameView;
	private JMenuItem mViewDeleteView;
	private JMenuItem mViewToggleCanvas;
	public static JCheckBoxMenuItem mViewOverview;
	private JMenuItem mViewPageLeft;
	private JMenuItem mViewPageRight;
	private JRadioButtonMenuItem mViewGenotypes;
	private JRadioButtonMenuItem mViewChromosomes;

	private JMenu mViz;
	private JMenuItem mVizExportImage;
	private JMenuItem mVizExportData;
	private JMenuItem mVizCreatePedigree;
	private JMenu mVizColor;
	private JMenuItem mVizColorCustomize;
	private JRadioButtonMenuItem mVizColorRandom;
	private JRadioButtonMenuItem mVizColorRandomWSP;
	private JRadioButtonMenuItem mVizColorNucleotide;
	private JRadioButtonMenuItem mVizColorNucleotide01;
	private JRadioButtonMenuItem mVizColorABHData;
	private JRadioButtonMenuItem mVizColorLineSim;
	private JRadioButtonMenuItem mVizColorLineSimExact;
	private JRadioButtonMenuItem mVizColorMarkerSim;
	private JRadioButtonMenuItem mVizColorSimple2Color;
	private JRadioButtonMenuItem mVizColorAlleleFreq;
	private JRadioButtonMenuItem mVizColorBinned;
	private JRadioButtonMenuItem mVizColorMagic;
	private JRadioButtonMenuItem mVizColorParentDual;
	private JRadioButtonMenuItem mvizColorSimilarityToEitherParent;
	private JRadioButtonMenuItem mVizColorLineSimAny;
	private JRadioButtonMenuItem mVizColorFavAllele;
	private JMenu mVizScaling;
	private JCheckBoxMenuItem mVizScalingLocal;
	private JCheckBoxMenuItem mVizScalingGlobal;
	private JCheckBoxMenuItem mVizScalingClassic;
	private JCheckBoxMenuItem mVizOverlayGenotypes;
	private JCheckBoxMenuItem mVizDisableGradients;
	private JMenu mVizHighlight;
	private JCheckBoxMenuItem mVizHighlightHtZ;
	private JCheckBoxMenuItem mVizHighlightHoZ;
	private JCheckBoxMenuItem mVizHighlightGaps;
	private JMenu mDataTraits;
	private JMenuItem mDataSelectTraits;
	private JMenuItem mDataSelectTextTraits;

	private JMenu mAnalysis;
	private JMenu mAlysSortLines;
	private JMenu mAlysStatsPedVer;
	private JMenuItem mAlysSortLinesBySimilarity;
	private JMenuItem mAlysSortLinesByTrait;
	private JMenuItem mAlysSortLinesByExternal;
	private JMenuItem mAlysSortLinesAlphabetically;
	private JMenuItem mAlysSimMatrix;
	private JMenuItem mAlysDendrogram;
	private JMenuItem mAlysPCoA;
	private JMenuItem mAlysMABC;
	private JMenuItem mAlysPedVer;
	private JMenuItem mAlysPedVerLines;
	private JMenuItem mAlysForwardBreeding;

	private JMenu mData;
	private JMenuItem malysFilterQTLs;
	private JMenuItem mDataFind;
	private JMenuItem mDataStatistics;
	private JMenu mDataDB;
	private JMenuItem mDataDBLineName;
	private JMenuItem mDataDBMarkerName;
	private JMenuItem mDataDBSettings;
	private JMenuItem mDataRenameDataSet;
	private JMenuItem mDataDeleteDataSet;
	private JMenuItem mDataSelectGraph;

	private JMenu mWnd;
	private JMenuItem mWndMinimize;
	private JMenuItem mWndZoom;
	static  JCheckBoxMenuItem mWndFlapjack;

	private JMenu mHelp;
	private JMenuItem mHelpContents;
	private JMenuItem mHelpPrefs;
	private JMenuItem mHelpUpdate;
	private JMenuItem mHelpAbout;

	WinMainMenuBar(WinMain winMain)
	{
		this.winMain = winMain;

		// Returns value for "CTRL" under most OSs, and the "apple" key for OS X
		menuShortcut = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

		new Actions(winMain);

		setBorderPainted(false);

		createFileMenu();
		createEditMenu();
		createViewMenu();
		createVizMenu();
		createAnalysisMenu();
		createDataMenu();
		createWndMenu();
		createHelpMenu();
	}

	private void createFileMenu()
	{
		mFile = new JMenu(RB.getString("gui.WinMainMenuBar.mFile"));
		RB.setMnemonic(mFile, "gui.WinMainMenuBar.mFile");

		mFileNew = getItem(Actions.fileNew, "gui.Actions.fileNew", KeyEvent.VK_N, menuShortcut);
		mFileOpen = getItem(Actions.fileOpen, "gui.Actions.fileOpen", KeyEvent.VK_O, menuShortcut);
		mFileSave = getItem(Actions.fileSave, "gui.Actions.fileSave", KeyEvent.VK_S, menuShortcut);
		mFileSaveAs = getItem(Actions.fileSaveAs, "gui.Actions.fileSaveAs");
		mFileOptimize = getItem(Actions.fileOptimize, "gui.Actions.fileOptimize");
		mFileImport = getItem(Actions.fileImport, "gui.Actions.fileImport");
		mFileImportBrapi = getItem(Actions.fileImportBrapi, "gui.Actions.fileImportBrapi");
		mFileExport = getItem(Actions.fileExport, "gui.Actions.fileExport");
		mFileExit = getItem(Actions.fileExit, "gui.Actions.fileExit");

		mFileRecent = new JMenu(RB.getString("gui.WinMainMenuBar.mFileRecent"));
		RB.setMnemonic(mFileRecent, "gui.WinMainMenuBar.mFileRecent");
		createRecentMenu(null);

		mFile.add(mFileNew);
		mFile.add(mFileOpen);
		mFile.addSeparator();
		mFile.add(mFileSave);
		mFile.add(mFileSaveAs);
		mFile.add(mFileOptimize);
		mFile.addSeparator();
		mFile.add(mFileImport);
		mFile.add(mFileImportBrapi);
		mFile.add(mFileExport);
		mFile.addSeparator();
		mFile.add(mFileRecent);
		// We don't add these options to OS X as they are auto-added by Apple
		if (SystemUtils.isMacOS() == false)
		{
			mFile.addSeparator();
			mFile.add(mFileExit);
		}

		add(mFile);
	}

	private void createEditMenu()
	{
		mEdit = new JMenu(RB.getString("gui.WinMainMenuBar.mEdit"));
		RB.setMnemonic(mEdit, "gui.WinMainMenuBar.mEdit");

		mEditSelectMarkers = new JMenu(RB.getString("gui.WinMainMenuBar.mEditSelectMarkers"));
		RB.setMnemonic(mEditSelectMarkers, "gui.WinMainMenuBar.mEditSelectMarkers");
		mEditSelectLines = new JMenu(RB.getString("gui.WinMainMenuBar.mEditSelectLines"));
		RB.setMnemonic(mEditSelectLines, "gui.WinMainMenuBar.mEditSelectLines");
		mEditFilterMarkers = new JMenu(RB.getString("gui.WinMainMenuBar.mEditFilterMarkers"));
		RB.setMnemonic(mEditFilterMarkers, "gui.WinMainMenuBar.mEditFilterMarkers");
		mEditFilterLines = new JMenu(RB.getString("gui.WinMainMenuBar.mEditFilterLines"));
		RB.setMnemonic(mEditFilterLines, "gui.WinMainMenuBar.mEditFilterLines");

		mEditUndo = getItem(Actions.editUndo, "gui.Actions.editUndo", KeyEvent.VK_Z, menuShortcut);
		mEditRedo = getItem(Actions.editRedo, "gui.Actions.editRedo", KeyEvent.VK_Y, menuShortcut);
		mEditModeNavigation = getRadioItem(Actions.editModeNavigation, "gui.Actions.editModeNavigation",
			KeyEvent.VK_1, InputEvent.ALT_DOWN_MASK);
		mEditModeMarker = getRadioItem(Actions.editModeMarker, "gui.Actions.editModeMarker",
			KeyEvent.VK_2, InputEvent.ALT_DOWN_MASK);
		mEditModeLine = getRadioItem(Actions.editModeLine, "gui.Actions.editModeLine",
			KeyEvent.VK_3, InputEvent.ALT_DOWN_MASK);
		mEditSelectMarkersAll = getItem(Actions.editSelectMarkersAll, "gui.Actions.editSelectMarkersAll");
		mEditSelectMarkersNone = getItem(Actions.editSelectMarkersNone, "gui.Actions.editSelectMarkersNone");
		mEditSelectMarkersInvert = getItem(Actions.editSelectMarkersInvert, "gui.Actions.editSelectMarkersInvert");
		mEditSelectMarkersMonomorphic = getItem(Actions.editSelectMarkersMonomorphic, "gui.Actions.editSelectMarkersMonomorphic");
		mEditSelectMarkersImport = getItem(Actions.editSelectMarkersImport, "gui.Actions.editSelectMarkersImport");
		mEditHideMarkers = getItem(Actions.editHideMarkers, "gui.Actions.editHideMarkers");
		mEditFilterMissingMarkers = getItem(Actions.editFilterMissingMarkers, "gui.Actions.editFilterMissingMarkers");
		mEditFilterMissingMarkersByLine = getItem(Actions.editFilterMissingMarkersByLine, "gui.Actions.editFilterMissingMarkersByLine");
		mEditFilterHeterozygousMarkers = getItem(Actions.editFilterHeterozygousMarkers, "gui.Actions.editFilterHeterozygousMarkers");
		mEditFilterHeterozygousMarkersByLine = getItem(Actions.editFilterHeterozygousMarkersByLine, "gui.Actions.editFilterHeterozygousMarkersByLine");
		mEditFilterMonomorphicMarkers = getItem(Actions.editFilterMonomorphicMarkers, "gui.Actions.editFilterMonomorphicMarkers");
		mEditSelectLinesAll = getItem(Actions.editSelectLinesAll, "gui.Actions.editSelectLinesAll");
		mEditSelectLinesNone = getItem(Actions.editSelectLinesNone, "gui.Actions.editSelectLinesNone");
		mEditSelectLinesInvert = getItem(Actions.editSelectLinesInvert, "gui.Actions.editSelectLinesInvert");
		mEditSelectLinesImport = getItem(Actions.editSelectLinesImport, "gui.Actions.editSelectLinesImport");
		mEditHideLines = getItem(Actions.editHideLines, "gui.Actions.editHideLines");
		mEditFilterMissingLines = getItem(Actions.editFilterMissingLines, "gui.Actions.editFilterMissingLines");
		mEditFilterHeterozygousLines = getItem(Actions.editFilterHeterozygousLines, "gui.Actions.editFilterHeterozygousLines");
		mEditFilterHomozygousLines = getItem(Actions.editFilterHomozygousLines, "gui.Actions.editFilterHomozygousLines");
		mEditCustomMap = getItem(Actions.editCustomMap, "gui.Actions.editCustomMap");

		mEditSelectMarkers.add(mEditSelectMarkersAll);
		mEditSelectMarkers.add(mEditSelectMarkersNone);
		mEditSelectMarkers.add(mEditSelectMarkersInvert);
		mEditSelectMarkers.addSeparator();
		mEditSelectMarkers.add(mEditSelectMarkersMonomorphic);
		mEditSelectMarkers.addSeparator();
		mEditSelectMarkers.add(mEditSelectMarkersImport);
		mEditSelectLines.add(mEditSelectLinesAll);
		mEditSelectLines.add(mEditSelectLinesNone);
		mEditSelectLines.add(mEditSelectLinesInvert);
		mEditSelectLines.addSeparator();
		mEditSelectLines.add(mEditSelectLinesImport);

		mEditFilterMarkers.add(mEditFilterMissingMarkers);
		mEditFilterMarkers.add(mEditFilterMissingMarkersByLine);
		mEditFilterMarkers.addSeparator();
		mEditFilterMarkers.add(mEditFilterHeterozygousMarkers);
		mEditFilterMarkers.add(mEditFilterHeterozygousMarkersByLine);
		mEditFilterMarkers.addSeparator();
		mEditFilterMarkers.add(mEditFilterMonomorphicMarkers);

		mEditFilterLines.add(mEditFilterMissingLines);
		mEditFilterLines.add(mEditFilterHeterozygousLines);
		mEditFilterLines.add(mEditFilterHomozygousLines);

		mEdit.add(mEditUndo);
		mEdit.add(mEditRedo);
		mEdit.addSeparator();
		mEdit.add(mEditModeNavigation);
		mEdit.add(mEditModeMarker);
		mEdit.add(mEditModeLine);
		mEdit.addSeparator();
		mEdit.add(mEditSelectMarkers);
		mEdit.add(mEditHideMarkers);
		mEdit.add(mEditFilterMarkers);
		mEdit.addSeparator();
		mEdit.add(mEditSelectLines);
		mEdit.add(mEditHideLines);
		mEdit.add(mEditFilterLines);
		mEdit.addSeparator();
		mEdit.add(mEditCustomMap);

		add(mEdit);
	}

	private void createViewMenu()
	{
		mView = new JMenu(RB.getString("gui.WinMainMenuBar.mView"));
		RB.setMnemonic(mView, "gui.WinMainMenuBar.mView");

		mViewNewView = getItem(Actions.viewNewView, "gui.Actions.viewNewView");
		mViewRenameView = getItem(Actions.viewRenameView, "gui.Actions.viewRenameView");
		mViewDeleteView = getItem(Actions.viewDeleteView, "gui.Actions.viewDeleteView");
		mViewToggleCanvas = getItem(Actions.viewToggleCanvas, "gui.Actions.viewToggleCanvas");
		mViewOverview = getCheckedItem(Actions.viewOverview, "gui.Actions.viewOverview", KeyEvent.VK_F7, 0);
		mViewPageLeft = getItem(Actions.viewPageLeft, "gui.Actions.viewPageLeft");
		mViewPageRight = getItem(Actions.viewPageRight, "gui.Actions.viewPageRight");
		mViewGenotypes = getRadioItem(Actions.viewGenotypes, "gui.Actions.viewGenotypes",
			KeyEvent.VK_5, InputEvent.ALT_DOWN_MASK);
		mViewChromosomes = getRadioItem(Actions.viewChromosomes, "gui.Actions.viewChromosomes",
			KeyEvent.VK_6, InputEvent.ALT_DOWN_MASK);

		mView.add(mViewNewView);
		mView.add(mViewRenameView);
		mView.add(mViewDeleteView);
		mView.addSeparator();
		mView.add(mViewGenotypes);
		mView.add(mViewChromosomes);
		mView.addSeparator();
		mView.add(mViewPageLeft);
		mView.add(mViewPageRight);
		mView.addSeparator();
		mView.add(mViewToggleCanvas);
		mView.add(mViewOverview);

		add(mView);
	}

	private void createVizMenu()
	{
		mViz = new JMenu(RB.getString("gui.WinMainMenuBar.mViz"));
		RB.setMnemonic(mViz, "gui.WinMainMenuBar.mViz");

		mVizColor = new JMenu(RB.getString("gui.WinMainMenuBar.mVizColor"));
		mVizColor.setIcon(Actions.getIcon("COLORS"));
		RB.setMnemonic(mVizColor, "gui.WinMainMenuBar.mVizColor");
		winMain.mViz.handleColorMenu(mVizColor);

		mVizScaling = new JMenu(RB.getString("gui.WinMainMenuBar.mVizScaling"));
		RB.setMnemonic(mVizScaling, "gui.WinMainMenuBar.mVizScaling");

		mVizHighlight = new JMenu(RB.getString("gui.WinMainMenuBar.mVizHighlight"));
		RB.setMnemonic(mVizHighlight, "gui.WinMainMenuBar.mVizHighlight");

		mVizExportImage = getItem(Actions.vizExportImage, "gui.Actions.vizExportImage");
		mVizExportData = getItem(Actions.vizExportData, "gui.Actions.vizExportData");
		mVizCreatePedigree = getItem(Actions.vizCreatePedigree, "gui.Actions.vizCreatePedigree");
		mVizColorCustomize = getItem(Actions.vizColorCustomize, "gui.Actions.vizColorCustomize");
		mVizColorRandom = getRadioItem(Actions.vizColorRandom, "gui.Actions.vizColorRandom");
		mVizColorRandomWSP = getRadioItem(Actions.vizColorRandomWSP, "gui.Actions.vizColorRandomWSP");
		mVizColorNucleotide = getRadioItem(Actions.vizColorNucleotide, "gui.Actions.vizColorNucleotide");
		mVizColorNucleotide01 = getRadioItem(Actions.vizColorNucleotide01, "gui.Actions.vizColorNucleotide01");
		mVizColorABHData = getRadioItem(Actions.vizColorABHData, "gui.Actions.vizColorABHData");
		mVizColorLineSim = getRadioItem(Actions.vizColorLineSim, "gui.Actions.vizColorLineSim");
		mVizColorLineSimExact = getRadioItem(Actions.vizColorLineSimExact, "gui.Actions.vizColorLineSimExact");
		mVizColorMarkerSim = getRadioItem(Actions.vizColorMarkerSim, "gui.Actions.vizColorMarkerSim");
		mVizColorSimple2Color = getRadioItem(Actions.vizColorSimple2Color, "gui.Actions.vizColorSimple2Color");
		mVizColorAlleleFreq = getRadioItem(Actions.vizColorAlleleFreq, "gui.Actions.vizColorAlleleFreq");
		mVizColorBinned = getRadioItem(Actions.vizColorBinned, "gui.Actions.vizColorBinned");
		mVizColorMagic = getRadioItem(Actions.vizColorMagic, "gui.Actions.vizColorMagic");
		mVizColorParentDual = getRadioItem(Actions.vizColorParentDual, "gui.Actions.vizColorSimilarityToEachParent");
		mvizColorSimilarityToEitherParent = getRadioItem(Actions.vizColorSimilarityToEitherParent, "gui.Actions.vizColorSimilarityToEitherParent");
		mVizColorLineSimAny = getRadioItem(Actions.vizColorLineSimAny, "gui.Actions.vizColorLineSimAny");
		mVizColorFavAllele = getRadioItem(Actions.vizColorFavAllele, "gui.Actions.vizColorFavAllele");
		mVizScalingLocal = getCheckedItem(Actions.vizScalingLocal, "gui.Actions.vizScalingLocal");
		mVizScalingGlobal = getCheckedItem(Actions.vizScalingGlobal, "gui.Actions.vizScalingGlobal");
		mVizScalingClassic = getCheckedItem(Actions.vizScalingClassic, "gui.Actions.vizScalingClassic");
		mVizOverlayGenotypes = getCheckedItem(Actions.vizOverlayGenotypes, "gui.Actions.vizOverlayGenotypes",
			KeyEvent.VK_G, menuShortcut);
		mVizDisableGradients = getCheckedItem(Actions.vizDisableGradients, "gui.Actions.vizDisableGradients");
		mVizHighlightHtZ = getCheckedItem(Actions.vizHighlightHtZ, "gui.Actions.vizHighlightHtZ");
		mVizHighlightHoZ = getCheckedItem(Actions.vizHighlightHoZ, "gui.Actions.vizHighlightHoZ");
		mVizHighlightGaps = getCheckedItem(Actions.vizHighlightGaps, "gui.Actions.vizHighlightGaps");

		mVizColor.add(mVizColorNucleotide);
		mVizColor.add(mVizColorNucleotide01);
//		mVizColor.add(mVizColorABHData);
		mVizColor.add(mVizColorSimple2Color);
		mVizColor.add(mVizColorLineSim);
		mVizColor.add(mVizColorLineSimExact);
		mVizColor.add(mVizColorLineSimAny);
		mVizColor.add(mVizColorMarkerSim);
		mVizColor.add(mVizColorParentDual);
		mVizColor.add(mvizColorSimilarityToEitherParent);
		mVizColor.add(mVizColorFavAllele);
		mVizColor.add(mVizColorAlleleFreq);
		mVizColor.add(mVizColorMagic);
		mVizColor.add(mVizColorBinned);
		mVizColor.addSeparator();
		mVizColor.add(mVizColorRandom);
		mVizColor.add(mVizColorRandomWSP);
		mVizColor.addSeparator();
		mVizColor.add(mVizColorCustomize);

		mVizHighlight.add(mVizHighlightHtZ);
		mVizHighlight.add(mVizHighlightHoZ);
		mVizHighlight.add(mVizHighlightGaps);

		mVizScaling.add(mVizScalingGlobal);
		mVizScaling.add(mVizScalingLocal);
		mVizScaling.add(mVizScalingClassic);

		mViz.add(mVizExportImage);
		mViz.add(mVizExportData);
		mViz.addSeparator();
//		mViz.add(mVizCreatePedigree);
//		mViz.addSeparator();
		mViz.add(mVizScaling);
		mViz.addSeparator();
		mViz.add(mVizColor);
		mViz.add(mVizOverlayGenotypes);
		mViz.add(mVizDisableGradients);
		mViz.add(mVizHighlight);

		add(mViz);
	}

	private void createAnalysisMenu()
	{
		mAnalysis = new JMenu(RB.getString("gui.WinMainMenuBar.mAnalysis"));
		RB.setMnemonic(mAnalysis, "gui.WinMainMenuBar.mAnalysis");

		mAlysSortLines = new JMenu(RB.getString("gui.WinMainMenuBar.mAlysSortLines"));
		RB.setMnemonic(mAlysSortLines, "gui.WinMainMenuBar.mAlysSortLines");

		mAlysStatsPedVer = new JMenu(RB.getString("gui.WinMainMenuBar.mAlysStatsPedVer"));
		RB.setMnemonic(mAlysStatsPedVer, "gui.WinMainMenuBar.mAlysStatsPedVer");

		mAlysSortLinesBySimilarity = getItem(Actions.alysSortLinesBySimilarity, "gui.Actions.alysSortLinesBySimilarity");
		mAlysSortLinesByTrait = getItem(Actions.alysSortLinesByTrait, "gui.Actions.alysSortLinesByTrait");
		mAlysSortLinesByExternal = getItem(Actions.alysSortLinesByExternal, "gui.Actions.alysSortLinesByExternal");
		mAlysSortLinesAlphabetically = getItem(Actions.alysSortLinesAlphabetically, "gui.Actions.alysSortLinesAlphabetically");

		mAlysSimMatrix = getItem(Actions.alysSimMatrix, "gui.Actions.alysSimMatrix");
		mAlysDendrogram = getItem(Actions.alysDendrogram, "gui.Actions.alysDendrogram");
		mAlysPCoA = getItem(Actions.alysPCoA, "gui.Actions.alysPCoA");
		mAlysMABC = getItem(Actions.alysMABC, "gui.Actions.alysMABC");
		mAlysPedVer = getItem(Actions.alysPedVer, "gui.Actions.alysPedVer");
		mAlysPedVerLines = getItem(Actions.alysPedVerLines, "gui.Actions.alysPedVerLines");
		mAlysForwardBreeding = getItem(Actions.alysForwardBreeding, "gui.Actions.alysForwardBreeding");

		mAlysSortLines.add(mAlysSortLinesAlphabetically);
		mAlysSortLines.addSeparator();
		mAlysSortLines.add(mAlysSortLinesBySimilarity);
		mAlysSortLines.add(mAlysSortLinesByTrait);
		mAlysSortLines.add(mAlysSortLinesByExternal);

		mAlysStatsPedVer.add(mAlysPedVer);
		mAlysStatsPedVer.addSeparator();
		mAlysStatsPedVer.add(mAlysPedVerLines);

		mAnalysis.add(mAlysSortLines);
		mAnalysis.addSeparator();
		mAnalysis.add(mAlysSimMatrix);
		mAnalysis.add(mAlysDendrogram);
		mAnalysis.add(mAlysPCoA);
		mAnalysis.addSeparator();
		mAnalysis.add(mAlysMABC);
		mAnalysis.add(mAlysStatsPedVer);
		mAnalysis.add(mAlysForwardBreeding);


		add(mAnalysis);
	}

	private void createDataMenu()
	{
		mData = new JMenu(RB.getString("gui.WinMainMenuBar.mData"));
		RB.setMnemonic(mData, "gui.WinMainMenuBar.mData");

		mDataDB = new JMenu(RB.getString("gui.WinMainMenuBar.mDataDB"));
		mDataDB.setIcon(Actions.getIcon("DATABASE"));
		RB.setMnemonic(mDataDB, "gui.WinMainMenuBar.mDataDB");

		mDataTraits = new JMenu(RB.getString("gui.WinMainMenuBar.mDataTraits"));
		RB.setMnemonic(mDataTraits, "gui.WinMainMenuBar.mDataTraits");

		malysFilterQTLs = getItem(Actions.dataFilterQTLs, "gui.Actions.dataFilterQTLs");
		mDataSelectGraph = getItem(Actions.dataSelectGraph, "gui.Actions.dataSelectGraph");
		mDataFind = getItem(Actions.dataFind, "gui.Actions.dataFind", KeyEvent.VK_F, menuShortcut);

		mDataStatistics = getItem(Actions.dataStatistics, "gui.Actions.dataStatistics");
		mDataDBLineName = getItem(Actions.dataDBLineName, "gui.Actions.dataDBLineName");
		mDataDBMarkerName = getItem(Actions.dataDBMarkerName, "gui.Actions.dataDBMarkerName");
		mDataDBSettings = getItem(Actions.dataDBSettings, "gui.Actions.dataDBSettings");
		mDataRenameDataSet = getItem(Actions.dataRenameDataSet, "gui.Actions.dataRenameDataSet");
		mDataDeleteDataSet = getItem(Actions.dataDeleteDataSet, "gui.Actions.dataDeleteDataSet");
		mDataSelectTraits = getItem(Actions.dataSelectTraits, "gui.Actions.dataSelectTraits");
		mDataSelectTextTraits = getItem(Actions.dataSelectTextTraits, "gui.Actions.dataSelectTextTraits");

		mDataDB.add(mDataDBLineName);
		mDataDB.add(mDataDBMarkerName);
		mDataDB.addSeparator();
		mDataDB.add(mDataDBSettings);

		mDataTraits.add(mDataSelectTraits);
		mDataTraits.add(mDataSelectTextTraits);

		mData.add(malysFilterQTLs);
		mData.add(mDataTraits);
		mData.add(mDataSelectGraph);
		mData.addSeparator();
		mData.add(mDataFind);
		mData.add(mDataStatistics);
		mData.add(mDataDB);
		mData.addSeparator();
		mData.add(mDataRenameDataSet);
		mData.add(mDataDeleteDataSet);

		add(mData);
	}

	private void createWndMenu()
	{
		mWnd = new JMenu(RB.getString("gui.WinMainMenuBar.mWnd"));
		RB.setMnemonic(mWnd, "gui.WinMainMenuBar.mWnd");

		mWndMinimize = getItem(Actions.wndMinimize, "gui.Actions.wndMinimize", KeyEvent.VK_M, menuShortcut);
		mWndZoom = getItem(Actions.wndZoom, "gui.Actions.wndZoom");
		mWndFlapjack = getCheckedItem(Actions.wndFlapjack, "gui.Actions.wndFlapjack");

		mWnd.add(mWndMinimize);
		mWnd.add(mWndZoom);
		mWnd.addSeparator();
		mWnd.add(mWndFlapjack);

		if (SystemUtils.isMacOS())
			add(mWnd);
	}

	private void createHelpMenu()
	{
		mHelp = new JMenu(RB.getString("gui.WinMainMenuBar.mHelp"));
		RB.setMnemonic(mHelp, "gui.WinMainMenuBar.mHelp");

		mHelpContents = getItem(Actions.helpContents, "gui.Actions.helpContents", KeyEvent.VK_F1, 0);
		mHelpPrefs = getItem(Actions.helpPrefs, "gui.Actions.helpPrefs");
		mHelpUpdate = getItem(Actions.helpUpdate, "gui.Actions.helpUpdate");
		mHelpAbout = getItem(Actions.helpAbout, "gui.Actions.helpAbout");

		mHelp.add(mHelpContents);
		mHelp.addSeparator();

		// We don't add this option to OS X as it is auto-added by Apple
		if (SystemUtils.isMacOS() == false)
		{
			mHelp.add(mHelpPrefs);
			mHelp.addSeparator();
		}

		mHelp.add(mHelpUpdate);

		// We don't add this option to OS X as it is auto-added by Apple
		if (SystemUtils.isMacOS() == false)
		{
			mHelp.addSeparator();
			mHelp.add(mHelpAbout);
		}

		add(mHelp);
	}

	public static JMenuItem getItem(Action action, String key)
	{
		return getItem(action, key, 0, 0);
	}

	public static JMenuItem getItem(Action action, String key, int keymask, int modifiers)
	{
		JMenuItem item = new JMenuItem(action);
		RB.setMnemonic(item, key);

		if (keymask != 0)
			item.setAccelerator(KeyStroke.getKeyStroke(keymask, modifiers));

		if (SystemUtils.isMacOS())
			item.setIcon(null);

		return item;
	}

	public static JCheckBoxMenuItem getCheckedItem(Action action, String key)
	{
		return getCheckedItem(action, key, 0, 0);
	}

	public static JCheckBoxMenuItem getCheckedItem(Action action, String key, int keymask, int modifiers)
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem(action);
		RB.setMnemonic(item, key);

		if (keymask != 0)
			item.setAccelerator(KeyStroke.getKeyStroke(keymask, modifiers));

		if (SystemUtils.isMacOS())
			item.setIcon(null);

		return item;
	}

	public static JRadioButtonMenuItem getRadioItem(Action action, String key)
	{
		return getRadioItem(action, key, 0, 0);
	}

	public static JRadioButtonMenuItem getRadioItem(Action action, String key, int keymask, int modifiers)
	{
		JRadioButtonMenuItem item = new JRadioButtonMenuItem(action);
		RB.setMnemonic(item, key);

		if (keymask != 0)
			item.setAccelerator(KeyStroke.getKeyStroke(keymask, modifiers));

		if (SystemUtils.isMacOS())
			item.setIcon(null);

		return item;
	}

	// Maintains and creates the Recent Projects file menu, adding new entries
	// as previously unseen projects are opened or saved, and ensuring that:
	//   a) the most recently accessed file is always at the start of the list
	//   b) the list never grows bigger than four entries
	void createRecentMenu(FlapjackFile file)
	{
		// Begin by making a list of the recent file locations
		LinkedList<String> entries = new LinkedList<>();
		entries.add(Prefs.guiRecentProject1);
		entries.add(Prefs.guiRecentProject2);
		entries.add(Prefs.guiRecentProject3);
		entries.add(Prefs.guiRecentProject4);
		entries.add(Prefs.guiRecentProject5);
		entries.add(Prefs.guiRecentProject6);
		entries.add(Prefs.guiRecentProject7);
		entries.add(Prefs.guiRecentProject8);
		entries.add(Prefs.guiRecentProject9);
		entries.add(Prefs.guiRecentProject10);

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

			if (entries.size() > 10)
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
						winMain.mFile.fileOpen(new FlapjackFile(entry));
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
		Prefs.guiRecentProject5 = entries.get(4);
		Prefs.guiRecentProject6 = entries.get(5);
		Prefs.guiRecentProject7 = entries.get(6);
		Prefs.guiRecentProject8 = entries.get(7);
		Prefs.guiRecentProject9 = entries.get(8);
		Prefs.guiRecentProject10 = entries.get(9);
	}
}