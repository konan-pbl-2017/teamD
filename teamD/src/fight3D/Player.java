package fight3D;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Sphere;

import framework.animation.Animation3D;
import framework.audio.Sound3D;
import framework.gameMain.OvergroundActor;
import framework.model3D.Position3D;
import framework.model3D.Quaternion3D;
import framework.model3D.Universe;
import framework.physics.Velocity3D;

/**
 * �Q�[���ɓo�ꂷ��v���C���[
 * @author �V�c����
 *
 */
public class Player extends OvergroundActor {
	public Character character;
	private int hp;
	private int tp;
	private int gp;
	private Attackable normalAttack;
	private Attackable upperAttack;
	private Attackable jumpAttack;
	private int defeat = 0;
	private int defeated = 0;
	private int gpCounter;
	private TransformGroup guardPos;
	private TransformGroup guardScale;
	private Sphere guardSphere;

	private State stateAttack = new StateAttack();
	private State stateUpperAttack = new StateUpperAttack();
	private State stateJumpAttack = new StateJumpAttack();
	private State stateJump = new StateJump();
	private State stateRightMove = new StateRightMove();
	private State stateLeftMove = new StateLeftMove();
	private State stateGuard = new StateGuard();
	private State stateBend = new StateBend();
	private State stateFlinch = new StateFlinch();
	private State stateNormalRight = new StateNormalRight();
	private State stateNormalLeft = new StateNormalLeft();
	private State stateDamaged = new StateDamaged();

	public State state = stateNormalRight;
	private State lastMoveState = stateRightMove;

	public Player(Character m) {
		super(m);
		character = m;
		// �ړ����x��ݒ肷��
		((StateRightMove)stateRightMove).setSpeed(character.getRunSpeed());
		((StateLeftMove)stateLeftMove).setSpeed(character.getRunSpeed());
		
		// �W�����v�́i������ւ̏����x�j��ݒ肷��
		((StateJump)stateJump).setSpeed(character.getJumpPower());		

		// �U�����@��ݒ肵�Ă����i�ʏ�U���A�΋�U���A�W�����v�U���j
		if (character.hasAttackingPart()) {
			// �L�����N�^�[���U���Ɏg�p����̂́A�̂̈ꕔ�B
			normalAttack = new AttackingPart(character.getAttackingPartName(), this, 
					character.getAttackTimeLag());
		} else {
			// �L�����N�^�[���U���Ɏg�p����̂́A��ѓ���B
			normalAttack = new Weapon(character.getWeaponModel(), this,
					character.getAttackTimeLag());
		}
		if (character.hasUpperAttackingPart()) {
			// �L�����N�^�[���U���Ɏg�p����̂́A�̂̈ꕔ�B
			upperAttack = new AttackingPart(character.getUpperAttackingPartName(), this, 
					character.getUpperAttackTimeLag());
		} else {
			// �L�����N�^�[���U���Ɏg�p����̂́A��ѓ���B
			upperAttack = new Weapon(character.getUpperWeaponModel(), this, 
					character.getUpperAttackTimeLag());
		}
		if (character.hasJumpAttackingPart()) {
			// �L�����N�^�[���U���Ɏg�p����̂́A�̂̈ꕔ�B
			jumpAttack = new AttackingPart(character.getJumpAttackingPartName(), this, 
					character.getJumpAttackTimeLag());
		} else {
			// �L�����N�^�[���U���Ɏg�p����̂́A��ѓ���B
			jumpAttack = new Weapon(character.getJumpWeaponModel(), this, 
					character.getJumpAttackTimeLag());
		}
	}

	// SceneGraph�ɂԂ牺����
	public void placeTo(Universe universe) {
		universe.placeAsAnOcculuder(this);
		// �e�̕\���̏���
		if (!character.hasAttackingPart()) {
			universe.place((Weapon) normalAttack);
		}
		if (!character.hasUpperAttackingPart()) {
			universe.place((Weapon) upperAttack);
		}
		if (!character.hasJumpAttackingPart()) {
			universe.place((Weapon) jumpAttack);
		}
		
		// �K�[�h�\���̏���
		guardSphere = new Sphere();
		Appearance ap = new Appearance();
		Material ma = new Material();
		ap.setMaterial(ma);
		TransparencyAttributes ta = new TransparencyAttributes(
				TransparencyAttributes.FASTEST, 0.5f);
		ap.setTransparencyAttributes(ta);
		guardSphere.setAppearance(ap);

		guardPos = new TransformGroup();
		guardPos.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		guardPos.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		guardScale = new TransformGroup();
		guardScale.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		guardScale.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		guardPos.addChild(guardScale);
		guardScale.addChild(guardSphere);
		universe.place(guardPos);
		setGuardInvisible();
	}

	public void onEndAnimation() {
		if (!state.isRepeatable()) {
			normal(true);
		}
	}

