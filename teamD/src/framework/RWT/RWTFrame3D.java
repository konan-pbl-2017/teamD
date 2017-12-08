package framework.RWT;
import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;


public class RWTFrame3D extends JFrame implements KeyListener, MouseInputListener {

	private static final long serialVersionUID = 1L;
	private RWTVirtualController virtualController = new RWTVirtualController();
	private RWTContainer container;
	private Robot robot = null;
	private boolean bShadowCasting = false;
	private boolean bMouseCapture = false;

	public RWTFrame3D() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		GraphicsDevice screen = GraphicsEnvironment.
//	    getLocalGraphicsEnvironment().getDefaultScreenDevice();
//		setUndecorated(true);
//		screen.setFullScreenWindow(this);
//		screen.setDisplayMode(new DisplayMode(800, 600, 32,
//		    DisplayMode.REFRESH_RATE_UNKNOWN));
		
		// �L�[�C�x���g���󂯎�郊�X�i��o�^
		addKeyListener(this);
		
		//�@�C�x���g���󂯎��Ď���ǉ�
		addMouseListener(this);
		
		// �}�E�X�h���b�O�ɂ��C�x���g�̓o�^�� addMouseListener()�ł͎����ł��Ȃ����߁A
		// �}�E�X�̓�����ǂ��K�v������B�܂�A�h���b�O�Ή��ɂ��Ȃ���΂Ȃ�Ȃ��̂�
		// �}�E�X�̃h���b�O�ɂ́AComponent�N���X��addMouseMotionListener()���\�b�h���g��
		addMouseMotionListener(this);
		
		// �e�X�g�̎������A�������s�̃f���A����у}�E�X��L�[�{�[�h���䂪�K�v�ȃA�v���P�[�V�����̂��߂ɁA
		// �l�C�e�B�u�ȃV�X�e�����͂̃C�x���g�𐶐�
		try {
			robot = new Robot();
		} catch (AWTException e) {
		}
		requestFocus();
	}

	public void setContentPane(RWTContainer contentPane) {
		container = contentPane;
		super.setContentPane(contentPane);
		setVisible(true);
		contentPane.repaint();
		requestFocus();
	}
	
	/**
	 * �����_�����O�̊J�n�i�I�t�X�N���[�����[�h�̏ꍇ�̂ݗL���j
	 */
	public void doRender() {
		for (int n = 0; n < container.getNumberOfRWTCanvas3D(); n++) {
			container.getRWTCanvas3D(n).doRender();
		}
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (0 <= e.getKeyCode() && e.getKeyCode() < 256) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
			virtualController.setKeyDown(e.getKeyCode(), true);
			if (virtualController.getVirtualKey(e.getKeyCode()) != null) {
				container.keyPressed(virtualController.getVirtualKey(e.getKeyCode()));
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (0 <= e.getKeyCode() && e.getKeyCode() < 256) {
			virtualController.setKeyDown(e.getKeyCode(), false);
			if (virtualController.getVirtualKey(e.getKeyCode()) != null) {
				container.keyReleased(virtualController.getVirtualKey(e.getKeyCode()));
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public RWTVirtualController getVirtualController() {
		return virtualController;
	}

	public void setShadowCasting(boolean b) {
		bShadowCasting  = b;
	}
	
	public boolean isShadowCasting() {
		return bShadowCasting;
	}
	
	public void setMouseCapture(boolean b) {
		bMouseCapture = b;
	}
	
	public void processEvent(AWTEvent e) {
		super.processEvent(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			virtualController.setMouseButtonDown(0, true);
			break;
		case MouseEvent.BUTTON2:
			virtualController.setMouseButtonDown(1, true);
			break;
		case MouseEvent.BUTTON3:
			virtualController.setMouseButtonDown(2, true);
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			virtualController.setMouseButtonDown(0, false);
			break;
		case MouseEvent.BUTTON2:
			virtualController.setMouseButtonDown(1, false);
			break;
		case MouseEvent.BUTTON3:
			virtualController.setMouseButtonDown(2, false);
			break;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (bMouseCapture ) {
			int cx = getWidth() / 2;
			int cy = getHeight() / 2;
			robot.mouseMove(cx, cy);
			virtualController.moveMousePosition((double)(e.getXOnScreen()- cx) / (double)getWidth(), (double)(e.getYOnScreen() - cy) / (double)getHeight());
		} else {
			virtualController.setMousePosition((double)e.getXOnScreen() / (double)getWidth(), (double)e.getYOnScreen() / (double)getHeight());			
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (bMouseCapture) {
			int cx = getWidth() / 2;
			int cy = getHeight() / 2;
			robot.mouseMove(cx, cy);
			virtualController.moveMousePosition((double)(e.getXOnScreen() - cx) / (double)getWidth(), (double)(e.getYOnScreen() - cy) / (double)getHeight());
		} else {
			virtualController.setMousePosition((double)e.getXOnScreen() / (double)getWidth(), (double)e.getYOnScreen() / (double)getHeight());			
		}
	}
}
