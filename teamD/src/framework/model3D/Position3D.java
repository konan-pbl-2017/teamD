package framework.model3D;

import javax.vecmath.Vector3d;

/**
 * ���W�l��\��
 * @author �V�c����
 *
 */
public class Position3D extends Property3D {
	private double x;
	private double y;
	private double z;

	// �R���X�g���N�^
	public Position3D() {
		x = 0.0;
		y = 0.0;
		z = 0.0;
	}

	// �R���X�g���N�^
	public Position3D(double px, double py, double pz) {
		x = px;
		y = py;
		z = pz;
	}

	public Position3D(Vector3d v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	// property3D�̒��ۃN���X�𖄂߂�
	public void applyTo(Object3D o) {
		o.setPosition(this);
	}

	// �R�s�[�R���X�g���N�^
	public Position3D(Position3D p) {
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}
	
	public Position3D set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	// ���Z
	public Position3D add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	// ���Z
	public Position3D add(Position3D p) {
		this.x += p.x;
		this.y += p.y;
		this.z += p.z;
		return this;
	}

	// ���Z
	public Position3D add(Vector3d v) {
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
		return this;
	}

	// ���Z
	public Position3D sub(double x, double y, double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	// ���Z
	public Position3D sub(Position3D p) {
		this.x -= p.x;
		this.y -= p.y;
		this.z -= p.z;
		return this;
	}
	
	// ���Z
	public Position3D sub(Vector3d v) {
		this.x -= v.x;
		this.y -= v.y;
		this.z -= v.z;
		return this;
	}

	// �X�J���[�{
	public Position3D mul(double d) {
		this.x *= d;
		this.y *= d;
		this.z *= d;
		return this;
	}

	public Position3D setX(double x) {
		this.x = x;
		return this;
	}

	public double getX() {
		return x;
	}

	public Position3D setY(double y) {
		this.y = y;
		return this;
	}

	public double getY() {
		return y;
	}

	public Position3D setZ(double z) {
		this.z = z;
		return this;
	}

	public double getZ() {
		return z;
	}

	public Vector3d getVector3d() {
		return new Vector3d(x, y, z);
	}

	public Position3D setVector3d(Vector3d v) {
		x = v.x;
		y = v.y;
		z = v.z;
		return this;
	}
	
	public Position3D clone() {
		return new Position3D(this);
	}
}
