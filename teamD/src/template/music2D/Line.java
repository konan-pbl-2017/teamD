package template.music2D;

import framework.game2D.Sprite;

public class Line extends Sprite {

	public Line(String string) {
		super(string);
	}

	
	// ////////////////////////////////////////////////////
	//
	// �v���C���[���E�B���h�E���ɂ��邩�ǂ����̃��\�b�h
	//
	// ///////////////////////////////////////////////////

	/**
	 * width��height�Ō��߂�ꂽ�E�B���h�E�̕��̒��Ƀv���C���[�����邩�ǂ�����Ԃ�
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean doesScrrenOut(int width, int height) {
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