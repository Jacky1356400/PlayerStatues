package me.jacky1356400.statues.items;

import com.theprogrammingturkey.gobblecore.items.BaseItem;

import me.jacky1356400.statues.GeneralStatueClient;
import me.jacky1356400.statues.Statues;
import me.jacky1356400.statues.blocks.tileentities.TileEntityStatue;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPalette extends BaseItem
{
	public ItemPalette()
	{
		super("palette");
	}

	@Override
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		Block block;
		int meta;
		y++;
		do
		{
			y--;
			block = world.getBlock(x, y, z);
			meta = world.getBlockMetadata(x, y, z);
		} while(block.equals(Statues.statue) && (meta & 4) != 0);

		if(block != Statues.statue)
			return true;

		TileEntity te = world.getTileEntity(x, y, z);
		if(!(te instanceof TileEntityStatue))
			return true;
		TileEntityStatue statue = (TileEntityStatue) te;

		statue.block = Blocks.bedrock;
		stack.damageItem(1, player);
		world.markBlockForUpdate(x, y, z);

		stack.stackSize--;

		if(world.isRemote)
		{
			statue.updateModel();
			GeneralStatueClient.spawnPaintEffect(world, x, y, z);
			world.playSound(x + 0.5, y + 0.5, z + 0.5, "statues:paint", 1.0f, 1.0f, true);
		}

		return true;
	}

}
