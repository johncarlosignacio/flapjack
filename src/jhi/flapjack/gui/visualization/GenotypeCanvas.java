// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.visualization;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.util.concurrent.*;
import javax.swing.*;

import jhi.flapjack.data.*;
import jhi.flapjack.gui.*;
import jhi.flapjack.gui.dialog.*;
import jhi.flapjack.gui.visualization.colors.*;

class GenotypeCanvas extends JPanel
{
	private GenotypePanel gPanel;

	// The "view" being rendered
	GTViewSet viewSet;
	GTView view;

	// The current color model
	ColorScheme cScheme;

	boolean locked = false;

	CrosshairOverlay crosshair;

	// The total number of boxes (allele states) in the dataset
	int boxTotalX, boxTotalY;
	// Width and height of the main drawing canvas
	int canvasW, canvasH;
	// Width and height of an allele "box"
	int boxW, boxH;
	// How many boxes will fit into the current screen size?
	int boxCountX, boxCountY;

	// These are the x and y pixel positions on the canvas that currently appear
	// in the top left corner of the current view
	int pX1, pY1;
	// These positions represent the bottom right hand corner of the visible
	// data onscreen (pX2 == pX2Max when more data is offscreen)
	int pX2, pY2;
	// And bottom right hand corner
	int pX2Max, pY2Max;

	// Starting and ending indices of the x (marker) and y (line) data that will
	// be drawn during the next repaint operation
	private int xS, xE, yE;

	// Tracks the genotype closest to the centre of the view
	float gtCenterX, gtCenterY;

	// Holds the current dimensions of the canvas in an AWT friendly format
	private Dimension dimension = new Dimension();

	// This buffer holds the current viewport (visible) area
	BufferedImage imageViewPort;
	// TRUE if we really MUST redraw, rather than just copying from the buffer
	private boolean redraw = true;

	// A list of renderers that will perform further drawing once the main
	// canvas has been drawn (eg animators, minesweeper, etc)
	LinkedList<IOverlayRenderer> overlays = new LinkedList<>();

	HashSet<Integer> qtlHash = new HashSet<>();

	// Objects for multicore rendering
	private int cores = Runtime.getRuntime().availableProcessors();
	private ExecutorService executor;
	private Future[] tasks;


