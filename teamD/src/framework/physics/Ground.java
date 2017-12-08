package framework.physics;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;

import framework.model3D.BaseObject3D;
import framework.model3D.BoundingSurface;
import framework.model3D.Object3D;
import framework.model3D.Placeable;


/**
 * �n�ʂȂǂ́i��{�I�ɓ����Ȃ��j�\������\���I�u�W�F�N�g
 * @author �V�c����
 *
 */
public class Ground implements Placeable {
	private BaseObject3D groundObj = null;
	private BoundingSurface boundingSurface = null;		// �Փ˔���p�{�����[���̃L���b�V��
	
	public Ground(BaseObject3D obj) {
		groundObj = obj;
	}
	
	public BaseObject3D getBody() {
		return groundObj;
	}
	

	@Override
	public TransformGroup getTransformGroupToPlace() {
		return groundObj.getTransformGroupToPlace();
	}

	BoundingSurface getBoundingSurface() {
		if (boundingSurface == null) {
			// �L���b�V���ɉ����ς܂�Ă��Ȃ��ꍇ
			BoundingSurfaceVisitor surfaceVisitor = new BoundingSurfaceVisitor();
			if (groundObj instanceof Object3D) {
				((Object3D)groundObj).accept(surfaceVisitor);
			} else {
				surfaceVisitor.baseVisit(groundObj);
			}
			boundingSurface = surfaceVisitor.getBoundingSurface();
		}
		return boundingSurface;
	}
}
