package framework.AI;

import java.awt.Point;
import java.util.ArrayList;

import javax.media.j3d.IndexedTriangleArray;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import framework.model3D.GeometryUtility;

public class Location implements IState {
	public int planeIndex;
	public int[] indexList = new int[3];
	public int[] successorIndex = new int[100];
	public int numberOfSharedEdge;// ���L�ӂ̖{��
	public int[] IndexOfSharedEdge = new int[3];// ���L�ӂ̒��_�C���f�b�N�X
	private Point3d center;
	private Vector3d normal;

	private double cost = 0;
	private double heuristicCost = 0;
	private Location parentNode = null;
	private ArrayList<IState> successors = new ArrayList<IState>();

	// �e�X�g�p�̋�̃R���X�g���N�^
	public Location(Point3d center) {
		this.center = center;
		normal = new Vector3d(0.0, 1.0, 0.0);
	}

	// �R���X�g���N�^
	public Location(int index, IndexedTriangleArray ita) {
		planeIndex = index;

		indexList[0] = ita.getCoordinateIndex(index * 3);
		indexList[1] = ita.getCoordinateIndex(index * 3 + 1);
		indexList[2] = ita.getCoordinateIndex(index * 3 + 2);

		Point3d p1 = new Point3d();
		Point3d p2 = new Point3d();
		Point3d p3 = new Point3d();

		ita.getCoordinate(indexList[0], p1);
		ita.getCoordinate(indexList[1], p2);
		ita.getCoordinate(indexList[2], p3);

		// ���S���W�̌v�Z
		center = new Point3d((p1.getX() + p2.getX() + p3.getX()) / 3.0, (p1
				.getY()
				+ p2.getY() + p3.getY()) / 3.0, (p1.getZ() + p2.getZ() + p3
				.getZ()) / 3.0);

		// �@���x�N�g���̌v�Z
		p2.sub(p1);
		p3.sub(p1);
		Vector3d v2 = new Vector3d(p2);
		Vector3d v3 = new Vector3d(p3);
		v2.cross(v2, v3);
		if (v2.length() < GeometryUtility.TOLERANCE) {
			v2 = null;
		} else {
			v2.normalize();
		}
		normal = v2;
	}
	
	public void addSuccessor(IState s) {
		successors.add(s);
	}

	@Override
	public ArrayList<IState> getSuccessors() {
		// TODO Auto-generated method stub
		return (ArrayList<IState>) successors;
	}

	@Override
	public IState getParent() {
		// TODO Auto-generated method stub
		return parentNode;
	}

	/**
	 * �X�R�A�̎擾�B
	 * 
	 * @return �X�R�A��Ԃ��B
	 */
	public int getScore() {
		// ��r�ɏ����_�����f������ׁA1000���|���Ă���
		return (int) ((cost + heuristicCost) * 1000);
	}

	/**
	 * �R�X�g�v�Z�ƒT�����m�[�h��ݒ�B
	 * 
	 * @param parentNode
	 *            �T�����̃m�[�h�B
	 * @param goalNode
	 *            �ړI�n�̃m�[�h�B
	 */
	public void calculatesCosts(Location parentNode, Location goalNode) {
		// �R�X�g�����Z
		cost = parentNode.cost + 1; // agent.getPathCost(parentNode, this, null,
									// null, null, null, null, null);

		// //�q���[���X�e�B�b�N�R�X�g���v�Z
		// disX = point.x - goalNode.point.x;
		// disY = point.y - goalNode.point.y;
		// �q���[���X�e�B�b�N�R�X�g�̐M�����������ׁA3����1�ɂ��Ă���
		heuristicCost = 0;

		// �T�����m�[�h���L�^
		this.parentNode = parentNode;
	}

	// /////////
	// �A�N�Z�b�T//
	// /////////
	public int getPlaneIndex() {
		return planeIndex;
	}

	public int getIndexList(int index) {
		return indexList[index];
	}

	public int getSuccessorIndex(int index) {
		return successorIndex[index];
	}

	public Point3d getCenter() {
		return center;
	}

	public Vector3d getNormal() {
		return normal;
	}
}
