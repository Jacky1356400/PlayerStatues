package me.jacky1356400.playerstatues.util;

import me.jacky1356400.playerstatues.init.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final List<Block> BLOCKS = new ArrayList<Block>();
    public static final List<IRecipe> RECIPES = new ArrayList<IRecipe>();
    public static final String VERSION = "1.0";
    public static final String MODID = "playerstatues";
    public static final String MODNAME = "PlayerStatues";
    public static final String DEPENDS = "required-after:forge@14.21.0.2355;";
    public static final CreativeTabs TAB = new CreativeTabs(MODID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModRegistry.PALETTE);
        }
    };

}
