package fight3D;

import framework.gameMain.Actor;
import framework.model3D.CollisionResult;
import framework.model3D.Object3D;
import framework.physics.Force3D;
import framework.physics.Ground;
import framework.physics.PhysicsUtility;
import framework.physics.Solid3D;


/**
 * ��ѓ���A�e
 * @author �V�c����
 *
 */
public class Weapon extends Actor implements Attackable {
	private Player owner;
	private boolean fCalculatePhysics = false;
	boolean fActive = true;
	long lifetime;
	private long timeLag = 0;	// �U���������n�߂�܂ł̎��ԁi�������ԁj
	
	public Weapon(WeaponModel m, Player owner, long t) {
		super(m);
		this.owner = owner;
		lifetime = 10000;
		timeLag = t;
		disappear();
	}
	
	public void motion(long interval, Ground ground){
		
		if(doesCalculatePhysics()){
			lifetime = lifetime -interval;
			if(lifetime <= 0){
				disappear();
			}
		}
		super.motion(interval, ground);
	}
	
	public void appear(){
		fActive = true;
		setVisible();
	}
	
	public void disappear() {
		fActive = false;
		setInvisible();
	}

	//�������U���̒����^��������̂������^��������̂��𔻒�
	public boolean doesCalculatePhysics(){
		
		return fCalculatePhysics;
	}	
	
	public boolean isAlive(){
		return fActive;
	}
			
	//�U���͂�������
	public int getAP() {
		// TODO Auto-generated method stub
		return owner.character.getPower();
	}

	//2�̕��̂�������Ƃ��̂R�����̌`����������
	public Object3D getBody() {
		// TODO Auto-generated method stub
		return body;
	}

	@Override
	public String getPartName() {
		// TODO Auto-generated method stub
		return null;
	}	

	//�N��Weapon�����������̂����肷��
	public Player getOwner() {
		// TODO Auto-generated method stub
		return owner;
	}
	
	//Player�ƓƗ���������������̂��ǂ�������iWeapon�ł͓Ɨ�����������������j
	public boolean isMovable() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void onEndAnimation() {
	}
	
	public void onEndFall(){
	}

	//Weapon���n�ʂɓ��������Ƃ��ɂǂ��������������邩
	public void onIntersect(CollisionResult cr, long interval) {
		// TODO Auto-generated method stub
		if(doesCalculatePhysics()){
			Force3D f = PhysicsUtility.calcForce(interval, cr.normal, (Solid3D)body);
			((Solid3D)body).move(interval, f, cr.collisionPoint);
		}
		else{
			disappear();
		}
	}
	
	public Force3D getGravity() {
		if (doesCalculatePhysics()) {
			return super.getGravity();
		}
		return Force3D.ZERO;
	}
	
	private void setVisible(){
		animation.reset();
		body.scale(1.0);
	}

	private void setInvisible(){
		body.apply(owner.getPosition(), false);
		body.scale(0.01);
	}

	@Override
	public boolean isActivate() {
		return (animation.time >= timeLag);
	}
}
