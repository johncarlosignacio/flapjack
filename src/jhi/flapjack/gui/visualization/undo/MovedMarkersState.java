// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.visualization.undo;

import jhi.flapjack.data.*;

public class MovedMarkersState implements IUndoState
{
	private GTView view;
	private String menuStr;

	// The state (that is, "order") of the markers before they were moved about
	private MarkerInfo[] undoMarkers;
	// The comparisonMarker (and index) before the move
	private Marker undoComparisonMarker;
	private int undoComparisonMarkerIndex;

	// And the state after the movement was finished
	private MarkerInfo[] redoMarkers;
	// The comparisonMarker (and index) after the move
	private Marker redoComparisonMarker;
	private int redoComparisonMarkerIndex;

	public MovedMarkersState(GTView view, String menuStr)
	{
		this.view = view;
		this.menuStr = menuStr;
	}

	public String getMenuString()
		{ return menuStr; }

	public GTView getView()
		{ return view; }

	public void createUndoState()
	{
		undoMarkers = view.getMarkersAsArray(true);
		undoComparisonMarker = view.getComparisonMarker();
		undoComparisonMarkerIndex = view.getComparisonMarkerIndex();
	}

	public void applyUndoState()
	{
		view.setMarkersFromArray(undoMarkers, true);
		view.setComparisonMarker(undoComparisonMarker);
		view.setComparisonMarkerIndex(undoComparisonMarkerIndex);
	}

	public void createRedoState()
	{
		redoMarkers = view.getMarkersAsArray(true);
		redoComparisonMarker = view.getComparisonMarker();
		redoComparisonMarkerIndex = view.getComparisonMarkerIndex();
	}

	public void applyRedoState()
	{
		view.setMarkersFromArray(redoMarkers, true);
		view.setComparisonMarker(redoComparisonMarker);
		view.setComparisonMarkerIndex(redoComparisonMarkerIndex);
	}
}