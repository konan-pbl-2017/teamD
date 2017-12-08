package framework.model3D;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.media.j3d.IndexedGeometryArray;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

public class GeometryUtility {
	public static final double TOLERANCE = 0.0000002;

	public static ProjectionResult projection3D(
			ArrayList<Vector3d> vertex3Dlist, Vector3d axis) {
		double k = 0.0;
		int i = 0;

		ProjectionResult pr = new ProjectionResult();

		axis.normalize();

		for (i = 0; i < vertex3Dlist.size(); i++) {
			Vector3d p = vertex3Dlist.get(i);
			Vector3d p1 = new Vector3d();

			k = p.dot(axis);
			p1.scaleAdd(-k, axis, p);

			if (i == 0 || k >= pr.max) {
				pr.max = k;
			} else if (i == 0 || k <= pr.min) {
				pr.min = k;
			}
			pr.vertexList.add(p1);
		}
		return pr;
	}

	public static ProjectionResult projection2D(
			ArrayList<Vector3d> vertex2Dlist, Vector3d axis) {
		int i = 0;
		double k = 0.0;
		// System.out.println("point3�F"+axis);
		if (axis.x != 0 || axis.y != 0 || axis.z != 0) {
			axis.normalize();
		}
		// System.out.println("point3_1�F"+axis);
		ProjectionResult pr = new ProjectionResult();

		for (i = 0; i < vertex2Dlist.size(); i++) {
			Vector3d p = vertex2Dlist.get(i);

			k = p.dot(axis);
			// System.out.println("k�F"+k);
			// System.out.println("point3�F"+axis);
			if (i == 0 || k >= pr.max) {
				pr.max = k;
			} else if (i == 0 || k <= pr.min) {
				pr.min = k;
			}
		}
		return pr;
	}

	// �ȉ��A���_�̕\���̔��胁�\�b�h
	public static boolean inside(Vector3d v, Vector4d plane) {
		// System.out.println("vertex:" + v.x + "," + v.y + "," + v.z);
		// System.out.println("plane:" + plane.x + "," + plane.y + "," + plane.z
		// + "," + plane.w);
		Vector3d pv = new Vector3d(plane.x, plane.y, plane.z);
		// Vector3d nv = (Vector3d)pv.clone();
		// nv.scaleAdd(plane.w / pv.lengthSquared(),v);
		if (pv.dot(v) + plane.w <= TOLERANCE) {
			// System.out.println("��������I�I"+nv.dot(pv));
			pv = null;
			return true;
		}
		// System.out.println("�O������I�I"+nv.dot(pv));
		pv = null;
		return false;
	}

	/**
	 * 3�_��ʂ镽�ʂ��쐬����i�������Av1, v2, v3�̓��e��������������̂Œ��Ӂj
	 * 
	 * @param v1
	 *            --- 1�_�ڂ̍��W
	 * @param v2
	 *            --- 2�_�ڂ̍��W
	 * @param v3
	 *            --- 3�_�ڂ̍��W
	 * @return�@v1, v2, v3��ʂ镽��
	 */
	public static Vector4d createPlane(Vector3d v1, Vector3d v2, Vector3d v3) {
		v2.sub(v1);
		v3.sub(v1);
		v2.cross(v2, v3);
		v2.normalize();
		return new Vector4d(v2.x, v2.y, v2.z, -v2.dot(v1));
	}

	/**
	 * 
	 * ���ʂƒ����̌�_�����߂�
	 * 
	 * @param plane
	 *            ����
	 * @param v1
	 *            ������̓_1
	 * @param v2
	 *            ������̓_2
	 * @return ��_
	 */
	public static Vector3d intersect(Vector4d plane, Vector3d v1, Vector3d v2) {
		Vector3d n = new Vector3d(plane.x, plane.y, plane.z);
		Vector3d v21 = (Vector3d) v2.clone();
		v21.sub(v1);
		v21.scale((-plane.w - n.dot(v1)) / n.dot(v21));
		v21.add(v1);
		return v21;
	}

	/**
	 * 
	 * �w�肳�ꂽ�_���ʃ|���S���̓����ɕ�܂���Ă��邩�H
	 * 
	 * @param vertexList
	 *            �ʃ|���S���̒��_��
	 * @param point
	 *            �w��_
	 * @return true --- ��܂���Ă���, false --- ��܂���Ă��Ȃ�
	 */
	public static boolean inside(ArrayList<Vector3d> vertexList, Vector3d point) {
		boolean inside = true;
		Vector3d v0 = null;
		for (int i = 0; i < vertexList.size(); i++) {
			// �|���S���̊e�ӂɑ΂��ďՓ˓_���E�����������H
			Vector3d center = (Vector3d) point.clone();
			Vector3d v2 = (Vector3d) (vertexList.get((i + 1)
					% vertexList.size()).clone());
			Vector3d v1 = (Vector3d) vertexList.get(i).clone();
			center.sub(v1);
			v2.sub(v1);
			v1.cross(center, v2);
			if (v0 != null && v0.dot(v1) < -GeometryUtility.TOLERANCE) {
				inside = false;
				break;
			}
			v0 = v1;
		}
		// ���ׂĉE���A�܂��͂��ׂč����������ꍇ�A�ʃ|���S���̓����Ɉʒu�����ƍl����
		return inside;
	}

	// IndexedGeometryArray�̃C���f�b�N�X�̂����A�������W�������C���f�b�N�X��u��������
	public static void compressGeometry(IndexedGeometryArray g) {
		// �@Hashtable�����Ȃ���Arepresentation[]�����
		Hashtable<Double, ArrayList<Integer>> h = new Hashtable<Double, ArrayList<Integer>>();
		Point3d p = new Point3d();
		Point3d p2 = new Point3d();
		double hash;
		ArrayList<Integer> list;
		int[] representation = new int[g.getVertexCount()];

		for (int i = 0; i < g.getVertexCount(); i++) {
			g.getCoordinate(i, p);

			hash = p.getX() + p.getY() + p.getZ();

			list = h.get(new Double(hash));

			if (list == null) {// hash�ɑΉ�����v�f���Ȃ��ꍇ
				// Hashtable�����
				list = new ArrayList<Integer>();
				list.add(new Integer(i));
				h.put(new Double(hash), list);
				// representation[]�����
				representation[i] = i;
			} else {
				boolean bFound = false;
				for (int j = 0; j < list.size(); j++) {
					g.getCoordinate(list.get(j).intValue(), p2);
					if (p.getX() == p2.getX() && p.getY() == p2.getY()
							&& p.getZ() == p2.getZ()) {
						representation[i] = list.get(j).intValue();
						bFound = true;
						break;
					}
				}
				if (!bFound) {
					list.add(new Integer(i));
					// representation[]�����
					representation[i] = i;
				}
			}
		}

		// �Aindex�̒u������
		for (int i = 0; i < g.getIndexCount(); i++) {
			int index = representation[g.getCoordinateIndex(i)];
			g.setCoordinateIndex(i, index);
			
		}

	}

}