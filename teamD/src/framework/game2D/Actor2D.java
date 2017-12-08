package framework.game2D;

import java.util.HashMap;

import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import framework.animation.Animation3D;
import framework.animation.AnimationFactory;
import framework.gameMain.Actor;
import framework.model3D.BaseObject3D;
import framework.model3D.Model3D;
import framework.model3D.ModelFactory;
import framework.model3D.Object3D;
import framework.model3D.Placeable;
import framework.model3D.Position3D;
import framework.physics.PhysicsUtility;
import framework.physics.Solid3D;
import framework.physics.Velocity3D;

/**
 * RadishFramework����Actor���B�����AActor�ւ̃A�N�Z�X�𒇉�钊�ۃN���X
 * 
 * @author T.Kuno
 * 
 */
public abstract class Actor2D implements Movable {
	private Actor actor;
	protected double collisionRadius = 0.5;

	private static HashMap<Class, Model3D> models = new HashMap<Class, Model3D>();
	private static HashMap<Class, Animation3D> animations = new HashMap<Class, Animation3D>();
	
	public abstract String getModelFileName();

	public abstract String getAnimationFileName();

	// /////////////////////////////////////////////////
	//
	// �R���X�g���N�^
	//
	// /////////////////////////////////////////////////

	public Actor2D() {
		String modelFileName = getModelFileName();
		if (modelFileName == null) return;
		if (models.containsKey(this.getClass()) == false) {
			models.put(this.getClass(), ModelFactory
					.loadModel(modelFileName));
		}
		if (getAnimationFileName() != null
				&& animations.containsKey(this.getClass()) == false) {
			animations.put(this.getClass(), AnimationFactory
					.loadAnimation(getAnimationFileName()));
		}
		// �L�����N�^�[��3D�f�[�^��ǂݍ��ݔz�u����
		Object3D modelBody = models.get(this.getClass()).createObject();
		Animation3D animation = animations.get(this.getClass());
		if (animation != null) {
			Animation3D anim = new Animation3D(animation);
			setActor(createActor(modelBody, anim));
		} else {
			setActor(createActor(modelBody, null));
		}
	}

	// /////////////////////////////////////////////////
	//
	// Placeble�̃C���^�[�t�F�[�X�̎���
	//
	// /////////////////////////////////////////////////

	public TransformGroup getTransformGroupToPlace() {
		return getActor().getBody().getTransformGroupToPlace();
	}

	public BaseObject3D getBody() {
		return getActor().getBody();
	}

	// /////////////////////////////////////////////////
	//
	// RadishFramework���Actor�Ɋւ��郁�\�b�h
	//
	// /////////////////////////////////////////////////

	abstract protected Actor createActor(Object3D modelBody, Animation3D anim);

	/**
	 * Actor2D������Actor�ɐݒ肷��
	 * 
	 * @param actor
	 *            -- RadishFramework�ō���Ă���Actor�Ɉ�����Actor��n��
	 */
	public void setActor(Actor actor) {
		this.actor = actor;
	}

	/**
	 * Actor2D������Actor��Ԃ�
	 * 
	 * @return RadishFramework�ō���Ă���Actor
	 */
	public Actor getActor() {
		return actor;
	}

	// /////////////////////////////////////////////////
	//
	// RadishFramework���Actor������Position�Ɋւ��郁�\�b�h
	//
	// /////////////////////////////////////////////////

	/**
	 * ����Actor�̈ʒu�� pos �ɐݒ肷��
	 * 
	 * @param pos
	 *            -- �V�����ʒu
	 */
	public void setPosition(Position2D pos) {
		Position3D p = new Position3D(pos.getX(), pos.getY(), getActor()
				.getPosition().getZ());
		getActor().body.apply(p, false);
	}

	/**
	 * ����Actor�̌��݂̈ʒu��Ԃ�
	 * 
	 * @return ���݂̈ʒu
	 */
	public Position2D getPosition() {
		return new Position2D(getActor().getPosition().getX(), getActor()
				.getPosition().getY());
	}

	// ////////////////////////////////////////////////
	//
	// RadishFramework���Actor������Direction�Ɋւ��郁�\�b�h
	//
	// ////////////////////////////////////////////////

	/**
	 * ����Actor�̌����� vec �ɐݒ肷��
	 * 
	 * @param vec
	 *            -- �V��������
	 */
	public void setDirection(Vector2d vec) {
		Vector3d v = new Vector3d(vec.x, vec.y, getActor().getDirection()
				.getZ());
		getActor().setDirection(v);
	}
	

