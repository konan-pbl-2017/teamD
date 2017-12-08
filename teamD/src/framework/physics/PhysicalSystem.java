package framework.physics;

import java.util.ArrayList;

import javax.vecmath.Vector3d;

import framework.model3D.CollisionResult;
import framework.model3D.Position3D;


public class PhysicalSystem {

	public ArrayList<Solid3D> objects = new ArrayList<Solid3D>();

	// ���̂̑}��
	public  int add(Solid3D s) {
		objects.add(s);
		return objects.size() - 1;
	}

	// ���̂̉^��
	public void motion(int id, long interval, Force3D f, Position3D appPoint, Ground ground) {
		ArrayList<Force3D> forces[] = new ArrayList[objects.size()];
		ArrayList<Position3D> appPoints[] = new ArrayList[objects.size()];
		for (int i = 0; i < objects.size(); i++) {
			forces[i] = new ArrayList<Force3D>();
			appPoints[i] = new ArrayList<Position3D>();
		}
		
		// id�Ԗڂ̊O�͂̌v�Z
		forces[id].add(f);
		appPoints[id].add(appPoint);
//		objects.get(id).move(interval, f, appPoint); 

		double l; // �΂˂̐L��

		Force3D penalty = new Force3D(0.0, 0.0, 0.0); // �y�i���e�B�@�ɂ��y�i���e�B�̍�p�̗�
		Force3D inversepenalty; //
		// //�y�i���e�B�@�ɂ��y�i���e�B�̔���p�̗�
		CollisionResult cr;
		for (int n = 0; n < objects.size(); n++) {
			// �d�͂̌v�Z
			forces[n].add(PhysicsUtility.getGravity(objects.get(n)));
			appPoints[n].add(objects.get(n).getGravityCenter());
//			objects.get(n).move(interval,
//					PhysicsFacade.getGravity(objects.get(n)),
//					objects.get(n).getGravityCenter()); // �d�͂̌v�Z
			// �n�ʂƂ̓����蔻��
			cr = PhysicsUtility.doesIntersect(objects.get(n), ground);
			// �n�ʂɕ��̂��߂荞��ł���ꍇ
			if (cr != null) {
				double gk = 100000; // �n�ʂł̂΂ˌW��
				double e = 0.6; // �n�ʂł̒��˕Ԃ莞�̒�R�W��
				double b = 1.0;
				l = cr.length;
				// <��p�̗͂̌v�Z>
				// �y�i���e�B�̕ϐ�
				Vector3d v = cr.normal;
				v.scale(gk * l);
				// ��p�_�x�N�g���̍쐬
				Vector3d app_point = cr.collisionPoint.getVector3d();
				// (��p�_-�d�S)�x�N�g��
				app_point.sub(objects.get(n).getGravityCenter().getVector3d());
				// �p���x�x�N�g���̍쐬
				Vector3d angular_v = objects.get(n).getAngularVelocity()
						.getVector3d();
				// �p���x�x�N�g����(��p�_-�d�S)�x�N�g���̊O�όv�Z
				angular_v.cross(angular_v, app_point);
				// ���x�x�N�g��+�p���x�x�N�g����(��p�_-�d�S)�x�N�g���̊O�όv�Z
				Vector3d relation_v = objects.get(n).getVelocity()
						.getVector3d();
				// ���Α��x�x�N�g���̍쐬
				relation_v.add(angular_v);
				// ���Α��x�x�N�g����e(���˕Ԃ莞�̒�R�W��)�{
				relation_v.scale(b * (1 + e) / e *  (1/ objects.get(n).mass));
				relation_v.scale( 1.0 / ((double) interval / 1000.0));
				// �y�i���e�B�̑傫������
				v.sub(relation_v);
				// penalty��v��}��
				penalty = new Force3D(v);

				// ��p�̗͂ɂ��^��
				forces[n].add(penalty);
				appPoints[n].add(cr.collisionPoint);
//				objects.get(n).move(interval, penalty, cr.collisionPoint);
			}
			// �n�ʂɕ��̂��߂荞��ł��Ȃ��ꍇ
			else {
			}
			for (int m = 0; m < n; m++) {
				Solid3D s1 = objects.get(n);
				Solid3D s2 = objects.get(m);
				cr = PhysicsUtility.checkCollision(s1, null, s2, null);
				// ���̂��߂荞��ł���ꍇ
				if (cr != null) {
					double sk = 100000; // ���̂ł̂΂ˌW��
					double e = 0.6; // ���̂ł̒��˕Ԃ莞�̒�R�W��
					double b = 1.0;
					l = cr.length;
					// <��p�̗͂̌v�Z>
					// �y�i���e�B�̕ϐ�
					Vector3d v = cr.normal;
					v.scale(sk * l);
					// ��p�_�x�N�g���̍쐬
					Vector3d app_point = cr.collisionPoint.getVector3d();
					// (��p�_-s2�̏d�S)�x�N�g��
					app_point.sub(s2.getGravityCenter().getVector3d());
					// s2�̊p���x�x�N�g���̍쐬
					Vector3d s2_angular_v = s2.getAngularVelocity()
							.getVector3d();
					// s2�̊p���x�x�N�g����(��p�_-s2�̏d�S)�x�N�g���̊O�όv�Z
					s2_angular_v.cross(s2_angular_v, app_point);
					// s2�̑��x�x�N�g���̍쐬
					Vector3d relative_v = s2.getVelocity().getVector3d();
					// s2�̑��x�x�N�g��+s2�̊p���x�x�N�g����(��p�_-s2�̏d�S)�x�N�g���̊O�όv�Z
					relative_v.add(s2_angular_v);
					// (��p�_-s1�̏d�S)�x�N�g��
					app_point = cr.collisionPoint.getVector3d();
					app_point.sub(s1.getGravityCenter().getVector3d());
					// s1�̊p���x�x�N�g���̍쐬
					Vector3d s1_angular_v = s1.getAngularVelocity()
							.getVector3d();
					// s1�̊p���x�x�N�g����(��p�_-s1�̏d�S)�x�N�g���̊O�όv�Z
					s1_angular_v.cross(s1_angular_v, app_point);
					// s1�̑��x�x�N�g���̍쐬
					Vector3d s1_v = s1.getVelocity().getVector3d();
					// s1�̑��x�x�N�g��+s1�̊p���x�x�N�g����(��p�_-s1�̏d�S)�x�N�g���̊O�όv�Z
					s1_v.add(s1_angular_v);
					// ���Α��x�x�N�g���̍쐬
					relative_v.sub(s1_v);
					// ���Α��x�x�N�g����e(���˕Ԃ莞�̒�R�W��)�{
					relative_v.scale(b * (1 + e) / e*(s1.mass + s2.mass) / (s1.mass * s2.mass));
					relative_v.scale( 1.0 / ((double) interval / 1000.0));
					// �y�i���e�B�̑傫������
					v.add(relative_v);
					// penalty��v��}��
					penalty = new Force3D(v);

					// ����p�̗͂̌v�Z
					v.scale(-1);
					inversepenalty = new Force3D(v);

					// ��p�̗͂ɂ�镨�̂̈ړ�
					forces[n].add(penalty);
					appPoints[n].add(cr.collisionPoint);
//					s1.move(interval, penalty, cr.collisionPoint);

					// ����p�̗͂ɂ�镨�̂̈ړ�
					forces[m].add(inversepenalty);
					appPoints[m].add(cr.collisionPoint);
//					s2.move(interval, inversepenalty, cr.collisionPoint);
				}
//				// ���̂��߂荞��ł��Ȃ��ꍇ
//				else {
//					s2.move(interval, f, s2.getGravityCenter());
//					s1.move(interval, f, s1.getGravityCenter());
//				}
			}
		}
		for (int n2 = 0; n2 < objects.size(); n2++) {
			objects.get(n2).move(interval, forces[n2], appPoints[n2]);				
		}
	}
}
