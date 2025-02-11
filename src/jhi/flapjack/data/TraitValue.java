// Copyright 2009-2019 Information & Computational Sciences, JHI. All rights
// reserved. Use is subject to the accompanying licence terms.

package jhi.flapjack.data;

import java.awt.*;

public class TraitValue extends XMLRoot
{
	// A reference to the trait that this value corresponds to
	private Trait trait;

	// The actual value itself
	private float value;
	// And it's normalized value when compared against all other lines
	private float normal;

	// Some lines may need "dummy" trait data
	private boolean isDefined;

	// XML (Castor) constructor
	public TraitValue()
	{
	}

	/** Constructs a new trait value that is undefined (no data). */
	public TraitValue(Trait trait)
	{
		this.trait = trait;

		isDefined = false;
	}

	/** Constructs a new trait value for the trait and value provided. */
	public TraitValue(Trait trait, float value)
	{
		this.trait = trait;
		this.value = value;

		isDefined = true;
	}

	void validate()
		throws NullPointerException
	{
		if (trait == null)
			throw new NullPointerException();

		if (Float.isNaN(normal))
			normal = 0;
	}


	// Methods required for XML serialization

	public Trait getTrait()
		{ return trait; }

	public void setTrait(Trait trait)
		{ this.trait = trait; }

	public float getValue()
		{ return value; }

	public void setValue(float value)
		{ this.value = value; }

	public float getNormal()
		{ return normal; }

	public void setNormal(float normal)
		{ this.normal = normal; }

	public boolean isDefined()
		{ return isDefined; }

	public void setDefined(boolean isDefined)
		{ this.isDefined = isDefined; }


	// Other methods

	/**
	 * Compares this trait value to another trait value (that MUST be a value
	 * for the same trait, otherwise the result will be undefined).
	 */
	public int compareTo(TraitValue other)
	{
		// To begin with, are the two trait values even comparable?
		if (!isDefined && !other.isDefined)
			return 0;
		if (!isDefined)
			return -1;
		if (!other.isDefined)
			return 1;

		// Numerical comparisons are easy...
		if (trait.traitIsNumerical())
		{
			if (value < other.value)
				return -1;
			else if (value == other.value)
				return 0;
			else
				return 1;
		}

		// But for categorical, we (unfortunately) need to do string comparisons
		// rather than the lookup value, otherwise the user won't neccassarily
		// see the same result as they would in Excel (eg, if "male", "female"
		// are the categories with lookup values 0 and 1, male will sort before
		// female (m before f) which is wrong).
		else
		{
			String myCat = trait.format(this);
			String otherCat = trait.format(other);

			return myCat.compareTo(otherCat);
		}
	}

	/**
	 * Uses the maximum and minimum values for all lines with this trait value,
	 * (from its reference to the Trait object) to compute a normalized score
	 * for this value.
	 */
	public void computeNormal()
	{
		float min = trait.min;
		float max = trait.max;

		normal = (value - min) / (max - min);

		if (Float.isNaN(normal))
			normal = 0;
	}

	/**
	 * Produces String formatting that can be used when writing a CurlyWhirly
	 * compatible file of phenotype data.
	 */
	public String formatForCurlyWhirly()
	{
		if (isDefined == false)
			return "\t";

		else if (trait.traitIsNumerical())
			return getValue() + "\t";

		else
			return trait.format(this) + "\t";
	}

	/**
	 * Returns the color to be used when graphically displaying the value for
	 * this TV. It may be a color stored specifically for this object, or for
	 * a cateogy within the parent trait, or an override of the low/high color
	 * graients for the entire trait, or finally just the old-style heatmap
	 * color worked out from the global low/high values.
	 */
	public Color displayColor()
	{
		return trait.displayColor(value, normal);
	}

	public Object tableValue()
	{
		if (isDefined() == false)
			return null;

		else if (trait.traitIsNumerical())
			return (double) getValue();
		else
			return trait.format(this);
	}
}