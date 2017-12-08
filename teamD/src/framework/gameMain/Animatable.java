package framework.gameMain;

import javax.media.j3d.TransformGroup;

import framework.animation.Animation3D;
import framework.model3D.BaseObject3D;
import framework.model3D.Object3D;
import framework.model3D.Position3D;
import framework.model3D.Placeable;

public abstract class Animatable implements Placeable {
	public Object3D body;
	public Animation3D animation;

	public Animatable(Object3D body, Animation3D animation) {
		this.body = body;
		this.animation = animation;
	}

	/**
	 * �P�ʎ��Ԃ��Ƃ̓���i�A�j���[�V���������j
	 * @param interval --- �O��Ăяo���ꂽ�Ƃ�����̌o�ߎ��ԁi�~���b�P�ʁj
	 */
	public void motion(long interval) {
		if (animation != null) {
			// 1. �A�j���[�V���������s
			if (animation.progress(interval) == false) {
				onEndAnimation();
			}
			
			// 2. �p����ς���
			body.apply(animation.getPose(), false);
		}
	}
	
	public TransformGroup getTransformGroupToPlace() {
		return getBody().getTransformGroupToPlace();
	}
	
	public BaseObject3D getBody() {
		return body;
	}
	
	/**
	 * �A�j���[�V�������I�����邽�тɌĂ΂��
	 */
	public abstract void onEndAnimation();

	public Position3D getPosition() {
		return body.getPosition3D();
	}

	public void setPosition(Position3D p) {
		body.apply(p, false);
	}

}