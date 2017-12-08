package framework.AI;

import java.util.LinkedList;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import framework.model3D.Position3D;

public class Plan {
	private LinkedList<Location> path;		// �v����e��\���p�X
	private int currentLoc = 0;		// �p�X��̌��݂� Location 

	/**
	 * �����̒ʉߓ_�����v��
	 * @param locationPath
	 */
	public Plan(LinkedList<Location> locationPath) {
		path = locationPath;
		currentLoc = locationPath.size() - 1;
	}
	
	/**
	 * �X�^�[�g�ƃS�[�����_�C���N�g�Ɍ��Ԍv��
	 * @param start
	 * @param goal
	 */
	public Plan(Location start, Location goal) {
		path = new LinkedList<Location>();
		path.add(goal);
		path.add(start);
		currentLoc = 1;
	}

	/**
	 * ���݂� Location ���擾����
	 * @return�@���݂� Location, ���łɃS�[���ɒ����Ă���Ƃ��� null ��Ԃ�
	 */
	public Location getCurrentLocation() {
		if (currentLoc <= 0) return null;
		return path.get(currentLoc);
	}

	/**
	 * ���� Location ���擾����
	 * @return�@���� Location, ���łɃS�[���ɒ����Ă���Ƃ��� null ��Ԃ�
	 */
	public Location getNextLocation() {
		if (currentLoc <= 0) return null;
		return path.get(currentLoc - 1);
	}

	/**
	 * ���݂̍��W�l�����ɁA���݂� Location ���X�V����
	 * @param position�@���݂̍��W�l
	 * @return�@�X�V���� --- true, �ȑO�̂܂� --- false
	 */
	public boolean updateCurrentLocation(Position3D position) {
		Vector3d toCurrentPosition = position.getVector3d();
		Location curLocation = getCurrentLocation();
		if (curLocation == null) return true;
		toCurrentPosition.sub(curLocation.getCenter());
		double distanceToCurrentPosition = toCurrentPosition.length();
		Vector3d toNextLocation = new Vector3d(getNextLocation().getCenter());
		toNextLocation.sub(curLocation.getCenter());
		double distanceToNextLocation = toNextLocation.length();
		if (distanceToCurrentPosition >= distanceToNextLocation) {
			// ���� Location ��ʂ�߂����ꍇ
			currentLoc--;
			return true;
		}
		return false;
	}

}
