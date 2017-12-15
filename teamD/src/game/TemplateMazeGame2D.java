package game;

import java.math.BigDecimal;

import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.gameMain.SimpleMazeGame;
import framework.model3D.Universe;

public class TemplateMazeGame2D extends SimpleMazeGame {
	
	double speed = 8.0;
	
	private MazeSpritePlayer Player1;
	private MazeSpritePlayer Player2;
	private MazeStage mazeGround;
	
	// ���x�ɂ���ĕ��̂������Ă��鎞�Ƀ{�^���������邩�ǂ����𔻒肷��t���O
	private boolean disableControl = false;

		private long lastTime;
		
		@Override
		public void init(Universe universe) {
			mazeGround = new MazeStage("data\\images\\block.gif", "data\\images\\Tile.gif");
			universe.place(mazeGround);
			camera.addTarget(mazeGround);
	
			//1P
			Player1 = new MazeSpritePlayer("data\\RPG\\player.png");
			Player1.setPosition(2.0, 2.0);
			Player1.setCollisionRadius(0.5);
			universe.place(Player1);
			
			//2P
			Player2 = new MazeSpritePlayer("data\\RPG\\player.png");
			Player2.setPosition(18.0, 18.0);
			Player2.setCollisionRadius(0.5);
			universe.place(Player2);
		}
	

	@Override
	public void progress(RWTVirtualController virtualController, long interval) {
		// ���H�Q�[���X�e�[�W���\������I�u�W�F�N�g�̈ʒu�ƃv���C���[�̈ʒu�����Ƃɑ��x��0�ɂ��邩�ǂ����𒲂ׂ�B
		boolean resetVelocity = mazeGround.checkGridPoint(Player1);

		//1P
		// �덷�ɂ��ʒu�C�����s�����߁A�v���C���[��x������y������0.0�̎��A�ʒu�̒l��؂�グ��
		if (Player1.getVelocity().getX() == 0.0
				&& Player1.getVelocity().getY() == 0.0) {
			Player1.setPosition(new BigDecimal(Player1
					.getPosition().getX())
			.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue(),
			new BigDecimal(Player1.getPosition().getY())
			.setScale(0, BigDecimal.ROUND_HALF_UP)
			.doubleValue());
		}

		// ���x��0.0�ɂ���t���O�������Ă���΁A���x��0�ɂ���
		if (resetVelocity) {
			Player1.setVelocity(0.0, 0.0);
			disableControl = false;
		}
		
		//2P
		if (Player2.getVelocity().getX() == 0.0
				&& Player2.getVelocity().getY() == 0.0) {
			Player2.setPosition(new BigDecimal(Player2
					.getPosition().getX())
			.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue(),
			new BigDecimal(Player2.getPosition().getY())
			.setScale(0, BigDecimal.ROUND_HALF_UP)
			.doubleValue());
		}

		// ���x��0.0�ɂ���t���O�������Ă���΁A���x��0�ɂ���
		if (resetVelocity) {
			Player2.setVelocity(0.0, 0.0);
			disableControl = false;
		}
		
		// 1P
		// �L�������ړ����Ă��Ȃ���΁A�L�[����̏������s����B
		if(!disableControl){
			// �L�[����̏���
			// ��
			if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
				Player1.setVelocity(-speed, 0.0);
				disableControl = true;
			}
			// �E
			else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
				Player1.setVelocity(speed, 0.0);
				disableControl = true;
			}
			// ��
			else if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
				Player1.setVelocity(0.0, speed);
				disableControl = true;
			}
			// ��
			else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
				Player1.setVelocity(0.0, -speed);
				disableControl = true;
			}
		}
		Player1.motion(interval, mazeGround);
		
		//2P
		if (virtualController.isKeyDown(1, RWTVirtualController.LEFT)) {
			Player2.setVelocity(-speed, 0.0);
			disableControl = true;
		}
		// �E
		else if (virtualController.isKeyDown(1, RWTVirtualController.RIGHT)) {
			Player2.setVelocity(speed, 0.0);
			disableControl = true;
		}
		// ��
		else if (virtualController.isKeyDown(1, RWTVirtualController.UP)) {
			Player2.setVelocity(0.0, speed);
			disableControl = true;
		}
		// ��
		else if (virtualController.isKeyDown(1, RWTVirtualController.DOWN)) {
			Player2.setVelocity(0.0, -speed);
			disableControl = true;
		}
	Player2.motion(interval, mazeGround);
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