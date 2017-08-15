package me.jacky1356400.playerstatues.util;

import me.jacky1356400.playerstatues.PlayerStatues;
import me.jacky1356400.playerstatues.tile.TileEntityStatue;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.io.IOException;

public class Packets extends MessageHandlerBase {

    public static final int SCULPTURE_CREATION = 1;
    public static final int SCULPTURE_ADJUSTMENT = 2;
    public static final int SCULPTED = 3;

    public void onSculptureCreation(Packet packet, EntityPlayer player) throws IOException {
        final int x = packet.readInt();
        final int y = packet.readInt();
        final int z = packet.readInt();
        int face = packet.readByte();

        final Block block = player.world.getBlockState(new BlockPos(x,y,z)).getBlock();
        if(!PlayerStatues.canSculpt(block, player.world, block.getDefaultState())) return;

        final int meta = player.world.getBlockState(new BlockPos(x,y,z)).getBlock().getMetaFromState(player.world.getBlockState(new BlockPos(x,y,z)));
        if(!block.equals(player.world.getBlockState(new BlockPos(x,y+1,z)).getBlock())
                || meta != player.world.getBlockState(new BlockPos(x,y+1,z)).getBlock()
                .getMetaFromState(player.world.getBlockState(new BlockPos(x,y+1,z)))) return;

        player.world.setBlockState(new BlockPos(x, y, z), PlayerStatues.statue.getDefaultState(), face);

        TileEntity tileEntity = player.world.getTileEntity(new BlockPos(x, y, z));
        if (!(tileEntity instanceof TileEntityStatue))
            return;
        TileEntityStatue entity = (TileEntityStatue) tileEntity;

        entity.skinName=packet.readString();
        entity.pose.read(packet);
        entity.block=block;
        entity.meta=meta;
        entity.facing=2;

        player.world.setBlockState(new BlockPos(x, y+1, z), PlayerStatues.statue.getDefaultState(), face|4);
        player.world.notifyNeighborsOfStateChange(new BlockPos(x, y, z), PlayerStatues.statue.getDefaultState().getBlock(), true);
        player.world.markBlockRangeForRenderUpdate(new BlockPos(x, y, z), new BlockPos(x, y, z));

        Packet sculpted = PlayerStatues.packet.create(Packets.SCULPTED)
                .writeInt(x).writeInt(y).writeInt(z).writeShort((short)Block.getIdFromBlock(block)).writeByte((byte)meta);
        PlayerStatues.packet.sendToAllAround(sculpted, player, 64);
    }

    public void onSculptureAdjustment(Packet packet, EntityPlayer player) throws IOException {
        final int x = packet.readInt();
        final int y = packet.readInt();
        final int z = packet.readInt();
        float itemLeftA = packet.readFloat();
        float itemRightA = packet.readFloat();

        TileEntity tileEntity = player.world.getTileEntity(new BlockPos(x, y, z));
        if (!(tileEntity instanceof TileEntityStatue))
            return;
        TileEntityStatue entity = (TileEntityStatue) tileEntity;

        entity.pose.itemLeftA=itemLeftA;
        entity.pose.itemRightA=itemRightA;
        player.world.markBlockRangeForRenderUpdate(new BlockPos(x, y, z), new BlockPos(x, y, z));
    }

    public void onSculpted(Packet packet, EntityPlayer player) throws IOException {
        final int x=packet.readInt();
        final int y=packet.readInt();
        final int z=packet.readInt();
        final Block block=Block.getBlockById(packet.readShort());
        final byte meta=packet.readByte();

        GeneralStatueClient.spawnSculptEffect(x, y, z, block, meta);
        GeneralStatueClient.spawnSculptEffect(x, y+1, z, block, meta);
    }

    @Override
    public void onMessage(Packet packet, INetHandler handler, EntityPlayer player, int command) throws IOException {
        switch(command) {
            case SCULPTURE_CREATION:
                onSculptureCreation(packet, player);
                break;
            case SCULPTURE_ADJUSTMENT:
                onSculptureAdjustment(packet, player);
                break;
            case SCULPTED:
                onSculpted(packet, player);
                break;
        }
    }

}
