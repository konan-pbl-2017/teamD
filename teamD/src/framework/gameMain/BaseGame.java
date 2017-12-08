package framework.gameMain;

import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ��ʓI�ȃQ�[���p�̃N���X�i��ԑJ�ڂ������Ƃ��ł���j
 * @author �V�c����
 *
 */
public abstract class BaseGame extends AbstractGame {
	Stack<AbstractGameState> gameStateStack = new Stack<AbstractGameState>();

	public abstract AbstractGameState getInitialGameState();
	public abstract boolean canGoPrevGameState();
	public abstract AbstractGameState changeNextGameState();
	
	public BaseGame() {
		super();
		pushNewGameState(getInitialGameState());
	}
	
	protected AbstractGameState getCurrentGameState() {
		return gameStateStack.peek();
	}
	
	public void goNextGameState(){		
		deactivateState(getCurrentGameState());
		AbstractGameState g = changeNextGameState();
		pushNewGameState(g);
		activateState(g);
	}

	public void goPrevGameState(){
		if(canGoPrevGameState()){
			deactivateState(getCurrentGameState());
			popGameState();
			activateState(getCurrentGameState());
		}
	}

	private void pushNewGameState(AbstractGameState g) {
		gameStateStack.push(g);
	}

	private void popGameState() {
		gameStateStack.pop();
	}
}
