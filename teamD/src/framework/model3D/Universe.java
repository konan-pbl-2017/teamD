package framework.model3D;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Stack;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Light;
import javax.media.j3d.Locale;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.RenderingAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.VirtualUniverse;
import javax.vecmath.Color3f;



/**
 * �e�A���˃}�b�s���O�A�o���v�}�b�s���O�Ȃǂ������_�����O�ł��� SimpleUniverse
 * �i���ׂĂ̕��̂�z�u������ɁA�K�� compile() ���Ă΂Ȃ��Ƃ����Ȃ����Ƃɒ��ӁI�j
 * @author �V�c����
 *
 */
public class Universe extends VirtualUniverse {
	private Locale locale = null;
	private BranchGroup root = null;
	private BranchGroup additionalRoot = null;
	private ArrayList<Light> lights = new ArrayList<Light>();
	private BackgroundBox skyBox = null;
//	private ArrayList<Background> backgrounds = new ArrayList<Background>();
	private double shadowDepth = 100.0;
	private ArrayList<Object3D> occluders = new ArrayList<Object3D>();
	private ArrayList<BaseObject3D> shadows =  new ArrayList<BaseObject3D>();
	private ArrayList<BaseObject3D> receivers =  new ArrayList<BaseObject3D>();
	private ArrayList<BaseObject3D> extraObjects = new ArrayList<BaseObject3D>();
	
	public Universe() {
		super();
		locale = new Locale(this);
		root = new BranchGroup();
		additionalRoot = new BranchGroup();
		additionalRoot.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		additionalRoot.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		additionalRoot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		shadowDepth = 100.0;
	}
	
	/**
	 * ���f���̃��[�g��ݒ肷��
	 * @param root
	 */
	public void setRoot(BranchGroup root) {
		removeRoot();
		this.root = root;
	}

	/**
	 * ���f���̃��[�g���擾����
	 * @return ���f���̃��[�g�ɑ������� SceneGraph
	 */
	public BranchGroup getRoot() {
		return root;
	}

	/**
	 * ���f���̃��[�g���폜����
	 */
	public void removeRoot() {
		if (root == null) return;
		Enumeration<BranchGroup> branchGroups = locale.getAllBranchGraphs();
		while (branchGroups.hasMoreElements()) {
			if (branchGroups.nextElement() == root) {
				locale.removeBranchGraph(root);
				root = null;
				break;
			}
		}
	}
	
	public void addBranchGraph(BranchGroup g) {
		locale.addBranchGraph(g);
	}

	/**
	 * �I�u�W�F�N�g��z�u����
	 * @param obj �z�u����I�u�W�F�N�g
	 */
	public void placeUnremovable(Node obj) {
		if (!root.isCompiled()) {
			root.addChild(obj);
		} else {
			additionalRoot.addChild(obj);
		}
	}

	/**
	 * �I�u�W�F�N�g��z�u����
	 * @param obj �z�u����I�u�W�F�N�g
	 */
	public void placeUnremovable(Placeable obj) {
		BaseObject3D body = obj.getBody();
		if (body.isReflectionMappingApplied() || body.isBumpMappingApplied()) {
			extraObjects.add(body);
		} else {
			placeUnremovable(obj.getTransformGroupToPlace());
		}
	}
	
	/**
	 * ��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����
	 * @param obj �z�u����I�u�W�F�N�g
	 */
	public void place(Node obj) {
		BranchGroup objRoot;
		if (obj.getParent() != null 
				&& obj.getParent() instanceof BranchGroup) {
			objRoot = (BranchGroup)obj.getParent();
		} else {
			objRoot = new BranchGroup();
			objRoot.setCapability(BranchGroup.ALLOW_DETACH);
			objRoot.addChild(obj);
		}
		additionalRoot.addChild(objRoot);
	}
	
	/**
	 * ��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����
	 * @param obj �z�u����I�u�W�F�N�g
	 */
	public void place(Placeable obj) {
		BaseObject3D body = obj.getBody();
		if (body != null && (body.isReflectionMappingApplied() || body.isBumpMappingApplied())) {
			extraObjects.add(body);
		} else {
			place(obj.getTransformGroupToPlace());
		}
	}
	
	/**
	 * �e�t���Ŕz�u����
	 * @param obj �z�u����I�u�W�F�N�g
	 */
	public void placeAsAnOcculuder(Placeable obj) {
		BaseObject3D body = obj.getBody();
		if (body instanceof Object3D) {
			addShadowOcculuder((Object3D)body);
		} else {
			placeUnremovable(obj);
		}
	}
	
	/**
	 * ���̃I�u�W�F�N�g�̉e��������悤�ɃI�u�W�F�N�g��z�u����
	 * @param obj �z�u����I�u�W�F�N�g
	 */
	public void placeAsAReceiver(Placeable obj) {
		addShadowReceiver(obj.getBody(), new Color3f(0.5f, 0.5f, 0.5f));
	}

