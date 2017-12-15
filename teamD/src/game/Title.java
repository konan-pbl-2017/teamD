package game;

import framework.RWT.RWTFrame3D;

public class Title {

	public static void main(String[] args) {

		int scene = 1;

		switch(scene){
		case 1:
			RWTFrame3D f = new RWTFrame3D();
			f.setSize(800, 800);
			f.setTitle("Black Santa");
			f.setVisible(true);
			System.out.println("タイトル");
			break;
		case 2: 
			TemplateMazeGame2D game = new TemplateMazeGame2D();
			game.setFramePolicy(5, 33, false);
			game.start();
			System.out.println("aaaaaaaaa");
			break;
		case 3:
			break;
		case 4:
			break;
		}
	}

	private static void setsize(int i, int j) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
