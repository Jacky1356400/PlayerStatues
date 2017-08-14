/**
 * Slot class for the slotArmorStatue
 */

package me.jacky1356400.playerstatues.util;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class SlotArmorStatue extends Slot {

	private final int	armorType;	// The armor type that can be placed on that
									// slot, it uses the same values of
									// armorType field on ItemArmor.

	public SlotArmorStatue(IInventory par2IInventory, int par3, int par4, int par5, int par6) {
		super(par2IInventory, par3, par4, par5);
		this.armorType = par6;
	}

	/**
	 * Returns the maximum stack size for a given slot (usually the same as
	 * getInventoryStackLimit(), but 1 in the case of armor slots)
	 */
	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	/**
	 * Check if the stack is a valid item for this slot. Always true beside for
	 * the armor slots.
	 */
	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return par1ItemStack.getItem() instanceof ItemArmor ? ((ItemArmor) par1ItemStack.getItem()).armorType == this.armorType : par1ItemStack.getItem().equals(Blocks.PUMPKIN) && this.armorType == 0;
	}

	/**
	 * Returns the icon index on items.png that is used as background image of
	 * the slot.
	 */
	@Override
	public TextureAtlasSprite getBackgroundSprite() {
        return ItemArmor.func_94602_b(this.armorType);
	}

}
