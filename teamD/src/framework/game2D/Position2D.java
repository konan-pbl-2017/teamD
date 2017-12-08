package framework.game2D;

import javax.vecmath.Vector2d;

import framework.model3D.Position3D;

/**
 * 2�����̍��W�l��\��
 * 
 * @author T.Kuno
 * 
 */
public class Position2D extends Property2D {

	private double x;
	private double y;

	// //////////////////////////////////////////////////////////////
	//
	// �R���X�g���N�^
	//
	// //////////////////////////////////////////////////////////////
	public Position2D() {
		this.x = 0.0;
		this.y = 0.0;
	}

	public Position2D(double px, double py) {
		this.x = px;
		this.y = py;
	}

	public Position2D(Vector2d v) {
		this.x = v.x;
		this.y = v.y;
	}

	// �R�s�[�R���X�g���N�^
	public Position2D(Position2D p) {
		this.x = p.x;
		this.y = p.y;
	}

	// Position3D����Position2D�ɂ���R���X�g���N�^
	public Position2D(Position3D p) {
		this.x = p.getX();
		this.y = p.getY();
	}

	// /////////////////////////////////////////////////////////
	//
	// ���ۃ��\�b�h�̎���
	//
	// /////////////////////////////////////////////////////////

	public Position2D clone() {
		return new Position2D(this);
	}

	// //////////////////////////////////////////////////////
	//
	// Position2D�Ɋւ�����Z�E���Z�E�|���Z���\�b�h
	//
	// //////////////////////////////////////////////////////

	// ���Z
	/**
	 * Position2D��x,y�ɉ��Z����
	 * 
	 * @param x
	 *            ���W�l
	 * @param y
	 *            ���W�l
	 * @return Position2D
	 */
	public Position2D add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * Position2D��x,y�Ɉ�����Position2D��x,y�����Z����
	 * 
	 * @param p
	 *            Position2D(x,y)
	 * @return Position2D
	 */
	public Position2D add(Position2D p) {
		this.x += p.x;
		this.y += p.y;
		return this;
	}

	/**
	 * Position2D��x,y�Ɉ�����Vector2d��x,y�����Z����
	 * 
	 * @param v
	 *            2�����x�N�g��
	 * @return Position2D
	 */
	public Position2D add(Vector2d v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}

	// ���Z
	/**
	 * Position2D��x,y�Ɍ��Z����
	 * 
	 * @param x
	 *            ���W�l
	 * @param y
	 *            ���W�l
	 * @return Position2D
	 */
	public Position2D sub(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	/**
	 * Position2D��x,y�Ɉ�����Position2D��x,y�����Z����
	 * 
	 * @param p
	 *            Position2D(x,y)
	 * @return Position2D
	 */
	public Position2D sub(Position2D p) {
		this.x -= p.x;
		this.y -= p.y;
		return this;
	}

	/**
	 * Position2D��x,y�Ɉ�����Vector2d��x,y�����Z����
	 * 
	 * @param v
	 *            2�����x�N�g��
	 * @return Position2D
	 */
	public Position2D sub(Vector2d v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}

	// �|���Z
	/**
	 * Position2D��x,y�Ɉ����̒l�����|���Z����
	 * 
	 * @param d
	 *            �{��
	 * @return Position2D
	 */
	public Position2D mul(double d) {
		this.x *= d;
		this.y *= d;
		return this;
	}

	// //////////////////////////////////////////////////////
	//
	// Position2D�Ɋւ��郁�\�b�h
	//
	// //////////////////////////////////////////////////////

	/**
	 * Position2D��x,y�ɐݒ肷��
	 * 
	 * @param x
	 *            ���W�l
	 * @param y
	 *            ���W�l
	 * @return Position2D
	 */
	public Position2D set(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}

	// x�Ɋւ��郁�\�b�h
	/**
	 * Position2D��x�Ɉ�����x��ݒ肷��
	 * 
	 * @param x
	 *            ���W�l
	 * @return Position2D
	 */

	public Position2D setX(double x) {
		this.x = x;
		return this;
	}

	/**
	 * Position2D��x���擾����
	 * 
	 * @return Position2D
	 */

	public double getX() {
		return x;
	}

	/**
	 * Position2D��x�Ɉ����̒l�����Z����
	 * 
	 * @param d
	 */
	public void addX(double d) {
		this.x += d;
	}

	// y�Ɋւ��郁�\�b�h

	/**
	 * Position2D��y�Ɉ�����y��ݒ肷��
	 * 
	 * @param x
	 *            ���W�l
	 * @return Position2D
	 */

	public Position2D setY(double y) {
		this.y = y;
		return this;
	}

	/**
	 * Position2D��y���擾����
	 * 
	 * @return Position2D
	 */

	public double getY() {
		return y;
	}

	/**
	 * Position2D��y�Ɉ����̒l�����Z����
	 * 
	 * @param d
	 */

	public void addY(double d) {
		this.y += d;
	}

	// Vector2d�Ɋւ��郁�\�b�h
	/**
	 * Position2D��x,y��Vector2d�Őݒ肷��
	 * 
	 * @return Position2D
	 */

	public Position2D setVector2d(Vector2d v) {
		x = v.x;
		y = v.y;
		return this;
	}

	/**
	 * Position2D��x,y��Vector2d�Ŏ擾����
	 * 
	 * @return Vector2d
	 */

	public Vector2d getVector2d() {
		return new Vector2d(x, y);
	}

}
