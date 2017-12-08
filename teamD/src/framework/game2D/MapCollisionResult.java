package framework.game2D;


/**
 * ���H�Q�[���ł̏Փ˔���ɂ��Ԃ�l�����N���X
 * 
 * @author T.Kuno
 * 
 */
public class MapCollisionResult {
	private boolean checkColision = false;
	private Velocity2D colisionBackVelocity = new Velocity2D();

	
	/**
	 * �Փ˂����������ǂ�����Ԃ�
	 * 
	 * @return true --- �Փ˂������� false --- �Փ˂��Ȃ�����
	 */
	public boolean isCheckColision() {
		return checkColision;
	}

	/**
	 * �Փ˂��������Ƃ��A���̌��ʂ�V����checkColision�Őݒ肷��
	 * 
	 * @param checkColision
	 */
	public void setCheckColision(boolean checkColision) {
		this.checkColision = checkColision;
	}

	/**
	 * �Փ˂��������Ƃ��Ɍ��̈ʒu�ɖ߂����x��Ԃ�
	 * 
	 * @return
	 */
	public Velocity2D getColisionBackVelocity() {
		return colisionBackVelocity;
	}

	/**
	 * �Փ˂��������Ƃ��Ɍ��̈ʒu�ɖ߂����x��V����colisionBackVelocity�Őݒ肷��
	 * 
	 * @param colisionBackVelocity
	 */
	public void setColisionBackVelocity(Velocity2D colisionBackVelocity) {
		this.colisionBackVelocity = colisionBackVelocity;
	}

}
