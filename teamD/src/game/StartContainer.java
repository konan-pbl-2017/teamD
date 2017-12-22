package game;

import java.applet.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;

import framework.RWT.RWTContainer;
import framework.RWT.RWTImage;
import framework.RWT.RWTLabel;
import framework.RWT.RWTVirtualController;
import framework.RWT.RWTVirtualKey;
import framework.model3D.Universe;

public class StartContainer extends RWTContainer {
	private TemplateMazeGame2D game;
	private RWTImage back;

	public StartContainer(TemplateMazeGame2D game) {
		this.game = game;
	}

	

	@Override
	public void build(GraphicsConfiguration gc) {
		back = new RWTImage("data\\images\\haikei\\sannta_1.jpg");
//		back.setRelativePosition(0.1f, 0.1f);
		addWidget(back);
		
		RWTLabel startLabel = new RWTLabel();
		startLabel.setString("Black Santa");
		startLabel.setRelativePosition(0.35f, 0.15f);
		RWTLabel startLabel2 = new RWTLabel();
		startLabel2.setString("Pless Space");
		startLabel2.setRelativePosition(0.47f, 0.29f);
		Font f = new Font("", Font.PLAIN, 60);
		Font f2 = new Font("", Font.PLAIN, 45);
		startLabel.setColor(Color.YELLOW);
		startLabel2.setColor(Color.YELLOW);
		startLabel.setFont(f);
		addWidget(startLabel);
		startLabel2.setFont(f2);
		addWidget(startLabel2);
		
		repaint();
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
