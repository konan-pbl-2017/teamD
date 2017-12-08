package framework.gameMain;


import java.util.ArrayList;

import javax.media.j3d.Transform3D;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Vector3d;

import framework.animation.Animation3D;
import framework.model3D.CollisionResult;
import framework.model3D.GeometryUtility;
import framework.model3D.Object3D;
import framework.model3D.Position3D;
import framework.model3D.Quaternion3D;
import framework.physics.Force3D;
import framework.physics.Ground;
import framework.physics.PhysicsUtility;
import framework.physics.Solid3D;
import framework.physics.Velocity3D;

/**
 * �Q�[�����̓o�ꕨ�S��
 * @author �V�c����
 *
 */
public abstract class Actor extends Animatable {
	protected Vector3d direction = new Vector3d(1.0, 0.0, 0.0);
	protected Mode mode;
	// �ȉ��ȃ��������̂��ߗ\�߃C���X�^���X�𐶐�
	protected Mode modeFreeFall = new ModeFreeFall();
	protected Mode modeOnGround = new ModeOnGround();
	private static final Vector3d X_AXIS = new Vector3d(1.0, 0.0, 0.0);
	private static final Vector3d Y_AXIS = new Vector3d(0.0, 1.0, 0.0);
	private static final Vector3d Z_AXIS = new Vector3d(0.0, 0.0, 1.0);
	
	public Actor(Object3D body, Animation3D animation) {
		super(new Solid3D(body), animation);
		mode = modeOnGround;
	}

	public Actor(Solid3D body, Animation3D animation) {
		super(body, animation);
		mode = modeOnGround;
	}

	public Actor(ActorModel model) {
		super(model.createBody(), model.getAnimation());
		mode = modeOnGround;
	}

	
	/**
	 * �P�ʎ��Ԃ��Ƃ̓���i�Փ˔��菈�����s���j
	 * @param interval --- �O��Ăяo���ꂽ�Ƃ�����̌o�ߎ��ԁi�~���b�P�ʁj
	 */
	public void motion(long interval){
		// 1. �ʒu�𓮂���
		((Solid3D)body).move(interval, getGravity(), getGravityCenter());
		super.motion(interval);
	}
	
	
	/**
	 * �P�ʎ��Ԃ��Ƃ̓���i�Փ˔��菈�����s���j
	 * @param interval --- �O��Ăяo���ꂽ�Ƃ�����̌o�ߎ��ԁi�~���b�P�ʁj
	 * @param ground --- �n�ʁi�\�����j
	 */
	public void motion(long interval, Ground ground) {

		// 1. �ʒu�𓮂���
		((Solid3D)body).move(interval, getGravity(), getGravityCenter());

		if (animation != null) {
			// 2. �A�j���[�V���������s
			if (animation.progress(interval) == false) {
				onEndAnimation();
			}
			
			// 3. �p����ς���
			body.apply(animation.getPose(), false);
		}

		// 4. �Փ˔��� 
		CollisionResult cr = PhysicsUtility.doesIntersect((Solid3D)body, ground);

		// 5. �Փˉ���
		if (cr != null) {
			// �\�����ɂԂ������A�܂��͐ڐG���Ă��鎞
			if (cr.normal.dot(PhysicsUtility.vertical) > GeometryUtility.TOLERANCE) {
				// ������̖ʁi���n�ʁj�ɂԂ������A�܂��͐ڐG���Ă��鎞
				if (cr.length <= GeometryUtility.TOLERANCE) {
					// �n�ʂɏ���Ă���
					if (!(mode instanceof ModeOnGround)) {
						// �����Ă��Ē��x�����
						mode = modeOnGround;
						((ModeOnGround)mode).setNormal(cr.normal);
						onEndFall();
					}
				} else {
					// �n�ʂɂ߂荞��
					// 5.1. �����߂�
					onIntersect(cr, interval);
					if (!(mode instanceof ModeOnGround)) {
						// �����Ă��Ă߂荞��
						// 6. �n�ʃ��[�h�ɂ���
						mode = modeOnGround;
						((ModeOnGround)mode).setNormal(cr.normal);
						onEndFall();
					} else {
						// �����Ă���r���ŁA�A�j���[�V�����̊֌W�ň�u�����߂荞��
						// �܂��́A�����Ă���r���ŎΖʂɍ����|������
						((ModeOnGround)mode).setNormal(cr.normal);
					}
				}
			} else if (cr.normal.dot(PhysicsUtility.vertical) >= -GeometryUtility.TOLERANCE) {
				// �����ǂɂ߂荞��
				// 5.1. �����߂�
				onIntersect(cr, interval);
			} else {
				// ������Ԃ������A�܂��͐ڐG����(�����Ԃ���)
				if (cr.length > GeometryUtility.TOLERANCE) {
					// 5.1. �����߂�
					onIntersect(cr, interval);				
				}
			}
			cr = null;
		} else {
			// �n�ʂƂԂ����Ă��ڐG���Ă����Ȃ���
			// 6. �������[�h�ɂ���
			mode = modeFreeFall;
		}
	}
	