	GenotypeCanvas(GenotypePanel gPanel, WinMain winMain)
	{
		this.gPanel = gPanel;

		setOpaque(false);
		setBackground(Prefs.visColorBackground);

		new CanvasMouseListener(gPanel, this, winMain);

		// Set up some keyboard navigation
		Action pageLeft = new AbstractAction() {
			public void actionPerformed(ActionEvent e)
				{ Actions.viewPageLeft.actionPerformed(e); }
		};
		Action pageRight = new AbstractAction() {
			public void actionPerformed(ActionEvent e)
				{ Actions.viewPageRight.actionPerformed(e); }
		};

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
			KeyStroke.getKeyStroke(KeyEvent.VK_OPEN_BRACKET, 0), "left");
		getActionMap().put("left", pageLeft);

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
			KeyStroke.getKeyStroke(KeyEvent.VK_CLOSE_BRACKET, 0), "right");
		getActionMap().put("right", pageRight);

		// Prepare the background threads that will do the main painting
		executor = Executors.newFixedThreadPool(cores);
		tasks = new Future[cores];

		overlays.add(new SplitterOverlay(gPanel));
		crosshair = new CrosshairOverlay(gPanel);
		overlays.add(crosshair);
	}

	void setView(GTViewSet viewSet, GTView view)
	{
		this.viewSet = viewSet;
		this.view = view;

		qtlHash = new HashSet<>();

		for (MarkerInfo info : view.getMarkers())
			for (QTLInfo qtlInfo : view.getQTLs())
				if (qtlInfo.getQTL().isVisible() && info.position() >= qtlInfo.min() && info.position() <= qtlInfo.max())
					qtlHash.add(info.getIndex());
	}

	// Compute canvas related dimensions that only change if the data or the
	// box-drawing size needs to be changed
	void setDimensions(int sizeX, int sizeY)
	{
		Font font = new Font("Monospaced", Font.PLAIN, sizeY);
		FontMetrics fm = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
			.getGraphics().getFontMetrics(font);

		// If the zoom is linked, always use the vertical component for x and y
		if (Prefs.visLinkSliders)
			boxW = sizeY*2;
		else
			boxW = sizeX*2;

		boxH = fm.getHeight();

		boxTotalX = view.markerCount();
		boxTotalY = view.lineCount();

		canvasW = (boxTotalX * boxW);
		canvasH = (boxTotalY * boxH);

		setSize(dimension = new Dimension(canvasW, canvasH));

		// TODO: track sizeX/Y (or something) so we only recreate the color
		// scheme when it really needs to be recreated
		updateColorScheme();

		resetBufferedState(true);
	}

	void updateColorScheme()
	{
		switch (viewSet.getColorScheme())
		{
			case ColorScheme.NUCLEOTIDE:
				cScheme = new NucleotideColorScheme(view, boxW, boxH);
				break;

			case ColorScheme.NUCLEOTIDE01:
				cScheme = new Nucleotide01ColorScheme(view, boxW, boxH);
				break;

			case ColorScheme.ABH_DATA:
				cScheme = new ABHDataColorScheme(view, boxW, boxH);
				break;

			case ColorScheme.LINE_SIMILARITY:
				cScheme = new LineSimilarityColorScheme(view, boxW, boxH);
				break;

			case ColorScheme.LINE_SIMILARITY_EXACT_MATCH:
				cScheme = new LineSimilarityExactColorScheme(view, boxW, boxH);
				break;

			case ColorScheme.LINE_SIMILARITY_ANY_MATCH:
				cScheme = new LineSimilarityAnyColorScheme(view, boxW, boxH);
				break;

			case ColorScheme.SIMILARITY_TO_EACH_PARENT:
				cScheme = new SimilarityToEachParentColorScheme(view, boxW, boxH);
				break;

			case ColorScheme.SIMILARITY_TO_EITHER_PARENT:
				cScheme = new SimilarityToEitherParentColorScheme(view, boxW, boxH);
				break;

			case ColorScheme.FAV_ALLELE:
				cScheme = new FavAlleleColorScheme(view, boxW, boxH);
				break;

			case ColorScheme.MARKER_SIMILARITY:
				cScheme = new MarkerSimilarityColorScheme(view, boxW, boxH);
				break;

			case ColorScheme.SIMPLE_TWO_COLOR:
				cScheme = new SimpleTwoColorScheme(view, boxW, boxH);
				break;

			case ColorScheme.ALLELE_FREQUENCY:
				// This scheme *needs* marker frequencies before it can display
				if (AlleleFrequencyDialog.calculate(viewSet))
				cScheme = new AlleleFrequencyColorScheme(view, boxW, boxH);
				// If the user doesn't allow the operation to finish, fall back
				// on another colour scheme otherwise things will break!
				else
				{
					gPanel.getViewSet().setColorScheme(ColorScheme.NUCLEOTIDE);
					cScheme = new NucleotideColorScheme(view, boxW, boxH);
				}
				break;

			case ColorScheme.BINNED_10:
				cScheme = new BinnedColorScheme(view, boxW, boxH);
				break;

			case ColorScheme.RANDOM:
				cScheme = new RandomColorScheme(ColorScheme.RANDOM, view, boxW, boxH);
				break;

			case ColorScheme.RANDOM_WSP:
				cScheme = new RandomColorScheme(ColorScheme.RANDOM_WSP, view, boxW, boxH);
				break;

			case ColorScheme.MAGIC:
				cScheme = new MagicColorScheme(view, boxW, boxH);
				break;

			default: // ColorScheme.NUCLEOTIDE
				cScheme = new NucleotideColorScheme(view, boxW, boxH);
		}

		redraw = true;
	}

	// Compute real-time variables, that change as the viewpoint is moved across
	// the canvas
	void computeForRedraw(Dimension viewSize, Point viewPosition)
	{
		boxCountX = 1 + (int) ((float) viewSize.getWidth()  / boxW);
		boxCountY = 1 + (int) ((float) viewSize.getHeight() / boxH);

		pX1 = viewPosition.x;
		pX2 = pX2Max = pX1 + viewSize.width -1;

		pY1 = viewPosition.y;
		pY2 = pY2Max = pY1 + viewSize.height - 1;

		// Adjust for canvases that are smaller than the window size
		if (pX2 >= canvasW)
			pX2 = canvasW - 1;
		if (pY2 >= canvasH)
			pY2 = canvasH - 1;

		// Track the base closest to the center of the current view
		gtCenterX = (pX1 / boxW) + ((viewSize.width  / boxW) / 2);
		gtCenterY = (pY1 / boxH) + ((viewSize.height / boxH) / 2);

		updateOverviewSelectionBox();

		redraw = true;
		repaint();
	}

	void updateOverviewSelectionBox()
	{
		gPanel.canvasViewChanged((pX1/boxW), boxCountX, (pY1/boxH), boxCountY);
	}

	// Called as the mouse moves over the canvas - we want to highlight this
	void setHighlightedIndices(int rowIndex, int colIndex)
	{
		if (rowIndex != view.mouseOverLine || colIndex != view.mouseOverMarker)
		{
			view.mouseOverLine = rowIndex;
			view.mouseOverMarker = colIndex;
		}

		if (Prefs.visCrosshair || Prefs.visTooltip)
			repaint();
	}

	public Dimension getPreferredSize()
		{ return dimension; }

	int getMarker(Point mousePoint)
		{ return mousePoint.x / boxW; }

	int getLine(Point mousePoint)
		{ return mousePoint.y / boxH; }

	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);

		Graphics2D g = (Graphics2D) graphics;

