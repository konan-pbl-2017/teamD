package fight3D;
import javax.media.j3d.BranchGroup;

import framework.model3D.Object3D;


/**
 * �v���C���[�̑̂̒��̍U���Ɏg�p���Ă��镔��
 * @author �V�c����
 *
 */
public class AttackingPart implements Attackable {

	private String partName = null;
	private Player owner;
	private boolean fActive = true;
	private long timeLag = 0;	// �U���������n�߂�܂ł̎��ԁi�������ԁj
	
	//�R���X�g���N�^
	public AttackingPart(String pn, Player o, long t){
		partName = pn;
		owner = o;
		timeLag = t;
	}
	
	@Override
	//�U���͂�Ԃ�
	public int getAP() {
		// TODO Auto-generated method stub
		return owner.character.getPower();
	}

	@Override
	//���́i�R�����j�̌`��������
	public Object3D getBody() {
		// TODO Auto-generated method stub
		return owner.body;
	
	}

	@Override
	public String getPartName() {
		// TODO Auto-generated method stub
		return partName;
	}

	@Override
	//�N���U���������i�L���ȍU���j
	public Player getOwner() {
		// TODO Auto-generated method stub
		return owner;
	}

	@Override
	//�U�����镨�i�̂̈ꕔ�═��j�ƈꏏ�Ɏ��g���������Ƃ��ł��邩
	public boolean isMovable() {
		// TODO Auto-generated method stub
		return false;
	}

	//�U�����I�������A������
	void onEndAnimation() {
		disappear();
	}

	//���݂��Ă��邩
	public boolean isAlive() {
		return fActive;
	}

	@Override
	public void appear() {
		// TODO Auto-generated method stub
		fActive = true;
	}

	@Override
	public void disappear() {
		// TODO Auto-generated method stub
		fActive = false;		
	}

	@Override
	public boolean isActivate() {
		// TODO Auto-generated method stub
		return (owner.animation.time >= timeLag);
	}
}
