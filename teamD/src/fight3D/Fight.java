package fight3D;

import com.sun.j3d.utils.universe.SimpleUniverse;

import framework.RWT.RWTCanvas3D;
import framework.RWT.RWTVirtualController;
import framework.gameMain.Actor;
import framework.model3D.CollisionResult;
import framework.model3D.Model3D;
import framework.model3D.Object3D;
import framework.model3D.Position3D;
import framework.model3D.Universe;
import framework.physics.Ground;
import framework.physics.PhysicsUtility;
import framework.view3D.Camera3D;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 * @author �V�c����
 *
 */
public class Fight { // ����{�^��
//	static long speed[] = new long[2000];
//	static int count = 0;
	
	static final long INTERVAL = 17L;
	static final long ENDTIME = 60L * 1000L;
	
	static final double DEFAULT_STAGE_WIDTH = 10.0;
	static final double DEFAULT_AREA_RIGHT = 20.0;
	static final double DEFAULT_AREA_LEFT = -20.0;
	static final double DEFAULT_AREA_BOTTOM = -30.0;

	private ArrayList<Player> playerList = new ArrayList<Player>();
	ArrayList<Actor> actorList = new ArrayList<Actor>();
	ArrayList<Attackable> attackableList = new ArrayList<Attackable>();
	FightCalculation fightCalculation = new FightCalculation();

	Universe universe;
	Camera3D camera;
	Ground stageGround = null;

	FightListener fightListener;
	private boolean isEnd = false;
	int rank[];
	int defeated[];
	int defeat[];
	private int tp[];
	private long startTime = 0;
	private long leftTime;

	public Fight(RWTCanvas3D canvas, int stagenum, int[] selectedCharacterArray,
			FightListener fightListener) {
		
		this.universe = new Universe();
		this.fightListener = fightListener;
		
		// �J�����̐ݒ�
		camera = new Camera3D(universe);
		camera.setSideView();
		canvas.attachCamera(camera);

		// �����̐ݒ�
		createLight(universe);

		// �X�e�[�W�̓ǂݍ���
		Stage s = StageManager.getInstance().getStage(stagenum);
		Model3D model = s.getModel();
		Object3D stageObj = model.createObject();
		stageGround = new Ground(stageObj);
		universe.placeAsAReceiver(stageGround);
		RWTCanvas3D c = (RWTCanvas3D) fightListener;
		s.setBackgroundSize(new Dimension(c.getWidth(), c.getHeight()));
		universe.place(s.getBackground());

		// �L�����N�^�̒ǉ��A����̒ǉ�
		for (int i = 0; i < selectedCharacterArray.length; i++) {
			Player player = createPlayer(selectedCharacterArray[i]);
			player.setPosition(getPlayersPosition(i, selectedCharacterArray.length));
			player.placeTo(universe);
			getPlayerList().add(player);
			actorList.add(player);
			camera.addTarget(player.body);
			player.setHp(fightCalculation.initialiseHP());
			player.setGp(fightCalculation.initialiseGP());
			player.setTp(fightCalculation.initialiseTP());

		}

		universe.compile();

		leftTime = ENDTIME;
		rank = new int[2];
	}

	void createLight(Universe universe){
		//����
		AmbientLight amblight = new AmbientLight(new Color3f(0.5f, 0.5f, 0.5f));
		
		amblight.setInfluencingBounds(new BoundingSphere(new Point3d(), 10000.0));
		universe.placeLight(amblight);
		
    	//���s����
        DirectionalLight dirlight = new DirectionalLight(
        		true,                           //����ON/OFF
                new Color3f(1.0f, 1.0f, 1.0f),  //���̐F
                new Vector3f(0.0f, -1.0f, -0.5f) //���̕����x�N�g��
        );
        dirlight.setInfluencingBounds(new BoundingSphere(new Point3d(), 10000.0));
        
        universe.placeLight(dirlight);
    }

	public Player createPlayer(int id) {
		Player player;
		Character c = CharacterManager.getInstance().getCharacter(id);
		player = new Player(c);

		return player;
	}
	
