package framework.game2D;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector2d;

import framework.model3D.BaseObject3D;
import framework.model3D.Placeable;
import framework.physics.Velocity3D;

public abstract class Map2D implements Placeable {

	protected int[][] map;
	protected TransformGroup transformGroup;
	protected int mapWidth;
	protected int mapHeight;
	protected int blockId;
	private ArrayList<MapSprite> stageObjectList = new ArrayList<MapSprite>();
	private ArrayList<SimpleEntry<Position2D, Position2D>> latticeX = new ArrayList<>();
	private ArrayList<SimpleEntry<Position2D, Position2D>> latticeY = new ArrayList<>();

	/**
	 * MazeGame�Ŏg�p����X�e�[�W��Ԃ����ۃ��\�b�h�B
	 * �I�u�W�F�N�g�̐ݒu�̎w���0:�^�C���A1:�u���b�N�Őݒu����B
	 * @return	2�����z��Ŏw�肵���X�e�[�W�̔z��
	 */
	public abstract int[][] createMap();
	
	/**
	 * @param spriteImages �}�b�v�Ŏg�p����X�v���C�g�̃C���[�W�t�@�C���̔z��
	 * @param blockId ���̒l�ȏ��id�����X�v���C�g���u���b�N�i��Q���j�Ƃ݂Ȃ�
	 */
	public Map2D(String[] spriteImages, int blockId) {
		transformGroup = new TransformGroup();
		this.blockId = blockId;
		
		// �}�b�v�̐���
		map = createMap();
		
		// �}�b�v�𐶐�����ۂɔz�u��_�ƂȂ���W�_�̕ϐ�
		double cX = 0.0;
		double cY = 0.0;
		
		// 2�����z��ŕ\���ꂽ�}�b�v����^�C���A�u���b�N�̃C���X�^���X�����A�X�e�[�W���\�����郊�X�g�ɒǉ����Ă����B
		// �z�u�ʒu�͔z�u��_���畝2.0�A����2.0�̃I�u�W�F�N�g�𐶐�����B
		// �Ⴆ��(2.0 2.0�j�̈ʒu�ɐ�������ꍇ��(2.0,2.0)(4.0,2.0)(2.0,4.0)(4.0,4.0)�̂���4�_���u���b�N�̊p�ƂȂ�
		//�@�������ɏՓ˔���ȂǃQ�[���ɂ����鏈�����s���Ă���B
		for (int i = 0; i < map[0].length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (map[map.length - 1 - j][i] < blockId) {
					Tile tile = new Tile(spriteImages[map[map.length - 1 - j][i]]);
					tile.setMapPositonX(i);
					tile.setMapPositonY(map.length - 1 - j);					
					tile.setPosition(cX + i * 2.0, cY + j * 2.0, -1.0);
					tile.setCollisionRadius(1.0);
					stageObjectList.add(tile);
					transformGroup.addChild(tile.getTransformGroupToPlace());
					// �i�q���𐶐�
					if (i > 0) {
						if (map[map.length - 1 - j][i - 1] < blockId) {
							latticeX.add(new SimpleEntry<Position2D, Position2D>(
									new Position2D(cX + (i - 1) * 2.0, cY + j * 2.0), 
									new Position2D(cX + i * 2.0, cY + j * 2.0)));
						}
					}
					if (j > 0) {
						if (map[map.length - 1 - (j - 1)][i] < blockId) {
							latticeY.add(new SimpleEntry<Position2D, Position2D>(
									new Position2D(cX + i * 2.0, cY + (j - 1) * 2.0), 
									new Position2D(cX + i * 2.0, cY + j * 2.0)));
						}
					}
				} else {
					Block block = new Block(spriteImages[map[map.length - 1 - j][i]]);
					block.setMapPositonX(i);
					block.setMapPositonY(map.length - 1 - j);					
					block.setPosition(cX + i * 2.0, cY + j * 2.0, -1.0);
					block.setCollisionRadius(1.0);
					stageObjectList.add(block);
					transformGroup.addChild(block.getTransformGroupToPlace());
				}
			}
		}
		// �}�b�v�̒��S�ɃJ���������~����悤�Ƀ}�b�v�̕��E�������v�Z����B
		mapWidth = map[0].length * 2;
		mapHeight = map.length * 2;		
	}

	@Override
	public BaseObject3D getBody() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransformGroup getTransformGroupToPlace() {
		// TODO Auto-generated method stub
		return transformGroup;
	}

	/**
	 * �}�b�v�̕���Ԃ�
	 * 
	 * @return �}�b�v�̕�
	 */
	public int getMapWidth() {
		return mapWidth;
	}

	/**
	 * �}�b�v�̕���mapWidth�Őݒ肷��
	 * 
	 * @param mapWidth
	 */
	protected void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	/**
	 * �}�b�v�̍�����Ԃ�
	 * 
	 * @return �}�b�v�̍���
	 */
	public int getMapHeight() {
		return mapHeight;
	}

	/**
	 * �}�b�v�̍�����mapHeight�Őݒ肷��
	 * 
	 * @param mapHeight
	 */
	protected void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}

	/**
	 * MazeGame�̃X�e�[�W���\������I�u�W�F�N�g�����������X�g��Ԃ�
	 * 
	 * @return stageObjectList --- �^�C����u���b�N�̃C���X�^���X�����������X�g
	 */
	public ArrayList<MapSprite> getStageObjectList() {
		return stageObjectList;
	}

	/**
	 * MazeGame�̃X�e�[�W���\������I�u�W�F�N�g�����������X�g��stageObjectList�Őݒ肷��
	 * 
	 * @param stageObjectList
	 */
	public void setStageObjectList(ArrayList<MapSprite> stageObjectList) {
		this.stageObjectList = stageObjectList;
	}
	

	/**
	 * �i�q����̍ł��߂��_�� sprite �̈ʒu�����킹��(�}�b�v�ɑ΂���Փˉ�������)
	 * @param sprite �Փ˂��镨��
	 */
	public void collisionResponse(Sprite sprite) {
		Position2D p = sprite.getPosition();
		Position2D nearest = null;
		double distance = -1;
		for (int i = 0; i < latticeX.size(); i++) {
			Position2D p1 = latticeX.get(i).getKey();
			Position2D p2 = latticeX.get(i).getValue();
			Position2D h = null;
			if (p.getX() < p1.getX()) {
				h = new Position2D(p1.getX(), p1.getY());
			} else if (p.getX() <= p2.getX()) {
				h = new Position2D(p.getX(), p1.getY());
			} else {
				h = new Position2D(p2.getX(), p1.getY());
			}
			Vector2d v = h.getVector2d();
			v.sub(p.getVector2d());
			double d = v.length();
			if (distance < 0 || distance > d) {
				distance = d;
				nearest = h;
			}
		}
		for (int i = 0; i < latticeY.size(); i++) {
			Position2D p1 = latticeY.get(i).getKey();
			Position2D p2 = latticeY.get(i).getValue();
			Position2D h = null;
			if (p.getY() < p1.getY()) {
				h = new Position2D(p1.getX(), p1.getY());
			} else if (p.getY() <= p2.getY()) {
				h = new Position2D(p1.getX(), p.getY());
			} else {
				h = new Position2D(p1.getX(), p2.getY());
			}
			Vector2d v = h.getVector2d();
			v.sub(p.getVector2d());
			double d = v.length();
			if (distance < 0 || distance > d) {
				distance = d;
				nearest = h;
			}
		}
		if (distance > 0) {
			sprite.setPosition(nearest);
			sprite.setVelocity(new Velocity2D());
		}
	}

	public Position2D getNeighborGridPoint(Sprite sprite) {
		// �ۂߌ덷�����p�ϐ��̐���
		int spritePositionX = (int)(sprite.getPosition().getX() * 50.0 + 0.5);
		int spritePositionY = (int)(sprite.getPosition().getY() * 50.0 + 0.5);
		if (spritePositionX % 100 == 0 && spritePositionY % 100 == 0) {
			return new Position2D(((double)spritePositionX) / 50.0, ((double)spritePositionY) / 50.0);
		}
		return null;
	}


	public MapCollisionResult checkCollision(Sprite mazeSprite) {
			MapCollisionResult collisionResult = new MapCollisionResult();
		
		// �Փ˔��莞��y�����̐[���x�N�g���̌v�Z�p�̕ϐ�
		Vector2d vecX1;
		Vector2d vecX2;
		Vector2d vecY1;
		Vector2d vecY2;
		
		// �Փ˔��肪���������ɃX�v���C�g���u���b�N�ɂ߂荞�񂾐[����\��2�����x�N�g��
		Vector2d collisionVectorX = new Vector2d();
		Vector2d collisionVectorY = new Vector2d();
		
		

		
		// CollisitonX,CollisitionY�̌덷�C���p�ϐ��i������2�ʂ���؂�̂āj
		double corectionX;
		double corectionY;
		
		// �X�v���C�g�̍��W�l��BigDecimal�Ŋۂߌ덷�����i�����_��3�ʂ���؂�̂āj���s���Adouble�^�̕ϐ��ɕϊ����A�������B
		// �����Ő錾����ϐ��̒l�̓X�v���C�g�̊p��4�_�̍��W����������B		
		double spriteMinX = new BigDecimal(mazeSprite.getPosition().getX()).setScale(2, BigDecimal.ROUND_HALF_EVEN ).doubleValue();
		double spriteMaxX = new BigDecimal(mazeSprite.getPosition().getX() + 2.0).setScale(2, BigDecimal.ROUND_HALF_EVEN ).doubleValue();
		double spriteMinY = new BigDecimal(mazeSprite.getPosition().getY()).setScale(2, BigDecimal.ROUND_HALF_EVEN ).doubleValue();
		double spriteMaxY = new BigDecimal(mazeSprite.getPosition().getY() + 2.0).setScale(2, BigDecimal.ROUND_HALF_EVEN ).doubleValue();
		
//		double spriteMinX = mazeSprite.getPosition().getX();
//		double spriteMaxX = mazeSprite.getPosition().getX() + 2.0;
//		double spriteMinY = mazeSprite.getPosition().getY();
//		double spriteMaxY = mazeSprite.getPosition().getY() + 2.0;
		
		// MageGame���\������I�u�W�F�N�g�Ƃ̏Փ˔���
		for(int i = 0; i < getStageObjectList().size(); i++){
			if (getStageObjectList().get(i) instanceof Block) {
				// �ۂߌ덷�����B������2�ʂ�؂�̂āB
				double blockMinX = new BigDecimal(getStageObjectList().get(i).getPosition().getX()).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
				double blockMaxX = new BigDecimal(getStageObjectList().get(i).getPosition().getX() + 2.0).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
				double blockMinY = new BigDecimal(getStageObjectList().get(i).getPosition().getY()).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
				double blockMaxY = new BigDecimal(getStageObjectList().get(i).getPosition().getY() + 2.0).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();

				
				
				
				///////////////////////////////////////////////////////////////////////////////////////
				//
				//	�u���b�N��2�p���X�v���C�g��2�̊p�Əd�Ȃ�ꍇ�i�����͊�j
				//
				//	3-----------4
				//	|			|
				//	|			|
				//	|			|
				//	|			|
				//	|			|
				//	2-----------1
				//
				///////////////////////////////////////////////////////////////////////////////////////
				
				// ��������Փ˔���̎n�܂�iif���̎n�܂�j
				if((spriteMinX <= blockMinX && spriteMaxX >= blockMaxX)
					|| (spriteMinX >= blockMinX && spriteMaxX <= blockMaxX)){
					if(spriteMaxY > blockMinY && spriteMaxY < blockMaxY){
//						System.out.println("��_1����ӓ��m�i�X�v���C�g�̐i�s������������j�ɂ��Փ˔�����s�������I");
						
						collisionResult.setCheckColision(true);
						
						// �Փ˔��莞��x�����̐[���x�N�g��collisionVectorX�̌v�Z
						vecX1 = new Vector2d(blockMinX, blockMinY);		//	2
						vecX2 = new Vector2d(blockMaxX, blockMinY);		//	1
						
						// �Փ˔��莞��y�����̐[���x�N�g��collisionVectorY�̌v�Z
						vecY1 = new Vector2d(spriteMaxX, spriteMaxY);	//	�X�v���C�g���W
						vecY2 = new Vector2d(blockMaxX, blockMinY);		//	1
	
						collisionVectorX.sub(vecX1, vecX2);
						collisionVectorY.sub(vecY1, vecY2);
						
						// �덷�C���i�l��������2�ȍ~�؂�̂āj
						corectionX = new BigDecimal(collisionVectorX.getX()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						corectionY = new BigDecimal(collisionVectorY.getY()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						
						collisionVectorX.set(corectionX, 0.0);
						collisionVectorY.set(0.0, corectionY);
						
						collisionResult.setColisionBackVelocity(collisionBackVelocity(collisionVectorX,collisionVectorY,mazeSprite.getVelocity()));
						break;
					}
					else if(spriteMinY < blockMaxY && spriteMinY > blockMinY){
//						System.out.println("��_3����ӓ��m�i�X�v���C�g�̐i�s�������������j�ɂ��Փ˔�����s�������I");

						collisionResult.setCheckColision(true);
						
						// �Փ˔��莞��x�����̐[���x�N�g��collisionVectorX�̌v�Z
						vecX1 = new Vector2d(blockMaxX, blockMaxY);		//	4
						vecX2 = new Vector2d(blockMinX, blockMaxY);		//	3
						
						// �Փ˔��莞��y�����̐[���x�N�g��collisionVectorY�̌v�Z
						vecY1 = new Vector2d(spriteMinX, spriteMinY);	//	�X�v���C�g���W	
						vecY2 = new Vector2d(blockMinX, blockMaxY);		//	3
	
						collisionVectorX.sub(vecX1, vecX2);
						collisionVectorY.sub(vecY1, vecY2);
						
						// �덷�C���i�l��������2�ȍ~�؂�̂āj
						corectionX = new BigDecimal(collisionVectorX.getX()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						corectionY = new BigDecimal(collisionVectorY.getY()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						
						collisionVectorX.set(corectionX, 0.0);
						collisionVectorY.set(0.0, corectionY);
						
						collisionResult.setColisionBackVelocity(collisionBackVelocity(collisionVectorX,collisionVectorY,mazeSprite.getVelocity()));

						break;
					}
				}
				else if((spriteMinY <= blockMinY && spriteMaxY >= blockMaxY)
						|| (spriteMinY >= blockMinY && spriteMaxY <= blockMaxY)){
					if(spriteMaxX > blockMinX && spriteMaxX < blockMaxX){
//						System.out.println("��_2����ӓ��m�i�X�v���C�g�̐i�s�������E�����j�ɂ��Փ˔�����s�������I");

						collisionResult.setCheckColision(true);
						
						// �Փ˔��莞��x�����̐[���x�N�g��collisionVectorX�̌v�Z
						vecX1 = new Vector2d(spriteMaxX, spriteMinY);		
						vecX2 = new Vector2d(blockMinX, blockMinY);		//	2
						
						// �Փ˔��莞��y�����̐[���x�N�g��collisionVectorY�̌v�Z
						vecY1 = new Vector2d(blockMinX, blockMaxY);		//	3
						vecY2 = new Vector2d(blockMinX, blockMinY);		//	2
	
						collisionVectorX.sub(vecX1, vecX2);
						collisionVectorY.sub(vecY1, vecY2);
						
						// �덷�C���i�l��������2�ȍ~�؂�̂āj
						corectionX = new BigDecimal(collisionVectorX.getX()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						corectionY = new BigDecimal(collisionVectorY.getY()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						
						collisionVectorX.set(corectionX, 0.0);
						collisionVectorY.set(0.0, corectionY);
						
						collisionResult.setColisionBackVelocity(collisionBackVelocity(collisionVectorX,collisionVectorY,mazeSprite.getVelocity()));

						break;
					}
					else if(spriteMinX < blockMaxX && spriteMinX > blockMinX){
//						System.out.println("��_4����ӓ��m�i�X�v���C�g�̐i�s�������������j�ɂ��Փ˔�����s�������I");
						
						collisionResult.setCheckColision(true);
						
						// �Փ˔��莞��x�����̐[���x�N�g��collisionVectorX�̌v�Z
						vecX1 = new Vector2d(spriteMinX, spriteMaxY);		
						vecX2 = new Vector2d(blockMaxX, blockMaxY);		//	4
						
						// �Փ˔��莞��y�����̐[���x�N�g��collisionVectorY�̌v�Z
						vecY1 = new Vector2d(blockMaxX, blockMinY);		//	1
						vecY2 = new Vector2d(blockMaxX, blockMaxY);		//	4
	
						collisionVectorX.sub(vecX1, vecX2);
						collisionVectorY.sub(vecY1, vecY2);
						
						// �덷�C���i�l��������2�ȍ~�؂�̂āj
						corectionX = new BigDecimal(collisionVectorX.getX()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						corectionY = new BigDecimal(collisionVectorY.getY()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						
						collisionVectorX.set(corectionX, 0.0);
						collisionVectorY.set(0.0, corectionY);
						
						collisionResult.setColisionBackVelocity(collisionBackVelocity(collisionVectorX,collisionVectorY,mazeSprite.getVelocity()));

						break;
					}
				}


				
				//////////////////////////////////////////////////////////////////////////////////////
				//
				//	1.�Փ˂����ۂ̃C���[�W�B	O�͏Փ˔��莞�̐[����\���x�N�g�����v�Z�����鎞�̊�Ƃ��čl���Ă��܂��B
				//
				//	+---------------+
				//	|	�u���b�N		|	
				//	|				|
				//	|		+-------+---+
				//	|		|		|	|
				//	|		|		|	|
				//	+-------+-------O	|	
				//			|			|
				//			|�@�v���C���[	|
				//			+-----------+
				//				
				/////////////////////////////////////////////////////////////////////////////////////
				
				else if (spriteMinX > blockMinX && spriteMinX < blockMaxX && spriteMaxY > blockMinY && spriteMaxY < blockMaxY) {
//						System.out.println("�߂荞�݃p�^�[���@�i�u���b�N�ƃX�v���C�g�d�Ȃ肪�u���b�N�̉E���j�ɂ��Փ˔�����s�������I");

					collisionResult.setCheckColision(true);
					
					// �Փ˔��莞��x�����̐[���x�N�g��collisionVectorX�̌v�Z
					vecX1 = new Vector2d(spriteMinX, blockMinY);
					vecX2 = new Vector2d(blockMaxX, blockMinY);
					
					// �Փ˔��莞��y�����̐[���x�N�g��collisionVectorY�̌v�Z
					vecY1 = new Vector2d(blockMaxX, spriteMaxY);
					vecY2 = new Vector2d(blockMaxX, blockMinY);

					collisionVectorX.sub(vecX1, vecX2);
					collisionVectorY.sub(vecY1, vecY2);
					
					// �덷�C���i�l��������2�ȍ~�؂�̂āj
					corectionX = new BigDecimal(collisionVectorX.getX()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					corectionY = new BigDecimal(collisionVectorY.getY()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					
					collisionVectorX.set(corectionX, 0.0);
					collisionVectorY.set(0.0, corectionY);
					collisionResult.setColisionBackVelocity(collisionBackVelocity(collisionVectorX,collisionVectorY,mazeSprite.getVelocity()));
					
//					System.out.println("�@�u���b�N" + i + "�ԖڂƓ���������I");
//					System.out.println("�@�u���b�N" + i + "�Ԗڂ�X���W��" + mazeGround.getStageObjectList().get(i).getPosition().getX()+", Y���W��"+mazeGround.getStageObjectList().get(i).getPosition().getY());
					break;
				}
				
				//////////////////////////////////////////////////////////////////////////////////////
				//
				//	2.�Փ˂����ۂ̃C���[�W�B	O�͏Փ˔��莞�̐[����\���x�N�g�����v�Z�����鎞�̊�Ƃ��čl���Ă��܂��B
				//
				//		+-----------+
				//		|	�u���b�N	|	
				//		|			|
				//	+---+-------+	|
				//	|	|		|	|
				//	|	|		|	|
				//	|	|		|	|
				//	|	O-------+---+
				//	|			|				
				//	|�@ �v���C���[	|			
				//	+-----------+
				//
				//////////////////////////////////////////////////////////////////////////////////////
				
				else if (spriteMaxX > blockMinX && spriteMaxX < blockMaxX && spriteMaxY > blockMinY && spriteMaxY < blockMaxY) {
//						System.out.println("�߂荞�݃p�^�[���A�i�u���b�N�ƃX�v���C�g�d�Ȃ肪�u���b�N�̍����j�ɂ��Փ˔�����s�������I");
					collisionResult.setCheckColision(true);
					
					// �Փ˔��莞��x�����̐[���x�N�g��collisionVectorX�̌v�Z
					vecX1 = new Vector2d(spriteMaxX, blockMinY);
					vecX2 = new Vector2d(blockMinX, blockMinY);
					
					// �Փ˔��莞��y�����̐[���x�N�g��collisionVectorY�̌v�Z
					vecY1 = new Vector2d(blockMinX, spriteMaxY);
					vecY2 = new Vector2d(blockMinX, blockMinY);

					collisionVectorX.sub(vecX1, vecX2);
					collisionVectorY.sub(vecY1, vecY2);

					
					// �덷�C���i�l��������2�ȍ~�؂�̂āj
					corectionX = new BigDecimal(collisionVectorX.getX()).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
					corectionY = new BigDecimal(collisionVectorY.getY()).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
					
					collisionVectorX.set(corectionX, 0.0);
					collisionVectorY.set(0.0, corectionY);
					
					collisionResult.setColisionBackVelocity(collisionBackVelocity(collisionVectorX,collisionVectorY,mazeSprite.getVelocity()));
//					System.out.println("�A�u���b�N" + i + "�ԖڂƓ���������I");
//					System.out.println("�A�u���b�N" + i + "�Ԗڂ�X���W��" + mazeGround.getStageObjectList().get(i).getPosition().getX()+", Y���W��"+mazeGround.getStageObjectList().get(i).getPosition().getY());
					break;
				}
				

	
				/////////////////////////////////////////////////////////////////////////////////
				//
				//	3.�Փ˂����ۂ̃C���[�W�B	O�̓x�N�g���̌v�Z���̊�Ƃ��čl���Ă��܂��B
				//
				//	+---------------+
				//	|	�v���C���[		|	
				//	|				|
				//	|		O-------+---+
				//	|		|		|	|
				//	|		|		|	|
				//	+-------+-------+	|	
				//			|			|
				//			|	�u���b�N	|
				//			+-----------+
				//				
				/////////////////////////////////////////////////////////////////////////////////

				
				else if (spriteMaxX > blockMinX && spriteMaxX < blockMaxX && spriteMinY > blockMinY && spriteMinY < blockMaxY) {
//						System.out.println("�߂荞�݃p�^�[���B�i�u���b�N�ƃX�v���C�g�d�Ȃ肪�u���b�N�̍���j�ɂ��Փ˔�����s�������I");
					collisionResult.setCheckColision(true);
					
					// �Փ˔��莞��x�����̐[���x�N�g��collisionVectorX�̌v�Z
					vecX1 = new Vector2d(spriteMaxX, blockMaxY);
					vecX2 = new Vector2d(blockMinX, blockMaxY);
					
					// �Փ˔��莞��y�����̐[���x�N�g��collisionVectorY�̌v�Z
					vecY1 = new Vector2d(blockMinX, spriteMinY);
					vecY2 = new Vector2d(blockMinX, blockMaxY);

					collisionVectorX.sub(vecX1, vecX2);
					collisionVectorY.sub(vecY1, vecY2);
					
					// �덷�C���i�l��������2�ȍ~�؂�̂āj
					corectionX = new BigDecimal(collisionVectorX.getX()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					corectionY = new BigDecimal(collisionVectorY.getY()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					
					collisionVectorX.set(corectionX, 0.0);
					collisionVectorY.set(0.0, corectionY);
					
					collisionResult.setColisionBackVelocity(collisionBackVelocity(collisionVectorX,collisionVectorY,mazeSprite.getVelocity()));
					
//					System.out.println("�B�u���b�N" + i + "�ԖڂƓ���������I");
//					System.out.println("�B�u���b�N" + i + "�Ԗڂ�X���W��" + mazeGround.getStageObjectList().get(i).getPosition().getX()+", Y���W��"+mazeGround.getStageObjectList().get(i).getPosition().getY());
					break;
				}



				/////////////////////////////////////////////////////////////////////////////////
				//
				//	4.�Փ˂����ۂ̃C���[�W�B	O�̓x�N�g���̌v�Z���̊�Ƃ��čl���Ă��܂��B
				//
				//		+-----------+
				//		|�@�v���C���[	|	
				//		|			|
				//	+---+-------O	|
				//	|	|		|	|
				//	|	|		|	|
				//	|	|		|	|
				//	|	+-------+---+
				//	|			|				
				//	|�@�u���b�N		|			
				//	+-----------+
				//
				/////////////////////////////////////////////////////////////////////////////////

				else if (spriteMinX > blockMinX && spriteMinX < blockMaxX && spriteMinY > blockMinY && spriteMinY < blockMaxY) {
//						System.out.println("�߂荞�݃p�^�[���C�i�u���b�N�ƃX�v���C�g�d�Ȃ肪�u���b�N�̉E��j�ɂ��Փ˔�����s�������I");
					collisionResult.setCheckColision(true);
					
					// �Փ˔��莞��x�����̐[���x�N�g��collisionVectorX�̌v�Z
					vecX1 = new Vector2d(spriteMinX, blockMaxY);
					vecX2 = new Vector2d(blockMaxX, blockMaxY);
					
					// �Փ˔��莞��y�����̐[���x�N�g��collisionVectorY�̌v�Z
					vecY1 = new Vector2d(blockMaxX, spriteMinY);
					vecY2 = new Vector2d(blockMaxX, blockMaxY);

					collisionVectorX.sub(vecX1, vecX2);
					collisionVectorY.sub(vecY1, vecY2);
					
					// �덷�C���i�l��������2�ȍ~�؂�̂āj
					corectionX = new BigDecimal(collisionVectorX.getX()).setScale(2, BigDecimal.ROUND_HALF_EVEN ).doubleValue();
					corectionY = new BigDecimal(collisionVectorY.getY()).setScale(2, BigDecimal.ROUND_HALF_EVEN ).doubleValue();
					
					collisionVectorX.set(corectionX, 0.0);
					collisionVectorY.set(0.0, corectionY);
					
					collisionResult.setColisionBackVelocity(collisionBackVelocity(collisionVectorX,collisionVectorY,mazeSprite.getVelocity()));
					
//					System.out.println("�C�u���b�N" + i + "�ԖڂƓ���������I");
//					System.out.println("�C�u���b�N" + i + "�Ԗڂ�X���W��" + mazeGround.getStageObjectList().get(i).getPosition().getX()+", Y���W��"+mazeGround.getStageObjectList().get(i).getPosition().getY());
					break;
				}
			}
		}
		return collisionResult;
	}

	/**
	 * �Փ˂��Ă����ۂɐ�����A�t�̑��xbackVelocity��Ԃ�
	 * 
	 * @param collisionVectorX
	 *            �Փ˔��肵�����̐[����\��x�����x�N�g��
	 * @param collisionVectorY
	 *            �Փ˔��肵�����̐[����\��y�����x�N�g��
	 * @param objectVelocity
	 *            �I�u�W�F�N�g�̌��݂̑��x
	 *            
	 * @return �Փ˂��Ă����I�u�W�F�N�g�ɑ΂��鑬�x�x�N�g��
	 */
	public Velocity2D collisionBackVelocity(Vector2d collisionVectorX, Vector2d collisionVectorY, Velocity2D objectVelocity) {
		Velocity2D collisionBackVel = new Velocity2D();
		
		if (objectVelocity.getY() >= 0) {
			if (collisionVectorX.length() > collisionVectorY.length()) {
				if (collisionVectorX.getX() > 0 && collisionVectorY.getY() > 0) {
					collisionBackVel.set(0.0, -1.0 * collisionVectorY.length());
					return collisionBackVel;
				} 
				else if (collisionVectorX.getX() > 0 && collisionVectorY.getY() < 0) {
					collisionBackVel.set(-1.0 * collisionVectorY.length(), 0.0);
					return collisionBackVel;
				} 
				else if (collisionVectorX.getX() < 0 && collisionVectorY.getY() > 0) {
					collisionBackVel.set(0.0, -1.0 * collisionVectorY.length());
					return collisionBackVel;
				} 
				else if (collisionVectorX.getX() < 0 && collisionVectorY.getY() < 0) {
					collisionBackVel.set(-1.0 * collisionVectorY.length(), 0.0);
					return collisionBackVel;
				}
			} 
			else if (collisionVectorX.length() < collisionVectorY.length()) {
				if (collisionVectorX.getX() > 0 && collisionVectorY.getY() > 0) {
					collisionBackVel.set(-1.0 * collisionVectorX.length(), 0.0);
					return collisionBackVel;
				}
				else if (collisionVectorX.getX() > 0 && collisionVectorY.getY() < 0) {
					collisionBackVel.set(-1.0 * collisionVectorX.length(), 0.0);
					return collisionBackVel;
				}
				else if (collisionVectorX.getX() < 0 && collisionVectorY.getY() > 0) {
					collisionBackVel.set(collisionVectorX.length(), 0.0);
					return collisionBackVel;
				}
				else if (collisionVectorX.getX() < 0 && collisionVectorY.getY() < 0) {
					collisionBackVel.set(1.0 * collisionVectorX.length(), 0.0);
					return collisionBackVel;
				}
			}
			collisionBackVel.set(-1.0	* collisionVectorX.getX(), 1.0	* objectVelocity.getY());
			return collisionBackVel;
		}
	
		else if (objectVelocity.getY() <= 0) {
			if (collisionVectorX.length() > collisionVectorY.length()) {
				if (collisionVectorX.getX() > 0 && collisionVectorY.getY() < 0) {
					collisionBackVel.set(0.0, 1.0 * collisionVectorY.length());
					return collisionBackVel;
				} 
				else if (collisionVectorX.getX() < 0 && collisionVectorY.getY() < 0) {
					collisionBackVel.set(0.0, 1.0 * collisionVectorY.length());
					return collisionBackVel;
				}
			} 
			else if (collisionVectorX.length() < collisionVectorY.length()) {
				if (collisionVectorX.getX() > 0 && collisionVectorY.getY() > 0) {
					collisionBackVel.set(-1.0 * collisionVectorX.length(), 0.0);
					return collisionBackVel;
				} 
				else if (collisionVectorX.getX() > 0 && collisionVectorY.getY() < 0) {
					collisionBackVel.set(-1.0 * collisionVectorX.length(), 0.0);
					return collisionBackVel;
				} 
				else if (collisionVectorX.getX() < 0 && collisionVectorY.getY() > 0) {
					collisionBackVel.set(1.0 * collisionVectorX.length(), 0.0);
					return collisionBackVel;
				}
				else if (collisionVectorX.getX() < 0 && collisionVectorY.getY() < 0) {
					collisionBackVel.set(1.0 * collisionVectorX.length(), 0.0);
					return collisionBackVel;
				}
			}
			collisionBackVel.set(-1.0 * objectVelocity.getX(), -1.0	* collisionVectorY.length());
			return collisionBackVel;
		}
	
		collisionBackVel.set(-1.0 * objectVelocity.getX(), -1.0	* objectVelocity.getY());
		return collisionBackVel;
	}
}
