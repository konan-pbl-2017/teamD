package fight3D;

import java.awt.Color;

import framework.RWT.RWTLabel;
import framework.RWT.RWTSelectableWidget;
import framework.model3D.Model3D;

public class StageSelectImageButton extends RWTLabel implements RWTSelectableWidget {
	private RWTLabel label;
	protected int number = 0;
	private String comment;
	StageSelectContainer container;
	Model3D model;

	public StageSelectImageButton(String str, Model3D m, RWTLabel activeLabel,
			int i, StageSelectContainer c) {
		comment = str;
		model = m;
		label = activeLabel;
		number = i;
		container = c;
	}

	@Override
	public void selected() {
		// �A�N�e�B�u�ȃL�����N�^�̖��O�������\��
		label.setString(comment);
		label.setColor(Color.WHITE);
		label.setRelativePosition(0.35f, 0.75f);

		// �A�N�e�B�u�ȃL�����N�^�̃I�u�W�F�N�g��ݒ�
		container.updateObjectCanvas(model.createObject());
	}

	@Override
	public void deselected() {		
	}

	@Override
	public int getAbsoluteHeight() {
		return super.getAbsoluteHeight();
	}

	@Override
	public int getAbsoluteWidth() {
		return super.getAbsoluteWidth();
	}

	@Override
	public int getAbsoluteX() {
		return super.getAbsoluteX();
	}

	@Override
	public int getAbsoluteY() {
		return super.getAbsoluteY();
	}
}
