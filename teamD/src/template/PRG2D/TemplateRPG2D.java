package template.PRG2D;

import java.awt.Color;
import java.math.BigDecimal;


import framework.RWT.RWTContainer;
import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.game2D.Position2D;
import framework.game2D.Sprite;
import framework.gameMain.BaseScenarioGameContainer;
import framework.gameMain.SimpleRolePlayingGame;
import framework.model3D.ModelFactory;
import framework.model3D.Object3D;
import framework.model3D.Position3D;
import framework.model3D.Quaternion3D;
import framework.model3D.Universe;
import framework.physics.PhysicsUtility;
import framework.scenario.Event;
import framework.scenario.ScenarioManager;
import framework.scenario.ScenarioState;

public class TemplateRPG2D extends SimpleRolePlayingGame {
	private MapStage map;
	private Player player;
	private Sprite king;
	private Sprite enemy;
		
	// ���x�ɂ���ĕ��̂������Ă��鎞�Ƀ{�^���������邩�ǂ����𔻒肷��t���O
	private boolean disableControl = false;

	@Override
	public void init(Universe universe) {
		map = new MapStage();
		universe.place(map);
		camera.addTarget(map);

		// �v���C���[�̔z�u
		player = new Player("data\\RPG\\player.png");
		player.setPosition(14.0, 14.0);
		player.setCollisionRadius(0.5);
		universe.place(player);
		
		// ���l�̔z�u
		king = new Sprite("data\\RPG\\king.png");
		king.setPosition(18.0, 24.0);
		king.setCollisionRadius(0.5);
		universe.place(king);
		
		// �v���C���[����ʂ̒�����
		setCenter(player);
		
		// �V�i���I�̐ݒ�
		setScenario("data\\TemplateRPG\\Scenario\\scenario2.xml");
	}
	
	@Override
	public void subInit(Universe universe) {
		enemy = new Sprite("data\\RPG\\monster.png", 10.0f);
		enemy.setPosition(15.0, 15.0);
		universe.place(enemy);
		
		// �G����ʂ̒�����
		setSubCenter(enemy);
	}

	@Override
	public RWTFrame3D createFrame3D() {
		frame = new RWTFrame3D();
		frame.setSize(1000, 800);
		frame.setTitle("Template for 2D Role Playing Game");
		frame.setBackground(Color.BLACK);
		return frame;
	}
	
	@Override
	protected RWTContainer createRWTContainer() {
		container = new ScenarioGameContainer();
		return container;
	}
	
	// �퓬�p��ʂ̍쐬
	public BaseScenarioGameContainer createSubRWTContainer() {
		subContainer = new FightContainer();
		return subContainer;
	}
	
	@Override
	public void progress(RWTVirtualController virtualController, long interval) {
		// ���H�Q�[���X�e�[�W���\������I�u�W�F�N�g�̈ʒu�ƃv���C���[�̈ʒu�����Ƃɑ��x��0�ɂ��邩�ǂ����𒲂ׂ�B
		Position2D gridPoint = map.getNeighborGridPoint(player);

		// ���x��0�ɂ���t���O�������Ă���΁A���x��0�ɂ���
		if (gridPoint != null) {
			player.setPosition(gridPoint);
			player.setVelocity(0.0, 0.0);
			disableControl = false;
		}
		
		// �L�������ړ����Ă��Ȃ���΁A�L�[����̏������s����B
		if(!disableControl){
			// �L�[����̏���
			// ��
			if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
				player.setVelocity(-4.0, 0.0);
				disableControl = true;
			}
			// �E
			else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
				player.setVelocity(4.0, 0.0);
				disableControl = true;
	
			}
			// ��
			else if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
				player.setVelocity(0.0, 4.0);
				disableControl = true;
			}
			// ��
			else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
				player.setVelocity(0.0, -4.0);
				disableControl = true;
			}
		}
		player.motion(interval, map);
		
		// �Փ˔���
		if (player.checkCollision(king)) {
			// �v���C���[�Ɖ��l���Ԃ������ꍇ
			scenario.fire("���l�ƂԂ���");	// �u���l�ƂԂ���v�Ƃ����C�x���g�𔭐�����i�V�i���I���i�ށj
		}
	}

	@Override
	public void action(String action, Event event, ScenarioState nextState) {
		// �V�i���I�i�s�ɂ�鐢�E�ւ̍�p�������ɏ���
		if (action.equals("startFight")) {
			changeToSubContainer();
		} else if (action.equals("endFight")) {
			changeToMainContainer();
		}
	}

	/**
	 * �Q�[���̃��C��
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleRolePlayingGame game = new TemplateRPG2D();
		game.setFramePolicy(5, 33, false);
		game.start();
	}

}
