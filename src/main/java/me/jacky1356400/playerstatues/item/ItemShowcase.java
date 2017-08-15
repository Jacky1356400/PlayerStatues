/**
 * Item class for the statue
 */

package me.jacky1356400.playerstatues.item;

import me.jacky1356400.playerstatues.PlayerStatues;
import me.jacky1356400.playerstatues.util.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemShowcase extends Item implements IHasModel {

    public ItemShowcase() {
        setHasSubtypes(true);
        setRegistryName(PlayerStatues.MODID + ":showcase_item");
        setUnlocalizedName(PlayerStatues.MODID + ".showcase_item");
        setCreativeTab(PlayerStatues.TAB);
        PlayerStatues.ITEMS.add(this);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    @Override
    public int getMetadata(int par1) {
        return par1;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return EnumActionResult.FAIL;

        int meta = MathHelper.floor((player.rotationYaw * 4F) / 360F + 0.5D) & 3;
        int dx, dz;
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();

        switch(meta){
            default:
            case 0: case 2: dx=1; dz=0; break;
            case 1: case 3: dx=0; dz=1; break;
        }

        switch (facing) {
            case DOWN: y--; break;
            case UP: y++; break;
            case NORTH: z--; break;
            case SOUTH: z++; break;
            case EAST: x--; break;
            case WEST: x++; break;
        }

        if(!player.canPlayerEdit(pos, facing, player.getHeldItem(hand))) return EnumActionResult.FAIL;
        if(!player.canPlayerEdit(new BlockPos(x+dx, y, z+dz), facing, player.getHeldItem(hand))) return EnumActionResult.FAIL;
        if(!player.canPlayerEdit(new BlockPos(x-dx, y, z-dz), facing, player.getHeldItem(hand))) return EnumActionResult.FAIL;

        if(!world.isAirBlock(pos)) return EnumActionResult.FAIL;
        if(!world.isAirBlock(new BlockPos(x+dx, y, z+dz))) return EnumActionResult.FAIL;
        if(!world.isAirBlock(new BlockPos(x-dx, y, z-dz))) return EnumActionResult.FAIL;

        world.setBlockState(pos, PlayerStatues.showcase.getDefaultState(), meta);
        if(meta>=2) meta-=2;
        world.setBlockState(new BlockPos(x+dx, y, z+dz), PlayerStatues.showcase.getDefaultState(), meta|4);
        world.setBlockState(new BlockPos(x-dx, y, z-dz), PlayerStatues.showcase.getDefaultState(), (meta+2)|4);

        player.getHeldItem(hand).shrink(1);
        return EnumActionResult.SUCCESS;
    }

}