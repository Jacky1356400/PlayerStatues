package me.jacky1356400.playerstatues.gui;

import me.jacky1356400.playerstatues.util.InventoryStatic;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ContainerPickBlock extends Container {

    NonNullList<ItemStack> items = NonNullList.create();
    GuiPickBlock gui;
    EntityPlayer player;
    int width = 9;
    int height = 7;

    SlotPickBlock resultSlot;

    public ContainerPickBlock(EntityPlayer p) {
        for (Object o : Item.REGISTRY.getKeys())
        {
            Item item = Item.REGISTRY.getObject(new ResourceLocation((String)o));

            if (item != null && item.getCreativeTab() != null)
            {
                item.getSubItems(CreativeTabs.HOTBAR, items);
            }
        }

        int index = 0;

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                addSlotToContainer(new SlotPickBlock(this, index++, 9 + x * 18, 18 + y * 18));
            }
        }

        addSlotToContainer(resultSlot = new SlotPickBlock(this, index++, 18, 153));
        player = p;
        scrollTo(0);
    }

    public InventoryStatic inventory = new InventoryStatic(width * height + 1) {
        @Override
        public String getName() {
            return null;
        }

        @Override
        public boolean hasCustomName() {
            return false;
        }

        @Override
        public ITextComponent getDisplayName() {
            return null;
        }

        @Override
        public ItemStack removeStackFromSlot(int index) {
            return null;
        }

        @Override
        public void openInventory(EntityPlayer player) {

        }

        @Override
        public void closeInventory(EntityPlayer player) {

        }

        @Override
        public boolean isItemValidForSlot(int i, ItemStack itemstack)
        {
            return false;
        }

        @Override
        public int getField(int id) {
            return 0;
        }

        @Override
        public void setField(int id, int value) {

        }

        @Override
        public int getFieldCount() {
            return 0;
        }
    };

    public void scrollTo(float offset) {
        int columnsNotFitting = items.size() / width - height + 1;

        if (columnsNotFitting < 0)
        {
            columnsNotFitting = 0;
        }

        int columnOffset = (int)(offset * columnsNotFitting + 0.5D);

        for (int y = 0; y < height; ++y)
        {
            for (int x = 0; x < width; ++x)
            {
                int index = x + (y + columnOffset) * width;

                if (index >= 0 && index < items.size())
                {
                    inventory.setInventorySlotContents(x + y * width, items.get(index));
                }
                else
                {
                    inventory.setInventorySlotContents(x + y * width, (ItemStack) null);
                }
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        SlotPickBlock slot = (SlotPickBlock) this.inventorySlots.get(index);
        return slot.transferStackInSlot(player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }

}
