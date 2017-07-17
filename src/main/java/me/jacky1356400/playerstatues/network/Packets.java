package me.jacky1356400.playerstatues.network;

import java.io.IOException;

import com.theprogrammingturkey.gobblecore.network.NetworkManager;

import me.jacky1356400.playerstatues.GeneralStatueClient;
import me.jacky1356400.playerstatues.PlayerStatues;
import me.jacky1356400.playerstatues.blocks.StatuesBlocks;
import me.jacky1356400.playerstatues.blocks.tileentities.TileEntityStatue;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import pl.asie.lib.network.MessageHandlerBase;
import pl.asie.lib.network.Packet;

public class Packets extends MessageHandlerBase
{
	public static final int SCULPTURE_CREATION = 1;
	public static final int SCULPTURE_ADJUSTMENT = 2;
	public static final int SCULPTED = 3;

	public void onSculptureCreation(Packet packet, EntityPlayer player) throws IOException
	{
		final int x = packet.readInt();
		final int y = packet.readInt();
		final int z = packet.readInt();
		int face = packet.readByte();

		BlockPos pos = new BlockPos(x, y, z);

		IBlockState state = player.worldObj.getBlockState(pos);
		if(!PlayerStatues.canSculpt(state, player.worldObj, pos))
			return;

		int meta = state.getBlock().getMetaFromState(state);
		IBlockState stateAbove = player.worldObj.getBlockState(pos.add(0, 1, 0));
		if(!state.equals(stateAbove) || meta != stateAbove.getBlock().getMetaFromState(stateAbove))
			return;

		player.worldObj.setBlockState(pos, StatuesBlocks.statue.getStateFromMeta(face), 3);

		TileEntity tileEntity = player.worldObj.getTileEntity(pos);
		if(!(tileEntity instanceof TileEntityStatue))
			return;
		TileEntityStatue entity = (TileEntityStatue) tileEntity;

		entity.skinName = packet.readString();
		entity.pose.read(packet);
		entity.blockstate = state;
		entity.meta = meta;
		entity.facing = 2;

		player.worldObj.setBlockState(pos.add(0, 1, 0), StatuesBlocks.statue.getStateFromMeta(face | 4), 3);
		player.worldObj.notifyNeighborsOfStateChange(pos, StatuesBlocks.statue);

		Packet sculpted = PlayerStatues.packet.create(Packets.SCULPTED).writeInt(x).writeInt(y).writeInt(z).writeShort((short) Block.getIdFromBlock(block)).writeByte((byte) meta);
		World world = player.worldObj;
		BlockPos ppos = player.getPosition();
		NetworkManager.getSimpleNetwork(PlayerStatues.instance).sendToAllAround(sculpted, new TargetPoint(world.provider.getDimensionType().getId(), ppos.getX(), ppos.getY(), ppos.getZ(), 64));
	}

	public void onSculptureAdjustment(Packet packet, EntityPlayer player) throws IOException
	{
		final int x = packet.readInt();
		final int y = packet.readInt();
		final int z = packet.readInt();
		float itemLeftA = packet.readFloat();
		float itemRightA = packet.readFloat();

		BlockPos pos = new BlockPos(x, y, z);

		TileEntity tileEntity = player.worldObj.getTileEntity(pos);
		if(!(tileEntity instanceof TileEntityStatue))
			return;
		TileEntityStatue entity = (TileEntityStatue) tileEntity;

		entity.pose.itemLeftA = itemLeftA;
		entity.pose.itemRightA = itemRightA;
	}

	public void onSculpted(Packet packet, EntityPlayer player) throws IOException
	{
		final int x = packet.readInt();
		final int y = packet.readInt();
		final int z = packet.readInt();
		final Block block = Block.getBlockById(packet.readShort());
		final byte meta = packet.readByte();

		GeneralStatueClient.spawnSculptEffect(x, y, z, block, meta);
		GeneralStatueClient.spawnSculptEffect(x, y + 1, z, block, meta);
	}

	@Override
	public void onMessage(pl.asie.lib.network.Packet packet, INetHandler handler, EntityPlayer player, int command) throws IOException
	{
		switch(command)
		{
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
