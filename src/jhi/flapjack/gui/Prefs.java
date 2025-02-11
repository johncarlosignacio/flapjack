// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui;

import java.awt.*;

import jhi.flapjack.io.*;

import scri.commons.gui.*;

public class Prefs extends XMLPreferences
{
	// Is this the first time the program has ever been run (by this user)?
	public static boolean isFirstRun = true;
	public static boolean isSCRIUser = false;
	public static int rating = 0;

	public static String visColorSeed = "" + (System.currentTimeMillis() -
		1987200000L); // First appearance after 7 days (now-23 days)

	// Unique Flapjack ID for this user
	public static String flapjackID = SystemUtils.createGUID(32);

	// The last used directory location in file chooser dialogs
	public static String guiCurrentDir = "";
	//replacements for guiCurrentMap and guiCurrentGeno
	public static String guiMapList = "";
	public static boolean guiMapListUseBlank = false;
	public static String guiGenoList = "";
	// Path to the last selected trait file
	public static String guiTraitHistory = "";
	public static String guiQTLHistory = "";
	public static String guiGraphHistory = "";

	public static String guiQuickExportHistory = "";

	// History for selected external sort orders
	public static String guiExternalSortHistory = "";
	// History for selected external line selections
	public static String guiExternalLineSelectionHistory = "";
	// History for selected external marker selections
	public static String guiExternalMarkerSelectionHistory = "";

	// Use UK English decimal mark settings
	public static boolean guiDecimalEnglish = false;

	public static String guiPedigreeList = "";

	// How many projects have been created
	public static int guiProjectCount = 1;

	// The locations of the last four accessed projects
	public static String guiRecentProject1;
	public static String guiRecentProject2;
	public static String guiRecentProject3;
	public static String guiRecentProject4;
	public static String guiRecentProject5;
	public static String guiRecentProject6;
	public static String guiRecentProject7;
	public static String guiRecentProject8;
	public static String guiRecentProject9;
	public static String guiRecentProject10;

	// Display localised text in...
	public static String localeText = "auto";

	// Truncate names in the ListPanel?
	public static boolean guiTruncateNames = false;
	public static int guiTruncateNamesLength = 7;
	// Truncate traits in the ListPanel?
	public static boolean guiTruncateTraits = true;
	public static int guiTruncateTraitsLength = 7;

	// The width, height, location and maximized status of the main window
	public static int guiWinMainW = 1000;
	public static int guiWinMainH = 700;
	public static int guiWinMainX = Integer.MIN_VALUE;
	public static int guiWinMainY = Integer.MIN_VALUE;
	public static boolean guiWinMainMaximized = false;

	// The location of the main splits pane divider
	public static int guiNavSplitsLocation = 180;

	// The width, height and location of the overview dialog/panel
	public static boolean guiOverviewDialog = false;
	public static int guiOverviewWidth = 300;
	public static int guiOverviewHeight = 300;
	public static int guiOverviewX = Integer.MIN_VALUE;
	public static int guiOverviewY = Integer.MIN_VALUE;
	public static int guiOverviewSplitsLocation = 450;

	// Position of the Filter QTLs dialog
	public static int guiFilterQTLDialogX = Integer.MIN_VALUE;
	public static int guiFilterQTLDialogY = Integer.MIN_VALUE;

	// QTL splitpane location
	public static int guiQTLSplitterLocation = 25;

	// When to check for updates
	public static int guiUpdateSchedule = Install4j.STARTUP;

	// Data import type: 0=classic, 1=brapi
	public static int guiImportType = DataImporter.IMPORT_CLASSIC;

	public static boolean guiBrapiSkipMap = false;

	// Last selected tab on single/batch analysis dialogs
	public static int mabcDialogTab = 0;
	public static int pedVerF1DialogTab = 0;
	public static int pedVerLinesDialogTab = 0;
	public static int forwardBreedingDialogTab = 0;

	// PedVerF1 threshold values
	public static int pedVerF1HetThreshold = 0;
	public static int pedVerF1F1Threshold = 0;
	public static int pedVerF1ErrorThreshold = 0;
	public static int pedVerF1ParentHetThreshold = 0;
	public static int pedVerF1isHetThreshold = 0;

	// Warning messages
	public static boolean warnDuplicateMarkers = true;
	public static boolean warnEditMarkerMode = true;
	public static boolean warnEditLineMode = true;
	public static boolean warnEditCustomMap = true;

	// String matches for missing data and heterozygous values when importing
	public static String ioMissingData = "-";
	public static String ioHeteroSeparator = "/";
	public static boolean ioHeteroCollapse = true;
	public static boolean ioMakeAllChromosome = false;
	public static boolean ioTransposed = false;
	public static boolean ioAllowDupLines = false;

