package me.jacky1356400.playerstatues.item;

import me.jacky1356400.playerstatues.PlayerStatues;
import me.jacky1356400.playerstatues.tile.TileEntityStatue;
import me.jacky1356400.playerstatues.util.GeneralStatueClient;
import me.jacky1356400.playerstatues.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPalette extends Item implements IHasModel {

	public ItemPalette() {
		super();
        setRegistryName(PlayerStatues.MODID + ":palette");
        setUnlocalizedName(PlayerStatues.MODID + ".palette");
        setCreativeTab(PlayerStatues.TAB);
        PlayerStatues.ITEMS.add(this);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block block;
		int meta;
        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
		y++;
		do{
			y--;
			block = world.getBlockState(pos).getBlock();
			meta = state.getBlock().getMetaFromState(state);
		} while(block.equals(PlayerStatues.statue) && (meta&4)!=0);
		
		if(block != PlayerStatues.statue)
			return EnumActionResult.SUCCESS;
		
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof TileEntityStatue))
			return EnumActionResult.SUCCESS;
		TileEntityStatue statue=(TileEntityStatue) te;
		
		statue.block=Blocks.BEDROCK;
		stack.damageItem(1, player);
		world.markBlockRangeForRenderUpdate(pos, pos);
		
		stack.shrink(1);
		
		if(world.isRemote){
			statue.updateModel();
			GeneralStatueClient.spawnPaintEffect(world, pos);
			world.playSound(null, x+0.5, y+0.5, z+0.5,
                    SoundEvent.REGISTRY.getObject(new ResourceLocation("playerstatues", "paint")),
                    SoundCategory.BLOCKS, 1.0f, 1.0f);
		}
			
		return EnumActionResult.SUCCESS;
	}

}
