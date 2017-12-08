package fight3D;

import javax.vecmath.Vector3d;

import framework.gameMain.Mode;
import framework.gameMain.ModeFreeFall;
import framework.gameMain.ModeOnGround;
import framework.physics.PhysicsUtility;
import framework.physics.Velocity3D;


public class StateLeftMove extends StateRepeatable {
	private Velocity3D initialVelocity = new Velocity3D(-5.0, 0.0, 0.0);
	
	void setSpeed(double speed) {
		initialVelocity.setX(-speed);
	}
	
	public boolean canChange(State nextState, Mode mode) {		
		if(mode instanceof ModeFreeFall) {
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
			// �n�ʂ̌����ɉ����悤�ɑ��x�x�N�g���̌�����ς���
			// �i�n�������Ă���Ƃ��̓A�j���[�V�����Ȃǂ̉e���ő��x���ς��\��������̂ŁA��ԕω����Ȃ��Ă��������x��ݒ肵������j
			ModeOnGround modeOnGround = (ModeOnGround)mode;
			Vector3d v = getInitialVelocity().getVector3d();
			double d = v.dot(modeOnGround.getNormal());
			v.scaleAdd(-d, modeOnGround.getNormal(), v);
			return new Velocity3D(v);
		}
		// �󒆂ɑ؍݂��Ă���ꍇ�͐��������̑��x���������ύX����i���R�����ɂ͉e����^���Ȃ��悤�Ɂj
		if (counter == 0) {
			// �󒆂̏ꍇ�A���������ɉ��������邱�Ƃ͂Ȃ��̂ŁA���x��ݒ肷��̂͏���݂̂ł���
			Vector3d v0 = curVelocity.getVector3d();
			Vector3d v1 = getInitialVelocity().getVector3d();
			v0.add(v1);
			double d0 = v0.dot(PhysicsUtility.horizon);	
			double d1 = v1.dot(PhysicsUtility.horizon);	
			v0.scaleAdd(d1 - d0, PhysicsUtility.horizon, v0);
			return new Velocity3D(v0);
		}
		return null;
	}
}
