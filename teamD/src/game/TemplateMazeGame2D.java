package game;

import java.math.BigDecimal;

import framework.RWT.RWTContainer;
import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.game2D.Position2D;
import framework.game2D.Sprite;
import framework.gameMain.IGameState;
import framework.gameMain.SimpleMazeGame;
import framework.model3D.Universe;

public class TemplateMazeGame2D extends SimpleMazeGame {

	double speed = 5.0;

	private MazeSpritePlayer Player1;
	private MazeSpritePlayer P1down; 
	private MazeSpritePlayer Player2;
	private MazeSpritePlayer P2down;
	private MazeStage mazeGround;
	private bombe bome[] = new bombe[5];
	private bomb bom;
	private bombe bome2[] = new bombe[5];
	private bomb bom2;
	// ���x�ɂ���ĕ��̂������Ă��鎞�Ƀ{�^���������邩�ǂ����𔻒肷��t���O
	private boolean disableControl1 = false;
	private boolean disableControl2 = false;
	private boolean bfrag = false;
	private boolean bfrag2 = false;
	private boolean expflag = false;
	private boolean displace = false;
	private boolean displace2 = false;
	private boolean down = false;
	private boolean down2 = false;
    private boolean end = false;
	private IGameState startGameState = null;
	private IGameState endingGameState = null;
	private long putt = 0;
	private long expt = 0;
	private long downt = 0;
	private long downt2 = 0; 
	private long lastTime;
	int i = 0;
	int up = 0, down1 = 1, left = 2, right = 3;
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

		P1down = new MazeSpritePlayer("data\\images\\santa3\\�T���^�|��.png");
		P2down = new MazeSpritePlayer("data\\images\\player2\\�R�X�|��.png");
		//2P
		Player2 = new MazeSpritePlayer("data\\images\\player2\\�R�X��.png");
		Player2.setPosition(18.0, 18.0);
		Player2.setCollisionRadius(0.5);
		universe.place(Player2);

		//���e
		bom = new bomb("data\\images\\bakudan\\���e.png");
		for(i = 0;i<5;i++){
			if(i==0){
				bome[i] = new bombe("data\\images\\bakudan\\����.png"); 
			}else if(i ==1 ){
				bome[i] = new bombe("data\\images\\bakudan\\����.png"); 
			}else if(i ==2 ){
				bome[i] = new bombe("data\\images\\bakudan\\����.png"); 
			}else if(i ==3 ){
				bome[i] = new bombe("data\\images\\bakudan\\����.png");
			}else if(i ==4 ){
				bome[i] = new bombe("data\\images\\bakudan\\���E.png"); 
			}
		}
		bom2 = new bomb("data\\images\\bakudan\\���e.png");
		for(i = 0;i<5;i++){
			if(i==0){
				bome2[i] = new bombe("data\\images\\bakudan\\����.png"); 
			}else if(i ==1 ){
				bome2[i] = new bombe("data\\images\\bakudan\\����.png"); 
			}else if(i ==2 ){
				bome2[i] = new bombe("data\\images\\bakudan\\����.png"); 
			}else if(i ==3 ){
				bome2[i] = new bombe("data\\images\\bakudan\\����.png");
			}else if(i ==4 ){
				bome2[i] = new bombe("data\\images\\bakudan\\���E.png"); 
			}
		}
	}

	@Override
	public void progress(RWTVirtualController virtualController, long interval) {
		Position2D gridPoint1 = mazeGround.getNeighborGridPoint(Player1);

		// ���x��0�ɂ���t���O�������Ă���΁A���x��0�ɂ���
		if (gridPoint1 != null) {
			Player1.setPosition(gridPoint1);
			Player1.setVelocity(0.0, 0.0);
			disableControl1 = false;
System.out.println("player1 grid");
		}
		
		Position2D gridPoint2 = mazeGround.getNeighborGridPoint(Player2);

		// ���x��0�ɂ���t���O�������Ă���΁A���x��0�ɂ���
		if (gridPoint2 != null) {
			Player2.setPosition(gridPoint2);
			Player2.setVelocity(0.0, 0.0);
			disableControl2 = false;
System.out.println("player2 grid");
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
				muki = left;
				disableControl1 = true;
System.out.println("player1 ��");
			}
			// �E : d
			else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
				Player1.setVelocity(speed, 0.0);
				Player1.setImage("data\\images\\santa3\\�T���^�E.png");
				muki = right;
				disableControl1 = true;
System.out.println("player1 �E");
			}
			// �� : w
			else if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
				Player1.setVelocity(0.0, speed);
				Player1.setImage("data\\images\\santa3\\�T���^��.png");
				muki = up;
				disableControl1 = true;
