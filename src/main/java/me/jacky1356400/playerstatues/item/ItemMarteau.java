/**
 * Item Class for the hammer
 */

package me.jacky1356400.playerstatues.item;

import me.jacky1356400.playerstatues.PlayerStatues;
import me.jacky1356400.playerstatues.block.BlockStatue;
import me.jacky1356400.playerstatues.gui.GuiSculpt;
import me.jacky1356400.playerstatues.tile.TileEntityStatue;
import me.jacky1356400.playerstatues.util.GeneralStatueClient;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMarteau extends Item {

	public ItemMarteau() {
		super();
        setMaxDamage(2);
		maxStackSize=1;
		setCreativeTab(PlayerStatues.TAB);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        int x=pos.getX(),y=pos.getY(),z=pos.getZ();
	    if (world.isRemote && world.getTileEntity(pos) instanceof TileEntityStatue){
	    	int ox=x;int oy=y;int oz=z;
			
			while(BlockStatue.isStatue(world, pos) && ! BlockStatue.isMainBlock(world, pos, world.getBlockState(pos))) y--;
	    	if(! BlockStatue.isStatue(world, pos)) return EnumActionResult.SUCCESS;
			
			TileEntityStatue statue=(TileEntityStatue) world.getTileEntity(pos);

			GuiSculpt.pose.copyFrom(statue.pose);
			GeneralStatueClient.spawnCopyEffect(world, ox, oy, oz, facing, hitX, hitY, hitZ, statue);
			
			return EnumActionResult.SUCCESS;
		}
		
		Block block = world.getBlockState(pos).getBlock();
		if(! PlayerStatues.canSculpt(block,player.world,world.getBlockState(pos)))
			return EnumActionResult.FAIL;
        if (world.isRemote)
            return EnumActionResult.SUCCESS;
		
		int meta = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
		
		if(block.equals(world.getBlockState(new BlockPos(x, y+1, z))) && meta == world.getBlockState(new BlockPos(x, y+1, z)).getBlock().getMetaFromState(world.getBlockState(new BlockPos(x, y+1, z))) && PlayerStatues.canSculpt(block,player.world,world.getBlockState(new BlockPos(x, y+1, z)))){
			PlayerStatues.guiSculpt.open(player, world, x, y, z);
			return EnumActionResult.SUCCESS;
		}
		
		if(block.equals(world.getBlockState(new BlockPos(x, y-1, z))) && meta == world.getBlockState(new BlockPos(x, y-1, z)).getBlock().getMetaFromState(world.getBlockState(new BlockPos(x, y-1, z))) && PlayerStatues.canSculpt(block,player.world,world.getBlockState(new BlockPos(x, y-1, z)))){
			PlayerStatues.guiSculpt.open(player, world, x, y-1, z);
			return EnumActionResult.SUCCESS;
		}
				
		return EnumActionResult.SUCCESS;
	}

}