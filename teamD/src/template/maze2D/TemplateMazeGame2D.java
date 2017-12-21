package template.maze2D;

import java.math.BigDecimal;

import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.game2D.Position2D;
import framework.gameMain.SimpleMazeGame;
import framework.model3D.Universe;

public class TemplateMazeGame2D extends SimpleMazeGame {
	private MazeSpritePlayer mazeSpritePlayer;
	private MazeStage mazeGround;
	
	// ���x�ɂ���ĕ��̂������Ă��鎞�Ƀ{�^���������邩�ǂ����𔻒肷��t���O
	private boolean disableControl = false;

	private long lastTime;
	
	@Override
	public void init(Universe universe) {
		mazeGround = new MazeStage("data\\images\\block.gif", "data\\images\\Tile.gif");
		universe.place(mazeGround);
		camera.addTarget(mazeGround);

		mazeSpritePlayer = new MazeSpritePlayer("data\\RPG\\player.png");
		mazeSpritePlayer.setPosition(6.0, 2.0);
		mazeSpritePlayer.setCollisionRadius(0.5);
		universe.place(mazeSpritePlayer);
	}

	@Override
	public void progress(RWTVirtualController virtualController, long interval) {
		// ���H�Q�[���X�e�[�W���\������I�u�W�F�N�g�̈ʒu�ƃv���C���[�̈ʒu�����Ƃɑ��x��0�ɂ��邩�ǂ����𒲂ׂ�B
		Position2D gridPoint = mazeGround.getNeighborGridPoint(mazeSpritePlayer);

		// ���x��0�ɂ���t���O�������Ă���΁A���x��0�ɂ���
		if (gridPoint != null) {
			mazeSpritePlayer.setPosition(gridPoint);
			mazeSpritePlayer.setVelocity(0.0, 0.0);
			disableControl = false;
		}
		
		// �L�������ړ����Ă��Ȃ���΁A�L�[����̏������s����B
		if(!disableControl){
			// �L�[����̏���
			// ��
			if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
				mazeSpritePlayer.setVelocity(-2.0, 0.0);
				disableControl = true;
			}
			// �E
			else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
				mazeSpritePlayer.setVelocity(2.0, 0.0);
				disableControl = true;
	
			}
			// ��
			else if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
				mazeSpritePlayer.setVelocity(0.0, 2.0);
				disableControl = true;
			}
			// ��
			else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
				mazeSpritePlayer.setVelocity(0.0, -2.0);
				disableControl = true;
			}
		}
		mazeSpritePlayer.motion(interval, mazeGround);
	}

	// public void progress(RWTVirtualController virtualController, long
	// interval) {
	// velocityFlg = mazeGround.checkVelocityZero(mazeSpritePlayer);
	// if (velocityFlg) {
	// mazeSpritePlayer.setVelocity(0.0, 0.0);
	// }
	// // �L�[����̏���
	// // ��
	// if (!bMove) {
	// if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
	// // mazeSpritePlayer.setVelocity(-2.0, 0.0);
	// mazeSpritePlayer.setPosition(mazeSpritePlayer.getPosition()
	// .getX() - 2.0, mazeSpritePlayer.getPosition().getY());
	// // ��
	// if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
	// mazeSpritePlayer.addVelocity(0.0, 2.0);
	// }
	// // ��
	// else if (virtualController.isKeyDown(0,
	// RWTVirtualController.DOWN)) {
	// mazeSpritePlayer.addVelocity(0.0, -2.0);
	// }
	// bMove = true;
	// }
	// // �E
	// else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
	// // mazeSpritePlayer.setVelocity(2.0, 0.0);
	// mazeSpritePlayer.setPosition(mazeSpritePlayer.getPosition()
	// .getX() + 2.0, mazeSpritePlayer.getPosition().getY());
	//
	// // ��
	// if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
	// mazeSpritePlayer.addVelocity(0.0, 2.0);
	// }
	// // ��
	// else if (virtualController.isKeyDown(0,
	// RWTVirtualController.DOWN)) {
	// mazeSpritePlayer.addVelocity(0.0, -2.0);
	// }
	// bMove = true;
	// }
	// // ��
	// else if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
	// // mazeSpritePlayer.setVelocity(0.0, 2.0);
	// mazeSpritePlayer.setPosition(mazeSpritePlayer.getPosition()
	// .getX(), mazeSpritePlayer.getPosition().getY() + 2.0);
	//
	// // ��
	// if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
	// mazeSpritePlayer.addVelocity(-2.0, 0.0);
	// }
	// // �E
	// else if (virtualController.isKeyDown(0,
	// RWTVirtualController.RIGHT)) {
	// mazeSpritePlayer.addVelocity(2.0, 0.0);
	// }
	// bMove = true;
	// }
	// // ��
	// else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
	// // mazeSpritePlayer.setVelocity(0.0, -2.0);
	// mazeSpritePlayer.setPosition(mazeSpritePlayer.getPosition()
	// .getX(), mazeSpritePlayer.getPosition().getY() - 2.0);
	//
	// // ��
	// if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
	// mazeSpritePlayer.addVelocity(-2.0, 0.0);
	// }
	// // �E
	// else if (virtualController.isKeyDown(0,
	// RWTVirtualController.RIGHT)) {
	// mazeSpritePlayer.addVelocity(2.0, 0.0);
	// }
	// bMove = true;
	// }
	// lastTime = System.currentTimeMillis();
	// }
	//
	// if (System.currentTimeMillis() - lastTime > 250) {
	// bMove = false;
	// }
	// mazeSpritePlayer.motion(interval, mazeGround);
	// }

	@Override
	public RWTFrame3D createFrame3D() {
		RWTFrame3D f = new RWTFrame3D();
		f.setSize(800, 800);
		f.setTitle("Template for Mage 2DGame");
		return f;
	}

	/**
	 * �Q�[���̃��C��
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TemplateMazeGame2D game = new TemplateMazeGame2D();
		game.setFramePolicy(5, 33, false);
		game.start();
	}

}
