/**
 * Container class for the statue
 */

package me.jacky1356400.playerstatues.container;

import me.jacky1356400.playerstatues.tile.TileEntityShowcase;
import me.jacky1356400.playerstatues.util.SlotHand;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerShowcase extends ContainerBase {

	public ContainerShowcase(TileEntityShowcase tile, InventoryPlayer inventory) {
		super(tile, inventory);

		addSlotToContainer(new SlotHand(tile, 0, 120, 59));

		bindPlayerInventory(inventory, 48, 144);
		
		tile.openInventory(inventory.player);
	}

}
