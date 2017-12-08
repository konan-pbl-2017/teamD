package fight3D;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.util.ArrayList;

import framework.RWT.RWTContainer;
import framework.RWT.RWTLabel;
import framework.RWT.RWTLine;
import framework.RWT.RWTSelectionCanvas3D;
import framework.RWT.RWTVirtualController;
import framework.RWT.RWTVirtualKey;
import framework.model3D.BaseObject3D;

public class StageSelectContainer extends RWTContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3826880530032193822L;

	Game game;
	
	private RWTSelectionCanvas3D objectCanvas;
	private boolean goFight = false;
	private RWTLabel activatedLabel = new RWTLabel();
	private ArrayList<Stage> stages;
	private RWTLabel title = new RWTLabel();
	private RWTLabel back = new RWTLabel();
	private RWTLabel message1 = new RWTLabel();
	private RWTLabel message2 = new RWTLabel();
	private RWTLine line1 = new RWTLine();
	private RWTLine line2 = new RWTLine();

	public StageSelectContainer(Game g) {
		game = g;
	}
	
	// �t���[���ɒǉ����ăT�C�Y���m�肵����ɒ��g���\�z����
	public void build(GraphicsConfiguration gc) {
		// ������
		removeAll();
		setBackground(Color.BLACK);
		
		// �I�������X�e�[�W��`�悷�邽�߂̃L�����o�X��ǉ�
		objectCanvas = new RWTSelectionCanvas3D();
		objectCanvas.setRelativePosition(0.03f, 0.675f);
		objectCanvas.setRelativeSize(0.25f, 0.30f);
		addCanvas(objectCanvas);

		stages = StageManager.getInstance().getStageList();

		// �^�C�g���E�߂�A�C�R���E���b�Z�[�W�G���A�̕`��
		title.setString("��X�e�[�W�I��");
		title.setColor(Color.white);
		title.setFont(new Font("", Font.PLAIN, 20));
		title.setRelativePosition(0.20f, 0.10f);
		addWidget(title);

		back.setString("B:��׸���I����ʂ�");
		back.setColor(Color.white);
		back.setFont(new Font("", Font.PLAIN, 8));
		back.setRelativePosition(0.025f, 0.05f);
		addWidget(back);

		message1.setRelativePosition(0.60f, 0.05f);
		message1.setString("�X�e�[�W��I�����ĉ������B");
		message1.setColor(Color.YELLOW);
		addWidget(message1);
		message2.setRelativePosition(0.60f, 0.10f);
		message2.setString("�`�F����");
		message2.setColor(Color.YELLOW);
		addWidget(message2);

		// �X�e�[�W�̕`��
		for (int i = 0; i < stages.size(); i++) {
			String str = stages.get(i).getName() + RWTLabel.NEW_PARAGRAPH
					+ stages.get(i).getComment();
			StageSelectImageButton stage = new StageSelectImageButton(str,
					stages.get(i).getModel(), activatedLabel, i, this);
			stage.setString(stages.get(i).getName());
			stage.setColor(Color.white);
			stage.setRelativePosition(0.10f + i % 3 * 0.3f, (i / 3 + 1) * 0.3f);
			addSelectableWidget(stage, i % 3, i / 3);
		}

		// �I�����ꂽ�X�e�[�W�̐������̕`��
		addWidget(activatedLabel);

		// ��ʕ������C���̕`��
		line1.setColor(Color.white);
		line1.setRelativePosition(0.00f, 0.15f, 1.00f, 0.15f);
		addWidget(line1);

		line2.setColor(Color.white);
		line2.setRelativePosition(0.00f, 0.65f, 1.00f, 0.65f);
		addWidget(line2);

		repaint();
	}

	public void updateObjectCanvas(BaseObject3D o) {
		if (objectCanvas != null) objectCanvas.setObject(o);
	}

	@Override
	public void keyPressed(RWTVirtualKey key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(RWTVirtualKey key) {
		// TODO Auto-generated method stub

		if (key.getVirtualKey() == RWTVirtualController.BUTTON_A) {
			// �m�F���
			setConfirmMessage();
			repaint();

			if (goFight == true) {
				// �I�����ꂽ�X�e�[�W�̓o�^
				StageSelectImageButton activeWidget;
				activeWidget = (StageSelectImageButton) getSelectedWidget();
				game.setStage(activeWidget.number);

				game.goNextGameState();
			}
			goFight = true;
		}

		if (key.getVirtualKey() == RWTVirtualController.BUTTON_B) {
			if (goFight == true) {
				initializeMessage();
				repaint();
				goFight = false;
			} else {
				game.goPrevGameState();
			}
		}

		if (key.getVirtualKey() == RWTVirtualController.RIGHT) {
			cursorMoveRight();

		}
		if (key.getVirtualKey() == RWTVirtualController.LEFT) {
			cursorMoveLeft();
		}
	}

	@Override
	public void keyTyped(RWTVirtualKey key) {
		// TODO Auto-generated method stub

	}

	// �X�e�[�W��I�񂾍ۂ̃��x���̏�����������
	private void setConfirmMessage() {
		back.setString("");
		message1.setString("����ł�낵���ł����B");
		message2.setString("�`�F����@�a�F�߂�");
	}

	// �X�e�[�W��I�тȂ������ۂ̃��x���̏�����������
	private void initializeMessage() {
		back.setString("B:��׸���I����ʂ�");
		message1.setString("�X�e�[�W��I�����ĉ������B");
		message2.setString("�`�F����");
	}
}
