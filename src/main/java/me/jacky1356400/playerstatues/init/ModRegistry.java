package me.jacky1356400.playerstatues.init;

import me.jacky1356400.playerstatues.helper.RecipeHelper;
import me.jacky1356400.playerstatues.item.ItemHammer;
import me.jacky1356400.playerstatues.item.ItemPalette;
import me.jacky1356400.playerstatues.util.Data;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModRegistry {

    public static final Item HAMMER = new ItemHammer();
    public static final Item PALETTE = new ItemPalette();
    //public static final Block SHOWCASE = new BlockShowcase();

    @SuppressWarnings("deprecation")
    private static void initRecipes() {
        String I = "ingotIron";
        String i = "nuggetIron";
        String S = "stickWood";
        String R = "dyeRed";
        String G = "dyeGreen";
        String B = "dyeBlue";
        String W = "plankWood";
        RecipeHelper.addShaped(HAMMER, 3, 3, null, I, i, null, S, I, S, null, null);
        RecipeHelper.addShaped(PALETTE, 2, 2, G, B, R, W);
    }

    @SubscribeEvent
    public void onBlockRegistry(RegistryEvent.Register<Block> e) {
        e.getRegistry().registerAll(Data.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public void onItemRegistry(RegistryEvent.Register<Item> e) {
        e.getRegistry().registerAll(Data.ITEMS.toArray(new Item[0]));
        for (Block block : Data.BLOCKS)
            e.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    @SubscribeEvent
    public void onRecipeRegistry(RegistryEvent.Register<IRecipe> e) {
        initRecipes();
        e.getRegistry().registerAll(Data.RECIPES.toArray(new IRecipe[0]));
    }

}