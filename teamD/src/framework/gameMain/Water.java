package framework.gameMain;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DistanceLOD;
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.IndexedTriangleArray;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Switch;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import com.sun.j3d.utils.image.TextureLoader;

import framework.animation.Animation3D;
import framework.animation.PartAnimation;
import framework.model3D.BumpMapGenerator;
import framework.model3D.FresnelsReflectionMapGenerator;
import framework.model3D.Object3D;
import framework.model3D.Position3D;
import framework.model3D.ReflectionMapGenerator;

/**
 * ���ʂ�\������I�u�W�F�N�g�B
 * �g�̓����̓o���v�}�b�s���O���X�N���[�������ĕ\�����Ă���B
 * ���˂ł̓t���l�����ʂ��������Ă���B
 * Java3D�ł́A���_�Ԃ̔��˃x�N�g���͐��`��Ԃł������߂Ă��Ȃ����߁A
 * ���x���グ�邽�߂ɁALOD���g���Đ��ʂ̎��_�ɋ߂������̓��b�V���������ׂ������Ă���B
 * @author Nitta
 *
 */
public class Water extends Animatable {
	private static int DEFAULT_FIRST_LEVEL_MESH_SIZE = 25;
	private static int DEFAULT_SECOND_LEVEL_MESH_SIZE = 1;
	private static int DEFAULT_THIRD_LEVEL_MESH_SIZE = 40;
	
	public Water(double minX, double minZ, double maxX, double maxZ, double height, boolean bUseLOD) {
		this(minX, minZ, maxX, maxZ, height, 0.5f, bUseLOD);
	}
	
	public Water(double minX, double minZ, double maxX, double maxZ, double height, float transparency, boolean bUseLOD) {
		this(minX, minZ, maxX, maxZ, height, transparency, 0.3f, bUseLOD);		
	}
	
	public Water(double minX, double minZ, double maxX, double maxZ, double height, float transparency, float reflection, boolean bUseLOD) {
		this(minX, minZ, maxX, maxZ, height, transparency, reflection, new Color3f(0.2f, 0.5f, 1.0f), bUseLOD);				
	}

	public Water(double minX, double minZ, double maxX, double maxZ, double height, 
			float transparency, float reflection, Color3f waterColor, boolean bUseLOD) {
		this(minX, minZ, maxX, maxZ, height, transparency, reflection, new Color3f(0.2f, 0.5f, 1.0f), 
				DEFAULT_FIRST_LEVEL_MESH_SIZE, DEFAULT_SECOND_LEVEL_MESH_SIZE, DEFAULT_THIRD_LEVEL_MESH_SIZE, bUseLOD);						
	}	
	
	public Water(double minX, double minZ, double maxX, double maxZ, double height, float transparency, float reflection, 
			int firstLevelMeshSize, int secondLevelMeshSize, int thirdLevelMeshSize, boolean bUseLOD) {
		this(minX, minZ, maxX, maxZ, height, transparency, reflection, new Color3f(0.2f, 0.5f, 1.0f), 
				firstLevelMeshSize, secondLevelMeshSize, thirdLevelMeshSize, bUseLOD);						
	}	
	
