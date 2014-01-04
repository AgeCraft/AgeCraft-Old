package elcon.mods.agecraft.core.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.Tools;

public class EntityArrow extends Entity implements IProjectile {

	public int tileX = -1;
	public int tileY = -1;
	public int tileZ = -1;
	public int inTile;
	public int inData;
	public boolean inGround;
	public int canBePickedUp;
	public int arrowShake;
	public Entity shootingEntity;
	public int ticksInGround;
	public int ticksInAir;
	public double damage = 1.0D;
	public int knockbackStrength;
	public int material;
	public int rodMaterial;

	public EntityArrow(World world) {
		super(world);
		renderDistanceWeight = 10.0D;
		setSize(0.5F, 0.5F);
	}

	public EntityArrow(World world, double x, double y, double z) {
		this(world);
		setPosition(x, y, z);
		yOffset = 0.0F;
	}

	public EntityArrow(World world, EntityLivingBase entityShooter, EntityLivingBase entity, float f1, float f2) {
		this(world);
		shootingEntity = entityShooter;
		if(entityShooter instanceof EntityPlayer) {
			canBePickedUp = 1;
		}
		posY = entityShooter.posY + (double) entityShooter.getEyeHeight() - 0.10000000149011612D;
		double x = entity.posX - entityShooter.posX;
		double y = entity.boundingBox.minY + (double) (entity.height / 3.0F) - posY;
		double z = entity.posZ - entityShooter.posZ;
		double dist = (double) MathHelper.sqrt_double(x * x + z * z);
		if(dist >= 1.0E-7D) {
			float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
			float pitch = (float) (-(Math.atan2(y, dist) * 180.0D / Math.PI));
			double dx = x / dist;
			double dz = z / dist;
			setLocationAndAngles(entityShooter.posX + dx, posY, entityShooter.posZ + dz, yaw, pitch);
			yOffset = 0.0F;
			float offsetY = (float) dist * 0.2F;
			setThrowableHeading(x, y + (double) offsetY, z, f1, f2);
		}
	}

