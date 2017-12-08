package template.maze2D;

import java.math.BigDecimal;
import framework.game2D.Maze2D;

/**
 * ���H�Q�[���̃X�e�[�W�̃N���X
 * @author T.Kuno
 *
 */
public class MazeStage extends Maze2D {
	// �R���X�g���N�^
	public MazeStage(String blockImage, String tileImage) {
		super(blockImage, tileImage);
	}
	
	// ���ۃ��\�b�h�̎���
	//�@0:�^�C���@1:�u���b�N�@
	@Override
	public int[][] createMap() {
		int[][] map = {
				{1,1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,0,0,0,0,1},
				{1,0,1,0,1,0,1,0,1,0,1},
				{1,0,0,0,0,0,0,0,0,0,1},
				{1,0,1,0,1,0,1,0,1,0,1},
				{1,0,0,0,0,0,0,0,0,0,1},
				{1,0,1,0,1,0,1,0,1,0,1},
				{1,0,0,0,0,0,0,0,0,0,1},
				{1,0,1,0,1,0,1,0,1,0,1},
				{1,0,0,0,0,0,0,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1,1}
				
//				{1,1,1,1,1},
//				{1,0,0,0,1},
//				{1,0,1,0,1},
//				{1,0,0,0,1},
//				{1,1,1,1,1}
				
//				{1}

//				{1,0},
//				{1,1}
		};
		return map;
	}

	public boolean checkGridPoint(MazeSpritePlayer mazeSpritePlayer) {
		// �ۂߌ덷�����p�ϐ��̐���
		double mazeSpritePositionX = new BigDecimal(mazeSpritePlayer
				.getPosition().getX()).setScale(1, BigDecimal.ROUND_DOWN)
				.doubleValue();
		double mazeSpritePositionY = new BigDecimal(mazeSpritePlayer
				.getPosition().getY()).setScale(1, BigDecimal.ROUND_DOWN)
				.doubleValue();
		
		// �X�e�[�W�̍\���I�u�W�F�N�g�̈ʒu�ƃv���C���[�̈ʒu���������ǂ��������肷��
		for (int i = 0; i < this.getStageObjectList().size(); i++) {
			if (
					mazeSpritePositionX == this.getStageObjectList().get(i).getPosition().getX()
					&& mazeSpritePositionY == this.getStageObjectList().get(i).getPosition().getY()
				){
				return true;
			}

		}
		return false;
	}
}
