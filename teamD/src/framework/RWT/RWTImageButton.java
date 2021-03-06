package framework.RWT;
/**
 * 画像のボタンです。
 * @author Wataru
 *
 */
public class RWTImageButton extends RWTImage implements RWTSelectableWidget {

	public RWTImageButton(String fileName) {
		super(fileName);
	}

	public void selected() {
	}

	public void buttonDown() {
	}

	public void buttonUp() {
	}

	public void deselected() {
	}

	@Override
	public int getAbsoluteHeight() {
		return height;
	}

	@Override
	public int getAbsoluteWidth() {
		return width;
	}

	@Override
	public int getAbsoluteX() {
		return x;
	}

	@Override
	public int getAbsoluteY() {
		return y;
	}
}