	// Method used when exporting images
	public static int guiExportImageMethod = 0;
	// Image size to use when exporting
	public static int guiExportImageX;
	public static int guiExportImageY;

	// Auto assign traits to the heatmap after a sort-by-trait?
	public static boolean guiAssignTraits = true;

	// Graphs (against markers)
	public static int guiGraphStyle = 0;

	// Finding stuff...
	public static int guiFindDialogX = Integer.MIN_VALUE;
	public static int guiFindDialogY = Integer.MIN_VALUE;
	public static int guiFindMethod = 0;
	public static boolean guiFindMatchCase = false;
	public static boolean guiFindUseRegex = true;
	public static String guiFindHistory = ".*";

	public static boolean guiHideSelectedMarkers = false;
	public static boolean guiHideSelectedLines = false;

	public static boolean guiUseSimpleMabcStats = false;
	public static boolean guiMabcExcludeParents = false;
	public static boolean guiPedVerF1sExcludeParents = false;

	public static int guiMouseMode = Constants.NAVIGATION;

	// Missing markers dialog
	public static int guiMissingMarkerPcnt = 95;
	// Missing lines dialog
	public static int guiMissingLinesPcnt = 95;
	// Heterozygous markers dialog
	public static int guiHeterozygousMarkerPcnt = 95;
	// Heterozygous lines dialog
	public static int guiHeterozygousLinesPcnt = 95;
	// Homozygous lines dialog
	public static int guiHomozygousLinesPcnt = 95;

	// LineDataTable export data dialog
	public static String guiLDTableExportHistory = "";
	// 0=export all, 1=export only visible, 2=export only visible/selected
	public static int guiLDTableExportType = 0;
	public static boolean guiLDTableExportHeaders = true;
	public static boolean guiLDTableExportTraits = true;

	// BrAPI Importer Options
	public static boolean guiBrAPIUseCustom = false;
	public static String guiBrAPICustomHistory = "";
	public static boolean guiBrAPIUseStudies = true;
	public static boolean guiBrAPIUseMaps = true;

	public static int guiBrAPICategoryIndex = -1;

	// What type of map scaling should be used (local=0, global=1)
	public static int visMapScaling = Constants.GLOBAL;

	// Display category boundaries on the traits heatmap?
	public static boolean visShowCatBoundaries = true;

	public static boolean visShowMiniMapCanvas = true;
	public static boolean visShowLinePanel = true;
	public static boolean visShowMapCanvas = true;
	public static boolean visShowQTLCanvas = true;
	public static boolean visShowRowCanvas = true;
	public static boolean visShowColCanvas = true;
	public static boolean visShowTraitCanvas = true;
	public static boolean visShowStatusPanel = true;
	public static boolean visShowGraphCanvas = true;

	// If true, then we're showing the main genotypes view
	public static boolean visShowChromosomes = false;

	// Link the x/y zoom sliders?
	public static boolean visLinkSliders = true;
	// Which is an option only visible when advanced zoom controls are used
	public static boolean visAdvancedZoom = false;
	// Always show heterozygotes as "H" with a mono colour
	public static boolean visShowHetsAsH = false;

	// Should the visualization canvas overlay the raw data?
	public static boolean visShowGenotypes = true;
	// Should the visualization canvas highlight heterozygotes?
	public static boolean visHighlightHtZ = false;
	public static boolean visHighlightHoZ = false;
	// Should the visualization canvas highlight gaps?
	public static boolean visHighlightGaps = false;

	// Mouse-over crosshair?
	public static boolean visCrosshair = true;
	// Mouse-over tooltips?
	public static boolean visTooltip = true;

	// Disable gradient painting?
	public static boolean visDisableGradients = false;

	// Standard colors used regardless of scheme
	public static Color visColorBackground;
	public static Color visColorOverviewOutline;
	public static Color visColorOverviewFill;
	public static Color visColorText;
	public static Color visColorHeatmapHigh;
	public static Color visColorHeatmapLow;
	public static Color visColorHetsAsH;

	// Colors used by the nucleotide color scheme
	public static Color visColorNucleotideA;
	public static Color visColorNucleotideC;
	public static Color visColorNucleotideG;
	public static Color visColorNucleotideT;
	public static Color visColorNucleotide0;
	public static Color visColorNucleotide1;
	public static Color visColorNucleotideHZ;
	public static Color visColorNucleotideOther;

	// Colors used by the ABH color scheme
	public static Color visColorABH_A;
	public static Color visColorABH_B;
	public static Color visColorABH_H;
	public static Color visColorABH_C;
	public static Color visColorABH_D;
	public static Color visColorABH_Other;

	// Colors used by the simple two color scheme
	public static Color visColorSimple2State1;
	public static Color visColorSimple2State2;
	public static Color visColorSimple2Other;

