package me.jacky1356400.playerstatues.init;

import me.jacky1356400.playerstatues.item.ItemHammer;
import me.jacky1356400.playerstatues.item.ItemPalette;
import me.jacky1356400.playerstatues.util.Data;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRegistry {

    public static final Item HAMMER = new ItemHammer();
    public static final Item PALETTE = new ItemPalette();
    //public static final Block SHOWCASE = new BlockShowcase();

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
        GameRegistry.addShapedRecipe(new ResourceLocation(Data.MODID, "hammer"),
                new ResourceLocation(Data.MODID, "hammer"), new ItemStack(HAMMER),
                " Ii", " SI", "S  ", 'I', "ingotIron", 'i', "nuggetIron", 'S', "stickWood");
        GameRegistry.addShapedRecipe(new ResourceLocation(Data.MODID, "palette"),
                new ResourceLocation(Data.MODID, "palette"), new ItemStack(PALETTE),
                "GB", "RW", 'R', "dyeRed", 'G', "dyeGreen", 'B', "dyeBlue", 'W', "plankWood");
    }

}