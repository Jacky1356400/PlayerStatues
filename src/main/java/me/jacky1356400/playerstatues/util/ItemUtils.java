package me.jacky1356400.playerstatues.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ItemUtils {

    public static void dropItems(World world, BlockPos pos, IInventory inventory) {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack item = inventory.getStackInSlot(i);

            if (item != null && item.getCount() > 0) {
                inventory.setInventorySlotContents(i, null);
                dropItem(world, pos, item);
                item.setCount(0);
            }
        }
    }

    public static void dropItems(World world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity == null || !(tileEntity instanceof IInventory)) {
            return;
        }
        IInventory inventory = (IInventory) tileEntity;
        dropItems(world, pos, inventory);
    }

    public static void dropItem(World world, BlockPos pos, ItemStack item) {
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        float rx = new Random().nextFloat() * 0.8F + 0.1F;
        float ry = new Random().nextFloat() * 0.8F + 0.1F;
        float rz = new Random().nextFloat() * 0.8F + 0.1F;

        EntityItem entityItem = new EntityItem(world,
                x + rx, y + ry, z + rz,
                new ItemStack(item.getItem(), item.getCount(), item.getItemDamage()));

        if (item.hasTagCompound()) {
            entityItem.getItem().setTagCompound(item.getTagCompound().copy());
        }

        float factor = 0.05F;
        entityItem.motionX = new Random().nextGaussian() * factor;
        entityItem.motionY = new Random().nextGaussian() * factor + 0.2F;
        entityItem.motionZ = new Random().nextGaussian() * factor;
        world.spawnEntity(entityItem);
    }

}
