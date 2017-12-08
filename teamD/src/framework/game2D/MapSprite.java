package framework.game2D;

/**
 * ���H�Q�[����p�̃X�v���C�g�̃N���X
 * @author T.Kuno
 *
 */
public class MapSprite extends Sprite {
	private int mapPositonX;
	private int mapPositonY;
	
	public MapSprite(String imageFile) {
		super(imageFile);
	}

	/**
	 * 2�����z��ŕ\�����}�b�v��X�����̓Y����Ԃ�
	 * 
	 * @return
	 */
	public int getMapPositonX() {
		return mapPositonX;
	}

	/**
	 * 2�����z��ŕ\�����}�b�v��X�����̓Y���ɐV����mapPositonX��ݒ肷��
	 * 
	 * @param mapPositonX
	 */
	public void setMapPositonX(int mapPositonX) {
		this.mapPositonX = mapPositonX;
	}

	/**
	 * 2�����z��ŕ\�����}�b�v��X�����̓Y����Ԃ�
	 * 
	 * @return
	 */
	public int getMapPositonY() {
		return mapPositonY;
	}

	/**
	 * 2�����z��ŕ\�����}�b�v��X�����̓Y���ɐV����mapPositonX��ݒ肷��
	 * 
	 * @param mapPositonY
	 */
	public void setMapPositonY(int mapPositonY) {
		this.mapPositonY = mapPositonY;
	}


	
}
