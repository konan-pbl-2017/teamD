package template.shooting2D;

import java.util.ArrayList;

import framework.game2D.Sprite;
import framework.game2D.Velocity2D;

public class EnemySprite extends Sprite {
	// �Փ˔���p��BoundingSphere�̔��a
	public double collisionRadius = 1.0;

	private int enemyHP = 100;

	// �e���̍ő吔
	private final int MAX_DANMAKU = 32;
	
	// �e�̔��ˎ��̓G����̈ʒu
	private final int BULLET_DISTANCE = 1;

	// �Q�[���̕\���͈͂��瓾�邱�Ƃ��o���镝�E����
	int rangeWidth = TemplateShooting2D.RANGE;
	int rangeHeight = TemplateShooting2D.RANGE;

	public EnemySprite(String imageFile) {
		super(imageFile);
	}

	// ////////////////////////////////////////////////////
	//
	// �G�@�̏Փ˔���֘A���\�b�h
	//
	// ///////////////////////////////////////////////////

	/**
	 * �Փ˔����Bounding Sphere�i���E���j��collisionRadius�Őݒ肷��
	 * 
	 * @param collisionRadius
	 *            -- BoundingSphere�̔��a
	 */
	public void setCollisionRadius(double collisionRadius) {
		this.collisionRadius = collisionRadius;
	}

	/**
	 * �Փ˔����Bounding Sphere�i���E���j�̔��a��Ԃ�
	 * 
	 * @return�@ BoundingSphere�̔��a
	 */
	public double getCollisionRadius() {
		return collisionRadius;
	}

	// ////////////////////////////////////////////////////
	//
	// �G�@�̒e�𔭎˂��郁�\�b�h
	//
	// ///////////////////////////////////////////////////

	/**
	 * �e����������ArrayList��Ԃ�
	 * 
	 * @return -- �e����������ArrayList
	 */
	public ArrayList<EnemyBullet> shootDanmaku() {
		double bulletX, bulletY;
		
		ArrayList<EnemyBullet> enemyBulletList = new ArrayList<EnemyBullet>();
		for (int i = 0; i < MAX_DANMAKU; i++) {
			EnemyBullet enemyBullet = new EnemyBullet("data\\images\\enemyBullet.gif");

			bulletX = BULLET_DISTANCE * (Math.cos(i * (2 * Math.PI / MAX_DANMAKU)));
			bulletY = BULLET_DISTANCE * (Math.sin(i * (2 * Math.PI / MAX_DANMAKU)));

			// �e�̈ʒu��ݒ�
			enemyBullet.setPosition(this.getPosition());
			// �e�̈ړ��x�N�g����ݒ肷��
			enemyBullet.setVelocity(new Velocity2D(bulletX * 5, bulletY * 5));

			enemyBulletList.add(enemyBullet);
		}

		return enemyBulletList;
	}

	// ////////////////////////////////////////////////////
	//
	// �G���E�B���h�E���ɂ��邩�ǂ����̃��\�b�h
	//
	// ///////////////////////////////////////////////////

	public void motion(long interval) {
		Velocity2D vel = this.getVelocity();

		switch (insideX()) {
		case -1:
			vel.setX(Math.abs(this.getVelocity().getX()));
			break;
		case 1:
			vel.setX(Math.abs(this.getVelocity().getX()) * -1.0);
			break;
		}

		switch (insideY()) {
		case -1:
			vel.setY(Math.abs(this.getVelocity().getY()));
			break;
		case 1:
			vel.setY(Math.abs(this.getVelocity().getY()) * -1.0);
			break;
		}
		
		setVelocity(vel);
		super.motion(interval);

	}

	/**
	 * ��ʂ�X�����ɏo�Ă��Ȃ���?
	 * 
	 * @return -1: X�̕��̕����ɏo�Ă���, 0: X�����ɏo�Ă��Ȃ�, 1: X�̐��̕����ɏo�Ă���
	 */
	private int insideX() {
		if (this.getPosition().getX() > rangeWidth / 2.0) {
			return 1;		
		} else if (this.getPosition().getX() < -rangeWidth / 2.0) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * ��ʂ�Y�����ɏo�Ă��Ȃ���?
	 * 
	 * @return -1: Y�̕��̕����ɏo�Ă���, 0: Y�����ɏo�Ă��Ȃ�, 1: Y�̐��̕����ɏo�Ă���
	 */
	private int insideY() {
		if (this.getPosition().getY() > rangeHeight / 2.0) {
			return 1;
		} else if (this.getPosition().getY() < -rangeHeight / 2.0) {
			return -1;
		} else {
			return 0;
		}
	}

	// ////////////////////////////////////////////////////
	//
	// �G�@��HP�֘A���\�b�h
	//
	// ///////////////////////////////////////////////////
	public boolean shootDown() {
		if (enemyHP <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public void addEnemyHP(int value) {
		enemyHP += value;
	}

	public int getEnemyHP() {
		return enemyHP;
	}

	public void setEnemyHP(int enemyHP) {
		this.enemyHP = enemyHP;
	}

}
