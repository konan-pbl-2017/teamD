package game;

import java.math.BigDecimal;

import framework.RWT.RWTContainer;
import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.gameMain.IGameState;
import framework.gameMain.SimpleMazeGame;
import framework.model3D.Universe;

public class TemplateMazeGame2D extends SimpleMazeGame {
	
	double speed = 5.0;
	
	private MazeSpritePlayer Player1;
	private MazeSpritePlayer Player2;
	private MazeStage mazeGround;
	private bombe bome;
	private bomb bom;
	private bombe bome2;
	private bomb bom2;
	// ���x�ɂ���ĕ��̂������Ă��鎞�Ƀ{�^���������邩�ǂ����𔻒肷��t���O
	private boolean disableControl1 = false;
	private boolean disableControl2 = false;
	private boolean bfrag = false;
	private boolean bfrag2 = false;
	private boolean expflag = false;
	private boolean displace = false;
	private boolean displace2 = false;
	private IGameState startGameState = null;
	private IGameState endingGameState = null;
	private long putt = 0;
	private long expt = 0;
	private long lastTime;
	int up = 0, down = 1, left = 2, right = 3;
	int muki = 0;
	
	public TemplateMazeGame2D() {
		startGameState = new IGameState() {
			@Override
			public void init(RWTFrame3D frame) {
				TemplateMazeGame2D.this.mainFrame = frame;
				RWTContainer container = new StartContainer(TemplateMazeGame2D.this);
				changeContainer(container);
			}
			@Override
			public boolean useTimer() {
				return false;
			}
			@Override
			public void update(RWTVirtualController virtualController, long interval) {
			}
		};
		endingGameState = new IGameState() {
			@Override
			public void init(RWTFrame3D frame) {
				TemplateMazeGame2D.this.mainFrame = frame;
				RWTContainer container = new EndingContainer(TemplateMazeGame2D.this);
				changeContainer(container);
			}
			@Override
			public boolean useTimer() {
				return false;
			}
			@Override
			public void update(RWTVirtualController virtualController, long interval) {
			}
		};
		setCurrentGameState(startGameState);
	}
	
	@Override
	public void init(Universe universe) {
		mazeGround = new MazeStage();
		universe.place(mazeGround);
		camera.addTarget(mazeGround);

		//1P
		Player1 = new MazeSpritePlayer("data\\images\\santa3\\�T���^��.png");
		Player1.setPosition(2.0, 2.0);
		Player1.setCollisionRadius(0.5);
		universe.place(Player1);
		
		//2P
		Player2 = new MazeSpritePlayer("data\\images\\player2\\�R�X��.png");
		Player2.setPosition(22.0, 18.0);
		Player2.setCollisionRadius(0.5);
		universe.place(Player2);
		
		//���e
		bom = new bomb("data\\images\\bakudan\\���e.png");
        bome = new bombe("data\\images\\bakudan\\����.png");
        bom2 = new bomb("data\\images\\bakudan\\���e.png");
        bome2 = new bombe("data\\images\\bakudan\\����.png");
        
	}

