package templete.action2D;

import javax.vecmath.Vector2d;

import framework.game2D.OvergroundActor2D;
import framework.game2D.Position2D;

/**
 * �v���C���[�̃N���X
 * 
 * @author H.Kotobuki
 * 
 */
public class Player extends OvergroundActor2D {
	
	private static final Vector2d LEFT_AXIS = new Vector2d(-1.0, 0.0);
	private static final Vector2d Right_AXIS = new Vector2d(1.0, 0.0);
	public double getCollisionRadius() {
		return collisionRadius;
	}

	public String getAnimationFileName() {
		return "data\\TemplateShooting\\Character\\pocha\\walk.wrl";
	}

	public String getModelFileName() {
		return "data\\TemplateShooting\\Character\\pocha\\pocha.wrl";
	}

	/**
	 * �L�����N�^�̈ʒu�����Ɍ����̂Ɠ����ɍ������ɓ������B
	 * 
	 * @param d
	 *            ��������
	 */
	public void movePositionLeft(double d) {
		super.setDirection(LEFT_AXIS);
		super.movePositionLeft(d);
	}
	
	/**
	 * �L�����N�^�̈ʒu���E�Ɍ����̂Ɠ����ɉE�����ɓ������B
	 * 
	 * @param d
	 *            ��������
	 */
	public void movePositionRight(double d) {
		super.setDirection(Right_AXIS);
		super.movePositionRight(d);
	}
	
	public boolean isOnGround() {
		if (getActor().isOnGround()) {
			return true;
		} else {
			return false;
		}
	}
	

}
