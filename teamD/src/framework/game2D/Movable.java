package framework.game2D;

import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import framework.model3D.BaseObject3D;
import framework.model3D.Placeable;
import framework.model3D.Position3D;
import framework.physics.Solid3D;
import framework.physics.Velocity3D;

public interface Movable extends Placeable {

	// /////////////////////////////////////////////////
	//
	// ���삷�镨�̂�Position�Ɋւ���C���^�[�t�F�[�X
	//
	// /////////////////////////////////////////////////

	/**
	 * ���삷�镨�̂�pos��ݒ肷��
	 * 
	 * @param pos
	 *            -- �V�����ʒu
	 */
	public void setPosition(Position2D pos);

	/**
	 *�@���삷�镨�̂̌��݂̈ʒu��Ԃ��B
	 * 
	 * @return ���݂̈ʒu
	 */
	public Position2D getPosition();

	// ///////////////////////////////////////////////
	//
	// ���삷�镨�̂�Velocity�Ɋւ���C���^�[�t�F�[�X
	//
	// ///////////////////////////////////////////////

	/**
	 * ���삷�镨�̂�vel��ݒ肷��B
	 * 
	 * @param vel
	 *            -- �V�������x
	 */
	public void setVelocity(Velocity2D vel);

	/**
	 * ���삷�镨�̂̌��݂̑��x��Ԃ��B
	 * 
	 * @return ���݂̑��x
	 */
	public Velocity2D getVelocity();

	// /////////////////////////////////////////
	//
	// ���삷�镨�̂�motion�C���^�[�t�F�[�X
	//
	// ////////////////////////////////////////

	/**
	 * ���삷�镨�̂��ŗL�̑��x�œ�����
	 * 
	 * @param interval
	 */
	public void motion(long interval);

	/**
	 * �����ƕʂ̓o�ꕨ�Ƃ̓����蔻��
	 * 
	 * @param other
	 *            �����ȊO�̓o�ꕨ
	 * @return true --- ��������, false --- �������ĂȂ�
	 */
	public boolean checkCollision(Movable other);
}
