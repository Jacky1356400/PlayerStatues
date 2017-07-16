/**
 * Block class for the statue
 */

package me.jacky1356400.statues.blocks;

import java.util.Random;

import me.jacky1356400.statues.Statues;
import me.jacky1356400.statues.blocks.tileentities.TileEntityStatue;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStatue extends BlockContainer
{

	private static final AxisAlignedBB BOUNDS1 = new AxisAlignedBB(0.1F, 0F, 0.1F, 0.9F, 2F, 0.9F);
	private static final AxisAlignedBB BOUNDS2 = new AxisAlignedBB(0.1F, -1F, 0.1F, 0.9F, 1F, 0.9F);

	public BlockStatue()
	{
		super(Material.ROCK);
		super.setHardness(1F);
		super.setResistance(1F);
		super.setRegistryName(new ResourceLocation(Statues.MODID, "statue"));
		setLightOpacity(0);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		if((state.getBlock().getMetaFromState(state) & 4) == 0)
		{
			return BOUNDS1;
		}
		else
		{
			return BOUNDS2;
		}
	}

	public static boolean isMainBlock(IBlockAccess world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		return (state.getBlock().getMetaFromState(state) & 4) == 0;
	}

	public static boolean isStatue(IBlockAccess world, BlockPos pos)
	{
		return world.getBlockState(pos).getBlock() instanceof BlockStatue;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
			return true;

		if(!isMainBlock(world, pos))
			pos = pos.add(0, -1, 0);

		TileEntityStatue statue = (TileEntityStatue) world.getTileEntity(pos);
		if(statue instanceof TileEntityStatue)
			Statues.guiStatue.open(player, world, pos);

		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		if((state.getBlock().getMetaFromState(state) & 4) == 0)
		{
			world.setBlockToAir(pos.add(0, 1, 0));
			TileEntity tile = world.getTileEntity(pos);
			if(tile instanceof TileEntityStatue)
			{
				TileEntityLockable inventory = (TileEntityLockable) tile;
				for(int i = 0; i < inventory.getSizeInventory(); i++)
				{
					ItemStack item = inventory.getStackInSlot(i);

					if(item != null && item.stackSize > 0)
					{
						inventory.setInventorySlotContents(i, null);
						float rx = world.rand.nextFloat() * 0.8F + 0.1F;
						float ry = world.rand.nextFloat() * 0.8F + 0.1F;
						float rz = world.rand.nextFloat() * 0.8F + 0.1F;

						EntityItem entityItem = new EntityItem(world, pos.getX() + rx, pos.getY() + ry, pos.getZ() + rz, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));

						if(item.hasTagCompound())
						{
							entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
						}

						float factor = 0.05F;
						entityItem.motionX = world.rand.nextGaussian() * factor;
						entityItem.motionY = world.rand.nextGaussian() * factor + 0.2F;
						entityItem.motionZ = world.rand.nextGaussian() * factor;
						world.spawnEntityInWorld(entityItem);
						item.stackSize = 0;
					}
				}
			}
		}
		else
		{
			world.setBlockToAir(pos.add(0, -1, 0));
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int metadata)
	{
		return new TileEntityStatue();
	}

	// @Override
	// public IIcon getIcon(int side, int meta)
	// {
	// return Blocks.stone.getIcon(0, 0);
	// }
	//
	// @Override
	// public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	// {
	// TileEntity te = world.getTileEntity(x, y, z);
	// if(!(te instanceof TileEntityStatue))
	// return Blocks.stone.getIcon(0, 0);
	// TileEntityStatue statue = (TileEntityStatue) te;
	//
	// Block block = statue.blockstate;
	// if(block == null)
	// return Blocks.stone.getIcon(0, 0);
	//
	// return block.getIcon(side, statue.meta);
	// }

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		while(isStatue(world, pos) && !isMainBlock(world, pos))
			pos = pos.add(0, -1, 0);
		if(!isStatue(world, pos))
			return 0;

		TileEntity te = world.getTileEntity(pos);
		if(!(te instanceof TileEntityStatue))
			return 0;
		TileEntityStatue statue = (TileEntityStatue) te;

		return statue.blockstate.getLightValue(world, pos);
	}

	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return true;
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		int ox = pos.getX(), oy = pos.getY(), oz = pos.getZ();
		while(isStatue(world, pos) && !isMainBlock(world, pos))
			pos = pos.add(0, -1, 0);
		if(!isStatue(world, pos))
			return 0;

		TileEntity te = world.getTileEntity(pos);
		if(!(te instanceof TileEntityStatue))
			return 0;
		TileEntityStatue statue = (TileEntityStatue) te;

		if(statue.blockstate == null)
			return 0;
		return statue.blockstate.getWeakPower(world, new BlockPos(ox, oy, oz), side);
	}
}
