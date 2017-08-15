package me.jacky1356400.playerstatues.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityBase extends TileEntity {

    // Base functions for containers
    public void openInventory(EntityPlayer player) {
    }
    public void closeInventory(EntityPlayer player) {
    }
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(new BlockPos(this.getPos())) == this
                && player.getDistanceSq(
                (double) this.getPos().getX() + 0.5D,
                (double) this.getPos().getY() + 0.5D,
                (double) this.getPos().getZ() + 0.5D) <= 64.0D;
    }

    // Remote NBT data management
    public void readFromRemoteNBT(NBTTagCompound tag) { }
    public void writeToRemoteNBT(NBTTagCompound tag) { }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToRemoteNBT(tag);
        return new SPacketUpdateTileEntity(getPos(), 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        NBTTagCompound tag = pkt.getNbtCompound();
        if(tag != null)
            this.readFromRemoteNBT(tag);
    }

    protected int oldRedstoneSignal = -1;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.oldRedstoneSignal = tag.getInteger("old_redstone");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("old_redstone", this.oldRedstoneSignal);
        return tag;
    }

}
