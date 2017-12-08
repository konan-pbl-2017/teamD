package framework.game2D;

import java.util.ArrayList;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import framework.animation.Animation3D;
import framework.gameMain.Actor;
import framework.gameMain.OvergroundActor;
import framework.model3D.BaseObject3D;
import framework.model3D.Object3D;
import framework.model3D.Universe;
import framework.physics.Velocity3D;

/**
 * 2D�Q�[����̃L�����N�^�̒��ۃN���X�B�Q�[����̃L�����N�^�̃N���X�����ɂ͂�����p�����쐬����B
 * 
 * @author T.Kuno
 * 
 */
public abstract class Player2D extends Actor2D {
	private ArrayList<BaseObject3D> extraObjects = new ArrayList<BaseObject3D>();

	private static final Vector2d LEFT_AXIS = new Vector2d(-1.0, 0.0);
	private static final Vector2d Right_AXIS = new Vector2d(1.0, 0.0);
	private static final Vector2d UP_AXIS = new Vector2d(0.0, 1.0);
	private static final Vector2d DOWN_AXIS = new Vector2d(0.0, -1.0);

	// �L�����N�^�̓����Ŏg���O���[�o���ϐ��B�{���͂���������Ȃ��݌v�ɂ���B
	protected Vector3d direction = new Vector3d(1.0, 1.0, 1.0);
	private Velocity2D InitialVelocity = new Velocity2D(1.0, 0.0);

	protected Actor createActor(Object3D modelBody, Animation3D anim) {
		return new OvergroundActor(modelBody, anim);
	}

	// //////////////////////////////
	//
	// �v���C���[�̍쐬�Ɋւ��郁�\�b�h
	//
	// //////////////////////////////

	/**
	 * ������universe���Ƀv���C���[�������B�i�v���C���[�͎�菜�����Ƃ��o����B�j
	 * 
	 * @param universe
	 *            -- �Q�[���ɂ�������
	 */
	public void createPlayer(Universe universe) {
		BaseObject3D body = this.getBody();
		if (body.isReflectionMappingApplied() || body.isBumpMappingApplied()) {
			extraObjects.add(body);
		} else {
			universe.place(this.getTransformGroupToPlace());
		}
	}

	// //////////////////////////////
	//
	// �v���C���[�̈ʒu�Ɋւ��郁�\�b�h
	//
	// //////////////////////////////

	/**
	 * �L�����N�^�̈ʒu��ݒ肷��B
	 * 
	 * @param x
	 *            -- x���W
	 * @param y
	 *            -- y���W
	 */
	public void setPosition(double x, double y) {
		Position2D pos = new Position2D(x, y);
		super.setPosition(pos);
	}

	/**
	 * �L�����N�^�̈ʒu��Postion2D�Ŏ擾����B
	 * 
	 * @return Position2D
	 */
	public Position2D getPosition() {
		return super.getPosition();
	}

	// //////////////////////////////
	//
	// �v���C���[�����Ɋւ��郁�\�b�h
	//
	// //////////////////////////////

	/**
	 * �L�����N�^�̌�����Vector2d�Őݒ肷��B
	 * 
	 * @param dir
	 *            -- 2�����x�N�g���ŕ\��������
	 */
	public void setDirection(double x, double y) {
		Vector2d dir = new Vector2d(x, y);
		super.setDirection(dir);
	}

	/**
	 * �L�����N�^�̌��݂̌�����Vector2d�Ŏ擾����B
	 * 
	 * @return Vector2d -- 2�����x�N�g���ŕ\��������
	 */
	public Vector2d getDirection() {
		return super.getDirection();
	}

	// //////////////////////////////
	//
	// �v���C���[�𑬓x�Ɋւ��郁�\�b�h
	//
	// //////////////////////////////

	/**
	 * �L�����N�^�̑��x��ς���B�i�����̒l�Ŕ{�ɂ���j
	 * 
	 * @param velocity
	 *            -- �{��
	 */

	public void setVelocity(double velocity) {
		Velocity2D vel = (Velocity2D) InitialVelocity.clone();
		vel.setVelocity(velocity);
		super.setVelocity(vel);
	}

	/**
	 * �L�����N�^�̑��x��\��Velocity2D��get����B
	 * 
	 * @return velocity2D
	 */

	public Velocity2D getVelocity() {
		return super.getVelocity();
	}

	// //////////////////////////////
	//
	// �v���C���[���������ɓ��������\�b�h
	//
	// //////////////////////////////
	/**
	 * �L�����N�^���������l�������A�������̑��x�x�N�g����p���ē������B
	 * 
	 * @param d
	 *            ��������
	 */
	public void moveLeft(double d) {
		Velocity2D vel = new Velocity2D(-1.0 * d, 0.0);
		setVelocity(vel);
	}

