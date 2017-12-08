package template.shooting2D;

import java.util.ArrayList;

import javax.vecmath.Vector2d;

import framework.game2D.Actor2D;
import framework.game2D.Sprite;
import framework.game2D.Velocity2D;

public class MyShipSprite extends Sprite {
	// �e�̍��W
	double bulletX, bulletY;
	// �e���̍ő吔
	public final int MAX_DANMAKU = 32;
	// �e�̔��ˎ��̎��@����̈ʒu
	private final int BULLET_DISTANCE = 1;
	private MyShipBullet myShipBullet;

	public MyShipSprite(String string) {
		super(string);
		super.setCollisionRadius(1.0);
	}

	// ////////////////////////////////////////////////////
	//
	// ���@�̒e�𔭎˂��郁�\�b�h
	//
	// ///////////////////////////////////////////////////

	/**
	 * �e����������ArrayList��Ԃ�
	 * 
	 * @return -- �e����������ArrayList
	 */
	public ArrayList<MyShipBullet> shootDanmaku() {
		ArrayList<MyShipBullet> myShipBulletList = new ArrayList<MyShipBullet>();
		for (int i = 0; i < MAX_DANMAKU; i++) {
			myShipBullet = new MyShipBullet("data\\images\\myBullet.gif");

			bulletX = BULLET_DISTANCE
					* (Math.cos(i * (2 * Math.PI / MAX_DANMAKU)));
			bulletY = BULLET_DISTANCE
					* (Math.sin(i * (2 * Math.PI / MAX_DANMAKU)));

			// �e�̈ʒu
			myShipBullet.setPosition(this.getPosition());
			// �e�̈ړ��x�N�g��
			myShipBullet.setVelocity(new Velocity2D(bulletX * 5, bulletY * 5));

			myShipBulletList.add(myShipBullet);
		}

		return myShipBulletList;
	}

	// ////////////////////////////////////////////////////
	//
	// �v���C���[���E�B���h�E���ɂ��邩�ǂ����̃��\�b�h
	//
	// ///////////////////////////////////////////////////

	/**
	 * width��height�Ō��߂�ꂽ�E�B���h�E�̕��̒��Ƀv���C���[�����邩�ǂ�����Ԃ�
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean isInScreen(int width, int height) {
		if (this.getPosition().getX() < width / 2.0
				&& this.getPosition().getX() > -1.0 * width / 2.0) {
			if (this.getPosition().getY() < height / 2.0
					&& this.getPosition().getY() > -1.0 * height / 2.0) {
				return true;
			}
		}
		return false;
	}
}
