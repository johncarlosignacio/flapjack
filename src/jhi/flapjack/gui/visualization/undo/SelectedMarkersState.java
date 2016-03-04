// Copyright 2009-2016 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.visualization.undo;

import jhi.flapjack.data.*;

public class SelectedMarkersState implements IUndoState
{
	private GTView view;
	private String menuStr;

	// The selected state of the markers before they were changed
	private boolean[] undoMarkers;

	// And the state after the selection was finished
	private boolean[] redoMarkers;

	public SelectedMarkersState(GTView view)
	{
		this.view = view;
	}

	public SelectedMarkersState(GTView view, String menuStr)
	{
		this.view = view;
		this.menuStr = menuStr;
	}

	public String getMenuString()
		{ return menuStr; }

	public void setMenuString(String menuStr)
		{ this.menuStr = menuStr; }

	public GTView getView()
		{ return view; }

	public void createUndoState()
	{
		undoMarkers = new boolean[view.markerCount()];
		for (int i = 0; i < undoMarkers.length; i++)
			undoMarkers[i] = view.isMarkerSelected(i);
	}

	public void applyUndoState()
	{
		for (int i = 0; i < undoMarkers.length; i++)
			view.setMarkerState(i, undoMarkers[i]);
	}

	public void createRedoState()
	{
		redoMarkers = new boolean[view.markerCount()];
		for (int i = 0; i < redoMarkers.length; i++)
			redoMarkers[i] = view.isMarkerSelected(i);
	}

	public void applyRedoState()
	{
		for (int i = 0; i < redoMarkers.length; i++)
			view.setMarkerState(i, redoMarkers[i]);
	}
}