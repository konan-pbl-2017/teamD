package framework.model3D;

import java.util.ArrayList;

import javax.media.j3d.Light;
import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3d;

/**
 * �V���h�E�{�����[���p�̃r�W�^�[
 * @author Nitta
 *
 */
public class ShadowVolumeVisitor extends ObjectVisitor {
	private boolean bInitialize = true;
	private ArrayList<Light> lights = null;
	private double shadowDepth = 0;

	/**
	 * �V���h�E�{�����[�����X�V����ꍇ
	 */
	public ShadowVolumeVisitor() {
		this.bInitialize = false;
		Transform3D t = new Transform3D();
		stackList.add(t);
	}
	
	/**
	 * �V���h�E�{�����[�����쐬����ꍇ
	 * @param lights ����
	 * @param shadowDepth �e�̐[��
	 */
	public ShadowVolumeVisitor(ArrayList<Light> lights, double shadowDepth) {
		this.lights = lights;
		this.shadowDepth = shadowDepth;
		this.bInitialize = true;
		Transform3D t = new Transform3D();
		stackList.add(t);
	}

	@Override
	public void preVisit(Object3D obj) {
		Transform3D t = new Transform3D(stackList.get(stackList.size() - 1));
		Transform3D t2 = new Transform3D();
		obj.pos.getTransform(t2);
		t.mul(t2);
		obj.rot.getTransform(t2);
		t.mul(t2);
		obj.scale.getTransform(t2);
		t.mul(t2);
		obj.center.getTransform(t2);
		t.mul(t2);
		stackList.add(t);
	}

	@Override
	public void postVisit(Object3D obj) {
		Transform3D t = stackList.remove(stackList.size() - 1);
		if (!obj.hasChildren()) {
			// �t�I�u�W�F�N�g�̏ꍇ�A�V���h�E�{�����[���̏���������
			if (bInitialize) {
				// �������̏ꍇ�A�V���h�E�{�����[���̐���
				obj.createShadowVolume(lights, shadowDepth, t);
			} else {
				// �������łȂ��ꍇ�A�V���h�E�{�����[���̍X�V
				obj.updateShadowVolume(t);
			}
		}
	}
}
