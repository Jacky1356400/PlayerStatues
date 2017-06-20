/**
 * Container class for the statue
 */

package me.jacky1356400.statues;

import net.minecraft.entity.player.InventoryPlayer;
import pl.asie.lib.block.ContainerBase;

public class ContainerStatue extends ContainerBase {
	TileEntityStatue	tile;

	public ContainerStatue(InventoryPlayer inventory, TileEntityStatue te) {
		super(te, inventory);
		tile = te;

		addSlotToContainer(new SlotArmorStatue(tile, 0, 80, 33, 0));
		addSlotToContainer(new SlotArmorStatue(tile, 1, 80, 51, 1));
		addSlotToContainer(new SlotArmorStatue(tile, 2, 80, 69, 2));
		addSlotToContainer(new SlotArmorStatue(tile, 3, 80, 87, 3));
		addSlotToContainer(new SlotHand(tile, 4, 111, 51));
		addSlotToContainer(new SlotHand(tile, 5, 49, 51));
		
		this.bindPlayerInventory(inventory, 8, 144);
	}

}
