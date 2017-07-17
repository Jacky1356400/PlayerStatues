/**
 * GUI class for the basic GUI of the statue
 */

package me.jacky1356400.playerstatues.client.gui;

import me.jacky1356400.playerstatues.PlayerStatues;
import me.jacky1356400.playerstatues.blocks.containers.ContainerStatue;
import me.jacky1356400.playerstatues.blocks.tileentities.TileEntityStatue;
import me.jacky1356400.playerstatues.network.Packets;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class GuiStatue extends GuiScreenPlus
{
	public final InventoryPlayer invg;
	public final TileEntityStatue tile;
	private int wx, wy, wz;
	private float ila, ira;

	public GuiStatue(InventoryPlayer inv, final TileEntityStatue te, World par2, int x, int y, int z)
	{
		super(new ContainerStatue(inv, te), 176, 226, "playerstatues:textures/gui-statue.png");
		invg = inv;
		tile = te;
		wx = x;
		wy = y;
		wz = z;

		addChild(new Gui2dScroller(121, 92, 43, 13, "playerstatues:textures/gui-sculpt.png", 13, 13, 243, 0, setIra(te.pose.itemRightA), 0)
		{
			@Override
			void onChange()
			{
				setIra(te.pose.itemRightA = (float) u);
				te.updateModel();
			}
		});

		addChild(new Gui2dScroller(12, 92, 43, 13, "playerstatues:textures/gui-sculpt.png", 13, 13, 243, 0, setIla(te.pose.itemLeftA), 0)
		{
			@Override
			void onChange()
			{
				setIla(te.pose.itemLeftA = (float) u);
				te.updateModel();
			}
		});
	}

	@Override
	public void onGuiClosed()
	{
		try
		{
			Packet adjustStatue = PlayerStatues.packet.create(Packets.SCULPTURE_ADJUSTMENT).writeInt(wx).writeInt(wy).writeInt(wz).writeFloat(tile.pose.itemLeftA).writeFloat(tile.pose.itemRightA);
			PlayerStatues.packet.sendToServer(adjustStatue);
		} catch(Exception e)
		{
			e.printStackTrace();
		}

		super.onGuiClosed();
	}

	public float getIla()
	{
		return ila;
	}

	public float setIla(float ila)
	{
		this.ila = ila;
		return ila;
	}

	public float getIra()
	{
		return ira;
	}

	public float setIra(float ira)
	{
		this.ira = ira;
		return ira;
	}

}
