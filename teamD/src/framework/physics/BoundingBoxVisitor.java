package framework.physics;
import java.util.ArrayList;

import javax.media.j3d.BoundingSphere;

import framework.model3D.OBB;
import framework.model3D.Object3D;
import framework.model3D.ObjectVisitor;


public class BoundingBoxVisitor extends ObjectVisitor {
	private ArrayList<OBB> obbList = new ArrayList<OBB>();// �t�̂Ƃ��ABounds������ĕۑ�
	private String partName = null;	// ���i���w�肷��ꍇ�Ɏg��
	private boolean inPart = false;
	
	public BoundingBoxVisitor() {
		partName = null;
	}
	
	public BoundingBoxVisitor(String partName) {
		this.partName = partName;
	}
	
	public void preVisit(Object3D obj) {
		pushTransform(obj);
		if (partName != null && obj.name.equals(partName)) {
			inPart = true;
		}
	}

	public void postVisit(Object3D obj) {
		int pattern = 2;
		if (!obj.hasChildren()) {
			// �t�̏ꍇ
			OBB obb = obj.getOBB(pattern);
			if (obb != null) {
				if (obj.bs == null) {
					obj.bs = obb.getBoundingSphere();
				}
	
				obb = (OBB)obb.clone();
				for (int i = stackList.size() - 1; i >= 0; i--) {
					obb.transform(stackList.get(i));
//					obj.bs.transform(stackList.get(i));
				}
				if (partName == null || partName.length() == 0 || inPart) {
					obbList.add(obb); // Transform3D��K��������Bounds��boundsList�ɒǉ�
				}
			}
		} else {
			Object3D[] objList = obj.children;
			if (obj.bs == null) {
				obj.bs = new BoundingSphere();
				for (int i = 0; i < objList.length; i++) {
					obj.bs.combine(objList[i].bs);
				}
			}
		}
		popTransform();
		if (partName != null && obj.name.equals(partName)) {
			inPart = false;
		}
	}

	public ArrayList<OBB> getObbList() {
		return obbList;
	}
}
