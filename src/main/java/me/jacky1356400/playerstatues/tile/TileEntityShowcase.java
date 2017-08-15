/**
 * TileEntity class of the showcase
 */

package me.jacky1356400.playerstatues.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.SoundCategory;

public class TileEntityShowcase extends TileEntityChest {

	public float	lidAngle;
	public float	prevLidAngle;
	private int		ticksSinceSync;
	public int		numUsingPlayers	= 0;

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public String getName() {
		return "Showcase";
	}

	/**
	 * Called when the container is opened
	 */
	@Override
	public void openInventory(EntityPlayer player) {
		if (world.isRemote)
			return;

		numUsingPlayers++;
        world.markBlockRangeForRenderUpdate(pos, pos);
	}

	/**
	 * Called when the container is closed
	 */
	@Override
	public void closeInventory(EntityPlayer player) {
		if (world.isRemote)
			return;

		numUsingPlayers--;
        world.markBlockRangeForRenderUpdate(pos, pos);
	}

	@Override
	public void update() {
		super.update();

		if ((++ticksSinceSync % 20) * 4 == 0) {
		}

		prevLidAngle = lidAngle;
		float f = 0.1F;

		if (numUsingPlayers > 0 && lidAngle == 0F) {
			double d = getPos().getX() + 0.5D;
			double d1 = getPos().getZ() + 0.5D;

			world.playSound(null, d, getPos().getY() + 0.5D, d1, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		}

		if ((numUsingPlayers == 0 && lidAngle > 0F) || (numUsingPlayers > 0 && lidAngle < 1F)) {
			float f1 = lidAngle;

			if (numUsingPlayers > 0) {
				lidAngle += f;
			} else {
				lidAngle -= f;
			}

			if (lidAngle > 1F) {
				lidAngle = 1F;
			}
			if (lidAngle < 0F) {
				lidAngle = 0F;
			}

			float f2 = 0.5F;

			if (lidAngle < f2 && f1 >= f2) {
				double d2 = getPos().getX() + 0.5D;
				double d3 = getPos().getZ() + 0.5D;

				world.playSound(null, d2, getPos().getY() + 0.5D, d3, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
    public SPacketUpdateTileEntity getUpdatePacket() {
		if ((world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos)) & 4) != 0)
			return null;

		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		tag.setInteger("users", numUsingPlayers);
		return new SPacketUpdateTileEntity(pos, 1, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
		numUsingPlayers = pkt.getNbtCompound().getInteger("users");
	}

}