	/**
	 * ���̃I�u�W�F�N�g�̉e��������悤�ɃI�u�W�F�N�g��\������
	 * @param obj �\������I�u�W�F�N�g
	 * @param shadowColor �e�̐F
	 */
	public void placeAsAReceiver(Placeable obj, Color3f shadowColor) {
		addShadowReceiver(obj.getBody(), shadowColor);
	}

	/**
	 * �����̒ǉ�
	 * @param light �ǉ��������
	 */
	public void placeLight(Light light) {
		root.addChild(light);
		getLights().add((Light)light.cloneTree());
	}
	
	/**
	 * �����̒ǉ��i�e���j
	 * @param light �ǉ��������
	 * @param shadowDepth �e�̐[��
	 */
	public void placeLight(Light light, double shadowDepth) {
		root.addChild(light);
		getLights().add((Light)light.cloneTree());
		this.shadowDepth = shadowDepth;
	}
	
	/**
	 * �X�J�C�{�b�N�X�̒ǉ�
	 * @param skyBox �ǉ�����X�J�C�{�b�N�X
	 */
	public void placeSkyBox(BackgroundBox skyBox) {
		root.addChild(skyBox);		
		this.skyBox = skyBox;
	}
		
	/**
	 * �I�u�W�F�N�g���\�Ȃ�Ύ�菜��
	 * @param obj ��菜���I�u�W�F�N�g
	 */
	public void displace(Node obj) {
		Node parent = obj.getParent();
		if (parent == root) return;
		if (parent == additionalRoot) {
			additionalRoot.removeChild(obj);
		} else if (parent instanceof BranchGroup 
				&& parent.getCapability(BranchGroup.ALLOW_DETACH) 
				&& parent.getParent() == additionalRoot) {
			additionalRoot.removeChild(parent);
		}
	}
	
	/**
	 * �I�u�W�F�N�g���\�Ȃ�Ύ�菜��
	 * @param obj ��菜���I�u�W�F�N�g
	 */
	public void displace(Placeable obj) {
		BaseObject3D body = obj.getBody();
		if (occluders.contains(body)) {
			occluders.remove(body);
		}
		if (shadows.contains(body)) {
			shadows.remove(body);
		}
		if (receivers.contains(body)) {
			receivers.remove(body);
		}
		if (extraObjects.contains(body)) {
			extraObjects.remove(body);
		}
		displace(obj.getTransformGroupToPlace());
	}
		
	public ArrayList<Light> getLights() {
		return lights;
	}
	
	public BackgroundBox getSkyBox() {
		return skyBox;
	}
	
	private void addShadowOcculuder(Object3D occluder) {
//		occluder.view(this);					// occluder ���g�̕\��
		ShadowVolumeVisitor shadowVolumeVisitor = 
			new ShadowVolumeVisitor(getLights(), shadowDepth);	// ShadowVolume �̐���
		occluder.accept(shadowVolumeVisitor);	// occluder ������ ShadowVolume ���ێ������
		occluders.add(occluder);
	}
	
	private void addShadowReceiver(BaseObject3D receiverObject, Color3f shadowColor) {
		BaseObject3D receiverObjectBody = receiverObject.duplicate();
		
		// ���� BaseObject3D ���e�\���p�ɂ���
		ArrayList<Appearance> aps = receiverObject.getAppearances();
		Material m = new Material();
		m.setDiffuseColor(shadowColor);			// �e�̐F
		m.setAmbientColor(0.0f, 0.0f, 0.0f);
		m.setSpecularColor(0.0f, 0.0f, 0.0f);
		m.setShininess(1.0f);
		RenderingAttributes ra = new RenderingAttributes();
		ra.setStencilEnable(false);									// �X�e���V���o�b�t�@���g��Ȃ�
		ra.setDepthBufferWriteEnable(true);							// �ʏ�ʂ�Z�o�b�t�@�ɏ�������
		ra.setDepthTestFunction(RenderingAttributes.LESS_OR_EQUAL);	// �ʏ�ʂ�Z�o�b�t�@�Ŕ��肷��
		for (int n = 0; n < aps.size(); n++) {
			Appearance ap = aps.get(n);
			ap.setMaterial(m);
			ap.setRenderingAttributes(ra);
		}
		
		shadows.add(receiverObject);
		
		// �������� BaseObject3D ��ʏ�\���p�i�������e�̕����͕`�悵�Ȃ��j�ɂ���
		ArrayList<Appearance> aps2 = receiverObjectBody.getAppearances();
		RenderingAttributes ra2 = new RenderingAttributes();
		ra2.setStencilEnable(true);												// �X�e���V���o�b�t�@���g��
		ra2.setDepthBufferWriteEnable(true);
		ra2.setDepthTestFunction(RenderingAttributes.LESS_OR_EQUAL);			// �e�̏�ɏ㏑������
		ra2.setStencilFunction(RenderingAttributes.GREATER_OR_EQUAL, 0, ~0);	// �X�e���V���o�b�t�@�̒l��1�ȏ�̂Ƃ��`�悵�Ȃ�
		ra2.setStencilOp(RenderingAttributes.STENCIL_REPLACE, 						// �X�e���V���o�b�t�@���N���A����
				RenderingAttributes.STENCIL_REPLACE, 
				RenderingAttributes.STENCIL_REPLACE);
//		TransparencyAttributes ta2 = new TransparencyAttributes();
//		ta2.setTransparencyMode(TransparencyAttributes.BLENDED);
//		ta2.setTransparency(0.0001f);
		for (int n = 0; n < aps2.size(); n++) {
			Appearance ap2 = aps2.get(n);
			ap2.setRenderingAttributes(ra2);
//			ap2.setTransparencyAttributes(ta2);
		}

		receivers.add(receiverObjectBody);
	}
		
