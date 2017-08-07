package me.jacky1356400.playerstatues.item;

import me.jacky1356400.playerstatues.util.Data;
import me.jacky1356400.playerstatues.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHammer extends Item implements IHasModel {
	public ItemHammer() {
		setRegistryName(Data.MODID + ":hammer");
		setUnlocalizedName(Data.MODID + ".hammer");
		setMaxStackSize(1);
		setCreativeTab(Data.TAB);
		Data.ITEMS.add(this);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	public static boolean canSculpt(Block block, World world, IBlockState state) {
		if(block==null) return false;
		if(block.equals(Blocks.BEDROCK)) return false;
		if(block.getMaterial(state) == Material.CIRCUITS) return false;
		if(block.getMaterial(state) == Material.FIRE) return false;
		if(block.getMaterial(state) == Material.LAVA) return false;
		if(block.getMaterial(state) == Material.WATER) return false;
		if(world.getTileEntity(BlockPos.ORIGIN)!=null) return false;

		return true;
	}

	//TODO: Make hammer functional
	/*
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hx, float hy, float hz) {
		if (world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityStatue){
	    	int ox=x;int oy=y;int oz=z;
			
			while(BlockStatue.isStatue(world, x, y, z) && ! BlockStatue.isMainBlock(world, x, y, z)) y--;
	    	if(! BlockStatue.isStatue(world, x, y, z)) return true;
			
			TileEntityStatue statue=(TileEntityStatue) world.getTileEntity(x, y, z);

			GuiSculpt.pose.copyFrom(statue.pose);
			GeneralStatueClient.spawnCopyEffect(world, ox, oy, oz, side, hx, hy, hz, statue);
			
			return true;
		}
		
		Block block = world.getBlock(x,y,z);
		if(! PlayerStatues.canSculpt(block,player.worldObj,x,y,z))
			return false;
        if (world.isRemote)
            return true;
		
		int meta=world.getBlockMetadata(x,y,z);
		
		
		if(block.equals(world.getBlock(x,y+1,z)) && meta==world.getBlockMetadata(x,y+1,z) && PlayerStatues.canSculpt(block,player.worldObj,x,y+1,z)){
			PlayerStatues.guiSculpt.open(player, world, x, y, z);
			return true;
		}
		
		if(block.equals(world.getBlock(x,y-1,z)) && meta==world.getBlockMetadata(x,y-1,z) && PlayerStatues.canSculpt(block,player.worldObj,x,y-1,z)){
			PlayerStatues.guiSculpt.open(player, world, x, y-1, z);
			return true;
		}
				
		return true;
	}
	*/

}