//		long s = System.nanoTime();

		g.setColor(Prefs.visColorBackground);
		g.fillRect(0, 0, canvasW, canvasH);

		renderViewport(g);

		// Post (main-canvas) rendering operations
		// This is put under try catch as rapidly changing the overlays (which
		// can happen in the Find Dialog for instance), may change this list
		// while we're still interating over it
		try
		{
			for (IOverlayRenderer renderer: overlays)
				renderer.render(g);
		}
		catch (ConcurrentModificationException e) {
			repaint();
		}

//		long e = System.nanoTime();
//		System.out.println("Render time: " + ((e-s)/1000000f) + "ms");
	}

	private void renderViewport(Graphics2D g)
	{
		if (redraw)
		{
			// What size of viewport buffer do we need?
			int w = pX2-pX1+1, h = pY2-pY1+1;

			// Safety check for no data as BufferedImage can't be zero by zero
			if (w == 0 || h == 0) w = h = 1;

			// Only make a new buffer if we really really need to, as this has
			// a noticeable effect on performance
			if (imageViewPort == null ||
				imageViewPort.getWidth() != w || imageViewPort.getHeight() != h)
			{
				imageViewPort = (BufferedImage) createImage(w, h);
			}

			Graphics2D gImage = imageViewPort.createGraphics();

			// Draw what should be visible onto the screen buffer...
			gImage.translate(-pX1, -pY1);
			renderRegion(gImage);

			gImage.dispose();
		}

		g.drawImage(imageViewPort, pX1, pY1, null);
		redraw = false;
	}

	private void renderRegion(Graphics2D g)
	{
		// These are the index positions within the dataset that we'll start
		// drawing from
		xS = pX1 / boxW;
		int yS = pY1 / boxH;

		// The end indices are calculated as the:
		//   (the start index) + (the number that can be drawn on screen)
		// with a check to set the end index to the last value in the array if
		// the calculated index would go out of bounds
		xE = xS + boxCountX;
		if (xE >= boxTotalX)
			xE = boxTotalX-1;

		yE = yS + boxCountY;
		if (yE >= boxTotalY)
			yE = boxTotalY-1;

		render(g, yS);
	}

	private void renderAll(Graphics2D g)
	{
		xS = 0;
		xE = boxTotalX-1;
		int yS = 0;
		yE = boxTotalY-1;

		render(g, yS);
	}

	private void render(Graphics2D g, int yS)
	{
		try
		{
			// Paint the lines using multiple cores...
			for (int i = 0; i < tasks.length; i++)
				tasks[i] = executor.submit(new LinePainter(g, yS+i));
			for (Future task: tasks)
				task.get();
		}
		catch (Exception e) {}
	}

	private final class LinePainter implements Runnable
	{
		private Graphics g;
		private int yS;

		LinePainter(Graphics g, int yS)
		{
			this.g = g;
			this.yS = yS;
		}

		public void run()
		{
			boolean navMode = Prefs.guiMouseMode == Constants.NAVIGATION;
			boolean markerMode = Prefs.guiMouseMode == Constants.MARKERMODE;
			boolean lineMode = Prefs.guiMouseMode == Constants.LINEMODE;

			for (int row = yS, y = (boxH*yS); row <= yE; row += cores, y += boxH*cores)
			{
				for (int xIndex = xS, x = (boxW*xS); xIndex <= xE; xIndex++, x += boxW)
				{
					boolean underQTL = qtlHash.contains(xIndex);

					// "Allowed" states for an enabled/selected allele
					if (navMode || (markerMode && view.isMarkerSelected(xIndex)) ||
						(lineMode && view.isLineSelected(row)))
					{
						g.drawImage(cScheme.getSelectedImage(row, xIndex, underQTL), x, y, null);
					}
					// Otherwise, draw it disabled/unselected
					else
						g.drawImage(cScheme.getUnselectedImage(row, xIndex, underQTL), x, y, null);
				}
			}
		}
	}

	// Will stop the creation of any back-buffer, and optionally start working
	// on a new buffer (on the assumption that the view has changed in some way)
	void resetBufferedState(boolean createNewBuffer)
	{
		redraw = true;
		repaint();
	}

	BufferedImage createSavableImage(boolean full)
		throws Error, Exception
	{
		if (!full)
			return imageViewPort;

		BufferedImage image = (BufferedImage) createImage(canvasW, canvasH);

		Graphics2D g = image.createGraphics();
		renderAll(g);
		g.dispose();

		return image;
	}
}