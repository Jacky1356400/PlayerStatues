package me.jacky1356400.playerstatues;

import me.jacky1356400.playerstatues.block.BlockShowcase;
import me.jacky1356400.playerstatues.block.BlockStatue;
import me.jacky1356400.playerstatues.container.ContainerShowcase;
import me.jacky1356400.playerstatues.container.ContainerStatue;
import me.jacky1356400.playerstatues.gui.GuiSculpt;
import me.jacky1356400.playerstatues.gui.GuiShowcase;
import me.jacky1356400.playerstatues.gui.GuiStatue;
import me.jacky1356400.playerstatues.item.ItemMarteau;
import me.jacky1356400.playerstatues.item.ItemPalette;
import me.jacky1356400.playerstatues.item.ItemShowcase;
import me.jacky1356400.playerstatues.proxy.CommonProxy;
import me.jacky1356400.playerstatues.tile.TileEntityShowcase;
import me.jacky1356400.playerstatues.tile.TileEntityStatue;
import me.jacky1356400.playerstatues.util.DummyContainer;
import me.jacky1356400.playerstatues.util.GuiHandler;
import me.jacky1356400.playerstatues.util.Packets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import pl.asie.lib.network.PacketHandler;

import java.io.File;

@Mod(modid = PlayerStatues.MODID, name = PlayerStatues.MODNAME, version = PlayerStatues.VERSION)
public class PlayerStatues {

    public static Configuration config;

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
    public static final Item HAMMER = new ItemMarteau();
    public static final Item PALETTE = new ItemPalette();

	@SidedProxy(clientSide = "ClientProxy", serverSide = "CommonProxy")
	public static CommonProxy proxy;

	public static PacketHandler packet;
	
	public static Block			statue;
	public static Block			showcase;
	public static Item			itemStatue;
	public static Item			itemShowcase;

	public static GuiHandler	guiShowcase;
	public static GuiHandler	guiStatue;
	public static GuiHandler	guiSculpt;

	public static String skinServerLocation;
	
	public static boolean		debugImages;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		File configFile=event.getSuggestedConfigurationFile();
		config = new Configuration(configFile);
		config.load();

		statue				= new BlockStatue(Material.ROCK).setHardness(1F).setResistance(1F).setBlockName("playerstatues.statue").setStepSound(Block.soundTypeStone);
		showcase			= new BlockShowcase(Material.WOOD).setHardness(1F).setResistance(1F).setBlockName("playerstatues.showcase").setStepSound(Block.soundTypeWood);
        HAMMER				= new ItemMarteau().setTextureName("playerstatues:marteau").setUnlocalizedName("playerstatues.marteau");
		itemShowcase		= new ItemShowcase(showcase).setTextureName("playerstatues:itemshowcase").setUnlocalizedName("playerstatues.showcase");
		PALETTE			= new ItemPalette().setTextureName("playerstatues:palette").setUnlocalizedName("playerstatues.palette");
		
		GameRegistry.registerBlock(statue, "playerstatues.statue");
		GameRegistry.registerBlock(showcase, "playerstatues.showcase");
		GameRegistry.registerItem(HAMMER, "playerstatues.marteau");
		GameRegistry.registerItem(itemShowcase, "playerstatues.item.showcase");
		GameRegistry.registerItem(PALETTE, "playerstatues.item.palette");
		
		proxy.preInit();
 	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		skinServerLocation = config.get("general", "skin server location", "http://skins.minecraft.net/MinecraftSkins/", "Download skins from this path.").getString();
		debugImages = config.get("general", "debug skins", false, "Save generated skins to files.").getBoolean(false);
		
		packet = new PacketHandler("playerstatues", new Packets(), new Packets());
		
        GameRegistry.registerTileEntity(TileEntityStatue.class, "TileEntityStatue");
        GameRegistry.registerTileEntity(TileEntityShowcase.class, "TileEntityShowcase");
        
		GameRegistry.addShapedRecipe(new ResourceLocation(MODID, "hammer"),
                new ResourceLocation(MODID, "hammer"), new ItemStack(HAMMER, 1),
                " I ", " SI", "S  ", 'S', "stickWood", 'I', "ingotIron");
		GameRegistry.addShapedRecipe(new ResourceLocation(MODID, "showcase"),
                new ResourceLocation(MODID, "showcase"), new ItemStack(itemShowcase, 1),
                "GGG", "W W", "S S", 'S', "stickWood", 'W', "plankWood", 'G', Blocks.GLASS_PANE);
		GameRegistry.addShapedRecipe(new ResourceLocation(MODID, "palette"),
                new ResourceLocation(MODID, "palette"), new ItemStack(PALETTE, 1),
                "GB", "RW", 'W', "plankWood", 'R', "dyeRed", 'G', "dyeGreen", 'B', "dyeBlue");

        guiShowcase=new GuiHandler("showcase"){
			@Override
			public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

		        if(! (tileEntity instanceof TileEntityShowcase))
		        	return null;
		        
		        TileEntityShowcase e=(TileEntityShowcase) tileEntity;
		        
		        return new ContainerShowcase(e, player.inventory);
			}

			@Override
			public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
                TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

		        if(! (tileEntity instanceof TileEntityShowcase))
		        	return null;
		        
		        TileEntityShowcase e=(TileEntityShowcase) tileEntity;

                return new GuiShowcase(player.inventory,e,world,x,y,z);
			}
		};
        
        guiStatue=new GuiHandler("statue"){
			@Override
			public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

		        if(! (tileEntity instanceof TileEntityStatue))
		        	return null;
		        
		        TileEntityStatue e=(TileEntityStatue) tileEntity;
		        
		        return new ContainerStatue(player.inventory, e);
			}

			@Override
			public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
                TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

		        if(! (tileEntity instanceof TileEntityStatue))
		        	return null;
		        
		        TileEntityStatue e=(TileEntityStatue) tileEntity;

                return new GuiStatue(player.inventory,e,world,x,y,z);
			}
		};
		
		guiSculpt=new GuiHandler("sculpt"){
			@Override
			public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		        return new DummyContainer();
			}

			@Override
			public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
				int face = MathHelper.floor((player.rotationYaw * 4F) / 360F + 0.5D) & 3;

                return new GuiSculpt(world,x,y,z,player,face);
			}
		};

		GuiHandler.register(this);

        proxy.init();
        
        config.save();
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
