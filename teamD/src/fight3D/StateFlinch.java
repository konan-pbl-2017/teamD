package fight3D;
import framework.gameMain.Mode;
import framework.gameMain.ModeFreeFall;
import framework.gameMain.ModeOnGround;
import framework.physics.Velocity3D;

//�Ђ��
public class StateFlinch extends StateOnce {
	static final Velocity3D initialVelocity = new Velocity3D(0.0, 0.0, 0.0);

	@Override
	public boolean canChange(State nextState, Mode mode) {
		// TODO Auto-generated method stub
		if (nextState instanceof StateDamaged) return true;			

		//�ʏ�
		if(mode instanceof ModeOnGround) {
			
		}
		
		//������
		if(mode instanceof ModeFreeFall) {
			
		}
		
		return false;
	}
	
	public Velocity3D getInitialVelocity() {		
		return initialVelocity;
	}

}