	public Water(double minX, double minZ, double maxX, double maxZ, double height, float transparency, float reflection, Color3f waterColor, 
			int firstLevelMeshSize, int secondLevelMeshSize, int thirdLevelMeshSize, boolean bUseLOD) {
		super(null, null);
		
		int firstLevelMeshSizeX = firstLevelMeshSize;
		int firstLevelMeshSizeZ = firstLevelMeshSize;
		int secondLevelMeshSizeX = secondLevelMeshSize;
		int secondLevelMeshSizeZ = secondLevelMeshSize;
		int thirdLevelMeshSizeX = thirdLevelMeshSize;
		int thirdLevelMeshSizeZ = thirdLevelMeshSize;
		
		
		// ���ʂ̃W�I���g���̍쐬
		IndexedQuadArray coarseWaterGeometry = 
			new IndexedQuadArray((secondLevelMeshSizeX + 1) * (secondLevelMeshSizeZ + 1), 
				IndexedQuadArray.COORDINATES | IndexedQuadArray.NORMALS, 4 * secondLevelMeshSizeX * secondLevelMeshSizeZ);
		
		IndexedQuadArray fineWaterGeometry = 
			new IndexedQuadArray((secondLevelMeshSizeX * thirdLevelMeshSizeX + 1) * (secondLevelMeshSizeZ * thirdLevelMeshSizeZ + 1), 
				IndexedQuadArray.COORDINATES | IndexedQuadArray.NORMALS, 4 * secondLevelMeshSizeX * thirdLevelMeshSizeX * secondLevelMeshSizeZ * thirdLevelMeshSizeZ);
		
		double unitSizeX1 = (maxX - minX) / firstLevelMeshSizeX;
		double unitSizeZ1 = (maxZ - minZ) / firstLevelMeshSizeZ;
		double unitSizeX2 = unitSizeX1 / secondLevelMeshSizeX;
		double unitSizeZ2 = unitSizeZ1 / secondLevelMeshSizeZ;
		double unitSizeX3 = unitSizeX2 / thirdLevelMeshSizeX;
		double unitSizeZ3 = unitSizeZ2 / thirdLevelMeshSizeZ;
		double baseX = -(maxX - minX) / firstLevelMeshSizeX / 2.0;
		double baseZ = -(maxZ - minZ) / firstLevelMeshSizeZ / 2.0;
		
		// ���A��O���x���� LOD �Ő؂�ւ�
		for (int z2 = 0; z2 < secondLevelMeshSizeZ + 1; z2++) {
			for (int x2 = 0; x2 < secondLevelMeshSizeX + 1; x2++) {
				int indexX2 = x2;
				int indexZ2 = z2;
				int index20 = indexZ2 * secondLevelMeshSizeX + indexX2;
				int index21 = indexZ2 * (secondLevelMeshSizeX + 1) + indexX2;
				int index22 = (indexZ2 + 1) * (secondLevelMeshSizeX + 1) + indexX2;
				double xx2 = baseX + unitSizeX2 * indexX2;
				double zz2 = baseZ + unitSizeZ2 * indexZ2;
				coarseWaterGeometry.setCoordinate(index21, new Point3d(xx2, 0.0, zz2));
				coarseWaterGeometry.setNormal(index21, new Vector3f(0.0f, 1.0f, 0.0f));
				if (indexX2 < secondLevelMeshSizeX && indexZ2 < secondLevelMeshSizeZ) {
					coarseWaterGeometry.setCoordinateIndices(index20 * 4, new int[]{index22, index22 + 1, index21 + 1, index21});
					if (bUseLOD) {
						for (int z3 = 0; z3 < thirdLevelMeshSizeZ + 1; z3++) {
							for (int x3 = 0; x3 < thirdLevelMeshSizeX + 1; x3++) {
								int indexX3 = x2 * thirdLevelMeshSizeX + x3;
								int indexZ3 = z2 * thirdLevelMeshSizeZ + z3;
								int index30 = indexZ3 * (secondLevelMeshSizeX * thirdLevelMeshSizeX) + indexX3;
								int index31 = indexZ3 * (secondLevelMeshSizeX * thirdLevelMeshSizeX + 1) + indexX3;
								int index32 = (indexZ3 + 1) * (secondLevelMeshSizeX * thirdLevelMeshSizeX + 1) + indexX3;
								double xx3 = baseX + unitSizeX3 * indexX3;
								double zz3 = baseZ + unitSizeZ3 * indexZ3;
								fineWaterGeometry.setCoordinate(index31, new Point3d(xx3, 0.0, zz3));
								fineWaterGeometry.setNormal(index31, new Vector3f(0.0f, 1.0f, 0.0f));
								if (indexX3 < secondLevelMeshSizeX * thirdLevelMeshSizeX 
										&& indexZ3 < secondLevelMeshSizeZ * thirdLevelMeshSizeZ) {
									fineWaterGeometry.setCoordinateIndices(index30 * 4, new int[]{index32, index32 + 1, index31 + 1, index31});
								}					
							}
						}
					}
				}					
			}
		}
		
		// ��ꃌ�x���� Object3D �̏W���ō\��
		Object3D children[] = new Object3D[firstLevelMeshSizeX * firstLevelMeshSizeZ];
		TextureLoader loaderWater = new TextureLoader("data\\texture\\waternormalMap.jpg", 
				TextureLoader.BY_REFERENCE, 
				null);
		Texture textureWater = loaderWater.getTexture();		
		// ���ʂ̕\�ʑ����̍쐬�i�S�̂ŋ��L����j
		Appearance a = new Appearance();
		a.setCapability(Appearance.ALLOW_TEXTURE_UNIT_STATE_READ);
		a.setCapability(Appearance.ALLOW_TEXTURE_UNIT_STATE_WRITE);
		Material m1 = new Material();
		m1.setDiffuseColor(waterColor);	// ���̐F
		m1.setSpecularColor(0.0f, 0.0f, 0.0f);
		a.setMaterial(m1);
		ColoringAttributes ca = new ColoringAttributes();
		ca.setShadeModel(ColoringAttributes.NICEST);
		a.setColoringAttributes(ca);

		TransparencyAttributes ta = new TransparencyAttributes();
		ta.setTransparency(transparency);	// �����x
		ta.setTransparencyMode(TransparencyAttributes.BLENDED);
		a.setTransparencyAttributes(ta);
		// �g���\�ʑ����i�S�̂ŋ��L����j
		TexCoordGeneration tcg = new TexCoordGeneration(TexCoordGeneration.OBJECT_LINEAR, TexCoordGeneration.TEXTURE_COORDINATE_3);
		tcg.setPlaneS(new Vector4f(0.1f, 0.0f, 0.0f, 0.0f));
		tcg.setPlaneT(new Vector4f(0.0f, 0.0f, 0.1f, 0.0f));
		BumpMapGenerator bumpMapGenerator = new BumpMapGenerator(textureWater, tcg, true);	// �e�N�X�`�����j�b�g��1�g�p
		FresnelsReflectionMapGenerator fresnelsReflectionMapGenerator = 
			new FresnelsReflectionMapGenerator(new Color4f(reflection, reflection, reflection, 1.0f), true);	// �e�N�X�`�����j�b�g��2�g�p		
		
		for (int z1 = 0; z1 < firstLevelMeshSizeZ; z1++) {
			for (int x1 = 0; x1 < firstLevelMeshSizeX; x1++) {
				double xx1 = minX + (maxX - minX) / firstLevelMeshSizeX * x1;
				double zz1 = minZ + (maxZ - minZ) / firstLevelMeshSizeZ * z1;
				Object3D waterSurfaceUnit;
				if (bUseLOD) {
					DistanceLOD lod = new DistanceLOD();
					Switch surfaceSwitch = new Switch();
					surfaceSwitch.addChild(new Shape3D(fineWaterGeometry, a));
					surfaceSwitch.addChild(new Shape3D(coarseWaterGeometry, a));
					lod.addSwitch(surfaceSwitch);
					lod.setDistance(0, unitSizeX1 / 2.0);
					lod.setPosition(new Point3f(0.0f, 0.0f, 0.0f));
					waterSurfaceUnit = new Object3D("waterUnit", lod);
				} else {
					waterSurfaceUnit = new Object3D("waterUnit", new Shape3D(coarseWaterGeometry, a));					
				}
				waterSurfaceUnit.apply(new Position3D(xx1, height, zz1), false);
				
				// �g���\�ʑ�����ݒ�
				waterSurfaceUnit.setBumpMapping(bumpMapGenerator);
				waterSurfaceUnit.setReflectionMapping(fresnelsReflectionMapGenerator);		
				
				children[z1 * firstLevelMeshSizeX + x1] = waterSurfaceUnit;
			}
		}		
		
		// �S�̃I�u�W�F�N�g���\��
		Object3D waterSurface = new Object3D("water", children);
		
		body = waterSurface;
		
		// �A�j���[�V�����̍쐬
		Animation3D waveAnimation = new Animation3D();
		PartAnimation pa = new PartAnimation("waterUnit", 0);
		pa.addTexture(0L, new Position3D(0.0, 0.0, 0.0));
		pa.addTexture(10000L, new Position3D(1.0, -1.0, 0.0));
		waveAnimation.addPartAnimation(pa);
		animation = waveAnimation;
	}

	@Override
	public void onEndAnimation() {
	}
}
