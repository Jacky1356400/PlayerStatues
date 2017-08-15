package me.jacky1356400.playerstatues.proxy;

import me.jacky1356400.playerstatues.PlayerStatues;
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
import me.jacky1356400.playerstatues.tile.TileEntityShowcase;
import me.jacky1356400.playerstatues.tile.TileEntityStatue;
import me.jacky1356400.playerstatues.util.DummyContainer;
import me.jacky1356400.playerstatues.util.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

public class CommonProxy {

    public static Configuration config;

    public void preInit(FMLPreInitializationEvent event) {
        File configFile = event.getSuggestedConfigurationFile();
        config = new Configuration(configFile);
        config.load();
        MinecraftForge.EVENT_BUS.register(this);
	}

    @SubscribeEvent
    public void onBlockRegistry(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockStatue(Material.ROCK));
        event.getRegistry().register(new BlockShowcase(Material.WOOD));
    }

    @SubscribeEvent
    public void onItemRegistry(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemMarteau());
        event.getRegistry().register(new ItemShowcase());
        event.getRegistry().register(new ItemPalette());
    }

    @SubscribeEvent
    public void onRecipeRegistry(RegistryEvent.Register<IRecipe> event) {
        GameRegistry.addShapedRecipe(new ResourceLocation(PlayerStatues.MODID, "hammer"),
                new ResourceLocation(PlayerStatues.MODID, "hammer"), new ItemStack(PlayerStatues.HAMMER, 1),
                " Ii", " SI", "S  ", 'S', "stickWood", 'I', "ingotIron", 'i', "nuggetIron");
        GameRegistry.addShapedRecipe(new ResourceLocation(PlayerStatues.MODID, "showcase"),
                new ResourceLocation(PlayerStatues.MODID, "showcase"), new ItemStack(PlayerStatues.itemShowcase, 1),
                "GGG", "W W", "S S", 'S', "stickWood", 'W', "plankWood", 'G', Blocks.GLASS_PANE);
        GameRegistry.addShapedRecipe(new ResourceLocation(PlayerStatues.MODID, "palette"),
                new ResourceLocation(PlayerStatues.MODID, "palette"), new ItemStack(PlayerStatues.PALETTE, 1),
                "GB", "RW", 'W', "plankWood", 'R', "dyeRed", 'G', "dyeGreen", 'B', "dyeBlue");
    }

	public void init(FMLInitializationEvent event) {
        PlayerStatues.skinServerLocation = config.get("general", "skin server location", "http://skins.minecraft.net/MinecraftSkins/", "Download skins from this path.").getString();
        PlayerStatues.debugImages = config.get("general", "debug skins", false, "Save generated skins to files.").getBoolean(false);

        GameRegistry.registerTileEntity(TileEntityStatue.class, "TileEntityStatue");
        GameRegistry.registerTileEntity(TileEntityShowcase.class, "TileEntityShowcase");

        PlayerStatues.guiShowcase = new GuiHandler("showcase"){
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

        PlayerStatues.guiStatue = new GuiHandler("statue"){
            @Override
            public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
                TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

                if(! (tileEntity instanceof TileEntityStatue))
                    return null;

                TileEntityStatue e = (TileEntityStatue)tileEntity;

                return new ContainerStatue(e, player.inventory);
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

        PlayerStatues.guiSculpt = new GuiHandler("sculpt"){
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
        config.save();
	}

    public void postInit(FMLPostInitializationEvent event) {
    }

}
