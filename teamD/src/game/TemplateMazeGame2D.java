package game;

import java.math.BigDecimal;

import framework.RWT.RWTContainer;
import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.gameMain.IGameState;
import framework.gameMain.SimpleMazeGame;
import framework.model3D.Universe;

public class TemplateMazeGame2D extends SimpleMazeGame {
	
	double speed = 8.0;
	
	private MazeSpritePlayer Player1;
	private MazeSpritePlayer Player2;
	private MazeStage mazeGround;
	
	// 速度によって物体が動いている時にボタンを押せるかどうかを判定するフラグ
	private boolean disableControl1 = false;
	private boolean disableControl2 = false;
	private IGameState startGameState = null;
	private IGameState endingGameState = null;

	private long lastTime;
	
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
		Player1 = new MazeSpritePlayer("data\\images\\santa3\\サンタ下.png");
		Player1.setPosition(2.0, 2.0);
		Player1.setCollisionRadius(0.5);
		universe.place(Player1);
		
		//2P
		Player2 = new MazeSpritePlayer("data\\images\\santa3\\サンタ下.png");
		Player2.setPosition(18.0, 18.0);
		Player2.setCollisionRadius(0.5);
		universe.place(Player2);
	}

	@Override
	public void progress(RWTVirtualController virtualController, long interval) {
		// 迷路ゲームステージを構成するオブジェクトの位置とプレイヤーの位置をもとに速度を0にするかどうかを調べる。
		boolean resetVelocity = mazeGround.checkGridPoint(Player1, Player2);

		// 誤差による位置修正を行うため、プレイヤーのx成分とy成分が0.0の時、位置の値を切り上げる
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

		// 速度が0.0にするフラグが立っていれば、速度を0にする
		if (resetVelocity) {
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

		// 速度が0.0にするフラグが立っていれば、速度を0にする
		if (resetVelocity) {
			Player2.setVelocity(0.0, 0.0);
			disableControl2 = false;
		}
		// キャラが移動していなければ、キー操作の処理を行える。
		// 1P
				// キャラが移動していなければ、キー操作の処理を行える。
				if(!disableControl1){
					// キー操作の処理
					// 左 : a
					if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
						Player1.setVelocity(-speed, 0.0);
						disableControl1 = true;
					}
					// 右 : d
					else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
						Player1.setVelocity(speed, 0.0);
						disableControl1 = true;
					}
					// 上 : w
					else if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
						Player1.setVelocity(0.0, speed);
						disableControl1 = true;
					}
					// 下 : s
					else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
						Player1.setVelocity(0.0, -speed);
						disableControl1 = true;
					}
				}
				Player1.motion(interval, mazeGround);
				
				//2P
				// 左 : k
				if (virtualController.isKeyDown(1, RWTVirtualController.LEFT)) {
					Player2.setVelocity(-speed, 0.0);
					disableControl2 = true;
				}
				// 右 : ;
				else if (virtualController.isKeyDown(1, RWTVirtualController.RIGHT)) {
					Player2.setVelocity(speed, 0.0);
					disableControl2 = true;
				}
				// 上 : o
				else if (virtualController.isKeyDown(1, RWTVirtualController.UP)) {
					Player2.setVelocity(0.0, speed);
					disableControl2 = true;
				}
				// 下 : l
				else if (virtualController.isKeyDown(1, RWTVirtualController.DOWN)) {
					Player2.setVelocity(0.0, -speed);
					disableControl2 = true;
				}
			Player2.motion(interval, mazeGround);
			}

	// public void progress(RWTVirtualController virtualController, long
	// interval) {
	// velocityFlg = mazeGround.checkVelocityZero(mazeSpritePlayer);
	// if (velocityFlg) {
	// mazeSpritePlayer.setVelocity(0.0, 0.0);
	// }
	// // キー操作の処理
	// // 左
	// if (!bMove) {
	// if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
	// // mazeSpritePlayer.setVelocity(-2.0, 0.0);
	// mazeSpritePlayer.setPosition(mazeSpritePlayer.getPosition()
	// .getX() - 2.0, mazeSpritePlayer.getPosition().getY());
	// // 上
	// if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
	// mazeSpritePlayer.addVelocity(0.0, 2.0);
	// }
	// // 下
	// else if (virtualController.isKeyDown(0,
	// RWTVirtualController.DOWN)) {
	// mazeSpritePlayer.addVelocity(0.0, -2.0);
	// }
	// bMove = true;
	// }
	// // 右
	// else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
	// // mazeSpritePlayer.setVelocity(2.0, 0.0);
	// mazeSpritePlayer.setPosition(mazeSpritePlayer.getPosition()
	// .getX() + 2.0, mazeSpritePlayer.getPosition().getY());
	//
	// // 上
	// if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
	// mazeSpritePlayer.addVelocity(0.0, 2.0);
	// }
	// // 下
	// else if (virtualController.isKeyDown(0,
	// RWTVirtualController.DOWN)) {
	// mazeSpritePlayer.addVelocity(0.0, -2.0);
	// }
	// bMove = true;
	// }
	// // 上
	// else if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
	// // mazeSpritePlayer.setVelocity(0.0, 2.0);
	// mazeSpritePlayer.setPosition(mazeSpritePlayer.getPosition()
	// .getX(), mazeSpritePlayer.getPosition().getY() + 2.0);
	//
	// // 左
	// if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
	// mazeSpritePlayer.addVelocity(-2.0, 0.0);
	// }
	// // 右
	// else if (virtualController.isKeyDown(0,
	// RWTVirtualController.RIGHT)) {
	// mazeSpritePlayer.addVelocity(2.0, 0.0);
	// }
	// bMove = true;
	// }
	// // 下
	// else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
	// // mazeSpritePlayer.setVelocity(0.0, -2.0);
	// mazeSpritePlayer.setPosition(mazeSpritePlayer.getPosition()
	// .getX(), mazeSpritePlayer.getPosition().getY() - 2.0);
	//
	// // 左
	// if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
	// mazeSpritePlayer.addVelocity(-2.0, 0.0);
	// }
	// // 右
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
	 * ゲームのメイン
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TemplateMazeGame2D game = new TemplateMazeGame2D();
		game.setFramePolicy(5, 33, false);
		game.start();
	}
}
