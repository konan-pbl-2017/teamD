package framework.model3D;

import javax.media.j3d.TransformGroup;

/**
 * �z�u�ł�����̑S��
 * @author �V�c����
 *
 */
public interface Placeable {
	abstract TransformGroup getTransformGroupToPlace();
	abstract BaseObject3D getBody();
}
