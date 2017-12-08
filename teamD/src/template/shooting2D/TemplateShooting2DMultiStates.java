package template.shooting2D;

import java.awt.Event;
import java.util.ArrayList;

import framework.RWT.RWTContainer;
import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.game2D.Ground2D;
import framework.game2D.Velocity2D;
import framework.gameMain.IGameState;
import framework.gameMain.SimpleShootingGame;
import framework.model3D.Universe;

public class TemplateShooting2DMultiStates extends SimpleShootingGame {
	private MyShipSprite myShipSprite;
	private MyShipBullet myShipBullet;
	private ArrayList<MyShipBullet> myShipBulletList = new ArrayList<MyShipBullet>();
	private ArrayList<MyShipBullet> myShipBulletFromMyShip = new ArrayList<MyShipBullet>();

	private EnemySprite enemySprite;
	private ArrayList<EnemyBullet> enemyBulletList = new ArrayList<EnemyBullet>();
	private ArrayList<EnemyBullet> enemyBulletFromEnemy = new ArrayList<EnemyBullet>();

	private Ground2D stage;

	private long lastMyShipBulletShootTime = 0;
	private long lastMyShipBulletShootDanamakuTime = 0;
	private long lastEnemyShootTime = 0;

	// ���ƂŐ݌v�ύX
	// Enemy�N���X�ł��̒l���g���������߁B
	public static final int RANGE = 30;
	
	private IGameState initialGameState = null;
	private IGameState finalGameState = null;
	
