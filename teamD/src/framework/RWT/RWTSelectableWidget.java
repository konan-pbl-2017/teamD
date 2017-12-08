package framework.RWT;
import java.awt.Dimension;
import java.awt.Point;

/**
 * �A�N�e�B�u�ɂȂ邽�߂ɕK�v�Ȃ��̂ł��B
 * @author Wataru
 *
 */
public interface RWTSelectableWidget {

	public abstract void selected();
	
	public abstract void deselected();
	
	public abstract int getAbsoluteX();

	public abstract int getAbsoluteY();

	public abstract int getAbsoluteWidth();
	
	public abstract int getAbsoluteHeight();
}
