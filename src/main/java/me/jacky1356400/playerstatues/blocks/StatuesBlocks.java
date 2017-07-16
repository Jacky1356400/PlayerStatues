package me.jacky1356400.playerstatues.blocks;

import com.theprogrammingturkey.gobblecore.blocks.BlockLoader;
import com.theprogrammingturkey.gobblecore.blocks.IBlockHandler;

import me.jacky1356400.playerstatues.blocks.tileentities.TileEntityShowcase;
import me.jacky1356400.playerstatues.blocks.tileentities.TileEntityStatue;
import net.minecraft.block.Block;

public class StatuesBlocks implements IBlockHandler
{
	public static Block statue;
	public static Block showcase;

	@Override
	public void registerBlocks(BlockLoader loader)
	{
		loader.registerBlock(statue = new BlockStatue(), TileEntityStatue.class, "statue");
		loader.registerBlock(showcase = new BlockShowcase(), TileEntityShowcase.class, "showcase");
	}

	@Override
	public void registerModels(BlockLoader loader)
	{

	}
}