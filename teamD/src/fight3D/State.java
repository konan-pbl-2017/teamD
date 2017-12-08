package fight3D;
import framework.gameMain.Mode;
import framework.physics.Velocity3D;


public abstract class State {
	protected long counter = 0; // ���̏�Ԃɐ؂�ւ���Ă��牽���[�v�ڂ��H
	
	public void init() {
		counter = 0;
	}
	
	public void countUp() {
		counter++;
	}
	
	public long getCounter() {
		return counter;
	}
	
	/**
	 * ���x��Ԃ�
	 * @param curVelocity ���݂̑��x
	 * @param mode ���݂̃��[�h
	 * @return ���̏u�Ԃ̑��x
	 */
	public Velocity3D getVelocity(Velocity3D curVelocity, Mode mode) {
		if (counter == 0) {
			// �ŏ��̏u�Ԃ��������x��Ԃ�
			return getInitialVelocity();
		}
		return null;
	}
	
	/**
	 * �������玟�̏�ԂɑJ�ډ\���ۂ���Ԃ�
	 * @param nextState ���̏��
	 * @param mode ���݂̃��[�h�i�󒆂ɑ؍݂��Ă��邩�n��ɂ��邩�j
	 * @return true --- �J�ډ\, false --- �J�ڕs�\
	 */
	public abstract boolean canChange(State nextState, Mode mode);
	
	
	/**
	 * �����i�p���j�\�ȏ�Ԃ�1�����Ō��ɖ߂��Ԃ�
	 * @return true --- �����i�p���j�\�ȏ��, false --- 1�����Ō��ɖ߂���
	 */
	public abstract boolean isRepeatable();
	
	/**
	 * �����x��Ԃ�
	 * @return �����x
	 */
	public abstract Velocity3D getInitialVelocity();

}
