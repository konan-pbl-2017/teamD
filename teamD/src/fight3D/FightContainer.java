package fight3D;

import java.awt.GraphicsConfiguration;

import framework.RWT.RWTContainer;
import framework.RWT.RWTVirtualKey;
import framework.model3D.Universe;

public class FightContainer extends RWTContainer {
	private static final long serialVersionUID = -9039006775597199078L;

	private FightCanvas3D canvas;
	private GraphicsConfiguration gc;

	public void build(GraphicsConfiguration gc) {
		this.gc = gc;
	}

	// �t���[���ɒǉ����ăT�C�Y���m�肵����ɒ��g���\�z����
	// canvas�̒ǉ���K�v�ȂƂ��͍s���B
	public void build(Game g, int playerNumber) {
		// ������
		removeAll();

		if (gc != null) {
			canvas = new FightCanvas3D(gc, g, playerNumber);
		} else {
			canvas = new FightCanvas3D(g, playerNumber);			
		}

		canvas.setRelativePosition(0.0f, 0.0f);
		canvas.setRelativeSize(1.0f, 1.0f);
		addCanvas(canvas);

		repaint();
	}

	public FightCanvas3D getCanvas() {
		return canvas;
	}

	@Override
	public void keyPressed(RWTVirtualKey key) {
	}

	@Override
	public void keyReleased(RWTVirtualKey key) {
	}

	@Override
	public void keyTyped(RWTVirtualKey key) {
	}
}