System.out.println("player1 ��");
			}
			// �� : s
			else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
				Player1.setVelocity(0.0, -speed);
				Player1.setImage("data\\images\\santa3\\�T���^��.png");
				muki = down1;
				disableControl1 = true;
System.out.println("player1 ��");
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
System.out.println("player1 ���e");
			}
		}

		if (System.currentTimeMillis() - putt > 4000&&bfrag == true) {
			universe.displace(bom);			
			bfrag = false;
			for(i=0;i<5;i++){
				if(i == 0){
					bome[i].setPosition(bom.getPosition().getX(),bom.getPosition().getY());
					universe.place(bome[i]);
				}else if(i == 1){
					bome[i].setPosition(bom.getPosition().getX(),bom.getPosition().getY()-2);
					universe.place(bome[i]);
				}else if(i == 2){
					bome[i].setPosition(bom.getPosition().getX()-2,bom.getPosition().getY());
					universe.place(bome[i]);
				}else if(i == 3){
					bome[i].setPosition(bom.getPosition().getX(),bom.getPosition().getY()+2);
					universe.place(bome[i]);
				}else if(i == 4){
					bome[i].setPosition(bom.getPosition().getX()+2,bom.getPosition().getY());
					universe.place(bome[i]);
				}
				if(Player2.getPosition().getX()-bome[i].getPosition().getX()<2&&-2<Player2.getPosition().getX()-bome[i].getPosition().getX()&&Player2.getPosition().getY()-bome[i].getPosition().getY()<2&&-2<Player2.getPosition().getY()-bome[i].getPosition().getY()){
					universe.displace(Player2);
					P2down.setPosition(Player2.getPosition().getX(),Player2.getPosition().getY());
					universe.place(P2down);
					downt2 = System.currentTimeMillis();
					down2 =true;
				}else if(Player1.getPosition().getX()-bome[i].getPosition().getX()<2&&-2<Player1.getPosition().getX()-bome[i].getPosition().getX()&&Player1.getPosition().getY()-bome[i].getPosition().getY()<2&&-2<Player1.getPosition().getY()-bome[i].getPosition().getY()){
					universe.displace(Player1);
					P1down.setPosition(Player1.getPosition().getX(),Player1.getPosition().getY());
					universe.place(P1down);
					downt = System.currentTimeMillis();
					down =true;
				}
			}


			expt = System.currentTimeMillis();
			displace = true;
		}


		if(System.currentTimeMillis() - expt >= 1000&&displace ==true){
			for(i=0;i<5;i++){
				universe.displace(bome[i]);
			}
			
			bfrag = false;
			disableControl1 = true;
			expt = System.currentTimeMillis();
			displace = false;
		}
		//�{����v�Œu���Ĕ��j

		Player1.motion(interval, mazeGround);
