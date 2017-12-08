package fight3D;

import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.ImageComponent2D;

import framework.gameMain.GameModel;


public class Stage extends GameModel {
	
	private String name;
	private String comment;
//	private int level;
//	private int size;
	private ArrayList<StageEffect> effectList = new ArrayList<StageEffect>();
	
	private BufferedImage image;
	
	//�R���X�g���N�^
	public Stage(String fileName) {
		super(fileName);
		// TODO Auto-generated constructor stub
	}
	
	//�X�e�[�W��
	public void setName(String n){
		name = n;
	}
	public String getName(){
		return name;
	}

	//�w�i
	public void setBackground(String fileName){
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setBackgroundSize(Dimension size){
		double scalew = (double)size.width/image.getWidth();
		double scaleh = (double)size.height/image.getHeight();
		AffineTransformOp atOp = new AffineTransformOp(AffineTransform.getScaleInstance(scalew, scaleh), AffineTransformOp.TYPE_BILINEAR);
		
		BufferedImage img = new BufferedImage(size.width, size.height, image.getType());
		atOp.filter(image, img);
		image = img;
	}
	
	public Background getBackground() {
		Background background = new Background();
		ImageComponent2D imageCompornent = new ImageComponent2D(ImageComponent2D.FORMAT_RGB, image);
		background.setImage(imageCompornent);
		BoundingSphere bs = new BoundingSphere();
		bs.setRadius(100);
		background.setApplicationBounds(bs);

		return background;
	}

	//�X�e�[�W�̐���
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getComment() {
		return comment;
	}

	//�X�e�[�W�̓�Փx
//	public void setLevel(int level) {
//		this.level = level;
//	}
//	public int getLevel() {
//		return level;
//	}
	
//	//�X�e�[�W�̑傫��
//	public void setSize(int size) {
//		this.size = size;
//	}
//	public int getSize() {
//		return size;
//	}

	//������ʂ����X�e�[�W��
	public void addEffect(StageEffect effect){
		effectList.add(effect);
	}
	
	public ArrayList<StageEffect> getEffectList() {
		return effectList;
	}
}