	public void motion(long interval, Ground ground,ArrayList<Force3D> forces,
			ArrayList<Position3D> appPoints) {
		
		forces.add(getGravity());
		appPoints.add(getGravityCenter());

		// 1. �ʒu�𓮂���
		((Solid3D)body).move(interval, forces, appPoints);

		if (animation != null) {
			// 2. �A�j���[�V���������s
			if (animation.progress(interval) == false) {
				onEndAnimation();
			}
			
			// 3. �p����ς���
			body.apply(animation.getPose(), false);
		}

		// 4. �Փ˔��� 
		CollisionResult cr = PhysicsUtility.doesIntersect((Solid3D)body, ground);

		// 5. �Փˉ���
		if (cr != null) {
			// �\�����ɂԂ������A�܂��͐ڐG���Ă��鎞
			if (cr.normal.dot(PhysicsUtility.vertical) > GeometryUtility.TOLERANCE) {
				// ������̖ʁi���n�ʁj�ɂԂ������A�܂��͐ڐG���Ă��鎞
				if (cr.length <= GeometryUtility.TOLERANCE) {
					// �n�ʂɏ���Ă���
					if (!(mode instanceof ModeOnGround)) {
						// �����Ă��Ē��x�����
						mode = modeOnGround;
						((ModeOnGround)mode).setNormal(cr.normal);
						onEndFall();
					}
				} else {
					// �n�ʂɂ߂荞��
					// 5.1. �����߂�
					onIntersect(cr, interval);
					if (!(mode instanceof ModeOnGround)) {
						// �����Ă��Ă߂荞��
						// 6. �n�ʃ��[�h�ɂ���
						mode = modeOnGround;
						((ModeOnGround)mode).setNormal(cr.normal);
						onEndFall();
					} else {
						// �����Ă���r���ŁA�A�j���[�V�����̊֌W�ň�u�����߂荞��
						// �܂��́A�����Ă���r���ŎΖʂɍ����|������
						((ModeOnGround)mode).setNormal(cr.normal);
					}
				}
			} else if (cr.normal.dot(PhysicsUtility.vertical) >= -GeometryUtility.TOLERANCE) {
				// �����ǂɂ߂荞��
				// 5.1. �����߂�
				onIntersect(cr, interval);
			} else {
				// ������Ԃ������A�܂��͐ڐG����(�����Ԃ���)
				if (cr.length > GeometryUtility.TOLERANCE) {
					// 5.1. �����߂�
					onIntersect(cr, interval);				
				}
			}
			cr = null;
		} else {
			// �n�ʂƂԂ����Ă��ڐG���Ă����Ȃ���
			// 6. �������[�h�ɂ���
			mode = modeFreeFall;
		}
	}
	
	public void setInitialDirection(Vector3d dir) {
		direction = dir;
	}
	