System.out.println("player1 �ړ�");
			
		// �L�������ړ����Ă��Ȃ���΁A�L�[����̏������s����B
		if(!disableControl2){
			//2P
			// �� : k
			if (virtualController.isKeyDown(1, RWTVirtualController.LEFT)) {
				Player2.setVelocity(-speed, 0.0);
				Player2.setImage("data\\images\\player2\\�R�X��.png");
				muki = left;
				disableControl2 = true;
System.out.println("player2 ��");
			}
			// �E : ;
			else if (virtualController.isKeyDown(1, RWTVirtualController.RIGHT)) {
				Player2.setVelocity(speed, 0.0);
				Player2.setImage("data\\images\\player2\\�R�X�E.png");
				muki = right;
				disableControl2 = true;
System.out.println("player2 �E");
			}
			// �� : o
			else if (virtualController.isKeyDown(1, RWTVirtualController.UP)) {
				Player2.setVelocity(0.0, speed);
				Player2.setImage("data\\images\\player2\\�R�X��.png");
				muki = up;
				disableControl2 = true;
System.out.println("player2 ��");
			}
			// �� : l
			else if (virtualController.isKeyDown(1, RWTVirtualController.DOWN)) {
				Player2.setVelocity(0.0, -speed);
				Player2.setImage("data\\images\\player2\\�R�X��.png");
				muki = down1;
				disableControl2 = true;
System.out.println("player2 ��");
			}
			if (virtualController.isKeyDown(1, RWTVirtualController.BUTTON_A)) {
				if(bfrag2 == false){
					bom2.setPosition(Player2.getPosition().getX(),Player2.getPosition().getY());
					universe.place(bom2);
					putt = System.currentTimeMillis();
				}
				bfrag2 = true;
				disableControl2 = true;
				expflag = false;
System.out.println("player2 ���e");
			}
		}

		if (System.currentTimeMillis() - putt > 4000&&bfrag2 == true) {
			universe.displace(bom2);			
			bfrag2 = false;
			for(i=0;i<5;i++){

				if(i == 0){
					bome2[i].setPosition(bom2.getPosition().getX(),bom2.getPosition().getY());
					universe.place(bome2[i]);
				}else if(i == 1){
					bome2[i].setPosition(bom2.getPosition().getX(),bom2.getPosition().getY()-2);
					universe.place(bome2[i]);
				}else if(i == 2){
					bome2[i].setPosition(bom2.getPosition().getX()-2,bom2.getPosition().getY());
					universe.place(bome2[i]);
				}else if(i == 3){
					bome2[i].setPosition(bom2.getPosition().getX(),bom2.getPosition().getY()+2);
					universe.place(bome2[i]);
				}else if(i == 4){
					bome2[i].setPosition(bom2.getPosition().getX()+2,bom2.getPosition().getY());
					universe.place(bome2[i]);
				}
				if(Player1.getPosition().getX()-bome2[i].getPosition().getX()<2&&-2<Player1.getPosition().getX()-bome2[i].getPosition().getX()&&Player1.getPosition().getY()-bome2[i].getPosition().getY()<1&&-1<Player1.getPosition().getY()-bome2[i].getPosition().getY()){
					universe.displace(Player1);
					P1down.setPosition(Player1.getPosition().getX(),Player1.getPosition().getY());
					universe.place(P1down);
					down = true;
					downt = System.currentTimeMillis();
				}else if(Player2.getPosition().getX()-bome2[i].getPosition().getX()<2&&-2<Player2.getPosition().getX()-bome2[i].getPosition().getX()&&Player2.getPosition().getY()-bome2[i].getPosition().getY()<2&&-2<Player2.getPosition().getY()-bome2[i].getPosition().getY()){
					universe.displace(Player2);
					P2down.setPosition(Player2.getPosition().getX(),Player2.getPosition().getY());
					universe.place(P2down);
					down2 = true;
					downt2 = System.currentTimeMillis();
					
				}
			}

			expt = System.currentTimeMillis();
			displace2 = true;
		}


		if(down == true&&System.currentTimeMillis() - downt >= 3000){
			universe.displace(P1down);
			end = true;
		}
		if(down2 == true&&System.currentTimeMillis() - downt2 >= 3000){
			universe.displace(P2down);
			end = true;
		}
		
		if(end == true&&(System.currentTimeMillis() - downt2 >= 5000||System.currentTimeMillis() - downt >= 5000)){
			ending();
		}

		if(System.currentTimeMillis() - expt >= 1000&&displace2 ==true){
			for(i=0;i<5;i++){
				universe.displace(bome2[i]);
			}
			bfrag = false;
			disableControl2 = true;
			expt = System.currentTimeMillis();
			displace2 = false;
		}
		//�{����v�Œu���Ĕ��j

	System.out.println("player2 �ړ�");
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
