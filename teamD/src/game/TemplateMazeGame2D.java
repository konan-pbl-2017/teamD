package game;

import java.math.BigDecimal;
import java.util.ArrayList;

import template.shooting2D.MyShipBullet;

import framework.RWT.RWTContainer;
import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.game2D.Velocity2D;
import framework.gameMain.IGameState;
import framework.gameMain.SimpleMazeGame;
import framework.model3D.Universe;

public class TemplateMazeGame2D extends SimpleMazeGame {
	private MazeSpritePlayer mazeSpritePlayer;
	private MazeStage mazeGround;
	private ArrayList<MyShipBullet> myShipBulletList = new ArrayList<MyShipBullet>();
	private bome bome;
	private bomb bom;
	private long putt = 0;
	private long expt = 0;
	int bcount = 0;
	
	// 速度によって物体が動いている時にボタンを押せるかどうかを判定するフラグ
	private boolean disableControl = false;
	private boolean bfrag = false;
	private boolean expflag = false;
	boolean displace = false;
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

		mazeSpritePlayer = new MazeSpritePlayer("data\\RPG\\player.png");
		bom = new bomb("data\\images\\MyShip.gif");
        bome = new bome("data\\images\\Enemy.gif");
		mazeSpritePlayer.setPosition(6.0, 2.0);
		mazeSpritePlayer.setCollisionRadius(0.5);
		universe.place(mazeSpritePlayer);
		
	}

	@Override
	public void progress(RWTVirtualController virtualController, long interval) {
		// 迷路ゲームステージを構成するオブジェクトの位置とプレイヤーの位置をもとに速度を0にするかどうかを調べる。
		boolean resetVelocity = mazeGround.checkGridPoint(mazeSpritePlayer);
		// 誤差による位置修正を行うため、プレイヤーのx成分とy成分が0.0の時、位置の値を切り上げる
		if (mazeSpritePlayer.getVelocity().getX() == 0.0
				&& mazeSpritePlayer.getVelocity().getY() == 0.0) {
			mazeSpritePlayer.setPosition(new BigDecimal(mazeSpritePlayer
					.getPosition().getX())
			.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue(),
			new BigDecimal(mazeSpritePlayer.getPosition().getY())
			.setScale(0, BigDecimal.ROUND_HALF_UP)
			.doubleValue());
		}

		// 速度が0.0にするフラグが立っていれば、速度を0にする
		if (resetVelocity) {
			mazeSpritePlayer.setVelocity(0.0, 0.0);
			disableControl = false;
		}
		// キャラが移動していなければ、キー操作の処理を行える。
		if(!disableControl){
			// キー操作の処理
			// 左
			if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
				mazeSpritePlayer.setVelocity(-2.0, 0.0);
				disableControl = true;
			}
			// 右
			else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
				mazeSpritePlayer.setVelocity(2.0, 0.0);
				disableControl = true;
	
			}
			// 上
			else if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
				mazeSpritePlayer.setVelocity(0.0, 2.0);
				disableControl = true;
			}
			// 下
			else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
				mazeSpritePlayer.setVelocity(0.0, -2.0);
				disableControl = true;
			}
			if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)) {
				if(bfrag == false){
					bom.setPosition(mazeSpritePlayer.getPosition().getX(),mazeSpritePlayer.getPosition().getY());
					universe.place(bom);
				}
				bfrag = true;
				disableControl = true;
				expflag = false;
			}
			if (System.currentTimeMillis() - putt > 4000&&bfrag == true) {
				universe.displace(bom);
				putt = System.currentTimeMillis();
				expflag = true;			
				bfrag = false;
			}
			
			if(expflag == true&&System.currentTimeMillis() - expt == 6000){
				//System.out.println("ばーーーーーーーん");
				bome.setPosition(bom.getPosition().getX(),bom.getPosition().getY());
				universe.place(bome);
				expflag = false;
				expt = System.currentTimeMillis();
				displace = true;
			}
			if(System.currentTimeMillis() - expt >= 5000&&displace ==true){
				universe.place(bome);
				expflag = false;
				bfrag = false;
				disableControl = true;
				expt = System.currentTimeMillis();
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
