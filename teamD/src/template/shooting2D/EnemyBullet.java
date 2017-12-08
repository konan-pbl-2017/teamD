package template.shooting2D;

import framework.game2D.FlyingActor2D;
import framework.game2D.Sprite;

public class EnemyBullet extends Sprite {
	public EnemyBullet(String imageFile) {
		super(imageFile, 0.2f);
		setCollisionRadius(0.2);
	}


	// ////////////////////////////////////////////////////
	//
	// �e���E�B���h�E���ɂ��邩�ǂ����̃��\�b�h
	//
	// ///////////////////////////////////////////////////

	/**
	 * width��height�ŕ\�����E�B���h�E�T�C�Y���ɑ��݂��邩�𔻒肷��
	 * 
	 * @param width
	 *            --- �E�B���h�E�̕�
	 * @param height
	 *            --- �E�B���h�E�̍���
	 * @return
	 */
	public boolean isInScreen(int width, int height) {
		if (this.getPosition().getX() < width / 2.0
				&& this.getPosition().getX() > -1.0 * width / 2.0) {
			if (this.getPosition().getY() < height / 2.0
					&& this.getPosition().getY() > -1.0 * height / 2.0) {
				return true;
			}
		}
		return false;
	}
}
