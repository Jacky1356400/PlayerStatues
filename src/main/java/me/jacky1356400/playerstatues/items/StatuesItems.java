package me.jacky1356400.playerstatues.items;

import com.theprogrammingturkey.gobblecore.items.BaseItem;
import com.theprogrammingturkey.gobblecore.items.IItemHandler;
import com.theprogrammingturkey.gobblecore.items.ItemLoader;

import me.jacky1356400.playerstatues.blocks.StatuesBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class StatuesItems implements IItemHandler
{
	public static BaseItem hammer;
	public static BaseItem itemPalette;
	public static Item itemStatue;
	public static Item itemShowcase;

	@Override
	public void registerItems(ItemLoader loader)
	{
		loader.setCreativeTab(CreativeTabs.TOOLS);
		loader.registerItem(hammer = new ItemMarteau());
		loader.registerItem(itemPalette = new ItemPalette());
		loader.registerItem(itemShowcase = new ItemShowcase(StatuesBlocks.showcase));
		// .setTextureName("playerstatues:itemshowcase").setUnlocalizedName("playerstatues.showcase");
	}

	@Override
	public void registerModels(ItemLoader loader)
	{

	}

}
