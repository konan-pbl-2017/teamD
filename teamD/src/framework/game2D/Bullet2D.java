package framework.game2D;

import java.util.ArrayList;

import javax.vecmath.Vector2d;

import framework.animation.Animation3D;
import framework.gameMain.Actor;
import framework.gameMain.FlyingActor;
import framework.gameMain.OvergroundActor;
import framework.model3D.BaseObject3D;
import framework.model3D.Object3D;
import framework.model3D.Universe;

public abstract class Bullet2D extends Actor2D {
	private ArrayList<BaseObject3D> extraObjects = new ArrayList<BaseObject3D>();
	
	protected Actor createActor(Object3D modelBody, Animation3D anim) {
		return new FlyingActor(modelBody, anim);
	}
	
	// //////////////////////////////
	//
	// �e�̍쐬�Ɋւ��郁�\�b�h
	//
	// //////////////////////////////

	/**
	 * ������universe���ɒe�������B�i�e�͎�菜�����Ƃ��o����B�j
	 * 
	 * @param universe
	 *            -- �Q�[���ɂ�������
	 */
	public void createPlayer(Universe universe) {
		BaseObject3D body = this.getBody();
		if (body.isReflectionMappingApplied() || body.isBumpMappingApplied()) {
			extraObjects.add(body);
		} else {
			universe.placeUnremovable(this.getTransformGroupToPlace());
		}
	}
	
	/**
	 * �L�����N�^�̈ʒu��set����B
	 * @param x -- x���W
	 * @param y -- y���W
	 */
	public void setPosition(double x, double y) {
		Position2D pos = new Position2D(x,y);
		super.setPosition(pos);
	}
	
	/**
	 * �L�����N�^�̈ʒu��Postion2D��get����B
	 * @return	Position2D
	 */
	public Position2D getPosition() {
		return super.getPosition();
	}

	/**
	 * �L�����N�^�̌�����Vector2d��set����B
	 * @param dir -- 2�����x�N�g���ŕ\��������
	 */
	public void setDirection(double x, double y) {
		Vector2d dir = new Vector2d(x, y);
		super.setDirection(dir);
	}
	
	/**
	 * �L�����N�^�̌��݂̌�����Vector2d��get����B
	 * @return Vector2d -- 2�����x�N�g���ŕ\��������
	 */
	public Vector2d getDirection() {
		return super.getDirection();
	}
	
	/**
	 * �L�����N�^�̑��x��ς���B�i�����̒l�Ŕ{�ɂ���j
	 * @param velocity -- �{��  
	 */

	public void setMulVelocity(Velocity2D vel, double velocity) {
		vel.setX(vel.getX() * velocity);
		vel.setY(vel.getY() * velocity);
		super.setVelocity(vel);
	}

	/**
	 * �L�����N�^�̑��x��\��Velocity2D��get����B
	 * @return	velocity2D  
	 */
	
	public Velocity2D getVelocity() {
		return super.getVelocity();
	}
	
}