	public void progress(RWTVirtualController controller, long interval) {
		long curTime = System.currentTimeMillis();
		if (startTime == 0) {
			startTime = curTime;
		}
		
		// �o�ߎ��Ԃ̌v�Z
//		if (count < 2000) speed[count++] = interval;
		if (interval > INTERVAL * 3) interval = INTERVAL * 3;
		leftTime = (ENDTIME - (curTime - startTime));
		
		// ��ʂ��X�V���邽��fightListener��Fight��n���BFight�̏������B
		fightListener.update(this);

		// �T���o�߂�����
		if (leftTime < 0) {
			isEnd = true;
			calculateRank();
			calculateDefeated();
			calculateDefeat();
			calculateTP();
			fightListener.update(this);
//			for (int i = 0; i < count; i++) {
//				System.out.println(speed[i]);
//			}
			return;
		}

		// GP�̎��R��
		for (int i = 0; i < getPlayerList().size(); i++) {
			getPlayerList().get(i).recoverGp();
		}

		// �J�����𒲐�
		camera.adjust(interval);

		// �L�[���͂����������ǂ������L�^����
		boolean[] flags = new boolean[playerList.size()];
		for (int p = 0; p < flags.length; p++) {
			flags[p] = false;
		}
		for (int playerNum = 0; playerNum < getPlayerList().size(); playerNum++) {
			// keys��i�Ԗڂ̒��g�i�L�[���́j���L�����N�^�̂ǂ̓���ɓ��Ă͂܂邩
			if (controller.isKeyDown(playerNum, RWTVirtualController.UP)) {
				flags[playerNum] = true;
				// �U���{�^���𓯎��ɉ����Ă��Ȃ��ꍇ�W�����v����
				if (!controller.isKeyDown(playerNum, RWTVirtualController.BUTTON_A)) getPlayerList().get(playerNum).startJump();
			}
			if (controller.isKeyDown(playerNum, RWTVirtualController.RIGHT)) {
				flags[playerNum] = true;
				// �U���{�^���𓯎��ɉ����Ă��Ȃ��ꍇ�E�ɓ���
				if (!controller.isKeyDown(playerNum, RWTVirtualController.BUTTON_A)) getPlayerList().get(playerNum).rightMove();
			}
			if (controller.isKeyDown(playerNum, RWTVirtualController.LEFT)) {
				flags[playerNum] = true;
				// �U���{�^���𓯎��ɉ����Ă��Ȃ��ꍇ���ɓ���
				if (!controller.isKeyDown(playerNum, RWTVirtualController.BUTTON_A)) getPlayerList().get(playerNum).leftMove();
			}
			if (controller.isKeyDown(playerNum, RWTVirtualController.BUTTON_B)) {
				flags[playerNum] = true;
				// �U���{�^���������Ă��Ȃ��ꍇ�K�[�h�̃��\�b�h���Ă�
				if (!controller.isKeyDown(playerNum, RWTVirtualController.BUTTON_A)) getPlayerList().get(playerNum).guard();
			}
			if (controller.isKeyDown(playerNum, RWTVirtualController.BUTTON_A)) {
				flags[playerNum] = true;
				// �U���̃��\�b�h���Ă�
				Attackable a;
				Player player = getPlayerList().get(playerNum);
				if (!player.isOnGround()) {
					if (player.getElapsedTimeInJumping() > 2L || player.getElapsedTimeInJumping() < 0L) {
						// �󒆂ɂ���Ƃ��͊�{�I�ɃW�����v�U��
						a = player.startJumpAttack();
					} else {
						// �W�����v���Ă����͑΋�U��������悤�ɂ���
						// �i�������Ȃ��Ƒ΋�U�����o�����삪����Ȃ邽�߁j
						a = player.startUpperAttack();						
					}
				} else {
					// �n�ʂɂ���Ƃ�
					if (controller.isKeyDown(playerNum, RWTVirtualController.UP)) {
						// �΋�U��
						a = player.startUpperAttack();
					} else {
						// �ʏ�U��
						a = player.startNormalAttack();
					}
				}
				if (a != null) {
					attackableList.add(a);
					if (a instanceof Weapon) {
						if (a.isMovable()) {
							actorList.add((Weapon) a);
						}
					}
				}
			}
		}
		for (int i = 0; i < flags.length; i++) {
			if (!flags[i]) {
				getPlayerList().get(i).normal(false);
			}
		}

		// �S�o�ꕨ�𓮂���
		for (int i = 0; i < actorList.size(); i++) {
			Actor p = actorList.get(i);
			// �ړ�
			p.motion(interval, stageGround);
			// �͈͊O�ɏo�����ǂ����̔���
			Position3D pos = p.getPosition();
			if (pos.getY() < DEFAULT_AREA_BOTTOM
					|| pos.getX() > DEFAULT_AREA_RIGHT
					|| pos.getX() < DEFAULT_AREA_LEFT) {
				// �͈͊O�ɏo���Ƃ��̏���
				outOfArea(i);
			}
		}

		// �U���̓����蔻��
		hitJudg();
	}

