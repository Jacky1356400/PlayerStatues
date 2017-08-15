package me.jacky1356400.playerstatues;

import me.jacky1356400.playerstatues.item.ItemMarteau;
import me.jacky1356400.playerstatues.item.ItemPalette;
import me.jacky1356400.playerstatues.proxy.CommonProxy;
import me.jacky1356400.playerstatues.util.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = PlayerStatues.MODID, name = PlayerStatues.MODNAME, version = PlayerStatues.VERSION)
public class PlayerStatues {

	@Mod.Instance("PlayerStatues")
	public static PlayerStatues instance;

    public static final String VERSION = "1.0";
    public static final String MODID = "playerstatues";
    public static final String MODNAME = "PlayerStatues";
    public static final CreativeTabs TAB = new CreativeTabs(MODID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(PALETTE);
        }
    };
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final Item HAMMER = new ItemMarteau();
    public static final Item PALETTE = new ItemPalette();

	@SidedProxy(clientSide = "ClientProxy", serverSide = "CommonProxy")
	public static CommonProxy proxy;

	public static Block			statue;
	public static Block			showcase;
	public static Item			itemShowcase;

	public static GuiHandler	guiShowcase;
	public static GuiHandler	guiStatue;
	public static GuiHandler	guiSculpt;

	public static String skinServerLocation;
	
	public static boolean		debugImages;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
 	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
        proxy.init(event);
	}

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

	@SuppressWarnings("deprecation")
    public static boolean canSculpt(Block block, World world, IBlockState state) {
        if (block == null) return false;
        if (block.equals(Blocks.BEDROCK)) return false;
        if (block.getMaterial(state) == Material.CIRCUITS) return false;
        if (block.getMaterial(state) == Material.FIRE) return false;
        if (block.getMaterial(state) == Material.LAVA) return false;
        if (block.getMaterial(state) == Material.WATER) return false;
        if (world.getTileEntity(BlockPos.ORIGIN) != null) return false;
        return true;
    }

}
