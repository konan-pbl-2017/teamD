package game;

import java.math.BigDecimal;
import framework.game2D.Map2D;

/**
 * 迷路ゲームのステージのクラス
 * @author T.Kuno
 *
 */
public class MazeStage extends Map2D {
	// コンストラクタ
	public MazeStage() {
		super(new String[]{
				"data\\images\\haikei\\haikei_kuro.jpg",
				"data\\images\\block.gif"},
		1);
	}
	
	// 抽象メソッドの実装
	//　0:タイル　1:ブロック　
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
		// 丸め誤差処理用変数の生成
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
		
		// ステージの構成オブジェクトの位置とプレイヤーの位置が同じかどうか判定する
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
