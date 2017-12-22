package game;

import java.awt.Event;
import java.awt.Font;
import java.awt.GraphicsConfiguration;

import framework.RWT.RWTContainer;
import framework.RWT.RWTLabel;
import framework.RWT.RWTVirtualController;
import framework.RWT.RWTVirtualKey;

public class EndingContainer extends RWTContainer {
	private TemplateMazeGame2D game;

	public EndingContainer(TemplateMazeGame2D game) {
		this.game = game;
	}

	@Override
	public void build(GraphicsConfiguration gc) {
		RWTLabel startLabel = new RWTLabel();
		startLabel.setString("Thank you");
		startLabel.setRelativePosition(0.12f, 0.3f);
		RWTLabel startLabel2 = new RWTLabel();
		startLabel2.setString("for playing!");
		startLabel2.setRelativePosition(0.32f, 0.4f);
		RWTLabel startLabel3 = new RWTLabel();
		startLabel3.setString("Return Space");
		startLabel3.setRelativePosition(0.23f, 0.7f);
		Font f = new Font("", Font.PLAIN, 60);
		Font f2 = new Font("", Font.PLAIN, 45);
		startLabel.setFont(f);
		addWidget(startLabel);
		startLabel2.setFont(f);
		addWidget(startLabel2);
		startLabel3.setFont(f2);
		addWidget(startLabel3);
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
