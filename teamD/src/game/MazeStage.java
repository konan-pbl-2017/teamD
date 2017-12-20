package game;

import java.math.BigDecimal;
import framework.game2D.Map2D;

/**
 * ���H�Q�[���̃X�e�[�W�̃N���X
 * @author T.Kuno
 *
 */
public class MazeStage extends Map2D {
	// �R���X�g���N�^
	public MazeStage() {
		super(new String[]{
				"data\\images\\haikei\\haikei_kuro.jpg",
				"data\\images\\block.gif"},
		1);
	}
	
	// ���ۃ��\�b�h�̎���
	//�@0:�^�C���@1:�u���b�N�@
	@Override
	public int[][] createMap() {
		int[][] map = {
				{1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,1,0,1,0,1,0,1,0,1,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,1,0,1,0,1,0,1,0,1,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,1,0,1,0,1,0,1,0,1,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,1,0,1,0,1,0,1,0,1,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1}
				
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

	public boolean checkGridPoint(MazeSpritePlayer Player1,MazeSpritePlayer Player2) {
		// �ۂߌ덷�����p�ϐ��̐���
		//1P
		double mazeSpritePositionX1 = new BigDecimal(Player1
				.getPosition().getX()).setScale(1, BigDecimal.ROUND_DOWN)
						.doubleValue();
		double mazeSpritePositionY1 = new BigDecimal(Player1
				.getPosition().getY()).setScale(1, BigDecimal.ROUND_DOWN)
				.doubleValue();
				
		//2P
		double mazeSpritePositionX2 = new BigDecimal(Player2
				.getPosition().getX()).setScale(1, BigDecimal.ROUND_DOWN)
				.doubleValue();
		double mazeSpritePositionY2 = new BigDecimal(Player2
				.getPosition().getY()).setScale(1, BigDecimal.ROUND_DOWN)
				.doubleValue();
		
		// �X�e�[�W�̍\���I�u�W�F�N�g�̈ʒu�ƃv���C���[�̈ʒu���������ǂ������肷��
		//1P
		for (int i = 0; i < this.getStageObjectList().size(); i++) {
			if (
					mazeSpritePositionX1 == this.getStageObjectList().get(i).getPosition().getX()
					&& mazeSpritePositionY1 == this.getStageObjectList().get(i).getPosition().getY()
				){
				return true;
			}

		}
		
		//2P
		for (int i = 0; i < this.getStageObjectList().size(); i++) {
			if (
					mazeSpritePositionX2 == this.getStageObjectList().get(i).getPosition().getX()
					&& mazeSpritePositionY2 == this.getStageObjectList().get(i).getPosition().getY()
				){
				return true;
			}

		}
		return false;
	}
}