	// Colors used by the similarity schemes
	public static Color visColorSimStateMatch;
	public static Color visColorSimStateMatchDark;
	public static Color visColorSimStateNoMatch;
	public static Color visColorSimStateMissing;

	// Colors used by the allele frequency scheme
	public static Color visColorLoFreqState;
	public static Color visColorHiFreqState;

	// Colors used by the binned scheme
	public static Color visColorBinnedLow;
	public static Color visColorBinnedHigh;
	public static Color visColorBinnedOther;

	// Colours used by the Magic colour scheme
	public static Color visColorMagic1;
	public static Color visColorMagic2;
	public static Color visColorMagic3;
	public static Color visColorMagic4;
	public static Color visColorMagic5;
	public static Color visColorMagic6;
	public static Color visColorMagic7;
	public static Color visColorMagic8;

	public static Color visParentSimilarity1;
	public static Color visParentSimilarity2;
	public static Color visParentSimilarity1Match;
	public static Color visParentSimilarity2Match;

	public static boolean miscSubscribed = false;
	public static String miscEmail = "";
	public static String miscInstitution = "";

	// Proxy settings
	public static boolean proxyUse = false;
	public static boolean proxySocks = false;
	public static int proxyPort = 8080;
	public static String proxyAddress = "";
	public static String proxyUsername = "";
	public static String proxyPassword = "";

	// MABC stats
	public static double mabcMaxMrkrCoverage = 10.0d;

	static void setDefaults()
	{
		setColorDefaults();
	}

	public static void setColorDefaults()
	{
		visColorBackground = new Color(255, 255, 255);
		visColorOverviewOutline = new Color(255, 0, 0);
		visColorOverviewFill = new Color(50, 50, 0);
		visColorText = new Color(0, 0, 0);
		visColorHeatmapLow = new Color(120, 255, 120);
		visColorHeatmapHigh = new Color(255, 120, 120);
		visColorHetsAsH = new Color(255, 255, 0);

		visColorNucleotideA = new Color(120, 255, 120);
		visColorNucleotideC = new Color(255, 160, 120);
		visColorNucleotideG = new Color(255, 120, 120);
		visColorNucleotideT = new Color(120, 120, 255);
		visColorNucleotide0 = new Color(153, 204, 255);
		visColorNucleotide1 = new Color(255, 255, 153);
		visColorNucleotideHZ = new Color(100, 100, 100);
		visColorNucleotideOther = new Color(204, 204, 204);

		visColorABH_A = new Color(255, 120, 120);
		visColorABH_B = new Color(120, 120, 255);
		visColorABH_H = new Color(120, 255, 120);
		visColorABH_C = new Color(208, 208, 255);
		visColorABH_D = new Color(255, 160, 120);
		visColorABH_Other = new Color(204, 204, 204);

		visColorSimple2State1 = new Color(255, 120, 120);
		visColorSimple2State2 = new Color(120, 255, 120);
		visColorSimple2Other = new Color(204, 204, 204);

		visColorSimStateMatch = new Color(120, 255, 120);
		visColorSimStateMatchDark = new Color(90, 255, 90);
		visColorSimStateNoMatch = new Color(255, 120, 120);
		visColorSimStateMissing = new Color(192, 192, 192);

		visColorLoFreqState = new Color(102, 102, 255);
		visColorHiFreqState = new Color(204, 255, 204);

		visColorBinnedLow = new Color(255, 120, 120);
		visColorBinnedHigh = new Color(120, 255, 120);
		visColorBinnedOther = new Color(204, 204, 204);

		visColorMagic1 = new Color(119, 255, 255);
		visColorMagic2 = new Color(255, 160, 120);
		visColorMagic3 = new Color(120, 120, 255);
		visColorMagic4 = new Color(145, 145, 145);
		visColorMagic5 = new Color(120, 255, 120);
		visColorMagic6 = new Color(255, 253, 119);
		visColorMagic7 = new Color(255, 119, 231);
		visColorMagic8 = new Color(255, 120, 120);

		//visParentSimilarity1 = new Color(0,176,240).darker();
		//visParentSimilarity2 = new Color(255,255,0).darker();
		visParentSimilarity1 = new Color(80, 80, 200);
		visParentSimilarity2 = new Color(200, 160, 20);
		visParentSimilarity1Match = new Color(110, 110, 230);
		visParentSimilarity2Match = new Color(230, 190, 50);
	}

	// Updates the array of recently accessed documents so that 'document' is
	// the first element, even if it has been accessed previously
	public static void setRecentFiles(String[] files, String[] recentDocs)
	{
		for (int i = 0; i < files.length; i++)
			recentDocs[i] = files[i];
	}
}