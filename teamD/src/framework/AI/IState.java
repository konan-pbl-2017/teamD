package framework.AI;

import java.util.ArrayList;

public interface IState {
	
	abstract ArrayList<IState> getSuccessors();

	abstract void addSuccessor(IState s);
	/**
	 * �T�����̃m�[�h�擾�B
	 * @return
	 * �T�����̃m�[�h��Ԃ��B
	 */
	abstract IState getParent();
}