	public void onEndFall() {
		// �����Ă��鎞�͍��E�ɕ����A�j���[�V���������Ȃ����A
		// �n�ʂɂ����u�ԕ����A�j���[�V�������n�܂�
		Animation3D a = character.getAnimation(mode, state);
		if (!animation.equals(a)) {
			// ���Ƃ��Ε����Ă���r���ŁA�A�j���[�V�����̊֌W�ň�u������������ꍇ������B
			// ���̂悤�ȏꍇ�A���n���邽�тɕ����A�j���[�V���������Z�b�g���Ȃ��悤�ɁA�A�j���[�V�����̎�ނ��ς�����Ƃ�����
			// �A�j���[�V������ݒ肵�Ȃ����悤�ɂ���B
			animation = a;
		}
	}

	/**
	 * ���̏�Ԃɕς���
	 * @param nextState --- ���̏��
	 * @param forced --- �����I�ɕύX���邩�ǂ���
	 * @return
	 */
	boolean changeState(State nextState, boolean forced) {
		if (state.getClass() == nextState.getClass()) {
			// ��Ԃ̌p��
			state.countUp();
			// ���x�̕ύX
			Velocity3D nextVelocity = state.getVelocity(getVelocity(),
					mode);
			if (nextVelocity != null)
				body.apply(nextVelocity, false);
			return false;
		}
		boolean flag = state.canChange(nextState, mode);
		if (forced || flag) {
			// System.out.println("���:" + s.getClass().getName() + this);
			// ��Ԃ̕ύX�Ə�����
			State prevState = state;
			state = nextState;
			state.init();
			// ���x�̕ύX
			Velocity3D nextVelocity = state.getVelocity(getVelocity(), mode);
			if (nextVelocity != null)
				body.apply(nextVelocity, false);
			// �A�j���[�V�����̐ݒ�
			animation = character.getAnimation(mode, state);
			// ���ʉ���炷
			Sound3D sound = character.getSoundEffect(mode, state);
			if (sound != null) {
				sound.play();
			}
			
			if (prevState instanceof StateAttack 
					|| prevState instanceof StateUpperAttack 
					|| prevState instanceof StateJumpAttack) {
				endAttack();
			}

			if ((nextState instanceof StateRightMove) || (nextState instanceof StateLeftMove)) {
				// ���݂̌����Ă�����ƁA���ꂩ������������Ⴄ�Ȃ�L�����N�^����]������
				if (nextState.getClass() != lastMoveState.getClass()) {
					if (nextState instanceof StateRightMove) {
						Quaternion3D q = new Quaternion3D(0.0, 1.0, 0.0, 0);
						body.apply(q, false);
					} else if (nextState instanceof StateLeftMove) {
						Quaternion3D q = new Quaternion3D(0.0, 1.0, 0.0,
								Math.PI);
						body.apply(q, false);
					}
				}
				lastMoveState = nextState;
			}
		}
		return flag;
	}

	/**
	 * �ʏ��Ԃɖ߂�
	 * @param forced true --- �����I�ɖ߂�, false --- �߂���ꍇ�����߂�
	 */
	void normal(boolean forced) {
		if (lastMoveState instanceof StateRightMove) {
			State s = stateNormalRight;
			changeState(s, forced);
		} else if (lastMoveState instanceof StateLeftMove) {
			State s = stateNormalLeft;
			changeState(s, forced);

		}
		setGuardInvisible();

	}

	/**
	 * �ʏ�U�����J�n����
	 * @return �U���Ɏg���U����
	 */
	public Attackable startNormalAttack() {
		Velocity3D weaponSpeed = new Velocity3D();
		// �ʏ�U��
		State s = stateAttack;
		Attackable a = normalAttack;
		if (a.isMovable()) weaponSpeed = character.getWeaponSpeed();
		return startAttackSub(s, a, weaponSpeed);
	}
	
	/**
	 * �΋�U�����J�n����
	 * @return �U���Ɏg���U����
	 */
	public Attackable startUpperAttack() {
		Velocity3D weaponSpeed = new Velocity3D();
		State s = stateUpperAttack;
		Attackable a = upperAttack;
		if (a.isMovable()) weaponSpeed = character.getUpperWeaponSpeed();
		return startAttackSub(s, a, weaponSpeed);
	}

	/**
	 * �W�����v�U�����J�n����
	 * @return �U���Ɏg���U����
	 */
	public Attackable startJumpAttack() {
		Velocity3D weaponSpeed = new Velocity3D();
		State s = stateJumpAttack;
		Attackable a = jumpAttack;
		if (a.isMovable()) weaponSpeed = character.getJumpWeaponSpeed();
		return startAttackSub(s, a, weaponSpeed);
	}

