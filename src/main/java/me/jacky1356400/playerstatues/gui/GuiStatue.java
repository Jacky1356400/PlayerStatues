/**
 * GUI class for the basic GUI of the statue
 */

package me.jacky1356400.playerstatues.gui;

import me.jacky1356400.playerstatues.PlayerStatues;
import me.jacky1356400.playerstatues.container.ContainerStatue;
import me.jacky1356400.playerstatues.tile.TileEntityStatue;
import me.jacky1356400.playerstatues.util.Packet;
import me.jacky1356400.playerstatues.util.Packets;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

public class GuiStatue extends GuiScreenPlus {

	public final InventoryPlayer invg;
	public final TileEntityStatue tile;
    public int wx,wy,wz;
    public float ila,ira;

	public GuiStatue(InventoryPlayer inv, final TileEntityStatue te, World par2, int x, int y, int z) {
		super(new ContainerStatue(inv, te),176,226,"playerstatues:textures/gui-statue.png");
		invg = inv;
		tile = te;
		wx=x; wy=y; wz=z;
		
		addChild(new Gui2dScroller(121, 92, 43, 13, "playerstatues:textures/gui-sculpt.png",13,13,243,0,ira=te.pose.itemRightA,0) {
			@Override void onChange(){
				ira=te.pose.itemRightA=(float) u;
				te.updateModel();
			}
		});
		
		addChild(new Gui2dScroller(12, 92, 43, 13, "playerstatues:textures/gui-sculpt.png",13,13,243,0,ila=te.pose.itemLeftA,0) {
			@Override void onChange(){
				ila=te.pose.itemLeftA=(float) u;
				te.updateModel();
			}
		});
	}
	
    @Override
	public void onGuiClosed(){
    	try {
    		Packet adjustStatue = PlayerStatues.packet.create(Packets.SCULPTURE_ADJUSTMENT)
    			.writeInt(wx).writeInt(wy).writeInt(wz).writeFloat(tile.pose.itemLeftA).writeFloat(tile.pose.itemRightA);
    		PlayerStatues.packet.sendToServer(adjustStatue);
    	} catch(Exception e) { e.printStackTrace(); }
    	
    	super.onGuiClosed();
    }

}
