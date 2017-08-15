/**
 * Container class for the statue
 */

package me.jacky1356400.playerstatues.container;

import me.jacky1356400.playerstatues.tile.TileEntityStatue;
import me.jacky1356400.playerstatues.util.SlotArmorStatue;
import me.jacky1356400.playerstatues.util.SlotHand;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ContainerStatue extends ContainerBase {
	TileEntityStatue tile;

	public ContainerStatue(TileEntityStatue te, InventoryPlayer inventory) {
		super(te, inventory);
		tile = te;

		addSlotToContainer(new SlotArmorStatue(tile, 0, 80, 33, EntityEquipmentSlot.HEAD));
		addSlotToContainer(new SlotArmorStatue(tile, 1, 80, 51, EntityEquipmentSlot.CHEST));
		addSlotToContainer(new SlotArmorStatue(tile, 2, 80, 69, EntityEquipmentSlot.LEGS));
		addSlotToContainer(new SlotArmorStatue(tile, 3, 80, 87, EntityEquipmentSlot.FEET));
		addSlotToContainer(new SlotHand(tile, 4, 111, 51));
		addSlotToContainer(new SlotHand(tile, 5, 49, 51));
		
		this.bindPlayerInventory(inventory, 8, 144);
	}

}