	/**
	 * �w�肵�������Ɍ�������
	 * @param vec �V��������
	 */
	public void setDirection(Vector3d vec) {
		Vector3d v1 = new Vector3d();
		Vector3d v2 = new Vector3d();
		v1.cross(direction, Y_AXIS);
		v2.cross(vec, Y_AXIS);
		if (v2.length() < GeometryUtility.TOLERANCE) return;
		v1.normalize();
		v2.normalize();
		double cos = v1.dot(v2);
		v1.cross(v1, v2);
		double sin = v1.dot(Y_AXIS);
		double angle = Math.atan2(sin, cos);
		AxisAngle4d axisAngle = new AxisAngle4d(Y_AXIS, angle); 
		Quaternion3D quat = new Quaternion3D(axisAngle);
		((Solid3D)body).apply(quat, false);
	}
	
	/**
	 * ���݌����Ă���������擾����
	 * @return ���݂̌���
	 */
	public Vector3d getDirection() {
		Vector3d dir = new Vector3d(direction);
		Transform3D trans = new Transform3D();
		trans.set(((Solid3D)body).getQuaternion().getAxisAngle());
		trans.transform(dir);
		return dir;
	}	
	
	/**
	 * �ړ����x�x�N�g����ݒ肷��
	 * @param vel �V�����ړ����x�x�N�g��
	 */
	public void setVelocity(Velocity3D vel) {
		((Solid3D)body).apply(vel, false);
	}
	
	/**
	 * �ړ����x�x�N�g�����擾����
	 * @return ���݂̈ړ����x�x�N�g��
	 */
	public Velocity3D getVelocity() {
		return ((Solid3D)body).getVelocity();
	}
	
	/**
	 * X���𒆐S�ɉ�]����
	 * @param angle ��]�p�i�����v���, �P��:���W�A���j
	 */
	public void rotX(double angle) {
		Quaternion3D curQuat = body.getQuaternion();
		curQuat.add(new AxisAngle4d(X_AXIS, angle));
		body.apply(curQuat, false);		
	}
	
	/**
	 * Y���𒆐S�ɉ�]����
	 * @param angle ��]�p�i�����v���, �P��:���W�A���j
	 */
	public void rotY(double angle) {
		Quaternion3D curQuat = body.getQuaternion();
		curQuat.add(new AxisAngle4d(Y_AXIS, angle));
		body.apply(curQuat, false);		
	}
	
	/**
	 * Z���𒆐S�ɉ�]����
	 * @param angle ��]�p�i�����v���, �P��:���W�A���j
	 */
	public void rotZ(double angle) {
		Quaternion3D curQuat = body.getQuaternion();
		curQuat.add(new AxisAngle4d(Z_AXIS, angle));
		body.apply(curQuat, false);		
	}

	/**
	 * ������Ă���d�͂��擾����
	 * @return �d��
	 */
	public Force3D getGravity() {
		return mode.getForce((Solid3D)body);
	}

	/**
	 * �d�S���擾����
	 * @return �d�S�ʒu
	 */
	public Position3D getGravityCenter() {
		return ((Solid3D)body).getGravityCenter();
	}

	/**
	 * �n�ʂɏ���Ă����Ԃ��ۂ����擾����
	 * @return true --- �n�ʂɏ���Ă���, false --- �n�ʂɏ���Ă��Ȃ��i�󒆂ɂ���j
	 */
	public boolean isOnGround() {
		return (mode instanceof ModeOnGround);
	}

	/**
	 * �n�ʁi�\�����j�ɗ��������u�ԂɌĂяo�����
	 */
	public abstract void onEndFall();

	/**
	 * �n�ʁi�\�����j�ɏՓ˂����u�ԂɌĂяo�����
	 * @param normal --- �n�ʂ̖@�������x�N�g��
	 * @param interval --- �O��̓��삩��̌o�ߎ��ԁi�~���b�P�ʁj
	 */
	public abstract void onIntersect(CollisionResult normal, long interval);

}
