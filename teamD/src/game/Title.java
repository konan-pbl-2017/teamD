package game;

import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.model3D.Universe;

public class Title {
	
	int scene;

	
	public void init(Universe universe) {
		scene = 1;
	}

	public void progress(RWTVirtualController virtualController, long interval) {
		if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)) {
			scene = 2;
		}
	}

	public static void main(String[] args) {

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