	/**
	 * �͈͊O�ɏo���Ƃ��̏���
	 * @param p �Ώە�
	 */
	private void outOfArea(int index) {
		Actor p = actorList.get(index);
		if (p instanceof Player) {
			playerDefeated(index);
		} else if (p instanceof Weapon) {
			((Weapon)p).disappear();
		}
	}

	/**
	 * �U���̓����蔻��ƁA���������Ƃ��̏���
	 */
	private void hitJudg() {
		// �����蔻��
		for (int i = attackableList.size() - 1; i >= 0; i--) {

			Attackable attack = attackableList.get(i);
			if (attack.isAlive() && attack.isActivate()) {
				// �U�������L���������ꍇ
				for (int j = 0; j < getPlayerList().size(); j++) {
					CollisionResult collisionResult = null;
					Player attacker = attack.getOwner();
					Player damagedCandidate = getPlayerList().get(j);
	
					if (attacker != damagedCandidate) {
						collisionResult = PhysicsUtility.checkCollision(attack.getBody(), 
								attack.getPartName(),
								(Object3D) damagedCandidate.body,
								null);
					}
					// �Փ˂��������ꍇ
					if (collisionResult != null) {
						// if (attacker != damagedCandidate) {
	
						if (damagedCandidate.state.getClass() != StateGuard.class) {
							// �_���[�W���󂯂�B
							if (damagedCandidate.state.getClass() == StateFlinch.class) {
								damagedCandidate.normal(true);
								damagedCandidate.setGp(100);
							}
							damagedCandidate.damaged(attacker.getPosition());
							int afterHP = (int) fightCalculation.decreaseHP(attack
									.getAP(), damagedCandidate.getHp(),
									damagedCandidate.character.getDefense());
							// �U���ɂ���ăL����������ł��Ȃ��ꍇ
							if (fightCalculation.isDead(afterHP) != true) {
								damagedCandidate.setHp(afterHP);
								// damagedCandidate.changeState(new StateFlinch(),
								// false);
							}
							// ���񂾏ꍇ
							else {
								// �L�������E�����L�����N�^�́u�|�����񐔁v���P���₷
								attacker.setDefeat(attacker.getDefeat() + 1);
	
								// �L�������|���ꂽ�Ƃ��̏���
								playerDefeated(j);
							}
							// �U���ɂ��TP�̏㏸
							int attackerAfterTP = fightCalculation.increaseTP(
									attack.getAP(), damagedCandidate.getHp(),
									damagedCandidate.character.getDefense(), attacker.getTp());
							attacker.setTp(attackerAfterTP);
						} else {
							// �_���[�W���󂯂Ȃ��B
							int afterGP = (int) fightCalculation.decreaseGP(attack
									.getAP(), damagedCandidate.character.getDefense(), damagedCandidate.getGp());
	
							if (!fightCalculation.isOver(afterGP)) {
								// �U���ɂ����GP���O�ɂȂ�Ȃ������ꍇ
								damagedCandidate.setGp(afterGP);
							} else {
								// �U���ɂ����GP���O�ɂȂ����ꍇ
								damagedCandidate.flinch();
								damagedCandidate.setGp(0);
							}
						}
	
						// �U�����𖳌��ɂ���
						attack.disappear();
					}
				}
			}
			if (!attack.isAlive()) {
				// �U�����������ɂȂ��Ă����ꍇ�A�U�������X�g����폜
				attackableList.remove(i);
				for (int j = 0; j < actorList.size(); j++) {
					if (actorList.get(j) == attack) {
						actorList.remove(j);
						break;
					}
				}
				continue;
			}
		}
	}

