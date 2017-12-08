package framework.view3D;

import javax.media.j3d.View;
import javax.vecmath.Vector3d;

import framework.model3D.Universe;

/**
 * 2D�V���[�e�B���O�p�̃J����
 * 
 * @author K.Yamane
 * 
 */
public class CameraShooting extends Camera3D {

	private Vector3d cameraBack = null;

	// �R���X�g���N�^
	public CameraShooting(Universe universe) {
		super(universe);
		setSideView();
		this.getView().setProjectionPolicy(View.PARALLEL_PROJECTION);
	}

	/**
	 * range�Ŏw�肳�ꂽ�l����J�������f�荞�ޔ͈͂�ݒ肷��
	 * 
	 * @param range
	 */
	public void setRange(double range) {
		cameraBack = new Vector3d(0.0, 0.0, range);
		setCameraBack(cameraBack);
	}
}
