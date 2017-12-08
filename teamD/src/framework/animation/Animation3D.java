package framework.animation;

import java.util.ArrayList;

import framework.model3D.Position3D;
import framework.model3D.Quaternion3D;

/**
 * �K�w�����ꂽ�I�u�W�F�N�g�ɑ΂���A�j���[�V��������ێ�����i���i�P�ʂňʒu�A�����A�e�N�X�`�����A�j���[�V�����\�j
 * @author �V�c����
 *
 */
public class Animation3D {
	public long time = 0;
	
	private ArrayList<PartAnimation> partList = new ArrayList<PartAnimation>();
	private long maxKey = getMaxKey();

	public Animation3D() {
		time = 0;
	}

	public Animation3D(Animation3D a) {
		time = 0;
		partList = a.partList;
		maxKey = a.maxKey;
	}

	public boolean equals(Animation3D a) {
		return (partList == a.partList && maxKey == a.maxKey);
	}

	public void reset() {
		time = 0;
	}

	public boolean progress(long interval) {
		if (maxKey == 0)
			return true; // ��̃A�j���[�V�����̏ꍇ�������Ȃ�
		time += interval;
		// System.out.println(time + "/" + maxKey);
		if (time > maxKey) {
			time = time % maxKey; // time���Ō�̗v�f��key�̒l�i�A�j���[�V�����̍Ō��key)�𒴂��Ă���ꍇ�At���Ō��key�̒l�imaxKey)�Ŋ������]��Ƃ��Đݒ肷��
			return false;
		} else
			return true;
	}

	public Pose3D getPose() {
		if (maxKey == 0 || partList.size() == 0)
			return new DefaultPose3D();

		KeyFrame[] aroundKey = new KeyFrame[2];
		Quaternion3D q;
		Position3D p;
		Quaternion3D tq;
		Position3D tp;
		Pose3D pose = new Pose3D();
		for (int i = 0; i < partList.size(); i++) {
			aroundKey = partList.get(i).getKey(time);

			// �R�s�[�R���X�g���N�^�̍쐬
			q = null;
			p = null;
			tq = null;
			tp = null;
			if (aroundKey[0].getPosition() != null) {
				p = new Position3D(aroundKey[0].getPosition()); // getPosition()���Q�Ƃ�Ԃ��̂ŃR�s�[���Ă���ύX
			}
			if (aroundKey[0].getQuaternion() != null) {
				q = new Quaternion3D(aroundKey[0].getQuaternion()); // getQuaternion()���A�h���X�i�Q�Ɓj��Ԃ��̂ŁA�R�s�[�i�����j���쐬���Ă������ύX���Ă���B
			}
			if (aroundKey[0].getTexturePosition() != null) {
				tp = new Position3D(aroundKey[0].getTexturePosition()); // getPosition()���Q�Ƃ�Ԃ��̂ŃR�s�[���Ă���ύX
			}
			if (aroundKey[0].getTextureQuaternion() != null) {
				tq = new Quaternion3D(aroundKey[0].getTextureQuaternion()); // getQuaternion()���A�h���X�i�Q�Ɓj��Ԃ��̂ŁA�R�s�[�i�����j���쐬���Ă������ύX���Ă���B
			}

			// time��key���̂��̂������ꍇ�i��Ԃ̌v�Z���s�v�ȏꍇ�j
			if (aroundKey[1] != null) {
				// t1 ��t�̑O��key�iaroundKey[0]�j�̃X�J���[�{
				double t1 = aroundKey[1].key - time;
				// t2 ��t�̌��key�iaroundKey[1]�j�̃X�J���[�{
				double t2 = time - aroundKey[0].key;
				double t3 = aroundKey[1].key - aroundKey[0].key;
				double timealpha = t2 / t3;

				// time�ɑ΂���Quaternion��Position�̌v�Z
				if (p != null) {
					Position3D p2 = new Position3D(aroundKey[1].getPosition());
					p.mul(t1 / t3).add(p2.mul(t2 / t3));
				}
				if (q != null) {
					Quaternion3D q2 = new Quaternion3D(aroundKey[1].getQuaternion());
					q.getInterpolate(q2, timealpha);
				}
				if (tp != null) {
					Position3D tp2 = new Position3D(aroundKey[1].getTexturePosition());
					tp.mul(t1 / t3).add(tp2.mul(t2 / t3));
				}
				if (tq != null) {
					Quaternion3D tq2 = new Quaternion3D(aroundKey[1].getTextureQuaternion());
					tq.getInterpolate(tq2, timealpha);
				}
			}
			pose.addPose(partList.get(i).getName(), p, q, aroundKey[0].getTexture(), tp, tq, partList.get(i).getTextureUnit());
		}

		return pose;
	}

	public Animation3D merge(Animation3D a) {
		this.partList.addAll(a.partList);
		maxKey = getMaxKey();
		return this;
	}

	public void addPartAnimation(PartAnimation pa) {
		partList.add(pa);
		maxKey = getMaxKey();
	}

	// �A�j���[�V�������I���������𔻒肷�邽�߂�key�̍ő�l��T�����ĕԂ����\�b�h
	private long getMaxKey() {
		long maxKey = 0;
		int i;

		for (i = 0; i < partList.size(); i++) {
			if (maxKey < partList.get(i).getLastKey()) {
				maxKey = partList.get(i).getLastKey();
			} else
				continue;
		}
		return maxKey;
	}
}
