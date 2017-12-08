package framework.AI;

import java.util.ArrayList;

import javax.media.j3d.Geometry;
import javax.media.j3d.IndexedTriangleArray;
import javax.vecmath.Point3d;

import framework.model3D.Position3D;

public class GeometryGraph extends StateMachine {
	// �R���X�g���N�^
	public GeometryGraph(Geometry g) {
		if (g instanceof IndexedTriangleArray) {

			IndexedTriangleArray ita = (IndexedTriangleArray) g;

			// ita����states�Ƀf�[�^����������
			for (int i = 0; i < ita.getIndexCount() / 3; i++) {// �ʂ̐��������[�v
				Location e = new Location(i, ita);
				if (e.getNormal() != null) {
					states.add(e);
				}
			}

			// �֘A�t��
			for (int i = 0; i < states.size(); i++) {
				setSuccessors(i, ita);
			}
		}
	}
	

	// �A���������֘A�t��
	public void setSuccessors(int index, IndexedTriangleArray ita) {
		Location e = (Location)states.get(index);
		
		// �T��
		int[] List = new int[ita.getIndexCount()];

		for (int i = 0; i < ita.getIndexCount(); i++) {
			List[i] = ita.getCoordinateIndex(i);
		}

		// -1�ŏ�����
		for (int i = 0; i < 3; i++) {
			e.successorIndex[i] = -1;
			e.IndexOfSharedEdge[i] = -1;
		}

		int j = 0;

		for (int i = 0; i < states.size() * 3; i++) {
			// System.out.println("indexList[0] ��List["+i+"] �̏ƍ�");
			// //////id�łk��������u�������ă��\�b�h�Ăяo�������Ȃ�?
			// int id = ita.getCoordinateIndex(i);
			if (e.indexList[0] == List[i]) {
				if (i % 3 == 0) {
					if (e.indexList[1] == List[i + 2]) {
						e.successorIndex[j] = i / 3;
						e.IndexOfSharedEdge[0] = e.indexList[0];
						e.IndexOfSharedEdge[1] = e.indexList[1];
						e.addSuccessor(states.get(i / 3));
						j++;
					} else if (e.indexList[2] == List[i + 1]) {
						e.successorIndex[j] = i / 3;
						e.IndexOfSharedEdge[0] = e.indexList[0];
						e.IndexOfSharedEdge[2] = e.indexList[2];
						e.addSuccessor(states.get(i / 3));
						j++;
					}
				} else if (i % 3 == 1) {
					if (e.indexList[1] == List[i - 1]) {
						e.successorIndex[j] = i / 3;
						e.IndexOfSharedEdge[0] = e.indexList[0];
						e.IndexOfSharedEdge[1] = e.indexList[1];
						e.addSuccessor(states.get(i / 3));
						j++;
					} else if (e.indexList[2] == List[i + 1]) {
						e.successorIndex[j] = i / 3;
						e.IndexOfSharedEdge[0] = e.indexList[0];
						e.IndexOfSharedEdge[2] = e.indexList[2];
						e.addSuccessor(states.get(i / 3));
						j++;
					}
				} else if (i % 3 == 2) {
					if (e.indexList[1] == List[i - 1]) {
						e.successorIndex[j] = i / 3;
						e.IndexOfSharedEdge[0] = e.indexList[0];
						e.IndexOfSharedEdge[1] = e.indexList[1];
						e.addSuccessor(states.get(i / 3));
						j++;
					} else if (e.indexList[2] == List[i - 2]) {
						e.successorIndex[j] = i / 3;
						e.IndexOfSharedEdge[0] = e.indexList[0];
						e.IndexOfSharedEdge[2] = e.indexList[2];
						e.addSuccessor(states.get(i / 3));
						j++;
					}
				}

			}
		}

		for (int i = 0; i < states.size() * 3; i++) {
			// System.out.println("indexList[1] ��List["+i+"] �̏ƍ�");
			if (e.indexList[1] == List[i]) {
				if (i % 3 == 0) {
					if (e.indexList[2] == List[i + 2]) {
						e.successorIndex[j] = i / 3;
						e.IndexOfSharedEdge[1] = e.indexList[1];
						e.IndexOfSharedEdge[2] = e.indexList[2];
						e.addSuccessor(states.get(i / 3));
						j++;
					}
				} else if (i % 3 == 1) {
					if (e.indexList[2] == List[i - 1]) {
						e.successorIndex[j] = i / 3;
						e.IndexOfSharedEdge[1] = e.indexList[1];
						e.IndexOfSharedEdge[2] = e.indexList[2];
						e.addSuccessor(states.get(i / 3));
						j++;
					}
				} else if (i % 3 == 2) {
					if (e.indexList[2] == List[i - 2]) {
						e.successorIndex[j] = i / 3;
						e.IndexOfSharedEdge[1] = e.indexList[1];
						e.IndexOfSharedEdge[2] = e.indexList[2];
						e.addSuccessor(states.get(i / 3));
						j++;
					}
				}
			}
		}

		// System.out.println("1�ڂ�successor**"+successorIndex[0]+"***");
		// System.out.println("2�ڂ�successor**"+successorIndex[1]+"***");
		// System.out.println("3�ڂ�successor**"+successorIndex[2]+"***");

		return;
	}


	// �A�N�Z�b�T
	public ArrayList<IState> getStates() {
		return states;
	}

	public Location getNearestLocation(Position3D pos) {
		Location nearest = null;
		double distance = 0.0;
		for (int n = 0; n < states.size(); n++) {
			Location loc = (Location)states.get(n);
			if (nearest == null) {
				nearest = loc;
				distance = nearest.getCenter().distance(
						new Point3d(pos.getVector3d()));
			} else {
				double d = loc.getCenter().distance(
						new Point3d(pos.getVector3d()));
				if (d < distance) {
					nearest = loc;
					distance = d;
				}
			}
		}
		return nearest;
	}
}