	private Attackable startAttackSub(State s, Attackable a, Velocity3D weaponSpeed) {
		State prevState = state;
		if (changeState(s, false)) {
			if (a instanceof Weapon) {
				((Weapon) a).body.apply(body.getPosition3D(), false);
				if (prevState instanceof StateNormalRight
						|| prevState instanceof StateRightMove) {
					// �E�����ɒe�𔭎�
					((Weapon) a).body.apply(weaponSpeed, false);
					((Weapon) a).body.apply(new Quaternion3D(0.0, 1.0, 0.0, 0), false);
				} if (prevState instanceof StateNormalLeft
						|| prevState instanceof StateLeftMove) {
					// �������ɒe�𔭎�
					Velocity3D weponSpeed2 = (Velocity3D)weaponSpeed.clone();
					weponSpeed2.setX(-weponSpeed2.getX());
					((Weapon) a).body.apply(weponSpeed2, false);
					((Weapon) a).body.apply(new Quaternion3D(0.0, 1.0, 0.0, Math.PI), false);
				} else if (lastMoveState instanceof StateNormalRight
						|| lastMoveState instanceof StateRightMove) {
					// �E�����ɒe�𔭎�
					((Weapon) a).body.apply(weaponSpeed, false);
					((Weapon) a).body.apply(new Quaternion3D(0.0, 1.0, 0.0, 0), false);
				} else {
					// �������ɒe�𔭎�
					Velocity3D weponSpeed2 = (Velocity3D)weaponSpeed.clone();
					weponSpeed2.setX(-weponSpeed2.getX());
					((Weapon) a).body.apply(weponSpeed2, false);
					((Weapon) a).body.apply(new Quaternion3D(0.0, 1.0, 0.0, Math.PI), false);
				}
			}
			a.appear();
			return a;
		} else {
			return null;
		}
	}

	private void endAttack() {
		normalAttack.disappear();
		upperAttack.disappear();
		jumpAttack.disappear();
	}

	void startJump() {
		State s = stateJump;
		changeState(s, false);
		mode = modeFreeFall;
	}

	void rightMove() {
		State s = stateRightMove;
		changeState(s, false);
	}

	void leftMove() {
		State s = stateLeftMove;
		changeState(s, false);
	}

	void guard() {
		State s = stateGuard;
		changeState(s, false);
		if (state instanceof StateGuard) {
			// StateGuard �ɏ�Ԃ��ς�����Ƃ������ł͂Ȃ��āAStateGuard�@��Ԃ̂܂܍U�����󂯂��Ƃ����ȉ��̏������s��
			Position3D p = body.getPosition3D().clone();
			Transform3D t = new Transform3D();
			Vector3d v = new Vector3d(p.getX(), p.getY(), p.getZ());
			t.set(v);
			guardPos.setTransform(t);
			setGuardScale(this.gp);
		}
	}

	void bend() {
		State s = stateBend;
		changeState(s, false);
	}

	void flinch() {
		State s = stateFlinch;
		changeState(s, true);
	}

	void damaged(Position3D attackerPosition) {
		// �U�����󂯂���G�̕���������
		if (attackerPosition.getX() > getPosition().getX()) {
			body.apply(new Quaternion3D(0.0, 1.0, 0.0, 0), false);
			lastMoveState = stateRightMove;
		} else {
			body.apply(new Quaternion3D(0.0, 1.0, 0.0, Math.PI), false);
			lastMoveState = stateLeftMove;
		}
		State s = stateDamaged;
		changeState(s, false);
	}

	void setHp(int hp) {
		this.hp = hp;
	}

	int getHp() {
		return hp;
	}

	void setTp(int tp) {
		this.tp = tp;
	}

	int getTp() {
		return tp;
	}

	void setGp(int gp) {
		this.gp = gp;
	}

	int getGp() {
		return gp;
	}

	void recoverGp() {
		if (state.getClass() == StateGuard.class) {
			gpCounter = 0;
			return;
		}
		gpCounter++;
		if (gpCounter < 20) {
			return;
		}
		gpCounter = 0;
		if (FightCalculation.MIN_GP < gp && gp < FightCalculation.MAX_GP) {
			this.gp += 1;
		} else if (gp >= FightCalculation.MAX_GP) {
			gp = FightCalculation.MAX_GP;
		}
	}

	void setDefeat(int defeat) {
		this.defeat = defeat;
	}

	int getDefeat() {
		return defeat;
	}

	void setDefeated(int defeated) {
		this.defeated = defeated;
	}

	int getDefeated() {
		return defeated;
	}

	/**
	 * �W�����v���J�n���Ă���̌o�ߎ��ԁi���[�v�񐔁j��Ԃ��A
	 * �W�����v��ԂɂȂ��Ƃ��� -1 ��Ԃ�
	 * @return �o�ߎ���
	 */
	public long getElapsedTimeInJumping() {
		if (state instanceof StateJump) {
//			System.out.println(state.getCounter());
			return state.getCounter();
		}
//		System.out.println(-1);
		return -1L;
	}

	private void setGuardVisible(double j) {
		// TODO Auto-generated method stub
		Transform3D t = new Transform3D();
		t.setScale(j);
		guardScale.setTransform(t);
	}

	private void setGuardInvisible() {
		// TODO Auto-generated method stub
		Transform3D t = new Transform3D();
		t.setScale(0.01);
		guardScale.setTransform(t);

	}

	private void setGuardScale(int i) {
		double j = 0;
		if (i > 0) {
			j = 1.5 * (i / 100.0);
			setGuardVisible(j);
		} else {
			setGuardInvisible();
		}
	}
}
