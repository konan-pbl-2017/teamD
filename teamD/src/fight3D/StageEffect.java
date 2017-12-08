package fight3D;
import javax.vecmath.Point3d;


public class StageEffect {

	private Point3d p1 = new Point3d();
	private Point3d p2 = new Point3d();
	private String effect;
	
	//�X�e�[�W�́A������ʂ̂���ʒu
	public void setPositions(Point3d p1, Point3d p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Point3d[] getPositions(){
		Point3d[] p = {p1, p2};
		return p;
	}

	//�X�e�[�W�̎��������
	public void setEffect(String e){
		effect = e;
	}
	public String getEffect(){
		return effect;
	}
	
	/**
	 * ���̃|�C���g��effect�G���A�ɓ����Ă邩�ǂ����B
	 * @param p
	 * @return
	 */
	public boolean isIncluded(Point3d p){
	if(isIncluded(p.x, p1.x, p2.x) && isIncluded(p.y, p1.y, p2.y) && isIncluded(p.z, p1.z, p2.z)){
		return true;
	}
	else{
		return false;}	
	}
	
	/**
	 * x��p1��p2�̊Ԃɓ����Ă邩�ǂ����B
	 * @param x
	 * @param p1
	 * @param p2
	 * @return
	 */
	private boolean isIncluded(double x, double p1, double p2) {
		if((x-p1)*(x-p2) <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
