package framework.RWT;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

/**
 * �{�^���ł��B
 * @author Wataru
 * 
 */
public class RWTButton extends RWTLabel implements RWTSelectableWidget {
	private float relativeX = 0.0f;
	private float relativeY = 0.0f;
	private float relativeWidth = 0.12f;
	private float relativeHeight = 0.06f;
	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;	
	private String label;

	
	public RWTButton(String s){
		label = s;
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

		// ���O�C���{�^���̃f�U�C��
		 //�F�̐ݒ�
		g.setColor(Color.GRAY);
		 //�w��͈͓h��Ԃ�
		g.fillRect(x+1, y+1, width, height);
		g.setColor(Color.BLACK);
		 //�w��͈́H�ɘg���H��`��
		g.drawRect(x+1, y+1, width, height);
		g.setFont(font);
		 //�{�^�����ɕ�������v�����g
		g.drawString(label, x+7, y+height-5);//(������,�����J�n�̍�����x���W,�����J�n�̍�����y���W)
		
	}

	@Override
	public int getAbsoluteHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public int getAbsoluteWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public int getAbsoluteX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public int getAbsoluteY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public void setRelativePosition(float x, float y) {
		// TODO Auto-generated method stub
		relativeX = x;
		relativeY = y;
	}

	public void setRelativeWidth(float relativeWidth) {
		this.relativeWidth = relativeWidth;
	}

	public void setRelativeHeight(float relativeHeight) {
		this.relativeHeight = relativeHeight;
	}

	@Override
	public void deselected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selected() {
		// TODO Auto-generated method stub
		
	}
	
	public void setLabel(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
}
