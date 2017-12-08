package framework.RWT;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.vecmath.Point2f;

/**
 * �摜�ł��B
 * @author Wataru
 *
 */
public class RWTImage extends RWTWidget {
	protected float relativeX = 0.0f;
	protected float relativeY = 0.0f;
	protected float relativeWidth = 0.0f;
	protected float relativeHeight = 0.0f;
	protected int x = 0;
	protected int y = 0;
	protected int width = 0;
	protected int height = 0;	

	private BufferedImage image = null;
	private ImageObserver observer;
	
	private int imageWidth = 0;
	private int imageHeight = 0;
	
	public RWTImage(String fileName) {
		try {
			image = ImageIO.read(new File(fileName));
			setSize(image.getWidth(), image.getHeight());
		} catch (Exception e) {
			e.printStackTrace();
			image = null;
		}
	}
	
	/**
	 * �摜�̑��Έʒu���w�肷��B
	 * @param x x���W�l(0.0f�`1.0f)
	 * @param y y���W�l(0.0f�`1.0f)
	 */
	public void setRelativePosition(float x, float y) {
		relativeX = x;
		relativeY = y;
	}
	
	/**
	 * �摜��ݒ肵�܂��B
	 * @param fileName
	 */
	public void setImage(String fileName) {
		try {
			image = ImageIO.read(new File(fileName));
			setSize(image.getWidth(), image.getHeight());
		} catch (Exception e) {
			e.printStackTrace();
			image = null;
		}
	}
	
	/**
	 * �摜�̃T�C�Y��ݒ肵�܂��B
	 * ���Βl�ł͂���܂���B
	 * @param x
	 * @param y
	 */
	public void setSize(int x, int y) {
		imageWidth = x;
		imageHeight = y;
	}
	
	public void setObserver(ImageObserver o) {
		observer = o;
	}
	
	@Override
	public void adjust(Component parent) {
		int sx = parent.getWidth();
		int sy = parent.getHeight();
		x = (int) (sx * relativeX);
		y = (int) (sy * relativeY);
	}	
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(image, x, y, imageWidth, imageHeight, observer);
	}
}
