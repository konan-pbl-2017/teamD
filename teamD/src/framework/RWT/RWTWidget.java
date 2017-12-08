package framework.RWT;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.vecmath.Point2f;

/**
 * �e�R���e�i�ɑ΂��đ��΍��W�Ŕz�u�ł���GUI���i
 * @author Wataru
 *
 */
public abstract class RWTWidget {
	protected Color color = Color.BLACK;
	Component parent;
	protected boolean visible = true;
	
	/**
	 * �e�R���e�i�i�L�����o�X�̏ꍇ������j�̑傫���ɍ��킹�Ĉʒu�ƃT�C�Y�𒲐�����
	 * @param parent
	 */
	public abstract void adjust(Component parent);
	public abstract void paint(Graphics g);
	
	public void setParent(Component parent){
		this.parent = parent;
	}
	
	public void repaint(){
		parent.repaint();
	}
	
	/**
	 * �F��ݒ肷��
	 * @param c
	 */
	public void setColor(Color c) {
		color = c;
	}
	
	public void setVisible(boolean b) {
		visible = b;
	}
	
	public boolean isVisible() {
		return visible;
	}
}
