package framework.gameMain;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.media.j3d.GraphicsConfigTemplate3D;

import com.sun.j3d.utils.universe.SimpleUniverse;

import framework.RWT.RWTFrame3D;
import framework.RWT.RWTCanvas3D;
import framework.RWT.RWTContainer;
import framework.RWT.RWTVirtualController;
import framework.model3D.Universe;

public abstract class AbstractGameState implements IGameState {
	protected RWTContainer container;
	
	public void init(RWTFrame3D frame) {
		if (container == null) {
			container = createContainer();	// Template���\�b�h�B�f�U�C���p�^�[���B
		}
		frame.setContentPane(container);	// Frame�����ς������ς��ɃR���e�i�\��t����B�R���e�i�̑傫�������߂ĕ�����B
		GraphicsConfiguration gc = null;
		if (frame.isShadowCasting()) {
			// �e��t����ꍇ
			// �X�e���V���o�b�t�@���g�p���� GraphicsConfiguration �̐���
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			GraphicsConfigTemplate3D gct3D = new GraphicsConfigTemplate3D();
			gct3D.setStencilSize(8);
			gc = gd.getBestConfiguration(gct3D);
		}
		container.build(gc);
	}

	public abstract RWTContainer createContainer();
	/* (non-Javadoc)
	 * @see IGameState#useTimer()
	 */
	public abstract boolean useTimer();
	/* (non-Javadoc)
	 * @see IGameState#progress(RWTVirtualController)
	 */
	public abstract void update(RWTVirtualController virtualController, long interval);	
	
	public RWTCanvas3D getRWTCanvas3D() {
		if (container == null) return null;
		return container.getPrimaryRWTCanvas3D();
	}
}
