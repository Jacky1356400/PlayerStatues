package me.jacky1356400.playerstatues.container;

import me.jacky1356400.playerstatues.tile.TileEntityBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public abstract class ContainerBase extends Container {

    private final TileEntityBase entity;

    public ContainerBase(TileEntityBase entity, InventoryPlayer inventoryPlayer) {
        super();
        this.entity = entity;
        entity.openInventory(inventoryPlayer.player);
    }

    public TileEntityBase getEntity() {
        return entity;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.entity.isUseableByPlayer(player);
    }

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        this.entity.closeInventory(par1EntityPlayer);
    }

}