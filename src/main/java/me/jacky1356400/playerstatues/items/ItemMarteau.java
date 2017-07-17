/**
 * Item Class for the hammer
 */

package me.jacky1356400.playerstatues.items;

import com.theprogrammingturkey.gobblecore.items.BaseItem;

import me.jacky1356400.playerstatues.GeneralStatueClient;
import me.jacky1356400.playerstatues.PlayerStatues;
import me.jacky1356400.playerstatues.blocks.BlockStatue;
import me.jacky1356400.playerstatues.blocks.tileentities.TileEntityStatue;
import me.jacky1356400.playerstatues.client.gui.GuiSculpt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMarteau extends BaseItem
{
	public ItemMarteau()
	{
		super("marteau", 1);
	}

	@Override
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote && world.getTileEntity(pos) instanceof TileEntityStatue)
		{
			int ox = pos.getX();
			int oy = pos.getY();
			int oz = pos.getZ();

			while(BlockStatue.isStatue(world, pos) && !BlockStatue.isMainBlock(world, pos))
				pos = pos.add(0, -1, 0);
			if(!BlockStatue.isStatue(world, pos))
				return EnumActionResult.PASS;

			TileEntityStatue statue = (TileEntityStatue) world.getTileEntity(pos);

			GuiSculpt.pose.copyFrom(statue.pose);
			GeneralStatueClient.spawnCopyEffect(world, ox, oy, oz, facing, hitX, hitY, hitZ, statue);

			return EnumActionResult.PASS;
		}

		IBlockState state = world.getBlockState(pos);
		if(!PlayerStatues.canSculpt(state, player.worldObj, pos))
			return EnumActionResult.FAIL;
		if(world.isRemote)
			return EnumActionResult.PASS;

		IBlockState stateAbove = world.getBlockState(pos.add(0, 1, 0));
		IBlockState stateBelow = world.getBlockState(pos.add(0, -1, 0));

		if(state.equals(stateAbove) && PlayerStatues.canSculpt(state, player.worldObj, pos.add(0, 1, 0)))
		{
			PlayerStatues.guiSculpt.open(player, world, pos);
			return EnumActionResult.PASS;
		}

		if(state.equals(stateBelow) && PlayerStatues.canSculpt(state, player.worldObj, pos.add(0, -1, 0)))
		{
			PlayerStatues.guiSculpt.open(player, world, pos.add(0, -1, 0));
			return EnumActionResult.PASS;
		}

		return EnumActionResult.PASS;
	}

}