	/**
	 * ����Actor�̌����� vec �ɐݒ肷��
	 * 
	 * @param vec
	 *            -- �V��������
	 */
	public void setDirection(Vector3d vec) {
		getActor().setDirection(vec);
	}

	/**
	 * ����Actor�̌��݂̌�����Ԃ�
	 * 
	 * @return ���݂̈ʒu
	 */
	public Vector2d getDirection() {
		return new Vector2d(getActor().getDirection().getX(), getActor()
				.getDirection().getY());
	}

	// ///////////////////////////////////////////////
	//
	// RadishFramework���Actor������Velocity�Ɋւ��郁�\�b�h
	//
	// ///////////////////////////////////////////////

	/**
	 * ����Actor�̑��x�� vel �ɐݒ肷��
	 * 
	 * @param vel
	 *            -- �V�������x
	 */
	public void setVelocity(Velocity2D vel) {
		Velocity3D velocity = new Velocity3D(vel.getX(), vel.getY(), getActor()
				.getVelocity().getZ());
		((Solid3D) getActor().body).apply(velocity, false);
	}

	/**
	 * ����Actor�̑��x�� velVector �ɐݒ肷��
	 * 
	 * @param velVector
	 *            -- �V�������x
	 */
	public void setVelocity(Vector2d velVector) {
		Velocity2D vel = new Velocity2D(velVector.getX(), velVector.getY());
		setVelocity(vel);
	}

	/**
	 * ����Actor�̌��݂̑��x��Ԃ�
	 * 
	 * @return ���݂̑��x
	 */
	public Velocity2D getVelocity() {
		return new Velocity2D(getActor().getVelocity().getX(), getActor()
				.getVelocity().getY());
	}

	// /////////////////////////////////////////
	//
	// RadishFramework���motion���\�b�h���Ăяo��
	//
	// ////////////////////////////////////////

	/**
	 * Actor��motion���\�b�h���Ăяo��
	 * 
	 * @param interval
	 */
	public void motion(long interval) {
		getActor().motion(interval);
	}

	/**
	 * Actor��motion���\�b�h���Ăяo��
	 * 
	 * @param interval
	 */
	public void motion(long interval, Ground2D ground) {
		getActor().motion(interval, ground.getGround());
	}
	
	public void rotate(double angle) {
		getActor().rotZ(angle);
	}
	
	public void rotateX(double angle) {
		getActor().rotX(angle);
	}
	
	public void rotateY(double angle) {
		getActor().rotY(angle);
	}
	
	public void rotateZ(double angle) {
		getActor().rotZ(angle);
	}

	// ///////////////////////////////////////////////////////////////
	//
	// RadishFramework���PhysicsUtility��checkColision���\�b�h���Ăяo��
	//
	// ///////////////////////////////////////////////////////////////

	/**
	 * �����ƕʂ̓o�ꕨ�Ƃ̓����蔻��
	 * 
	 * @param other
	 *            �����ȊO�̓o�ꕨ
	 * @return true --- ��������, false --- �������ĂȂ�
	 */
	public boolean checkCollision(Movable other) {
		if (other.getBody() != null) {
			if (PhysicsUtility.checkCollision((Object3D) this.getActor().getBody(),
					null, (Object3D) other.getBody(), null) != null) {
				return true;
			}
			return false;
		} else if (other instanceof Sprite) {
			return ((Sprite)other).checkCollision(this);
		} else if (other instanceof Actor2D) {
			Vector2d v = new Vector2d();
			v.sub(this.getPosition().getVector2d(), other.getPosition().getVector2d());
			if (v.length() <= (collisionRadius + ((Actor2D)other).getCollisionRadius())) {
				return true;
			} else {
				return false;
			}			
		}
		return false;
	}

	/**
	 * Bounding Circle�i���E�~�j �̔��a��ݒ肷��
	 * 
	 * @param collisionRadius
	 */
	public void setCollisionRadius(double collisionRadius) {
		this.collisionRadius = collisionRadius;
	}

	/**
	 * Bounding Circle�i���E�~�j �̔��a��ݒ肷��
	 * 
	 * @return --- BS�̔��a
	 */
	public double getCollisionRadius() {
		return collisionRadius;
	}

}
