package flapjack.gui.visualization;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import flapjack.data.*;

class ColoredAlleleState
{
	private AlleleState state;

	// AWT representation of this color, plus brighter and darker versions
	private Color color, colorB, colorD;
	private BufferedImage image;

	int w, h;

	ColoredAlleleState(AlleleState state, int w, int h)
	{
		this.state = state;
		this.w = w;
		this.h = h;

		// Don't create colors et al for the UNKNOWN state
		if (state.toString().length() > 0)
		{
			createColor();
			createBuffer();
		}
	}

	private void createBuffer()
	{
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();

//		g.setColor(Color.white);
//		g.fillRect(0, 0, w, h);

		g.setPaint(new GradientPaint(0, 0, colorB, w, h, colorD));

		Rectangle2D.Float r = null;

//		RoundRectangle2D.Float r = new RoundRectangle2D.Float(1, 1, w-1, h-1, 7, 7);
//		Rectangle2D.Float r = new Rectangle2D.Float(0, 0, w, h);

//		if (w > 7 && h > 7)
//			r = new Rectangle2D.Float(1, 1, w-1, h-1);
//		else
			r = new Rectangle2D.Float(0, 0, w, h);

		g.fill(r);


		if (true && h >= 10)
		{
			//Font font = new Font("Monospaced", Font.PLAIN, h);
			Font font = g.getFont().deriveFont(Font.PLAIN, h-3);
			g.setFont(font);
			FontMetrics fm = g.getFontMetrics();

			Rectangle2D bounds = fm.getStringBounds(state.toString(), g);

			g.setColor(Color.black);
			g.drawString(state.toString(),
				(int)((float)w/2-bounds.getWidth()/2),
				h - fm.getMaxDescent());
		}

//		r = new Rectangle2D.Float(0, 0, w-1, h-1);
//		g.setColor(Color.black);
//		g.draw(r);

//		g.fillRect(0, 0, w, h);
		g.dispose();
	}

	public BufferedImage getImage()
		{ return image; }

	private void createColor()
	{
		int value = 0;
		for (int i = 0; i < state.toString().length(); i++)
			value += state.toString().charAt(i);

		java.util.Random rnd = new java.util.Random(value+555);

		int r = rnd.nextInt(255);
		int g = rnd.nextInt(255);
		int b = rnd.nextInt(255);

		color = new Color(r, g, b);
		colorB = color.brighter();
		colorD = color.darker();
	}

	public Color getColor()
		{ return color; }

	public Color getBrightColor()
		{ return colorB; }

	public Color getDarkColor()
		{ return colorD; }
}