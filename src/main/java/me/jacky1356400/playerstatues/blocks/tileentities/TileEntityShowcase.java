/**
 * TileEntity class of the showcase
 */

package me.jacky1356400.playerstatues.blocks.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

public class TileEntityShowcase extends TileEntityLockable implements ITickable
{
	public float lidAngle;
	public float prevLidAngle;
	private int ticksSinceSync;
	public int numUsingPlayers = 0;

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public String getName()
	{
		return "Showcase";
	}

	/**
	 * Called when the container is opened
	 */
	@Override
	public void openInventory(EntityPlayer player)
	{
		if(worldObj.isRemote)
			return;

		numUsingPlayers++;
	}

	/**
	 * Called when the container is closed
	 */
	@Override
	public void closeInventory(EntityPlayer player)
	{
		if(worldObj.isRemote)
			return;

		numUsingPlayers--;
	}

	@Override
	public void update()
	{
		if((++ticksSinceSync % 20) * 4 == 0)
		{
			;
		}

		prevLidAngle = lidAngle;
		float f = 0.1F;

		if(numUsingPlayers > 0 && lidAngle == 0F)
		{
			double d = this.pos.getX() + 0.5D;
			double d1 = this.pos.getZ() + 0.5D;

			worldObj.playSound(null, d, this.pos.getY() + 0.5D, d1, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if((numUsingPlayers == 0 && lidAngle > 0F) || (numUsingPlayers > 0 && lidAngle < 1F))
		{
			float f1 = lidAngle;

			if(numUsingPlayers > 0)
			{
				lidAngle += f;
			}
			else
			{
				lidAngle -= f;
			}

			if(lidAngle > 1F)
			{
				lidAngle = 1F;
			}
			if(lidAngle < 0F)
			{
				lidAngle = 0F;
			}

			float f2 = 0.5F;

			if(lidAngle < f2 && f1 >= f2)
			{
				double d2 = this.pos.getX() + 0.5D;
				double d3 = this.pos.getZ() + 0.5D;

				worldObj.playSound(null, d2, this.pos.getY() + 0.5D, d3, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return true;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		IBlockState state = worldObj.getBlockState(pos);
		if((state.getBlock().getMetaFromState(state) & 4) != 0)
			return null;

		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		tag.setInteger("users", numUsingPlayers);
		return new SPacketUpdateTileEntity(pos, 1, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.getNbtCompound());
		numUsingPlayers = pkt.getNbtCompound().getInteger("users");
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGuiID()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getInventoryStackLimit()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getField(int id)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getFieldCount()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear()
	{
		// TODO Auto-generated method stub

	}
}