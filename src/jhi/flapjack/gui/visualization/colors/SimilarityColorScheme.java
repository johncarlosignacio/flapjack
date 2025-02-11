// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.gui.visualization.colors;

import java.awt.*;
import java.util.*;

import jhi.flapjack.data.*;
import jhi.flapjack.gui.*;

abstract class SimilarityColorScheme extends ColorScheme
{
	// List of comparison line/marker states (dark red by default)
	protected ArrayList<ColorState> compStates = new ArrayList<>();				// eg A matches A

	// States that exactly match the comparison
	protected ArrayList<ColorState> mtchStatesY = new ArrayList<>();			// eg A matches A, A/T matches A/T
	// States that don't exactly match the comparison
	protected ArrayList<ColorState> mtchStatesN = new ArrayList<>();			// eg A doesn't match T, A/T doesn't match C/G

	// States that match on the first het allele
	protected ArrayList<ColorState> het1States = new ArrayList<>();				// eg A/T matches A or A/G
	// States that match on the second het allele
	protected ArrayList<ColorState> het2States = new ArrayList<>();				// eg A/T matches T or C/T

	// Greyscale states for when the comparison state is missing
	protected ArrayList<ColorState> gsStates = new ArrayList<>();				// eg A could match MISSING, but we don't know

	protected int[][] lookupTable;


	/** Empty constructor that is ONLY used for color customization purposes. */
	public SimilarityColorScheme() {}

	public SimilarityColorScheme(GTView view, int w, int h)
	{
		super(view);

		// Shorthand names for the colours (makes the code below more readable)
		Color sHz = Prefs.visColorNucleotideHZ;
		Color drk = Prefs.visColorSimStateMatchDark;
		Color s1  = Prefs.visColorSimStateMatch;
		Color s2  = Prefs.visColorSimStateNoMatch;
		Color gsC = Prefs.visColorSimStateMissing;

		for (int i = 0; i < stateTable.size(); i++)
		{
			AlleleState state = stateTable.getAlleleState(i);

			ColorState comp, mtchY, mtchN, het1, het2, gs;

			// Use white for the default unknown state
			if (state.isUnknown())
				comp = mtchY = mtchN = het1 = het2 = gs = new SimpleColorState(state, Prefs.visColorBackground, w, h);

			// Homozygous states
			else if (state.isHomozygous())
			{
				comp  = new HomozygousColorState(state, drk, w, h);

				mtchY = new HomozygousColorState(state, s1, w, h);
				mtchN = new HomozygousColorState(state, s2, w, h);
				het1  = null;
				het2  = null;
				gs    = new HomozygousColorState(state, gsC, w, h);
			}

			// Heterozygous states
			else
			{
				comp  = new HeterozygeousColorState(state, sHz, drk, drk, w, h);

				mtchY = new HeterozygeousColorState(state, sHz, s1, s1, w, h);
				mtchN = new HeterozygeousColorState(state, sHz, s2, s2, w, h);
				het1  = new HeterozygeousColorState(state, sHz, s1, s2, w, h);
				het2  = new HeterozygeousColorState(state, sHz, s2, s1, w, h);
				gs    = new HeterozygeousColorState(state, sHz, gsC, gsC, w, h);
			}

			compStates.add(comp);
			mtchStatesY.add(mtchY);
			mtchStatesN.add(mtchN);
			het1States.add(het1);
			het2States.add(het2);
			gsStates.add(gs);
		}

		createLookupTable();
	}

	public void setColorSummaries(ArrayList<ColorSummary> colors)
	{
		Prefs.visColorSimStateMatchDark = colors.get(0).color;
		Prefs.visColorSimStateMatch = colors.get(1).color;
		Prefs.visColorSimStateNoMatch = colors.get(2).color;
		Prefs.visColorSimStateMissing = colors.get(3).color;
	}

	private void createLookupTable()
	{
		// Make a lookup table suitable for an all-by-all comparison of states
		// This is so we can, for example, compare A against A/T and decide that
		// A is a partial match and should be drawn in the same colour
		// Or (another example), comparing A/T against A, again a partial match
		// so draw A/T with the A half in red, T half in green (het1 state)

		int count = stateTable.size();
		lookupTable = new int[count][count];

		for (int i = 0; i < count; i++)
		{
			for (int j = 0; j < count; j++)
			{
				// Start by assuming no match - we can override later...
				lookupTable[i][j] = 2;

				// Perfect match... (does homoz<->homoz comparisons too)
				if (i == j)
				{
					lookupTable[i][j] = 1;
					continue;
				}

				AlleleState ai = stateTable.getAlleleState(i);
				AlleleState aj = stateTable.getAlleleState(j);

				// Comparing a blank against something else
				if (i == 0 || j == 0)
				{
					lookupTable[i][j] = 5;
				}

				// i is homoz, j is hetez
				else if (ai.isHomozygous() && !aj.isHomozygous())
				{
					// match
					if (ai.getState(0).equals(aj.getState(0)) || ai.getState(0).equals(aj.getState(1)))
						lookupTable[i][j] = 1;
				}

				// i is hetez, j is homoz
				else if (!ai.isHomozygous() && aj.isHomozygous())
				{
					// het1 match
					if (ai.getState(0).equals(aj.getState(0)))
						lookupTable[i][j] = 3;
					// het2 match
					else if (ai.getState(1).equals(aj.getState(0)))
						lookupTable[i][j] = 4;
				}

				// i and j both hetez
				else if (!ai.isHomozygous() && !aj.isHomozygous())
				{
					// A/T matches T/A ?
					if (ai.matches(aj))
						lookupTable[i][j] = 1;

					// het1 match  (eg A/T half matches A/G or G/A)
					else if (ai.getState(0).equals(aj.getState(0)) || ai.getState(0).equals(aj.getState(1)))
						lookupTable[i][j] = 3;
					// het2 match  (eg A/T half matches T/G or C/T)
					else if (ai.getState(1).equals(aj.getState(0)) || ai.getState(1).equals(aj.getState(1)))
						lookupTable[i][j] = 4;
				}
			}
		}
	}
}