package framework.animation;

import java.util.ArrayList;

import javax.media.j3d.Texture;

import framework.model3D.Position3D;
import framework.model3D.Quaternion3D;


/**
 * ���i�P�ʂ̃A�j���[�V��������ێ�����
 * @author �V�c����
 *
 */
public class PartAnimation {
	private String name;
	private int textureUnit;
	private ArrayList<KeyFrame> keyList = new ArrayList<KeyFrame>();
	public static final int SINGLE_TEXTURE = -1;
	
	public PartAnimation(String name) {
		 this.name = name;
		 textureUnit = SINGLE_TEXTURE;
	}
	
	public PartAnimation(String name, int textureUnit) {
		 this.name = name;
		 this.textureUnit = textureUnit;
	}
	
	String getName() {
		return name;
	}
	
	int getTextureUnit() {
		return textureUnit;
	}

	//�A�j���[�V�����̌o�ߎ��Ԃ̑O���key��KeyFrame�^�̔z��Ŏ擾���郁�\�b�h
	KeyFrame[] getKey(long t){
		int i;
		KeyFrame[] aroundKey = new KeyFrame[2];
					
		for(i=1;i<keyList.size();i++){
			
			//t��key���̂��̂������ꍇ�it�̑O���KeyFrame���K�v�Ȃ����j�A�v�f�O�ɂ��̂��̂������A�v�f�P��null�ɐݒ肵�Ă���
			if(t == keyList.get(i).key){
				aroundKey[0] = keyList.get(i);
				aroundKey[1] = null;
				return aroundKey;
			}
			
			//t�̑O���KeyFrame����
			if(t < keyList.get(i).key){
				aroundKey[0] = keyList.get(i-1);
				aroundKey[1] = keyList.get(i);
				return aroundKey;
			}
		}
		return null;
	}
	
	//keyList�̍Ō�̗v�f�ikey�̍ő�l�j���擾���ĕԂ����\�b�h
	long getLastKey() {
		return keyList.get(keyList.size()-1).key;
	}
	
	public void add(long k, Position3D p) {
		KeyFrame e = new KeyFrame();
		e.key = k;
		e.keyValuePosition = p;
		e.keyValueQuaternion = null;
		e.keyValueTexture = null;
		e.keyValueTexturePosition = null;
		e.keyValueTextureQuaternion = null;
		keyList.add(e);
	}
	
	public void add(long k, Quaternion3D q) {
		KeyFrame e = new KeyFrame();
		e.key = k;
		e.keyValuePosition = null;
		e.keyValueQuaternion = q;
		e.keyValueTexture = null;
		e.keyValueTexturePosition = null;
		e.keyValueTextureQuaternion = null;
		keyList.add(e);
	}
	
	public void add(long k, Position3D p, Quaternion3D q) {
		KeyFrame e = new KeyFrame();
		e.key = k;
		e.keyValuePosition = p;
		e.keyValueQuaternion = q;
		e.keyValueTexture = null;
		e.keyValueTexturePosition = null;
		e.keyValueTextureQuaternion = null;
		keyList.add(e);
	}
		
	public void addTexture(long k, Texture texture) {
		KeyFrame e = new KeyFrame();
		e.key = k;
		e.keyValuePosition = null;
		e.keyValueQuaternion = null;
		e.keyValueTexture = texture;
		e.keyValueTexturePosition = null;
		e.keyValueTextureQuaternion = null;
		keyList.add(e);
	}
	
	public void addTexture(long k, Texture texture, Position3D tp) {
		KeyFrame e = new KeyFrame();
		e.key = k;
		e.keyValuePosition = null;
		e.keyValueQuaternion = null;
		e.keyValueTexture = texture;
		e.keyValueTexturePosition = tp;
		e.keyValueTextureQuaternion = null;
		keyList.add(e);
	}
	
	public void addTexture(long k, Texture texture, Quaternion3D tq) {
		KeyFrame e = new KeyFrame();
		e.key = k;
		e.keyValuePosition = null;
		e.keyValueQuaternion = null;
		e.keyValueTexture = texture;
		e.keyValueTexturePosition = null;
		e.keyValueTextureQuaternion = tq;
		keyList.add(e);
	}
	
	public void addTexture(long k, Texture texture, Position3D tp, Quaternion3D tq) {
		KeyFrame e = new KeyFrame();
		e.key = k;
		e.keyValuePosition = null;
		e.keyValueQuaternion = null;
		e.keyValueTexture = texture;
		e.keyValueTexturePosition = tp;
		e.keyValueTextureQuaternion = tq;
		keyList.add(e);
	}
	
	public void addTexture(long k, Position3D tp) {
		KeyFrame e = new KeyFrame();
		e.key = k;
		e.keyValuePosition = null;
		e.keyValueQuaternion = null;
		e.keyValueTexture = null;
		e.keyValueTexturePosition = tp;
		e.keyValueTextureQuaternion = null;
		keyList.add(e);
	}
	
	public void addTexture(long k, Quaternion3D tq) {
		KeyFrame e = new KeyFrame();
		e.key = k;
		e.keyValuePosition = null;
		e.keyValueQuaternion = null;
		e.keyValueTexture = null;
		e.keyValueTexturePosition = null;
		e.keyValueTextureQuaternion = tq;
		keyList.add(e);
	}
	
	public void addTexture(long k, Position3D tp, Quaternion3D tq) {
		KeyFrame e = new KeyFrame();
		e.key = k;
		e.keyValuePosition = null;
		e.keyValueQuaternion = null;
		e.keyValueTexture = null;
		e.keyValueTexturePosition = tp;
		e.keyValueTextureQuaternion = tq;
		keyList.add(e);
	}
}
