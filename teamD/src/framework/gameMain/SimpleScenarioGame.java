package framework.gameMain;

import framework.scenario.IWorld;
import framework.scenario.ScenarioManager;

/**
 * �V�i���I�쓮�^�Q�[���̂��߂̃e���v���[�g
 * @author �V�c����
 *
 */
abstract public class SimpleScenarioGame extends SimpleGame implements IWorld {
	protected BaseScenarioGameContainer container;
	protected ScenarioManager scenario;	
	
	@Override
	public void dialogOpen() {
		container.dialogOpen();
	}

	@Override
	public void dialogClose() {
		container.dialogClose();
	}

	@Override
	public void dialogMessage(String message) {
		container.dialogMessage(message);
	}
	
	@Override
	public void showOption(int n, String option) {
	}

	@Override
	public boolean isDialogOpen() {
		return container.isDialogOpen();
	}
	
	public void setScenario(String scenarioFile) {
		scenario = new ScenarioManager(scenarioFile, this);
	}
}
