package me.jacky1356400.playerstatues.tile;

import me.jacky1356400.playerstatues.util.StatueParameters;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class TileEntityStatue extends TileEntity {

    public TileEntityStatue() {
    }

    private EntityPlayer clientPlayer;
    public String skinName = "";
    public StatueParameters pose = new StatueParameters();
    public Block block = Blocks.STONE;
    public int meta = 0;
    public int facing = 0;
    boolean updated = true;

    void randomize(Random rand){
    }

    public EntityStatuePlayer getStatue(){
        if(clientPlayer==null){
            EntityStatuePlayer player=new EntityStatuePlayer(world, skinName);
            player.ticksExisted=10;
            player.pose=pose;
            player.applySkin(skinName, block, facing, meta);

            clientPlayer=player;
            for(int i = 0; i < 6; i++) {
                this.onInventoryUpdate(i);
            }
        }

        return (EntityStatuePlayer)clientPlayer;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        skinName = nbttagcompound.getString("skin");
        pose.readFromNBT(nbttagcompound);

        block=Block.getBlockById(nbttagcompound.getShort("blockId"));
        if(block==null) block=Blocks.STONE;
        meta=nbttagcompound.getByte("meta");
        facing=nbttagcompound.getByte("face");

        updateModel(world);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);

        nbttagcompound.setString("skin", skinName);
        pose.writeToNBT(nbttagcompound);

        nbttagcompound.setShort("blockId",(short)Block.getIdFromBlock(block));
        nbttagcompound.setByte("meta",(byte)meta);
        nbttagcompound.setByte("face",(byte)facing);
        return nbttagcompound;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        if ((getBlockMetadata() & 4) != 0)
            return null;

        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new SPacketUpdateTileEntity(pos, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());

        if(world.isRemote && Minecraft.getMinecraft().currentScreen instanceof GuiStatue){
            GuiStatue gui=(GuiStatue) Minecraft.getMinecraft().currentScreen;
            pose.itemLeftA=gui.ila;
            pose.itemRightA=gui.ira;
        }
    }

    public void updateModel(World world) {
        if(clientPlayer!=null && world!=null && world.isRemote){
            clientPlayer.applySkin(skinName, block, facing, meta);
        }

        updated=false;
    }

    @Override
    public void onInventoryUpdate(int slot, World world) {
        if(clientPlayer != null) {
            clientPlayer.inventory.mainInventory[0]=getStackInSlot(4);
            clientPlayer.inventory.mainInventory[1]=getStackInSlot(5);
            clientPlayer.inventory.armorInventory[0]=getStackInSlot(3);
            clientPlayer.inventory.armorInventory[1]=getStackInSlot(2);
            clientPlayer.inventory.armorInventory[2]=getStackInSlot(1);
            clientPlayer.inventory.armorInventory[3]=getStackInSlot(0);
        }

        world.markBlockForUpdate(x, y, z);
    }

}