	public void update() {
		ShadowVolumeVisitor shadowVolumeVisitor = 
			new ShadowVolumeVisitor();	// ShadowVolume �� �X�V
		for (int n = 0; n < occluders.size(); n++) {
			occluders.get(n).accept(shadowVolumeVisitor);	
		}
	}
	
	/**
	 * Mixed���[�h�����_�����O��p���ĉe�̕`����s��
	 * �i�e��������I�u�W�F�N�g�i���V�[�o�j�̉e�����A�V���h�E�{�����[���j
	 * @param graphicsContext3D
	 */
	public void preRender(IViewer3D viewer) {	
		update();
		
		viewer.update(lights, skyBox);
	}

	/**
	 * Mixed���[�h�����_�����O��p���ĉe�̕`����s��
	 * �i�e��������I�u�W�F�N�g�̉e�ȊO�̕����j
	 * @param graphicsContext3D
	 */
	public void renderField(IViewer3D viewer) {
		// 		������ �����_�����O�͈ȉ��̏��Ԃł������܂������Ȃ� ������
		// ���Ƃ��΁A�V���h�E�{�����[���������_�����O������Ƀ��V�[�o�̉e�ȊO�̕����������_�����O����ƁA
		// BackgroundBox�̕`�悪�Ȃ��������Ȃ��Ă��܂��B
		
		// 1. ���V�[�o�̉e����
		Transform3D trans = new Transform3D();
		for (int n = 0; n < shadows.size(); n++) {
			BaseObject3D obj = shadows.get(n);
			if (obj instanceof Object3D) {
				((Object3D)obj).accept(new ObjectRenderer(viewer));
			} else {
				obj.center.getTransform(trans);
				viewer.setModelTransform(trans);
				viewer.draw(obj);
			}
		}
		
		// 2. ���V�[�o�̉e�ȊO�̕���
		for (int n = 0; n < receivers.size(); n++) {
			BaseObject3D obj = receivers.get(n);			
			if (obj instanceof Object3D) {
				((Object3D)obj).accept(new ObjectRenderer(viewer));
			} else {
				obj.center.getTransform(trans);
				viewer.setModelTransform(trans);
				viewer.draw(obj);
			}
		}
		
		// 3. �V���h�E�{�����[���̕\�ʁiocculuder�����ɐς�ł���j
		// 4. �V���h�E�{�����[���̗��ʁiocculuder�����ɐς�ł���j
		for (int n = 0; n < occluders.size(); n++) {
			Object3D occuluder = occluders.get(n);
			occuluder.accept(new ObjectRenderer(viewer));
		}
		
		// ����ȃ����_�����O���K�v�ƂȂ�I�u�W�F�N�g
		for (int n = 0; n < extraObjects.size(); n++) {
			BaseObject3D extra = extraObjects.get(n);
			if (extra instanceof Object3D) {
				((Object3D)extra).accept(new ObjectRenderer(viewer));
			} else {
				extra.center.getTransform(trans);
				viewer.setModelTransform(trans);
				viewer.draw(extra);
			}
		}
	}
	
	public void postRender(IViewer3D viewer) {
	}

	public void compile() {
		root.compile();
		locale.addBranchGraph(root);
		locale.addBranchGraph(additionalRoot);
	}
	
	private class ObjectRenderer extends ObjectVisitor {
		Stack<Transform3D> transforms = new Stack<Transform3D>();
		IViewer3D viewer = null;
		
		public ObjectRenderer(IViewer3D viewer) {
			Transform3D t = new Transform3D();
			transforms.push(t);
			this.viewer = viewer;
		}
		
		@Override
		public void preVisit(Object3D obj) {
			Transform3D t = new Transform3D(transforms.peek());
			Transform3D t2 = new Transform3D();
			obj.pos.getTransform(t2);
			t.mul(t2);
			obj.rot.getTransform(t2);
			t.mul(t2);
			obj.scale.getTransform(t2);
			t.mul(t2);
			obj.center.getTransform(t2);
			t.mul(t2);
			transforms.push(t);
		}
		
		@Override
		public void postVisit(Object3D obj) {
			Transform3D t = transforms.pop();
			if (!obj.hasChildren()) {
				viewer.setModelTransform(t);
				viewer.draw(obj);
			}
		}		
	}
}
