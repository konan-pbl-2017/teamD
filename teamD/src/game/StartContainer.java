package game;

import java.applet.*;
import java.awt.Font;
import java.awt.GraphicsConfiguration;

import framework.RWT.RWTContainer;
import framework.RWT.RWTLabel;
import framework.RWT.RWTVirtualController;
import framework.RWT.RWTVirtualKey;

public class StartContainer extends RWTContainer {
	private TemplateMazeGame2D game;

	public StartContainer(TemplateMazeGame2D game) {
		this.game = game;
	}

	@Override
	public void build(GraphicsConfiguration gc) {
		RWTLabel startLabel = new RWTLabel();
		startLabel.setString("Black Santa");
		startLabel.setRelativePosition(0.2f, 0.4f);
		RWTLabel startLabel2 = new RWTLabel();
		startLabel2.setString("Pless Space");
		startLabel2.setRelativePosition(0.25f, 0.7f);
		Font f = new Font("", Font.PLAIN, 60);
		Font f2 = new Font("", Font.PLAIN, 45);
		startLabel.setFont(f);
		addWidget(startLabel);
		startLabel2.setFont(f2);
		addWidget(startLabel2);
	}
	
	

	@Override
	public void keyPressed(RWTVirtualKey key) {
		if (key.getVirtualKey() == RWTVirtualController.BUTTON_C) {
			game.play();
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
