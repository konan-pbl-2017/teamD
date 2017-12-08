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

public class CharacterSelectContainer extends RWTContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8354537837334374243L;

	Game game;
	
	static final int PLAYER_NUM = 2; // ���v���C���[��
	
	private RWTSelectionCanvas3D objectCanvas;
	private boolean flag = false; // �m�F��ʂ̃t���O
	private ArrayList<Character> characters = new ArrayList<Character>();
	private int pn = 0;
	private int character[] = new int[PLAYER_NUM];
	protected RWTLabel activatedLabel = new RWTLabel();
	private RWTLabel s1 = new RWTLabel();
	private RWTLabel s2 = new RWTLabel();
	private RWTLabel m5 = new RWTLabel();
	private RWTLabel m6 = new RWTLabel();
	private RWTLine l1 = new RWTLine();
	private RWTLine l2 = new RWTLine();

	// �R���X�g���N�^
	public CharacterSelectContainer(Game g) {
		game = g;
	}
	
	// �t���[���ɒǉ����ăT�C�Y���m�肵����ɒ��g���\�z����
	public void build(GraphicsConfiguration gc) {
		// ������
		removeAll();
		setBackground(Color.BLACK);

		// �L�����N�^�[��`�悷�邽�߂̃L�����o�X(���ΓI�L�����o�X)��ǉ�
		objectCanvas = new RWTSelectionCanvas3D();
		objectCanvas.setRelativePosition(0.03f, 0.675f);
		objectCanvas.setRelativeSize(0.25f, 0.3f);
		addCanvas(objectCanvas);
		
        //��ʃ^�C�g���A�߂�A�C�R���A���b�Z�[�W�G���A�E�P
        s1.setRelativePosition(0.20f,0.10f);
        s1.setString("�u�L�����N�^�[�I���v");
        s1.setColor(Color.WHITE);
        Font f1 = new Font("",Font.PLAIN,20);
        s1.setFont(f1);
        addWidget(s1);
        
        s2.setRelativePosition(0.025f,0.05f);
        s2.setString("�a�F�^�C�g����ʂ�");
        s2.setColor(Color.WHITE);
        Font f2 = new Font("",Font.PLAIN,8);
        s2.setFont(f2);
        addWidget(s2);

        m5.setRelativePosition(0.60f,0.05f);
        m5.setString((pn+1) + "�o�̓L�����N�^�[��I�����ĉ������B");
        m5.setColor(Color.YELLOW);
        addWidget(m5);
        m6.setRelativePosition(0.60f,0.10f);
        m6.setString("�`�F����");
        m6.setColor(Color.YELLOW);
        addWidget(m6);
        
        //�S�L�����N�^�[�̐�
        int n = CharacterManager.getInstance().getNumberOfCharacters(); //�L�����N�^�[�̑���
        //�S�L�����N�^�[�̓o�^
        for(int i=0;i<n;i++){
            characters.add(i, CharacterManager.getInstance().getCharacter(i));
        }
        
        //�L�����N�^�[�{�^���̕\��
        for(int i=0;i<n;i++){
            String str = characters.get(i).getName()+ RWTLabel.NEW_PARAGRAPH + characters.get(i).getComment();
            CharacterSelectImageButton cb = new CharacterSelectImageButton(str, characters.get(i).getModel(),activatedLabel,i, this);
            cb.setString(characters.get(i).getName());
            cb.setColor(Color.YELLOW);
            cb.setRelativePosition(0.10f + i % 3 * 0.3f, (i / 3 + 1) * 0.3f);
            addSelectableWidget(cb, i%3, i/3);
        }
        
        //�I���L�����N�^�[�A���b�Z�[�W�G���A�E�Q
        addWidget(activatedLabel);
        
        //���C��
        l1.setRelativePosition(0.00f, 0.15f, 1.00f, 0.15f);
        l1.setColor(Color.WHITE);
        addWidget(l1);
        
        l2.setRelativePosition(0.00f, 0.65f, 1.00f, 0.65f);
        l2.setColor(Color.WHITE);
        addWidget(l2);
		
		repaint();
	}
	
	public void updateObjectCanvas(BaseObject3D o) {
		if (objectCanvas != null) objectCanvas.setObject(o);
	}

	// �L�[�{�[�h���������u��
	public void keyPressed(RWTVirtualKey key) {
		if (key.getPlayer() == -1) {
			key.assignPlayer(1);
		}
	}

	// �L�[�{�[�h�𗣂����u��
	public void keyReleased(RWTVirtualKey key) {
		if (key.getVirtualKey() == RWTVirtualController.UP && pn < PLAYER_NUM)
			cursorMoveUp();
		if (key.getVirtualKey() == RWTVirtualController.DOWN
				&& pn < PLAYER_NUM)
			cursorMoveDown();
		if (key.getVirtualKey() == RWTVirtualController.RIGHT
				&& pn < PLAYER_NUM)
			cursorMoveRight();
		if (key.getVirtualKey() == RWTVirtualController.LEFT
				&& pn < PLAYER_NUM)
			cursorMoveLeft();
		if (key.getVirtualKey() == RWTVirtualController.BUTTON_A) {
			// �L�����N�^�[�ԍ������
			if (pn < 2) {
				character[pn] = ((CharacterSelectImageButton) getSelectedWidget()).number;
				// �I�������L�����N�^�[�̔ԍ�
				pn++; // �v���C���[�̒ǉ�

				m5.setString((pn + 1) + "�o�̓L�����N�^�[��I�����ĉ������B");
				addWidget(m5);
				
				repaint();
			}

			// �m�F���
			if (pn == PLAYER_NUM) {
				s2.setString("");
				addWidget(s2);
				m5.setString("����ł�낵���ł����B");
				addWidget(m5);
				m6.setString("�`�F����@�a�F�߂�");
				addWidget(m6);
				
				repaint();

				if (flag == true) {
					// ���̉�ʂ�
					game.goNextGameState();
					game.setPlayerNum(pn);
					game.setCharacter(character);
				}

				flag = true;
			}
		}
		if (key.getVirtualKey() == RWTVirtualController.BUTTON_B) {
			// �m�F���
			if (flag == true) {
				pn = 0; // ���͂�������������

				s2.setString("�a�F�^�C�g����ʂ�");
				addWidget(s2);
				m5.setString((pn + 1) + "�o�̓L�����N�^�[��I�����ĉ������B");
				addWidget(m5);
				m6.setString("�`�F����");
				addWidget(m6);
				
				repaint();

				flag = false;
			} else {
				game.goPrevGameState();
			}
		}
	}

	// �L�[�{�[�h�������ė���
	public void keyTyped(RWTVirtualKey key) {

	}
}
