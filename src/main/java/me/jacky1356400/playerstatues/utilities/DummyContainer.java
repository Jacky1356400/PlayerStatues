package me.jacky1356400.playerstatues.utilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class DummyContainer extends Container {
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
}