	public TemplateShooting2DMultiStates() {
		super();
		initialGameState = new IGameState() {
			@Override
			public void init(RWTFrame3D frame) {
				TemplateShooting2DMultiStates.this.frame = frame;
				RWTContainer container = new StartContainer(TemplateShooting2DMultiStates.this);
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
		finalGameState = new IGameState() {
			@Override
			public void init(RWTFrame3D frame) {
				TemplateShooting2DMultiStates.this.frame = frame;
				RWTContainer container = new EndingContainer(TemplateShooting2DMultiStates.this);
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
		setCurrentGameState(initialGameState);
	}
		
	public void restart() {
		stop();
		setCurrentGameState(initialGameState);
		start();
	}
	
	public void play() {
		stop();
		setCurrentGameState(this);
		start();
	}
	
	public void ending() {
		stop();
		setCurrentGameState(finalGameState);
		start();
	}

	@Override
	public void init(Universe universe) {

		// /////////////////////////////////////////////////////////
		//
		// �e�o�ꕨ�̏�����
		//
		// ////////////////////////////////////////////////////////
		myShipSprite = new MyShipSprite("data\\images\\MyShip.gif");
		myShipSprite.setPosition(0.0, -10.0);
		universe.place(myShipSprite);

		enemySprite = new EnemySprite("data\\images\\Enemy.gif");
		enemySprite.setPosition(0.0, 10.0);
		enemySprite.setVelocity(5.0, 5.0);
		universe.place(enemySprite);

		stage = new Ground2D(null, "data\\images\\m101.jpg", windowSizeWidth,
				windowSizeHeight);
		universe.place(stage);

		// �\���͈͂����߂�i��������_�Ƃ��Ă��̌��_���畝�A�������v�Z����j
		setViewRange(RANGE, RANGE);
	}

	@Override
	public RWTFrame3D createFrame3D() {
		// TODO Auto-generated method stub
		RWTFrame3D f = new RWTFrame3D();
		f.setSize(800, 800);
		// f.setExtendedState(Frame.MAXIMIZED_BOTH);
		f.setTitle("Template for Shooting 2DGame");
		return f;
	}

	@Override
	public void progress(RWTVirtualController virtualController, long interval) {
		if (virtualController.isKeyDown(Event.ENTER)) {
			ending();
		}

		// /////////////////////////////////////////////////////////
		//
		// �e�o�ꕨ�̃A�N�V����
		//
		// ////////////////////////////////////////////////////////

		// �L�[����ɂ�鎩�@�̃A�N�V��������
		// ��
		if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
			myShipSprite.moveLeft(5.0);
		}
		// �E
		if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
			myShipSprite.moveRight(5.0);
		}
		// ��
		if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
			myShipSprite.moveUp(5.0);
		}
		// ��
		if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
			myShipSprite.moveDown(5.0);
		}

		// �e�̔���
		if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_A)) {
			if (System.currentTimeMillis() - lastMyShipBulletShootTime > 1000) {
				myShipBullet = new MyShipBullet("data\\images\\myBullet.gif");
				myShipBullet.setPosition(myShipSprite.getPosition());
				myShipBullet.setVelocity(new Velocity2D(0.0, 7.0));
				universe.place(myShipBullet);
				myShipBulletList.add(myShipBullet);
				lastMyShipBulletShootTime = System.currentTimeMillis();
			}
		}

		// �e���̔���
		if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_B)) {
			if (System.currentTimeMillis() - lastMyShipBulletShootDanamakuTime > 1000) {
				myShipBulletFromMyShip = myShipSprite.shootDanmaku();
				this.setMyShipBullet(myShipBulletFromMyShip);
				lastMyShipBulletShootDanamakuTime = System.currentTimeMillis();
			}
		}

		// �G�̃A�N�V��������
		// �e���̔���
		if (System.currentTimeMillis() - lastEnemyShootTime > 1000) {
			enemyBulletFromEnemy = enemySprite.shootDanmaku();
			this.setEnemyBullet(enemyBulletFromEnemy);
			lastEnemyShootTime = System.currentTimeMillis();
		}

		// /////////////////////////////////////////////////////////
		//
		// �e�o�ꕨ�𓮂�������
		//
		// ////////////////////////////////////////////////////////

		// �E�B���h�E���ɏo�悤�Ƃ������A���@�̈ʒu��[�ɌŒ肷��
		if (!(myShipSprite.isInScreen(viewRangeWidth, viewRangeHeight))) {
			if (myShipSprite.getPosition().getX() >= viewRangeWidth / 2) {
				myShipSprite.setPosition(viewRangeWidth / 2, myShipSprite.getPosition().getY());
			}
			if (myShipSprite.getPosition().getX() <= -1.0 * viewRangeWidth / 2) {
				myShipSprite.setPosition(-1.0 * viewRangeWidth / 2, myShipSprite.getPosition().getY());
			}
			if (myShipSprite.getPosition().getY() >= viewRangeHeight / 2) {
				myShipSprite.setPosition(myShipSprite.getPosition().getX(), viewRangeHeight / 2);
			}
			if (myShipSprite.getPosition().getY() <= -1.0 * viewRangeHeight / 2) {
				myShipSprite.setPosition(myShipSprite.getPosition().getX(), -1.0 * viewRangeHeight / 2);
			}
			myShipSprite.motion(interval);
		}

		// �v���C���[�̒e�𓮂���
		for (int i = 0; i < myShipBulletList.size(); i++) {
			MyShipBullet myShipBullet = myShipBulletList.get(i);
			myShipBullet.motion(interval);		// �v���C���[�̒e�̈ړ�
			if (myShipBullet.isInScreen(viewRangeWidth, viewRangeHeight) == false) {
				// �v���C���[�̒e������
				universe.displace(myShipBullet);
				myShipBulletList.remove(i);
			}
		}

		// �G�i�X�v���C�g�j�𓮂���
		enemySprite.motion(interval);

		// �G�̒e�𓮂����B�����ɃE�B���h�E�O�ɏo�Ă��܂������ǂ����𔻒肵�A�o�Ă��܂�����E�C���h�E����e�������B
		for (int i = 0; i < enemyBulletList.size(); i++) {
			EnemyBullet enemyBullet = enemyBulletList.get(i);
			enemyBullet.motion(interval);		// �G�̒e�̈ړ�
			if (enemyBullet.isInScreen(viewRangeWidth, viewRangeHeight) == false) {
				// �G�̒e������
				universe.displace(enemyBullet);
				enemyBulletList.remove(i);
			}
		}

		// /////////////////////////////////////////////////////////
		//
		// �e�o�ꕨ�𓮂�������̏���
		//
		// ////////////////////////////////////////////////////////

		// �v���C���[�ƁZ�Z�̏Փ˔���
		// �Փ˔���i�v���C���[�ƓG�̒e�j
		for (int i = 0; i < enemyBulletList.size(); i++) {
			EnemyBullet enemyBullet = enemyBulletList.get(i);
			if (myShipSprite.checkCollision(enemyBullet)) {
				System.out.println("�G�̒e" + i + "�ƏՓ˂����I");
			}
		}

		// �Փ˔���i�v���C���[�̒e�ƓG�̒e�j
		for (int i = 0; i < myShipBulletList.size(); i++) {
			MyShipBullet myShipBullet = myShipBulletList.get(i);
			for (int j = 0; j < enemyBulletList.size(); j++) {
				EnemyBullet enemyBullet = enemyBulletList.get(j);
				if (myShipBullet.checkCollision(enemyBullet)) {
					// �G�̒e������
					System.out.println("�v���C���[�̒e" + i + "���G�̒e" + j + "�ƏՓ˂����I");
					universe.displace(enemyBullet);
					enemyBulletList.remove(j);
				}
			}
		}

		// �Փ˔���i�v���C���[�̒e�ƓG�j
		for (int i = 0; i < myShipBulletList.size(); i++) {
			MyShipBullet myShipBullet = myShipBulletList.get(i);
			if (myShipBullet.checkCollision(enemySprite)) {
				System.out.println("�v���C���[�̒e���G�ɏՓ˂����I");
				enemySprite.addEnemyHP(-10);
				System.out.println("�G��HP" + enemySprite.getEnemyHP());

				if (enemySprite.shootDown()) {
					System.out.println("�G��|�����I");
				}

			}
		}

		// �Փ˔���i�v���C���[�ƓG�j
		if (myShipSprite.checkCollision(enemySprite)) {
			System.out.println("�G�ƏՓ˂����I");
		}

	}

	/**
	 * �e�������������X�g�imyShipBulletFromMyShip�j���v���C���[�̒e�̃��X�g�ɐݒ肷��
	 * 
	 * @param myShipBulletFromMyShip
	 */
	public void setMyShipBullet(ArrayList<MyShipBullet> myShipBulletFromMyShip) {
		for (int i = 0; i < myShipBulletFromMyShip.size(); i++) {
			universe.place(myShipBulletFromMyShip.get(i));
			this.myShipBulletList.add(myShipBulletFromMyShip.get(i));
		}
	}

	/**
	 * �e�������������X�g�ienemyBulletListFromEnemy�j��G�̒e�̃��X�g�ɐݒ肷��
	 * 
	 * @param enemyBulletListFromEnemy
	 */
	public void setEnemyBullet(ArrayList<EnemyBullet> enemyBulletListFromEnemy) {
		for (int i = 0; i < enemyBulletListFromEnemy.size(); i++) {
			universe.place(enemyBulletListFromEnemy.get(i));
			this.enemyBulletList.add(enemyBulletListFromEnemy.get(i));
		}
	}

	/**
	 * �Q�[���̃��C��
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TemplateShooting2DMultiStates game = new TemplateShooting2DMultiStates();
		game.setFramePolicy(5, 33, false);
		game.start();
	}

}
