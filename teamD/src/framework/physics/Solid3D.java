package framework.physics;

import java.util.ArrayList;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Vector3d;

import framework.model3D.Object3D;
import framework.model3D.Position3D;
import framework.model3D.Quaternion3D;

/**
 * �����I�ȐU�镑�������镨�́i���́j��\��
 * @author �V�c����
 *
 */
public class Solid3D extends Object3D {
	private Velocity3D velocity;
	private AngularVelocity3D angularvelocity;
//	private Inertia3D inertia = new Inertia3D(this);
	private Position3D gravityCenter = getPosition3D();
	double mass = 10;
	double e = 1.0;

	// �R�s�[�R���X�g���N�^
	public Solid3D(Object3D obj) {
		super(obj);
		velocity = new Velocity3D();
		quaternion = new Quaternion3D();
		angularvelocity = new AngularVelocity3D();
	}

	public Solid3D(Solid3D solid) {
		super(solid);
		velocity = new Velocity3D(solid.velocity);
		quaternion = new Quaternion3D(solid.getQuaternion());
		angularvelocity = new AngularVelocity3D(solid.angularvelocity);
	}

	/**
	 * �͊w�^���̌v�Z�i�����͂�1�̏ꍇ�j
	 * @param interval �P�ʎ���
	 * @param f ��
	 * @param applicationPoint �͂̍�p�_
	 */
	public void move(long interval, Force3D f, Position3D applicationPoint) {
		// ���[�����g�̌v�Z
		Vector3d moment = PhysicsUtility.calcMoment(f, getGravityCenter(),
				applicationPoint);
		moveSub(interval, f, moment);
	}
	
	/**
	 * �͊w�^���̌v�Z�i�����ɕ����̗͂������ꍇ�j
	 * @param interval �P�ʎ���
	 * @param forces �́i�����j
	 * @param appPoints ���ꂼ��̗͂̍�p�_
	 */
	public void move(long interval, ArrayList<Force3D> forces,
			ArrayList<Position3D> appPoints) {
		// �d�S�ɉ����͂̍��v�����߂�
		Force3D f = new Force3D(0.0, 0.0, 0.0);
		for (int n = 0; n < forces.size(); n++) {
			f.add(forces.get(n));
		}
		
		// ���[�����g�̍��v���v�Z����
		Position3D gc = getGravityCenter();
		Vector3d moment = new Vector3d(0.0, 0.0, 0.0);
		for (int n2 = 0; n2 < forces.size(); n2++) {
			moment.add(PhysicsUtility.calcMoment(forces.get(n2), gc, appPoints.get(n2)));
		}
		moveSub(interval, f, moment);		
	}
	
	private void moveSub(long interval, Force3D f, Vector3d moment) {
		// 1.�d�S�̉^���������i�j���[�g���������j
		Vector3d deltaP = velocity.getVector3d(); // ���x�x�N�g���̎擾
		deltaP.scale(((double) interval / 1000.0));
		Position3D p = getPosition3D().add(deltaP); // �ʒu�ɍ��������Z
		apply(p, false);

		// �����x�v�Z
		Vector3d deltaV = f.getVector3d(); // �̓x�N�g���̎擾
		deltaV.scale(1.0 / mass * ((double) interval / 1000.0)); // �����x���瑬�x�̍������v�Z
		Velocity3D v = getVelocity().add(deltaV); // ���x�ɍ��������Z
		apply(v, false);
		
		// 2.�I�C���[�̊p�^��������

//		// �p�����x�v�Z
//		Vector3d deltaAngularV = new Vector3d(moment.x / inertia.ixx,
//				moment.y / inertia.iyy, moment.z / inertia.izz);
//		deltaAngularV.scale(((double) interval / 1000.0 / mass));
//		AngularVelocity3D w = getAngularVelocity().add(deltaAngularV);
//		apply(w, false);
//
//		// �p���x�ɂ���]
//		AxisAngle4d axisAngle = w.getAxisAngle4d();
//		axisAngle.angle *= ((double) interval / 1000.0);
//		Quaternion3D q = getQuaternion().add(axisAngle);
//		apply(q, false);		
	}

	// ���������
	public Object3D duplicate() {
		Object3D copy = new Solid3D(this);
		return copy;
	}

	public Velocity3D getVelocity() {
		return (Velocity3D) velocity.clone();
	}

	public AngularVelocity3D getAngularVelocity() {
		return (AngularVelocity3D) angularvelocity.clone();
	}

	// Velocity3D �� applyTo �ȊO����͌Ă΂Ȃ�����
	void setVelocity(Velocity3D v) {
		velocity = (Velocity3D) v.clone();
	}

	// AngularVelocity3D �� applyTo �ȊO����͌Ă΂Ȃ�����
	void setAngularVelocity(AngularVelocity3D w) {
		angularvelocity = (AngularVelocity3D) w.clone();
	}

	public void setGravityCenter(Position3D gravityCenter) {
		this.gravityCenter = gravityCenter;
	}

	public Position3D getGravityCenter() {
		return getPosition3D().add(gravityCenter);
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getMass() {
		return mass;
	}

}