	/**
	 * �L�����N�^�̌������l�����āA�������̑��x�x�N�g����p���ē������B
	 * 
	 * @param d
	 *            ��������
	 */
	public void moveDirectionLeft(double d) {
		super.setDirection(LEFT_AXIS);
		moveLeft(d);
	}

	/**
	 * �L�����N�^�̈ʒu���������ɓ������B
	 * 
	 * @param d
	 *            ��������
	 */
	public void movePositionLeft(double d) {
		Position2D p = new Position2D(getActor().getPosition().getX(),
				getActor().getPosition().getY());
		p.addX(-1.0 * d);
		setPosition(p);
	}

	// //////////////////////////////
	//
	// �v���C���[���E�����ɓ��������\�b�h
	//
	// //////////////////////////////

	/**
	 * �L�����N�^���������l�������E�ɓ������B
	 * 
	 * @param d
	 *            ��������
	 */
	public void moveRight(double d) {
		Velocity2D vel = new Velocity2D(1.0 * d, 0.0);
		setVelocity(vel);
	}

	/**
	 * �L�����N�^�̌������l�����ĉE�ɓ�����
	 * 
	 * @param d
	 *            ��������
	 */
	public void moveDirectionRight(double d) {
		super.setDirection(Right_AXIS);
		moveRight(d);
	}

	/**
	 * �L�����N�^�̈ʒu���E�����ɓ������B
	 * 
	 * @param d
	 *            ��������
	 */
	public void movePositionRight(double d) {
		Position2D p = new Position2D(getActor().getPosition().getX(),
				getActor().getPosition().getY());
		p.addX(1.0 * d);
		setPosition(p);
	}

	// //////////////////////////////
	//
	// �v���C���[��������ɓ��������\�b�h
	//
	// //////////////////////////////

	/**
	 * �L�����N�^���������l��������ɓ������B
	 * 
	 * @param d
	 *            ��������
	 */
	public void moveUp(double d) {
		Velocity2D vel = new Velocity2D(0.0, 1.0 * d);
		setVelocity(vel);
	}

	/**
	 * �L�����N�^�̌������l�����ď�ɓ�����
	 * 
	 * @param d
	 *            ��������
	 */
	public void moveDirectionUp(double d) {
		super.setDirection(UP_AXIS);
		moveUp(d);
	}

	/**
	 * �L�����N�^�̈ʒu��������ɓ������B
	 * 
	 * @param d
	 *            ��������
	 */
	public void movePositionUp(double d) {
		Position2D p = new Position2D(getActor().getPosition().getX(),
				getActor().getPosition().getY());
		p.addY(1.0 * d);
		setPosition(p);
	}

	// //////////////////////////////
	//
	// �v���C���[���������ɓ��������\�b�h
	//
	// //////////////////////////////

	/**
	 * �L�����N�^���������l���������ɓ������B
	 * 
	 * @param d
	 *            ��������
	 */
	public void moveDown(double d) {
		Velocity2D vel = new Velocity2D(0.0, -1.0 * d);
		setVelocity(vel);
	}

	/**
	 * �L�����N�^�̌������l�����ĉ��ɓ�����
	 * 
	 * @param d
	 *            ��������
	 */
	public void moveDirectionDown(double d) {
		super.setDirection(DOWN_AXIS);
		moveDown(d);
	}

	/**
	 * �L�����N�^�̈ʒu���������ɓ������B
	 * 
	 * @param d
	 *            ��������
	 */
	public void movePositionDown(double d) {
		Position2D p = new Position2D(getActor().getPosition().getX(),
				getActor().getPosition().getY());
		p.addY(-1.0 * d);
		setPosition(p);
	}

	/**
	 * 
	 * �L�����N�^�̑��x��0�ɂ��A�������~�߂�B
	 * 
	 */

	public void stop() {
		Velocity3D curV = new Velocity3D();
		getActor().setVelocity(curV);
	}

	//////////////////////////////////////////////////////
	//
	// �v���C���[���E�B���h�E���ɂ��邩�ǂ����̃��\�b�h
	//
	/////////////////////////////////////////////////////
	
	public boolean doesScrrenOut(int w, int h) {
		if (this.getPosition().getX() < w / 2.0
				&& this.getPosition().getX() > -1.0 * w / 2.0) {
			if(this.getPosition().getY() < h / 2.0
					&& this.getPosition().getY() > -1.0 * h / 2.0){
				return true;
			}
		}
		return false;
	}
	
}
