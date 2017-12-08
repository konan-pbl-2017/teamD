package framework.gameMain;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import framework.RWT.RWTCanvas3D;
import framework.RWT.RWTContainer;
import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.RWT.RWTVirtualKey;
import framework.game2D.OvergroundActor2D;
import framework.model3D.Universe;
import framework.physics.PhysicsUtility;
import framework.view3D.CameraAction;

public abstract class SimpleActionGame extends AbstractGame implements
		IGameState {
	protected RWTFrame3D frame;
	protected Universe universe;
	protected CameraAction camera;
	protected OvergroundActor2D actor;
	private IGameState currentState = this;

	public abstract void init(Universe universe);

	public abstract void progress(RWTVirtualController virtualController,
			long interval);

	public abstract OvergroundActor2D getOvergroundActor();

	protected int windowSizeWidth;
	protected int windowSizeHeight;

	protected int viewRangeWidth;
	protected int viewRangeHeight;

	public SimpleActionGame() {
		PhysicsUtility.setGravityDirection(new Vector3d(0.0, 1.0, 0.0));
	}

	@Override
	protected IGameState getCurrentGameState() {
		return currentState;
	}
	
	protected void setCurrentGameState(IGameState state) {
		currentState = state;
	}

	@Override
	public void init(RWTFrame3D frame) {
		// TODO Auto-generated method stub
		this.frame = frame;
		RWTContainer container = createRWTContainer();
		changeContainer(container);

		windowSizeWidth = frame.getWidth();
		windowSizeHeight = frame.getHeight();

		universe = new Universe();
		camera = new CameraAction(universe);
		camera.setRange(50.0);

		// �����̍쐬
		// ����
		AmbientLight amblight = new AmbientLight(new Color3f(1.0f, 1.0f, 1.0f));
		amblight
				.setInfluencingBounds(new BoundingSphere(new Point3d(), 100000.0));
		universe.placeLight(amblight);

		// ���s����
		DirectionalLight dirlight = new DirectionalLight(true, // ����ON/OFF
				new Color3f(1.0f, 1.0f, 1.0f), // ���̐F
				new Vector3f(0.0f, -1.0f, -0.5f) // ���̕����x�N�g��
		);
		dirlight
				.setInfluencingBounds(new BoundingSphere(new Point3d(), 100000.0));
		universe.placeLight(dirlight);

		init(universe);
		container.getPrimaryRWTCanvas3D().attachCamera(camera);
		universe.compile();
	}

	@Override
	public void update(RWTVirtualController virtualController, long interval) {
		progress(virtualController, interval);
		camera.setChaseMode(getOvergroundActor());
		camera.adjust(interval);
	}

	@Override
	public boolean useTimer() {
		// TODO Auto-generated method stub
		return true;
	}

	protected RWTContainer createRWTContainer() {
		return new RWTContainer() {
			RWTCanvas3D canvas;

			@Override
			public void build(GraphicsConfiguration gc) {
				if (gc != null) {
					canvas = new RWTCanvas3D(gc);
				} else {
					canvas = new RWTCanvas3D();
				}
				canvas.setRelativePosition(0.0f, 0.0f);
				canvas.setRelativeSize(1.0f, 1.0f);
				addCanvas(canvas);
				repaint();
			}

			// RWT���ł̓C�x���g���������Ȃ�
			@Override
			public void keyPressed(RWTVirtualKey key) {
			}

			@Override
			public void keyReleased(RWTVirtualKey key) {
			}

			@Override
			public void keyTyped(RWTVirtualKey key) {
			}
		};
	}
	
	protected void changeContainer(RWTContainer container) {
		frame.setContentPane(container);
		GraphicsConfiguration gc = null;
		if (frame.isShadowCasting()) {

			// �e��t����ꍇ
			// �X�e���V���o�b�t�@���g�p���� GraphicsConfiguration �̐���
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			GraphicsConfigTemplate3D gct3D = new GraphicsConfigTemplate3D();
			gct3D.setStencilSize(8);
			gc = gd.getBestConfiguration(gct3D);
		}
		container.build(gc);		
	}


	protected void setViewRange(int width, int height) {
		viewRangeWidth = width;
		viewRangeHeight = height;
		Transform3D t = new Transform3D();
		t.ortho(-1.0 * width / 2.0, width / 2.0, -1.0 * height / 2.0,
				height / 2.0, 0.1, 10000);
		camera.getView().setCompatibilityModeEnable(true);
		camera.getView().setVpcToEc(t);
	};

}
