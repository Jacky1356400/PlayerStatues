package me.jacky1356400.playerstatues.item;

import me.jacky1356400.playerstatues.util.Data;
import me.jacky1356400.playerstatues.util.IHasModel;
import net.minecraft.item.Item;

public class ItemPalette extends Item implements IHasModel {
	public ItemPalette() {
		setRegistryName(Data.MODID + ":palette");
		setUnlocalizedName(Data.MODID + ".palette");
		setMaxStackSize(1);
		setCreativeTab(Data.TAB);
		Data.ITEMS.add(this);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	//TODO: Make palette functional
	/*
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hx, float hy, float hz) {
		Block block;
		int meta;
		y++;
		do{
			y--;
			block = world.getBlock(x,y,z);
			meta = world.getBlockMetadata(x,y,z);
		} while(block.equals(PlayerStatues.statue) && (meta&4)!=0);
		
		if(block != PlayerStatues.statue)
			return true;
		
		TileEntity te = world.getTileEntity(x, y, z);
		if (! (te instanceof TileEntityStatue))
			return true;
		TileEntityStatue statue=(TileEntityStatue) te;
		
		statue.block=Blocks.bedrock;
		stack.damageItem(1, player);
		world.markBlockForUpdate(x, y, z);
		
		stack.stackSize--;
		
		if(world.isRemote){
			statue.updateModel();
			GeneralStatueClient.spawnPaintEffect(world, x, y, z);
			world.playSound(x+0.5, y+0.5, z+0.5, "playerstatues:paint", 1.0f, 1.0f,true);
		}
			
		return true;
	}
	*/

}
