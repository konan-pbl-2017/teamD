package framework.RWT;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.vecmath.Point2f;

/**
 * �������\��������̂ł��B
 * @author Wataru
 *
 */
public class RWTLabel extends RWTWidget {
	private float relativeBaselineX = 0.0f;
	private float relativeBaselineY = 0.0f;
	private float relativeWidth = 0.0f;
	private float relativeHeight = 0.0f;
	private Font relativeFont = new Font("", Font.PLAIN, 12);
	
	private int baselineX = 0;
	private int baselineY = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;	
	protected Font font = new Font("", Font.PLAIN, 12);

	protected String[] strings;
	protected int drawMode = DRAW_RIGHT;
	
	public static final int DEFAULT_PARENT_WIDTH_IN_POINT = 500;
	
	public static final int DRAW_CENTER = 0;
	public static final int DRAW_RIGHT = 1;
	public static final int DRAW_LEFT = 2;
	
	public static final String NEW_PARAGRAPH = "#";
	
	public RWTLabel() {
		strings = new String[1];
		strings[0] = "";		
	}
	
	public RWTLabel(float x, float y, String s, Color c, Font f) {
		setRelativePosition(x, y);
		setString(s);
		setColor(c);
		setFont(f);
	}
	
	/**
	 * ���Έʒu���w�肷��B
	 * @param x x���W�l(0.0f�`1.0f)
	 * @param y y���W�l(0.0f�`1.0f)
	 */
	public void setRelativePosition(float x, float y) {
		relativeBaselineX = x;
		relativeBaselineY = y;
	}
	
	/**
	 * ���Έʒu���w�肷��B
	 * @param x x���W�l(0.0f�`1.0f)
	 * @param y y���W�l(0.0f�`1.0f)
	 * @param m �\�����@
	 */
	public void setRelativePosition(float x, float y, int m) {
		setRelativePosition(x, y);
		drawMode = m;
	}
	
	/**
	 * �\�����镶�����ݒ肷��B
	 * @param s
	 */
	public void setString(String s) {
		if(s != null) {
			strings = s.split(NEW_PARAGRAPH);
		}
	}
	
	/**
	 * �\�����镶����̃t�H���g��ݒ肷��B�������A�t�H���g�̃T�C�Y�̓L�����o�X�̕���500pt�ł���Ƃ��̂��̂Ƃ���B
	 * @param f
	 */
	public void setFont(Font f) {
		font = f;
		relativeFont = f;
	}
	
	public int getAbsoluteHeight() {
		return height;
	}

	public int getAbsoluteWidth() {
		return width;
	}

	public int getAbsoluteX() {
		return baselineX;
	}

	public int getAbsoluteY() {
		return y;
	}

	@Override
	public void adjust(Component parent) {
		int sx = parent.getWidth();
		int sy = parent.getHeight();
		baselineX = (int) (sx * relativeBaselineX);
		baselineY = (int) (sy * relativeBaselineY);
		font = new Font(relativeFont.getName(), 
				relativeFont.getStyle(), 
				(int)(relativeFont.getSize() * sx / DEFAULT_PARENT_WIDTH_IN_POINT));
		
		FontMetrics fm = parent.getFontMetrics(font);
		y = baselineY - fm.getAscent() + (int)(fm.getDescent() * 1.1);
		width = fm.stringWidth(strings[0]);		// ��ԏ�̍s�̕��i���{���͍ő啝�ɂ��ׂ��j
		height = fm.getAscent();
	}
	
	@Override
	public void paint(Graphics g) {
		FontMetrics fm = g.getFontMetrics(font);
		g.setColor(color);
		g.setFont(font);
		int height = fm.getHeight();
		int h = 0;
		for(int i = 0; i < strings.length; i++){
			int top = 0;
			if(drawMode == DRAW_CENTER) {
				top = fm.stringWidth(strings[i]) / 2;
			}
			else if(drawMode == DRAW_LEFT) {
				top = fm.stringWidth(strings[i]);
			}

			if (strings[i] != null && strings[i].length() > 0) {
				g.drawString(strings[i], baselineX - top, baselineY + h);
			}
			
			h += height;
		}
	}
}
