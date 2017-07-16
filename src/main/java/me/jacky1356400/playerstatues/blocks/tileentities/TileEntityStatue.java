/**
 * TileEntity class of the statue
 */

package me.jacky1356400.playerstatues.blocks.tileentities;

import java.util.Random;

import me.jacky1356400.playerstatues.StatueParameters;
import me.jacky1356400.playerstatues.client.gui.GuiStatue;
import me.jacky1356400.playerstatues.entities.EntityStatuePlayer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;

public class TileEntityStatue extends TileEntityLockable implements ITickable
{
	private EntityPlayer clientPlayer;
	public String skinName = "";
	public StatueParameters pose = new StatueParameters();

	public IBlockState blockstate = Blocks.STONE.getDefaultState();
	public int meta = 0;
	public int facing = 0;
	private boolean updated = true;

	public void randomize(Random rand)
	{
	}

	@Override
	public int getSizeInventory()
	{
		return 6;
	}

	public EntityStatuePlayer getStatue()
	{
		if(clientPlayer == null)
		{
			EntityStatuePlayer player = new EntityStatuePlayer(worldObj, skinName);
			player.ticksExisted = 10;
			player.setPose(pose);
			player.applySkin(skinName, blockstate.getBlock(), facing, meta);

			clientPlayer = player;
			for(int i = 0; i < 6; i++)
			{
				this.onInventoryUpdate(i);
			}
		}

		return (EntityStatuePlayer) clientPlayer;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);

		skinName = nbttagcompound.getString("skin");
		pose.readFromNBT(nbttagcompound);

		Block block = Block.getBlockById(nbttagcompound.getShort("blockId"));
		if(block == null)
			blockstate = Blocks.STONE.getDefaultState();
		else
			blockstate = block.getDefaultState();
		meta = nbttagcompound.getByte("meta");
		facing = nbttagcompound.getByte("face");

		updateModel();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound = super.writeToNBT(nbttagcompound);

		nbttagcompound.setString("skin", skinName);
		pose.writeToNBT(nbttagcompound);

		nbttagcompound.setShort("blockId", (short) Block.getIdFromBlock(blockstate.getBlock()));
		nbttagcompound.setByte("meta", (byte) meta);
		nbttagcompound.setByte("face", (byte) facing);
		return nbttagcompound;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{

	}

	@Override
	public void closeInventory(EntityPlayer player)
	{

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
		return new SPacketUpdateTileEntity(pos, 1, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.getNbtCompound());

		if(worldObj.isRemote && Minecraft.getMinecraft().currentScreen instanceof GuiStatue)
		{
			GuiStatue gui = (GuiStatue) Minecraft.getMinecraft().currentScreen;
			pose.itemLeftA = gui.getIla();
			pose.itemRightA = gui.getIra();
		}
	}

	public void updateModel()
	{
		if(clientPlayer != null && worldObj != null && worldObj.isRemote)
		{
			((EntityStatuePlayer) clientPlayer).applySkin(skinName, blockstate.getBlock(), facing, meta);
		}

		updated = false;
	}

	@Override
	public void update()
	{
		if(updated)
			return;
		updated = true;

	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public void onInventoryUpdate(int slot)
	{
		if(clientPlayer != null)
		{
			clientPlayer.inventory.mainInventory[0] = getStackInSlot(4);
			clientPlayer.inventory.mainInventory[1] = getStackInSlot(5);
			clientPlayer.inventory.armorInventory[0] = getStackInSlot(3);
			clientPlayer.inventory.armorInventory[1] = getStackInSlot(2);
			clientPlayer.inventory.armorInventory[2] = getStackInSlot(1);
			clientPlayer.inventory.armorInventory[3] = getStackInSlot(0);
		}
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
	public String getName()
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