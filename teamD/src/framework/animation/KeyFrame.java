package framework.animation;

import javax.media.j3d.Texture;

import framework.model3D.Position3D;
import framework.model3D.Quaternion3D;


/**
 * �A�j���[�V�������ŃL�[�t���[���P�ʂɒ�`�������i�L�[�l�j
 * @author �V�c����
 *
 */
public class KeyFrame {	
	long key = 0;
	Position3D keyValuePosition = null;
	Quaternion3D keyValueQuaternion = null;
	Texture keyValueTexture = null;
	Position3D keyValueTexturePosition = null;
	Quaternion3D keyValueTextureQuaternion = null;
	
	Position3D getPosition() {
		return keyValuePosition;
	}

	Quaternion3D getQuaternion() {
		return keyValueQuaternion;
	}
	
	Texture getTexture() {
		return keyValueTexture;
	}
	
	Position3D getTexturePosition() {
		return keyValueTexturePosition;
	}
	
	Quaternion3D getTextureQuaternion() {
		return keyValueTextureQuaternion;
	}
}

