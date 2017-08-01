/**
 * Slot class for the slot hand
 */

package me.jacky1356400.playerstatues;

import me.jacky1356400.playerstatues.blocks.tileentities.TileEntityStatue;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotHand extends Slot {
	public SlotHand(IInventory iinventory, int i, int j, int k) {
		super(iinventory, i, j, k);
	}

	public SlotHand(TileEntityStatue te, int i, int j, int k) {
		super(te, i, j, k);
	}

	/**
	 * Returns the icon index on items.png that is used as background image of
	 * the slot.
	 */
	/*
	TODO: IIcon replacement for 1.10+???
	@Override
	public IIcon getBackgroundIconIndex() {
		return PlayerStatues.slotHand;
	}
	*/
}