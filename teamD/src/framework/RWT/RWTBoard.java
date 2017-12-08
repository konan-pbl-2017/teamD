package framework.RWT;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

public class RWTBoard extends RWTWidget {
	protected float relativeX = 0.0f;
	protected float relativeY = 0.0f;
	protected float relativeWidth = 0.0f;
	protected float relativeHeight = 0.0f;
	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;	
	
	public RWTBoard(float x, float y, float w, float h, Color c) {
		setRelativePosition(x, y);
		setRelativeSize(w, h);
		setColor(c);
	}
	
	/**
	 * ���Έʒu���w�肷��B
	 * @param x x���W�l(0.0f�`1.0f)
	 * @param y y���W�l(0.0f�`1.0f)
	 */
	public void setRelativePosition(float x, float y) {
		relativeX = x;
		relativeY = y;
	}
	/**
	 * ���΃T�C�Y�����肷��B
	 * @param w ��(0.0f�`1.0f)
	 * @param h ����(0.0f�`1.0f)
	 */
	public void setRelativeSize(float w, float h) {
		relativeWidth = w;
		relativeHeight = h;
	}

	@Override
	public void adjust(Component parent) {
		int sx = parent.getWidth();
		int sy = parent.getHeight();
		x = (int) (sx * relativeX);
		y = (int) (sy * relativeY);
		width = (int) (sx * relativeWidth);
		height = (int) (sy * relativeHeight);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.3f));
		g.fillRoundRect(x + 5, y + 5, width, height, 10, 10);
		g.setColor(color);
		g.fillRoundRect(x, y, width, height, 10, 10);
		g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue()));
		g.drawRoundRect(x, y, width, height, 10, 10);
	}

}
