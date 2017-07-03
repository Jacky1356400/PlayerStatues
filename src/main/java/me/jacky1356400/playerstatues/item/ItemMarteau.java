/**
 * Item Class for the hammer
 */

package me.jacky1356400.playerstatues.item;

import me.jacky1356400.playerstatues.helper.GeneralStatueClient;
import me.jacky1356400.playerstatues.PlayerStatues;
import me.jacky1356400.playerstatues.block.BlockStatue;
import me.jacky1356400.playerstatues.gui.GuiSculpt;
import me.jacky1356400.playerstatues.tile.TileEntityStatue;
import me.jacky1356400.playerstatues.util.Data;
import me.jacky1356400.playerstatues.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMarteau extends Item implements IHasModel {
	public ItemMarteau() {
		setRegistryName(Data.MODID + ":hammer");
		setUnlocalizedName(Data.MODID + ".hammer");
		setMaxStackSize(1);
		setMaxDamage(2);
		setCreativeTab(Data.TAB);
		Data.ITEMS.add(this);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

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

}