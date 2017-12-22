package game;

import java.awt.Event;
import java.awt.Font;
import java.awt.GraphicsConfiguration;

import framework.RWT.RWTContainer;
import framework.RWT.RWTImage;
import framework.RWT.RWTLabel;
import framework.RWT.RWTVirtualController;
import framework.RWT.RWTVirtualKey;

public class EndingContainer extends RWTContainer {
	private TemplateMazeGame2D game;
	private RWTImage back;

	public EndingContainer(TemplateMazeGame2D game) {
		this.game = game;
	}

	@Override
	public void build(GraphicsConfiguration gc) {

		back = new RWTImage("data\\images\\haikei\\sannta_2.jpg");
//		back.setRelativePosition(0.1f, 0.1f);
		addWidget(back);
		
		RWTLabel startLabel = new RWTLabel();
		startLabel.setString("Thank you");
		startLabel.setRelativePosition(0.02f, 0.2f);
		RWTLabel startLabel2 = new RWTLabel();
		startLabel2.setString("for playing!");
		startLabel2.setRelativePosition(0.12f, 0.4f);
		RWTLabel startLabel3 = new RWTLabel();
		startLabel3.setString("Return Space");
		startLabel3.setRelativePosition(0.03f, 0.7f);
		Font f = new Font("", Font.PLAIN, 60);
		Font f2 = new Font("", Font.PLAIN, 45);
		startLabel.setFont(f);
		addWidget(startLabel);
		startLabel2.setFont(f);
		addWidget(startLabel2);
		startLabel3.setFont(f2);
		addWidget(startLabel3);
		
		repaint();
	}

	@Override
	public void keyPressed(RWTVirtualKey key) {
		if (key.getVirtualKey() == RWTVirtualController.BUTTON_C) {
			game.restart();
		}
	}

	@Override
	public void keyReleased(RWTVirtualKey key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(RWTVirtualKey key) {
		// TODO Auto-generated method stub

	}

}
