package framework.AI;

import java.util.*;

/**
 * A*�N���X�B
 */
public class AStar {
	private LinkedList<Location> locationOpenList = new LinkedList<Location>();
	private LinkedList<Location> locationClosedList = new LinkedList<Location>();

	/**
	 * �ŒZ�o�H�T���B �ŒZ�o�H�̃��X�g��Ԃ����A������Ȃ��ꍇnull��Ԃ��B
	 * start = goal�̏ꍇ��null��Ԃ��B
	 * �߂�l�ɂ�goal����start�̂P�O�܂Ōo�H�������̂ڂ������̂������Ă���Bstart�͓����Ă��Ȃ��B
	 */
	public Plan getPath(Location startLocation,
			Location goalLocation) {
		
		//�o���n�_�ƖړI�n�_�������Ȃ�null��Ԃ�
		if (startLocation.equals(goalLocation)) {
			return null;
		}
		
		// ������
		locationOpenList = new LinkedList<Location>();
		locationClosedList = new LinkedList<Location>();
		
		// �o���n�_��LocationOpenList�ɒǉ�����
		locationOpenList.add(startLocation);
		while (!locationOpenList.isEmpty()) {
			// LocationOpenList�̍ŏ��X�R�A�̒n�_��curLocation�Ƃ��Ď��o��
			Collections.sort(locationOpenList, new CostComparator());
			Location curLocation = locationOpenList.removeFirst();

			// ���݂̒n�_���ړI�n�_�Ȃ�ΒT������
			if (curLocation.equals(goalLocation)) {
				LinkedList<Location> locationPath = new LinkedList<Location>();

				// �o���n�_�܂Œʂ����n�_��H��
				curLocation = goalLocation;
				while (!curLocation.equals(startLocation)) {
					locationPath.add(curLocation);
					curLocation = (Location) curLocation.getParent();
				}
				return new Plan(locationPath);
			}

			// ���݂̒n�_���ړI�n�_�łȂ��Ȃ�A���݂̒n�_��LocationClosedList�Ɉڂ�
			locationClosedList.add(curLocation);

			// �אڂ���n�_�𒲂ׂ�
			ArrayList<IState> neighbors = curLocation.getSuccessors();

			// �אڂ���n�_�̐������A���[�v
			for (int i = 0; i < neighbors.size(); i++) {
				// �ʉ߂ł��A�e���X�g�ɓ����ĂȂ��Ȃ�R�X�g�����炢�A
				if (!locationOpenList.contains(neighbors.get(i))
						&& !locationClosedList.contains(neighbors.get(i))) {
					//�n�_�R�X�g�����߂�
					//agent.getLocationCost((Location)neighbors.get(i), null, null, null);
					
					//�p�X�R�X�g�����߂�
					((Location)neighbors.get(i)).calculatesCosts(curLocation, goalLocation);

					// LocationOpenList�Ɉڂ�
					locationOpenList.add((Location) neighbors.get(i));
				}
			}

			// -------------------------------------------------------------------------------------------------------------------------------------------

		}
		return null;
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------
	// �R�X�g�̏����\�[�g�̂��߂̃N���X����낤

	/**
	 * �\�[�g�������N���X�B
	 */
	class CostComparator implements Comparator<Location> {
		public int compare(Location n1, Location n2) {
			return n1.getScore() - n2.getScore();
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------

}
