package framework.gameMain;

import javax.vecmath.Vector3d;

import framework.animation.Animation3D;
import framework.model3D.CollisionResult;
import framework.model3D.Object3D;
import framework.model3D.Position3D;
import framework.physics.Solid3D;
import framework.physics.Velocity3D;

/**
 * �n�ʂ̏���ړ�������́i�W�����v�⎩�R���������邱�Ƃ��\�j
 * @author �V�c����
 *
 */
public class OvergroundActor extends Actor {

	public OvergroundActor(Object3D body, Animation3D animation) {
		super(body, animation);
	}

	public OvergroundActor(Solid3D body, Animation3D animation) {
		super(body, animation);
	}

	public OvergroundActor(ActorModel model) {
		super(model);
	}

	public void onIntersect(CollisionResult cr, long interval) {
		// �߂荞�񂾂�i�߂荞�񂾖ʂ̖@�������Ɂj�����߂�
		Vector3d back = (Vector3d) cr.normal.clone();
		back.scale(cr.length * 2.0);
		body.apply(new Position3D(body.getPosition3D().add(back)), false);
		
		// ���x�̖ʂ̖@�������̐����� 0 �ɂ���
		Vector3d v = (Vector3d) ((Solid3D)body).getVelocity().getVector3d().clone();
		double d = v.dot(cr.normal);
		v.scaleAdd(-d, cr.normal, v);
		body.apply(new Velocity3D(v), false);
	}

	@Override
	public void onEndFall() {
	}

	@Override
	public void onEndAnimation() {
	}
}