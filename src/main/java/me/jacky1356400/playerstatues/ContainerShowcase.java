/**
 * Container class for the statue
 */

package me.jacky1356400.playerstatues;

import net.minecraft.entity.player.InventoryPlayer;
import pl.asie.lib.block.ContainerBase;

public class ContainerShowcase extends ContainerBase {

	public ContainerShowcase(InventoryPlayer inventory, TileEntityShowcase tile) {
		super(tile, inventory);
		
		addSlotToContainer(new SlotHand(tile, 0, 120, 59));

		bindPlayerInventory(inventory, 48, 144);
		
		tile.openInventory();
	}
}
