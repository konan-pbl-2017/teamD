package fight3D;

import javax.vecmath.Vector3d;

import framework.gameMain.Mode;
import framework.gameMain.ModeFreeFall;
import framework.gameMain.ModeOnGround;
import framework.model3D.GeometryUtility;
import framework.physics.PhysicsUtility;
import framework.physics.Velocity3D;


public class StateNormalRight extends StateRepeatable {
	static final Velocity3D initialVelocity = new Velocity3D(0.0, 0.0, 0.0);
	
	public boolean canChange(State nextState, Mode mode) {
		if(mode instanceof ModeFreeFall){
			//������			
			if(nextState instanceof StateBend) return false;
			if(nextState instanceof StateGuard) return false;
			if(nextState instanceof StateJump) return false;
		}
		return true;
	}

	public Velocity3D getInitialVelocity() {
		return initialVelocity;
	}

	public Velocity3D getVelocity(Velocity3D curVelocity, Mode mode) {
		if (mode instanceof ModeOnGround) {
			if (curVelocity.getVector3d().length() <= GeometryUtility.TOLERANCE) return null;
			// �������Ă��ĎΖʂɏՓ˂�����A�Ζʂ����ׂ��Ă����Ȃ��悤�Ɏ~�߂�
			return getInitialVelocity();
		}
		// �󒆂ɑ؍݂��Ă���ꍇ�͐��������̑��x���������ύX����i���R�����ɂ͉e����^���Ȃ��悤�Ɂj
		if (counter == 0) {
			// ���̏�Ԃɕς�����u��
			Vector3d v0 = curVelocity.getVector3d();
			Vector3d v1 = getInitialVelocity().getVector3d();
			v0.add(v1);
			double d0 = v0.dot(PhysicsUtility.horizon);	
			double d1 = v1.dot(PhysicsUtility.horizon);	
			v0.scaleAdd(d1 - d0, PhysicsUtility.horizon, v0);
			if (v0.y >= 0.0) v0.y = 0.0; // �������㏸���͗������u�Ԃɗ������J�n����
			return new Velocity3D(v0);
		}
		return null;
	}
}
