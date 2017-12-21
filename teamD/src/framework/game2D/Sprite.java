package framework.game2D;

import java.math.BigDecimal;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.image.TextureLoader;

import framework.model3D.BaseObject3D;

/**
 * 2�����̓o�ꕨ�̃N���X
 * 
 * @author T.Kuno
 * 
 */
public class Sprite implements Movable {
	Box box = null;
	private TransformGroup transformGroup;
	private double collisionRadius;
	private float scale = 1.0f;

	// �o�ꕨ�̔z�u�ʒu
	private Position2D position = new Position2D();
	
	// �o�ꕨ�̔z�u�ʒu�̉��s
	private double depth = 0.0;
	
	// �o�ꕨ�̑��x
	private Velocity2D velocity = new Velocity2D();


	// /////////////////////////////////////////////////
	//
	// �R���X�g���N�^
	//
	// /////////////////////////////////////////////////

	public Sprite(String imageFile) {
		this(imageFile, 1.0f);
	}
	
	public Sprite(String imageFile, float scale) {
		this(imageFile, scale, 0.0);
	}

	public Sprite(String imageFile, float scale, double depth) {
		this.scale = scale;
		transformGroup = new TransformGroup();
		Appearance appearance = new Appearance();

		if (imageFile != null){
			TextureLoader loader = new TextureLoader(imageFile, TextureLoader.BY_REFERENCE, null);
			appearance.setTexture(loader.getTexture());
			appearance.setCapability(Appearance.ALLOW_TEXTURE_READ);
			appearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
		} 

		Material material = new Material();
		material.setLightingEnable(false);
		material.setDiffuseColor(0.0f, 0.0f, 0.0f);
		material.setAmbientColor(0.0f, 0.0f, 0.0f);
		material.setSpecularColor(0.0f, 0.0f, 0.0f);
		material.setEmissiveColor(1.0f, 1.0f, 1.0f);
		material.setShininess(1.0f);
		appearance.setMaterial(material);

		TextureAttributes ta = new TextureAttributes();
		ta.setTextureMode(TextureAttributes.REPLACE);
		appearance.setTextureAttributes(ta);

		TransparencyAttributes transAttributes = new TransparencyAttributes();

		transAttributes.setCapability(TransparencyAttributes.ALLOW_BLEND_FUNCTION_READ);
		transAttributes.setCapability(TransparencyAttributes.ALLOW_BLEND_FUNCTION_WRITE);
		transAttributes.setCapability(TransparencyAttributes.ALLOW_MODE_READ);
		transAttributes.setCapability(TransparencyAttributes.ALLOW_MODE_WRITE);
		transAttributes.setCapability(TransparencyAttributes.ALLOW_VALUE_READ);
		transAttributes.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);

		transAttributes.setTransparencyMode(TransparencyAttributes.BLENDED);
		transAttributes.setTransparency(0.0f);
		// transAttributes.setSrcBlendFunction(TransparencyAttributes.BLEND_SRC_ALPHA);
		transAttributes.setDstBlendFunction(TransparencyAttributes.BLEND_SRC_ALPHA);
		appearance.setTransparencyAttributes(transAttributes);