	@Override
	public void progress(RWTVirtualController virtualController, long interval) {
		// ���H�Q�[���X�e�[�W���\������I�u�W�F�N�g�̈ʒu�ƃv���C���[�̈ʒu�����Ƃɑ��x��0�ɂ��邩�ǂ����𒲂ׂ�B
		boolean resetVelocity = mazeGround.checkGridPoint(Player1, Player2);

		// �덷�ɂ��ʒu�C�����s�����߁A�v���C���[��x������y������0.0�̎��A�ʒu�̒l��؂�グ��
		//1P
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
			Player1.setPosition(Player1.getPosition().getX(),Player1.getPosition().getY());
			Player1.setVelocity(0.0, 0.0);
			disableControl1 = false;
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
			Player2.setPosition(Player2.getPosition().getX(),Player2.getPosition().getY());
			Player2.setVelocity(0.0, 0.0);
			disableControl2 = false;
		}
		// �L�������ړ����Ă��Ȃ���΁A�L�[����̏������s����B
		// 1P
				// �L�������ړ����Ă��Ȃ���΁A�L�[����̏������s����B
				if(!disableControl1){
					// �L�[����̏���
					// �� : a
					if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
						Player1.setVelocity(-speed, 0.0);
						Player1.setImage("data\\images\\santa3\\�T���^��.png");
						muki =left;
						disableControl1 = true;
					}
					// �E : d
					else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
						Player1.setVelocity(speed, 0.0);
						Player1.setImage("data\\images\\santa3\\�T���^�E.png");
						muki =right;
						disableControl1 = true;
					}
					// �� : w
					else if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
						Player1.setVelocity(0.0, speed);
						Player1.setImage("data\\images\\santa3\\�T���^��.png");
						muki =up;
						disableControl1 = true;
					}
					// �� : s
					else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
						Player1.setVelocity(0.0, -speed);
						Player1.setImage("data\\images\\santa3\\�T���^��.png");
						muki =down;
						disableControl1 = true;
					}
					if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_B)) {
						if(bfrag == false){
							bom.setPosition(Player1.getPosition().getX(),Player1.getPosition().getY());
							universe.place(bom);
							putt = System.currentTimeMillis();
						}
						bfrag = true;
						disableControl1 = true;
						expflag = false;
					}
					
					if (System.currentTimeMillis() - putt > 4000&&bfrag == true) {
						universe.displace(bom);			
						bfrag = false;
						bome.setPosition(bom.getPosition().getX(),bom.getPosition().getY());
						universe.place(bome);
						expt = System.currentTimeMillis();
						displace = true;
					}
					
				
					if(System.currentTimeMillis() - expt >= 1000&&displace ==true){
						universe.displace(bome);
						bfrag = false;
						disableControl1 = true;
						expt = System.currentTimeMillis();
						displace = false;
					}
					//�{����v�Œu���Ĕ��j
					
				Player1.motion(interval, mazeGround);
				}
				//2P
				// �� : k
				if (virtualController.isKeyDown(1, RWTVirtualController.LEFT)) {
					Player2.setVelocity(-speed, 0.0);
					Player2.setImage("data\\images\\player2\\�R�X��.png");
					muki = left;
					disableControl2 = true;
				}
				// �E : ;
				else if (virtualController.isKeyDown(1, RWTVirtualController.RIGHT)) {
					Player2.setVelocity(speed, 0.0);
					Player2.setImage("data\\images\\player2\\�R�X�E.png");
					muki = right;
					disableControl2 = true;
				}
				// �� : o
				else if (virtualController.isKeyDown(1, RWTVirtualController.UP)) {
					Player2.setVelocity(0.0, speed);
					Player2.setImage("data\\images\\player2\\�R�X��.png");
					muki = up;
					disableControl2 = true;
				}
				// �� : l
				else if (virtualController.isKeyDown(1, RWTVirtualController.DOWN)) {
					Player2.setVelocity(0.0, -speed);
					Player2.setImage("data\\images\\player2\\�R�X��.png");
					muki = down;
					disableControl2 = true;
				}
				if (virtualController.isKeyDown(1, RWTVirtualController.BUTTON_A)) {
					if(bfrag2 == false){
						bom2.setPosition(Player2.getPosition().getX(),Player2.getPosition().getY());
						universe.place(bom2);
						putt = System.currentTimeMillis();
					}
					bfrag2 = true;
					disableControl1 = true;
					expflag = false;
				}
				
				if (System.currentTimeMillis() - putt > 4000&&bfrag2 == true) {
					universe.displace(bom2);			
					bfrag2 = false;
					bome2.setPosition(bom2.getPosition().getX(),bom2.getPosition().getY());
					universe.place(bome2);
					expt = System.currentTimeMillis();
					displace2 = true;
				}
				
			
				if(System.currentTimeMillis() - expt >= 1000&&displace2 ==true){
					universe.displace(bome2);
					bfrag2 = false;
					disableControl1 = true;
					expt = System.currentTimeMillis();
					displace2 = false;
				}
				//�{����shift�Œu���Ĕ��j
				
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
		
	public void restart() {
		stop();
		setCurrentGameState(startGameState);
		start();
	}
	
	public void play() {
		stop();
		setCurrentGameState(this);
		start();
	}
	
	public void ending() {
		stop();
		setCurrentGameState(endingGameState);
		start();
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
