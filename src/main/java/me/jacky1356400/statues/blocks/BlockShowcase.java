/**
 * Block class for the statue
 */

package me.jacky1356400.statues.blocks;

import java.util.ArrayList;
import java.util.List;

import me.jacky1356400.statues.Statues;
import me.jacky1356400.statues.blocks.tileentities.TileEntityShowcase;
import me.jacky1356400.statues.items.StatuesItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockShowcase extends BlockContainer
{
	private static final AxisAlignedBB BOUNDS1 = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 0.5F, 1.5f, 1.0F);
	private static final AxisAlignedBB BOUNDS2 = new AxisAlignedBB(0.5F, 0.0F, 0.0F, 1.0F, 1.5f, 1.0F);
	private static final AxisAlignedBB BOUNDS3 = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.5f, 0.5F);
	private static final AxisAlignedBB BOUNDS4 = new AxisAlignedBB(0.0F, 0.0F, 0.5F, 1.0F, 1.5f, 1.0F);

	public BlockShowcase()
	{
		super(Material.WOOD);
		super.setHardness(1F);
		super.setResistance(1F);
		super.setRegistryName(new ResourceLocation(Statues.MODID, "showcase"));
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> ret = new ArrayList<ItemStack>();

		int meta = state.getBlock().getMetaFromState(state);
		if((meta & 4) != 0)
			return ret;

		ret.add(new ItemStack(StatuesItems.itemShowcase, 1));

		return ret;
	}

	/*
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT) public Item itemPicked(World par1World, int par2, int par3, int par4){ return Statues.itemShowcase.itemID; }
	 */ // TODO

	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state)
	{
		return EnumPushReaction.IGNORE;
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

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		int meta = state.getBlock().getMetaFromState(state);

		switch(meta)
		{
			case 0 | 4:
				return BOUNDS1;
			case 2 | 4:
				return BOUNDS2;
			case 1 | 4:
				return BOUNDS3;
			case 3 | 4:
				return BOUNDS4;
			default:
				return BOUNDS2;
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		int meta = MathHelper.floor_double((placer.rotationYaw * 4F) / 360F + 0.5D) & 3;
		int dx, dz;

		switch(meta)
		{
			default:
			case 0:
			case 2:
				dx = 1;
				dz = 0;
				break;
			case 1:
			case 3:
				dx = 0;
				dz = 1;
				break;
		}

		world.setBlockState(pos, state.getBlock().getStateFromMeta(meta));

		if(meta >= 2)
			meta -= 2;
		world.setBlockState(pos.add(dx, 0, dz), state.getBlock().getStateFromMeta(meta | 4));
		world.setBlockState(pos.add(-dx, 0, -dz), state.getBlock().getStateFromMeta((meta + 2) | 4));
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		int meta = state.getBlock().getMetaFromState(state);

		BlockPos newpos = pos;
		if((meta & 4) != 0)
		{
			switch(meta & 3)
			{
				default:
				case 0:
				case 2:
					if(isCenterBlock(world, pos.add(-1, 0, 0)))
						newpos = pos.add(-1, 0, 0);
					else if(isCenterBlock(world, pos.add(1, 0, 0)))
						newpos = pos.add(1, 0, 0);
					break;
				case 1:
				case 3:
					if(isCenterBlock(world, pos.add(0, 0, -1)))
						newpos = pos.add(0, 0, -1);
					else if(isCenterBlock(world, pos.add(0, 0, 1)))
						newpos = pos.add(0, 0, 1);
					break;
			}
		}

		if(!isCenterBlock(world, pos))
			return true;

		TileEntityShowcase teshowcase = (TileEntityShowcase) world.getTileEntity(pos);
		if(teshowcase instanceof TileEntityShowcase && !world.isRemote)
			Statues.guiShowcase.open(player, world, pos);

		return true;
	}

	boolean isCenterBlock(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		return (state.getBlock().equals(this)) && (state.getBlock().getMetaFromState(state) & 4) == 0;
	}

	boolean isCenterBlock(Block block, int meta)
	{
		return (block.equals(this)) && (meta & 4) == 0;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		BlockPos posCopy = pos;
		boolean found = true;

		int meta = state.getBlock().getMetaFromState(state);

		if(isCenterBlock(state.getBlock(), meta))
		{
			TileEntity tile = world.getTileEntity(pos);
			if(tile instanceof TileEntityShowcase)
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
			switch(meta & 3)
			{
				default:
				case 0:
				case 2:
					if(isCenterBlock(world, posCopy.add(-1, 0, 0)))
						posCopy = posCopy.add(-1, 0, 0);
					else if(isCenterBlock(world, posCopy.add(1, 0, 0)))
						posCopy = posCopy.add(1, 0, 0);
					else
						found = false;
					break;
				case 1:
				case 3:
					if(isCenterBlock(world, posCopy.add(0, 0, -1)))
						posCopy = posCopy.add(0, 0, -1);
					else if(isCenterBlock(world, posCopy.add(0, 0, 1)))
						posCopy = posCopy.add(0, 0, 1);
					else
						found = false;
					break;
			}
		}

		if(!found && !isCenterBlock(state.getBlock(), meta))
			return;

		world.setBlockToAir(posCopy);

		switch(meta & 3)
		{
			case 0:
			case 2:
				world.setBlockToAir(posCopy.add(1, 0, 0));
				world.setBlockToAir(posCopy.add(-1, 0, 0));
				break;
			case 1:
			case 3:
				world.setBlockToAir(posCopy.add(0, 0, 1));
				world.setBlockToAir(posCopy.add(0, 0, -1));
				break;
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int metadata)
	{
		return new TileEntityShowcase();
	}

	// @Override
	// public IIcon getIcon(int side, int meta)
	// {
	// return Blocks.planks.getIcon(2, 0);
	// }

}