	public EntityArrow(World world, EntityLivingBase entityShooter, float par3) {
		this(world);
		shootingEntity = entityShooter;
		if(entityShooter instanceof EntityPlayer) {
			canBePickedUp = 1;
		}
		setLocationAndAngles(entityShooter.posX, entityShooter.posY + (double) entityShooter.getEyeHeight(), entityShooter.posZ, entityShooter.rotationYaw, entityShooter.rotationPitch);
		posX -= (double) (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		posY -= 0.10000000149011612D;
		posZ -= (double) (MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		motionX = (double) (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionZ = (double) (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionY = (double) (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI));
		setThrowableHeading(motionX, motionY, motionZ, par3 * 1.5F, 1.0F);
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		dataWatcher.addObject(17, Short.valueOf((short) 0));
		dataWatcher.addObject(18, Short.valueOf((short) 0));
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, (double) f) * 180.0D / Math.PI);
		}
		int i = worldObj.getBlockId(tileX, tileY, tileZ);
		if(i > 0) {
			Block.blocksList[i].setBlockBoundsBasedOnState(worldObj, tileX, tileY, tileZ);
			AxisAlignedBB axisAlignedBB = Block.blocksList[i].getCollisionBoundingBoxFromPool(worldObj, tileX, tileY, tileZ);
			if(axisAlignedBB != null && axisAlignedBB.isVecInside(worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ))) {
				inGround = true;
			}
		}
		if(arrowShake > 0) {
			arrowShake--;
		}
		if(inGround) {
			int j = worldObj.getBlockId(tileX, tileY, tileZ);
			int k = worldObj.getBlockMetadata(tileX, tileY, tileZ);
			if(j == inTile && k == inData) {
				ticksInGround++;
				if(ticksInGround == 1200) {
					setDead();
				}
			} else {
				inGround = false;
				motionX *= (double) (rand.nextFloat() * 0.2F);
				motionY *= (double) (rand.nextFloat() * 0.2F);
				motionZ *= (double) (rand.nextFloat() * 0.2F);
				ticksInGround = 0;
				ticksInAir = 0;
			}
		} else {
			ticksInAir++;
			Vec3 vec3 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
			Vec3 vec31 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks_do_do(vec3, vec31, false, true);
			vec3 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
			vec31 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
			if(movingobjectposition != null) {
				vec31 = worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			}
			Entity entity = null;
			List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;
			int l;
			float f1;
			for(l = 0; l < list.size(); ++l) {
				Entity entity1 = (Entity) list.get(l);
				if(entity1.canBeCollidedWith() && (entity1 != shootingEntity || ticksInAir >= 5)) {
					f1 = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double) f1, (double) f1, (double) f1);
					MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3, vec31);
					if(movingobjectposition1 != null) {
						double d1 = vec3.distanceTo(movingobjectposition1.hitVec);
						if(d1 < d0 || d0 == 0.0D) {
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}
			if(entity != null) {
				movingobjectposition = new MovingObjectPosition(entity);
			}
			if(movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;
				if(entityplayer.capabilities.disableDamage || shootingEntity instanceof EntityPlayer && !((EntityPlayer) shootingEntity).canAttackPlayer(entityplayer)) {
					movingobjectposition = null;
				}
			}
			float f2;
			float f3;
			if(movingobjectposition != null) {
				if(movingobjectposition.entityHit != null) {
					f2 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
					int i1 = MathHelper.ceiling_double_int((double) f2 * damage);
					if(getIsCritical()) {
						i1 += rand.nextInt(i1 / 2 + 2);
					}
					DamageSource damagesource = null;
					if(shootingEntity == null) {
						damagesource =  (new EntityDamageSourceIndirect("arrow", this, this)).setProjectile();
					} else {
						damagesource =  (new EntityDamageSourceIndirect("arrow", this, shootingEntity)).setProjectile();
					}
					if(isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman)) {
						movingobjectposition.entityHit.setFire(5);
					}
					if(movingobjectposition.entityHit.attackEntityFrom(damagesource, (float) i1)) {
						if(movingobjectposition.entityHit instanceof EntityLivingBase) {
							EntityLivingBase entitylivingbase = (EntityLivingBase) movingobjectposition.entityHit;
							if(!worldObj.isRemote) {
								entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
							}
							if(knockbackStrength > 0) {
								f3 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
								if(f3 > 0.0F) {
									movingobjectposition.entityHit.addVelocity(motionX * (double) knockbackStrength * 0.6000000238418579D / (double) f3, 0.1D, motionZ * (double) knockbackStrength * 0.6000000238418579D / (double) f3);
								}
							}
							if(shootingEntity != null) {
								EnchantmentThorns.func_92096_a(shootingEntity, entitylivingbase, rand);
							}
							if(shootingEntity != null && movingobjectposition.entityHit != shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && shootingEntity instanceof EntityPlayerMP) {
								((EntityPlayerMP) shootingEntity).playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(6, 0));
							}
						}
						playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
						if(!(movingobjectposition.entityHit instanceof EntityEnderman)) {
							setDead();
						}
					} else {
						motionX *= -0.10000000149011612D;
						motionY *= -0.10000000149011612D;
						motionZ *= -0.10000000149011612D;
						rotationYaw += 180.0F;
						prevRotationYaw += 180.0F;
						ticksInAir = 0;
					}
				} else {
					tileX = movingobjectposition.blockX;
					tileY = movingobjectposition.blockY;
					tileZ = movingobjectposition.blockZ;
					inTile = worldObj.getBlockId(tileX, tileY, tileZ);
					inData = worldObj.getBlockMetadata(tileX, tileY, tileZ);
					motionX = (double) ((float) (movingobjectposition.hitVec.xCoord - posX));
					motionY = (double) ((float) (movingobjectposition.hitVec.yCoord - posY));
					motionZ = (double) ((float) (movingobjectposition.hitVec.zCoord - posZ));
					f2 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
					posX -= motionX / (double) f2 * 0.05000000074505806D;
					posY -= motionY / (double) f2 * 0.05000000074505806D;
					posZ -= motionZ / (double) f2 * 0.05000000074505806D;
					playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
					inGround = true;
					arrowShake = 7;
					setIsCritical(false);
					if(inTile != 0) {
						Block.blocksList[inTile].onEntityCollidedWithBlock(worldObj, tileX, tileY, tileZ, this);
					}
				}
			}
			if(getIsCritical()) {
				for(l = 0; l < 4; ++l) {
					worldObj.spawnParticle("crit", posX + motionX * (double) l / 4.0D, posY + motionY * (double) l / 4.0D, posZ + motionZ * (double) l / 4.0D, -motionX, -motionY + 0.2D, -motionZ);
				}
			}
			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			f2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
			for(rotationPitch = (float) (Math.atan2(motionY, (double) f2) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {}
			while(rotationPitch - prevRotationPitch >= 180.0F) {
				prevRotationPitch += 360.0F;
			}
			while(rotationYaw - prevRotationYaw < -180.0F) {
				prevRotationYaw -= 360.0F;
			}
			while(rotationYaw - prevRotationYaw >= 180.0F) {
				prevRotationYaw += 360.0F;
			}

			rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
			rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
			float f4 = 0.99F;
			f1 = 0.05F;
			if(isInWater()) {
				for(int j1 = 0; j1 < 4; ++j1) {
					f3 = 0.25F;
					worldObj.spawnParticle("bubble", posX - motionX * (double) f3, posY - motionY * (double) f3, posZ - motionZ * (double) f3, motionX, motionY, motionZ);
				}
				f4 = 0.8F;
			}
			motionX *= (double) f4;
			motionY *= (double) f4;
			motionZ *= (double) f4;
			motionY -= (double) f1;
			setPosition(posX, posY, posZ);
			doBlockCollisions();
		}
	}

	public ItemStack getItemStack() {
		ItemStack stack = new ItemStack(Tools.arrow.itemID, 1, 0);
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("Type", 19);
		tag.setInteger("Material", material);
		tag.setInteger("RodMaterial", rodMaterial);
		nbt.setTag("Tool", tag);
		stack.setTagCompound(nbt);
		return stack;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	@Override
	public boolean canAttackWithItem() {
		return false;
	}

	public boolean getIsCritical() {
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setIsCritical(boolean flag) {
		byte isCritical = dataWatcher.getWatchableObjectByte(16);
		if(flag) {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (isCritical | 1)));
		} else {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (isCritical & -2)));
		}
	}

	public int getMaterial() {
		return dataWatcher.getWatchableObjectShort(17);
	}

	public int getRodMaterial() {
		return dataWatcher.getWatchableObjectShort(18);
	}

	public void setMaterials(int material, int rodMaterial) {
		this.material = material;
		this.rodMaterial = rodMaterial;
		dataWatcher.updateObject(17, Short.valueOf((short) material));
		dataWatcher.updateObject(18, Short.valueOf((short) rodMaterial));
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer player) {
		if(!worldObj.isRemote && inGround && arrowShake <= 0) {
			boolean flag = canBePickedUp == 1 || canBePickedUp == 2 && player.capabilities.isCreativeMode;
			if(canBePickedUp == 1 && !player.inventory.addItemStackToInventory(getItemStack())) {
				flag = false;
			}
			if(flag) {
				playSound("random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				player.onItemPickup(this, 1);
				setDead();
			}
		}
	}

	@Override
	public void setThrowableHeading(double x, double y, double z, float f1, float f2) {
		float dist = MathHelper.sqrt_double(x * x + y * y + z * z);
		x /= (double) dist;
		y /= (double) dist;
		z /= (double) dist;
		x += rand.nextGaussian() * (double) (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) f2;
		y += rand.nextGaussian() * (double) (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) f2;
		z += rand.nextGaussian() * (double) (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) f2;
		x *= (double) f1;
		y *= (double) f1;
		z *= (double) f1;
		motionX = x;
		motionY = y;
		motionZ = z;
		float d = MathHelper.sqrt_double(x * x + z * z);
		prevRotationYaw = rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
		prevRotationPitch = rotationPitch = (float) (Math.atan2(y, (double) d) * 180.0D / Math.PI);
		ticksInGround = 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int i) {
		setPosition(x, y, z);
		setRotation(yaw, pitch);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z) {
		motionX = x;
		motionY = y;
		motionZ = z;
		if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			float dist = MathHelper.sqrt_double(x * x + z * z);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(y, (double) dist) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch;
			prevRotationYaw = rotationYaw;
			setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
			ticksInGround = 0;
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		tileX = nbt.getInteger("TileX");
		tileY = nbt.getInteger("TileY");
		tileZ = nbt.getInteger("TileZ");
		inTile = nbt.getByte("InTile") & 255;
		inData = nbt.getByte("InData") & 255;
		arrowShake = nbt.getByte("ArrowShake") & 255;
		inGround = nbt.getByte("InGround") == 1;
		if(nbt.hasKey("Damage")) {
			damage = nbt.getDouble("Damage");
		}
		if(nbt.hasKey("CanPickup")) {
			canBePickedUp = nbt.getByte("CanPickup");
		} else if(nbt.hasKey("Player")) {
			canBePickedUp = nbt.getBoolean("Player") ? 1 : 0;
		}
		setMaterials(nbt.getInteger("Material"), nbt.getInteger("RodMaterial"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("TileX", tileX);
		nbt.setInteger("TileY", tileY);
		nbt.setInteger("TileZ", tileZ);
		nbt.setByte("InTile", (byte) inTile);
		nbt.setByte("InData", (byte) inData);
		nbt.setByte("ArrowShake", (byte) arrowShake);
		nbt.setByte("InGround", (byte) (inGround ? 1 : 0));
		nbt.setByte("CanPickup", (byte) canBePickedUp);
		nbt.setDouble("Damage", damage);
		nbt.setInteger("Material", material);
		nbt.setInteger("RodMaterial", rodMaterial);
	}
}