		box = new Box(1.0f * scale, 1.0f * scale, 0.0f, Box.GENERATE_TEXTURE_COORDS | Box.GENERATE_NORMALS, appearance);
		transformGroup.addChild(box);
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		position.set(0.0, 0.0);
		setPosition(0.0, 0.0, depth);
		velocity.set(0.0, 0.0);
		setCollisionRadius(1.0);
	}


	// /////////////////////////////////////////////////
	//
	// Sprite��Position�Ɋւ��郁�\�b�h
	//
	// /////////////////////////////////////////////////

	/**
	 * �X�v���C�g�̈ʒu��ݒ肷��B
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(double x, double y) {
		position.set(x, y);
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(x, y, depth));
		transformGroup.setTransform(t3d);
	}
	
	/**
	 * �X�v���C�g�̈ʒu��ݒ肷��B
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(double x, double y, double z) {
		depth = z;
		position.set(x, y);
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(x, y, z));
		transformGroup.setTransform(t3d);
	}

	// �C���^�[�t�F�[�X�̎���
	@Override
	public Position2D getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(Position2D pos) {
		setPosition(pos.getX(), pos.getY());
	}


	// //////////////////////////////////////////////////////
	//
	// Sprite��Velocity�Ɋւ��郁�\�b�h
	//
	// //////////////////////////////////////////////////////
	/**
	 * �X�v���C�g�̑��x��ݒ肷��B
	 * 
	 * @param x
	 * @param y
	 */
	public void setVelocity(double x, double y) {
		velocity.set(x, y);
	}

	// �C���^�[�t�F�[�X�̎���
	@Override
	public void setVelocity(Velocity2D vel) {
		setVelocity(vel.getX(), vel.getY());
	}

	@Override
	public Velocity2D getVelocity() {
		return this.velocity;
	}

	// /////////////////////////////////////////
	//
	// Sprite��motion���\�b�h
	//
	// ////////////////////////////////////////

	// �C���^�[�t�F�[�X�̎���
	@Override
	public void motion(long interval) {
		double frame = (double)interval / 1000.0;
		setPosition(position.getX() + frame * velocity.getX(), position.getY() + frame * velocity.getY());
	}

	/**
	 * �I�u�W�F�N�g�Ƃ̏Փ˔�������A���̌��ʂɉ����ĕ��̂𓮂���
	 * 
	 * @param interval
	 * @param mazeGround
	 *            --- ���H�Q�[���̃X�e�[�W
	 */
	public void motion(long interval, Map2D mazeGround) {
		// �Փ˔��肷��O�Ɉ�x�X�v���C�g�̈ʒu�𓮂���
		motion(interval);

		// �Փ˔��肨��яՓˉ���
		mazeGround.collisionResponse(this);
	}

	
	// //////////////////////////////
	//
	// �v���C���[���������ɓ��������\�b�h
	//
	// //////////////////////////////

	/**
	 * �L�����N�^�̈ʒu���������ɓ������B
	 * 
	 * @param d
	 *            ��������
	 */
	public void moveLeft(double d) {
		this.position.addX(-1.0 * d / 100);
		setPosition(position);
	}

	// //////////////////////////////
	//
	// �v���C���[���E�����ɓ��������\�b�h
	//
	// //////////////////////////////

	/**
	 * �L�����N�^�̈ʒu���E�����ɓ������B
	 * 
	 * @param d
	 *            ��������
	 */
	public void moveRight(double d) {
		this.position.addX(1.0 * d / 100);
		setPosition(position);
	}

	// //////////////////////////////
	//
	// �v���C���[��������ɓ��������\�b�h
	//
	// //////////////////////////////

	/**
	 * �L�����N�^�̈ʒu��������ɓ������B
	 * 
	 * @param d
	 *            ��������
	 */
	public void moveUp(double d) {
		this.position.addY(1.0 * d / 100);
		setPosition(position);
	}

	// //////////////////////////////
	//
	// �v���C���[���������ɓ��������\�b�h
	//
	// //////////////////////////////

	/**
	 * �L�����N�^�̈ʒu���������ɓ������B
	 * 
	 * @param d
	 *            ��������
	 */
	public void moveDown(double d) {
		this.position.addY(-1.0 * d / 100);
		setPosition(position);
	}

	// //////////////////////////////
	//
	// �Փ˔���֘A�̃��\�b�h
	//
	// //////////////////////////////

	/**
	 * �Փ˔����Bounding Sphere�i���E���j��collisionRadius�Őݒ肷��
	 * 
	 * @param collisionRadius
	 *            -- BoundingSphere�̔��a
	 */
	public void setCollisionRadius(double collisionRadius) {
		this.collisionRadius = collisionRadius;
	}

	/**
	 * �Փ˔����Bounding Sphere�i���E���j�̔��a��Ԃ�
	 * 
	 * @return�@ BoundingSphere�̔��a
	 */
	public double getCollisionRadius() {
		return collisionRadius;
	}
	
	/**
	 * �C���[�W��ύX����
	 * @param imageFile �C���[�W�t�@�C����
	 */
	public void setImage(String imageFile) {
		Appearance appearance = box.getAppearance();
		if (imageFile != null && appearance != null){
			TextureLoader loader = new TextureLoader(imageFile, TextureLoader.BY_REFERENCE, null);
			appearance.setTexture(loader.getTexture());
		} 
	}

	@Override
	public BaseObject3D getBody() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransformGroup getTransformGroupToPlace() {
		// TODO Auto-generated method stub
		return transformGroup;
	}

	@Override
	public boolean checkCollision(Movable other) {
		if (other instanceof Sprite) {
			Sprite otherSprite = (Sprite)other;
			double thisMinX = getPosition().getX();
			double thisMaxX = getPosition().getX() + 2.0 * (double)scale;
			double thisMinY = getPosition().getY();
			double thisMaxY = getPosition().getY() + 2.0 * (double)scale;
			double otherMinX = otherSprite.getPosition().getX();
			double otherMaxX = otherSprite.getPosition().getX() + 2.0 * (double)scale;
			double otherMinY = otherSprite.getPosition().getY();
			double otherMaxY = otherSprite.getPosition().getY() + 2.0 * (double)scale;
			if((otherMinX <= thisMinX && otherMaxX >= thisMaxX)
					|| (otherMinX >= thisMinX && otherMaxX <= thisMaxX)){
				if(otherMaxY > thisMinY && otherMaxY < thisMaxY){
					return true;
				} else if(otherMinY < thisMaxY && otherMinY > thisMinY){
					return true;
				}
			} else if((otherMinY <= thisMinY && otherMaxY >= thisMaxY)
				|| (otherMinY >= thisMinY && otherMaxY <= thisMaxY)){
				if(otherMaxX > thisMinX && otherMaxX < thisMaxX){
					return true;				
				} else if(otherMinX < thisMaxX && otherMinX > thisMaxX){
					return true;				
				}
			} else if (otherMinX > thisMinX && otherMinX < thisMaxX && otherMaxY > thisMinY && otherMaxY < thisMaxY) {
				return true;			
			} else if (otherMaxX > thisMinX && otherMaxX < thisMaxX && otherMaxY > thisMinY && otherMaxY < thisMaxY) {
				return true;			
			} else if (otherMaxX > thisMinX && otherMaxX < thisMaxX && otherMinY > thisMinY && otherMinY < thisMaxY) {
				return true;			
			} else if (otherMinX > thisMinX && otherMinX < thisMaxX && otherMinY > thisMinY && otherMinY < thisMaxY) {
				return true;			
			}
			return false;
		} else if (other instanceof Actor2D) {
			Vector2d v = new Vector2d();
			v.sub(this.getPosition().getVector2d(), other.getPosition()
					.getVector2d());
			if (v.length() <= (getCollisionRadius() + ((Actor2D)other).getCollisionRadius())) {
				return true;
			} else {
				return false;
			}			
		}
		return false;
	}
	
	public boolean checkCollisionWithRadius(Movable other) {
		if (other instanceof Sprite) {
			Sprite otherSprite = (Sprite)other;
			double thisMinX = getPosition().getX();
			double thisMaxX = getPosition().getX() + 2.0 * collisionRadius;
			double thisMinY = getPosition().getY();
			double thisMaxY = getPosition().getY() + 2.0 * collisionRadius;
			double otherMinX = otherSprite.getPosition().getX();
			double otherMaxX = otherSprite.getPosition().getX() + 2.0 * otherSprite.collisionRadius;
			double otherMinY = otherSprite.getPosition().getY();
			double otherMaxY = otherSprite.getPosition().getY() + 2.0 * otherSprite.collisionRadius;
			if((otherMinX <= thisMinX && otherMaxX >= thisMaxX)
					|| (otherMinX >= thisMinX && otherMaxX <= thisMaxX)){
				if(otherMaxY > thisMinY && otherMaxY < thisMaxY){
					return true;
				} else if(otherMinY < thisMaxY && otherMinY > thisMinY){
					return true;
				}
			} else if((otherMinY <= thisMinY && otherMaxY >= thisMaxY)
				|| (otherMinY >= thisMinY && otherMaxY <= thisMaxY)){
				if(otherMaxX > thisMinX && otherMaxX < thisMaxX){
					return true;				
				} else if(otherMinX < thisMaxX && otherMinX > thisMaxX){
					return true;				
				}
			} else if (otherMinX > thisMinX && otherMinX < thisMaxX && otherMaxY > thisMinY && otherMaxY < thisMaxY) {
				return true;			
			} else if (otherMaxX > thisMinX && otherMaxX < thisMaxX && otherMaxY > thisMinY && otherMaxY < thisMaxY) {
				return true;			
			} else if (otherMaxX > thisMinX && otherMaxX < thisMaxX && otherMinY > thisMinY && otherMinY < thisMaxY) {
				return true;			
			} else if (otherMinX > thisMinX && otherMinX < thisMaxX && otherMinY > thisMinY && otherMinY < thisMaxY) {
				return true;			
			}
			return false;
		} else if (other instanceof Actor2D) {
			Vector2d v = new Vector2d();
			v.sub(this.getPosition().getVector2d(), other.getPosition()
					.getVector2d());
			if (v.length() <= (getCollisionRadius() + ((Actor2D)other).getCollisionRadius())) {
				return true;
			} else {
				return false;
			}			
		}
		return false;
	}
}