	/**
	 * �v���C���[���|���ꂽ�Ƃ��̏���
	 * @param index �|���ꂽ�v���C���[�̔ԍ�
	 */
	private void playerDefeated(int index) {
		Player damagedCandidate = getPlayerList().get(index);
		
		// TP�����炷
		int damagedAfterTP = fightCalculation
				.decreaseTP(damagedCandidate.getTp());
		damagedCandidate.setTp(damagedAfterTP);

		// ���񂾃L�����N�^�́u���S�񐔁v���P���₷
		damagedCandidate.setDefeated(damagedCandidate
				.getDefeated() + 1);

		// HP��GP�������l�ɖ߂�
		damagedCandidate.setHp(fightCalculation
				.initialiseHP());
		damagedCandidate.setGp(fightCalculation
				.initialiseGP());
		
		// �o���ʒu�ɍēo�ꂳ����
		damagedCandidate.setPosition(getPlayersPosition(index, getPlayerList().size()));
	}

	/**
	 * 
	 * �v���C���[�̓o��i�ēo��j�ʒu��Ԃ�
	 * @param index�@�v���C���[�̔ԍ�
	 * @param numPlayer �S�v���C���[�̐�
	 * @return
	 */
	private Position3D getPlayersPosition(int index, int numPlayer) {
		return new Position3D((((double)index / (double)(numPlayer - 1)) - 0.5) * DEFAULT_STAGE_WIDTH, 0.0, 0.0);
	}

	private void calculateTP() {
		// TODO Auto-generated method stub
		tp = new int[playerList.size()];
		for (int i = 0; i < playerList.size(); i++) {
			tp[i] = playerList.get(i).getTp();
		}
	}

	private void calculateDefeated() {
		// TODO Auto-generated method stub
		defeated = new int[playerList.size()];
		for (int i = 0; i < playerList.size(); i++) {
			defeated[i] = playerList.get(i).getDefeated();
		}
	}

	private void calculateDefeat() {
		// TODO Auto-generated method stub
		defeat = new int[playerList.size()];
		for (int i = 0; i < playerList.size(); i++) {
			defeat[i] = playerList.get(i).getDefeat();
		}
	}

	private void calculateRank() {
		// TODO Auto-generated method stub

		for (int i = 0; i < rank.length; i++) {
			int max = 0;
			int maxNum = 0;
			int playerTp;
			for (int j = 0; j < rank.length; j++) {
				if (rank[j] == 0
						&& max <= (playerTp = getPlayerList().get(j).getTp())) {
					max = playerTp;
					maxNum = j;
				}
			}
			rank[maxNum] = i + 1;
		}
	}

	int getRemnantMinute() {
		int time = (int) (leftTime / 1000) / 60;
		if(time > 0) {
			return time;
		}
		return 0;
	}

	int getRemnantSecond() {
		int time = (int) (leftTime / 1000) % 60;
		if (time > 0) {
			return time;
		}
		return 0;
	}

	boolean checkEnd() {
		return isEnd;
	}

	int[] getRank() {
		return rank;
	}

	ArrayList<Player> getPlayerList() {
		return playerList;
	}

	int[] getTp() {
		return tp;
	}

	int[] getDefeated() {
		return defeated;
	}

	int[] getDefeat() {
		return defeat;
	}
}