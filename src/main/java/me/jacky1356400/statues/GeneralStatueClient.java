package me.jacky1356400.statues;

import java.util.Random;

import me.jacky1356400.statues.blocks.tileentities.TileEntityStatue;
import me.jacky1356400.statues.entities.EntityStatuePlayer;
import me.jacky1356400.statues.entities.EntityTextureFX;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GeneralStatueClient
{
	static Random rand = new Random();

	public static ParticleDigging addBlockHitEffects(World world, BlockPos pos, IBlockState state, int side)
	{
		ParticleManager renderer = Minecraft.getMinecraft().effectRenderer;

		if(state == null)
			return null;

		float f = 0.1F;

		AxisAlignedBB bounds = world.getBlockState(pos).getBoundingBox(world, pos);
		double d0 = pos.getX() + rand.nextDouble() * (bounds.maxX - bounds.minX - f * 2.0F) + f + bounds.minX;
		double d1 = pos.getY() + rand.nextDouble() * (bounds.maxY - bounds.minY - f * 2.0F) + f + bounds.minY;
		double d2 = pos.getZ() + rand.nextDouble() * (bounds.maxZ - bounds.minZ - f * 2.0F) + f + bounds.minZ;

		switch(side)
		{
			case 0:
				d1 = pos.getY() + bounds.minY - f;
				break;
			case 1:
				d1 = pos.getY() + bounds.maxY + f;
				break;
			case 2:
				d2 = pos.getZ() + bounds.minZ - f;
				break;
			case 3:
				d2 = pos.getZ() + bounds.maxZ + f;
				break;
			case 4:
				d0 = pos.getX() + bounds.minX - f;
				break;
			case 5:
				d0 = pos.getX() + bounds.maxX + f;
				break;
		}

		ParticleDigging res = (ParticleDigging) renderer.spawnEffectParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), d0, d1, d2, 0.0D, 0.0D, 0.0D, state.getBlock().getMetaFromState(state));

		renderer.addEffect(res);

		return res;
	}

	public static void spawnSculptEffect(BlockPos pos, IBlockState state)
	{
		if(state == null)
			return;

		for(int side = 0; side < 6; side++)
		{
			for(int j = 0; j < 32; j++)
			{
				ParticleDigging fx = addBlockHitEffects(Minecraft.getMinecraft().theWorld, pos, state, side);
				if(fx == null)
					return;

				fx.multipleParticleScaleBy(0.25f + 0.5f * rand.nextFloat());
				fx.multiplyVelocity(0.3f * rand.nextFloat());
			}
		}

		World world = Minecraft.getMinecraft().theWorld;
		SoundType sound = state.getBlock().getSoundType(world.getBlockState(pos), world, pos, null);
		world.playSound(null, pos, sound.getPlaceSound(), SoundCategory.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getVolume() * 0.5F);
	}

	public static EntityTextureFX addTextureEffects(World world, BlockPos pos, AbstractTexture texture, int side, float u, float v)
	{
		IBlockState state = world.getBlockState(pos);
		if(state == null)
			return null;

		AxisAlignedBB bounds = state.getBoundingBox(world, pos);
		double w = bounds.maxX - bounds.minX;
		double h = bounds.maxY - bounds.minY;
		double l = bounds.maxZ - bounds.minZ;
		double px = rand.nextDouble() * w;
		double py = rand.nextDouble() * h;
		double pz = rand.nextDouble() * l;

		float f = 0.25F;
		double d0 = pos.getX() + bounds.minX + px;
		double d1 = pos.getY() + bounds.minY + py;
		double d2 = pos.getZ() + bounds.minZ + pz;

		switch(side)
		{
			case 0:
				d1 = pos.getY() + bounds.minY - f;
				break;
			case 1:
				d1 = pos.getY() + bounds.maxY + f;
				break;
			case 2:
				d2 = pos.getZ() + bounds.minZ - f;
				break;
			case 3:
				d2 = pos.getZ() + bounds.maxZ + f;
				break;
			case 4:
				d0 = pos.getX() + bounds.minX - f;
				break;
			case 5:
				d0 = pos.getX() + bounds.maxX + f;
				break;
		}

		double motion = 0.5;
		EntityTextureFX res = new EntityTextureFX(world, d0, d1, d2, motion * (px / w - 0.5), motion * (py / h - 0.5), motion * (pz / l - 0.5), texture, u, v);

		return res;
	}

	public static void spawnPaintEffect(World world, BlockPos pos)
	{
		TileEntity te = world.getTileEntity(pos);
		if(!(te instanceof TileEntityStatue))
			return;
		TileEntityStatue statue = (TileEntityStatue) te;
		EntityStatuePlayer player = statue.getStatue();

		AbstractTexture tex = player.getTextureSkin();

		for(int side = 2; side < 6; side++)
		{
			for(int i = 0; i < 80; i++)
			{
				EntityTextureFX p = addTextureEffects(world, pos.add(0, i % 2, 0), tex, side, 0.05f, 0.1f);
				if(p != null)
					world.spawnEntityInWorld(p);
			}
		}

	}

	public static void spawnCopyEffect(World world, BlockPos pos, EnumFacing facing, float hx, float hy, float hz, TileEntityStatue statue)
	{
		// world.playSound(x + 0.5, y + 0.5, z + 0.5, "statues:copy", 2.0F, world.rand.nextFloat() * 0.4f + 0.8f, false);

		for(int i = 0; i < 8; i++)
		{
			ParticleDigging fx = addBlockHitEffects(Minecraft.getMinecraft().theWorld, pos, statue.blockstate, i);
			if(fx == null)
				return;

			fx.setPosition(pos.getX() + hx, pos.getY() + hy, pos.getZ() + hz);
			fx.multipleParticleScaleBy(0.15f + 0.7f * rand.nextFloat());
			fx.multiplyVelocity(0.3f * rand.nextFloat());
		}
	}

}
