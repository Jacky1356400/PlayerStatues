package me.jacky1356400.playerstatues;

import java.io.File;

import com.theprogrammingturkey.gobblecore.IModCore;
import com.theprogrammingturkey.gobblecore.blocks.BlockManager;
import com.theprogrammingturkey.gobblecore.items.ItemManager;
import com.theprogrammingturkey.gobblecore.proxy.ProxyManager;

import me.jacky1356400.playerstatues.blocks.StatuesBlocks;
import me.jacky1356400.playerstatues.blocks.containers.ContainerShowcase;
import me.jacky1356400.playerstatues.blocks.containers.ContainerStatue;
import me.jacky1356400.playerstatues.blocks.tileentities.TileEntityShowcase;
import me.jacky1356400.playerstatues.blocks.tileentities.TileEntityStatue;
import me.jacky1356400.playerstatues.client.gui.GuiSculpt;
import me.jacky1356400.playerstatues.client.gui.GuiShowcase;
import me.jacky1356400.playerstatues.client.gui.GuiStatue;
import me.jacky1356400.playerstatues.items.StatuesItems;
import me.jacky1356400.playerstatues.network.PacketHandler;
import me.jacky1356400.playerstatues.proxy.Proxy;
import me.jacky1356400.playerstatues.utilities.DummyContainer;
import me.jacky1356400.playerstatues.utilities.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = PlayerStatues.MODID, name = PlayerStatues.NAME, version = PlayerStatues.VERSION, dependencies = "required-after:gobblecore")
public class PlayerStatues implements IModCore
{
	public static final String MODID = "playerstatues";
	public static final String NAME = "PlayerStatues";
	public static final String VERSION = "1.0";

	static Configuration config;

	@Instance("PlayerStatues")
	public static PlayerStatues instance;

	@SidedProxy(clientSide = "me.jacky1356400.playerstatues.proxy.ProxyClient", serverSide = "me.jacky1356400.playerstatues.proxy.Proxy")
	public static Proxy proxy;

	public static PacketHandler packet;

	public static IIcon slotHand;

	public static GuiHandler guiShowcase;
	public static GuiHandler guiStatue;
	public static GuiHandler guiSculpt;

	public static String skinServerLocation;

	public static boolean debugImages;

	public PlayerStatues()
	{
		BlockManager.registerBlockHandler(new StatuesBlocks(), this);
		ItemManager.registerItemHandler(new StatuesItems(), this);
		// NetworkManager.registerNetworkHandler(handler, this);
		// packet = new PacketHandler("playerstatues", new Packets(), new Packets());
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		File configFile = event.getSuggestedConfigurationFile();
		config = new Configuration(configFile);
		config.load();

		ProxyManager.registerModProxy(proxy);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		skinServerLocation = config.get("general", "skin server location", "http://skins.minecraft.net/MinecraftSkins/", "Download skins from this path.").getString();
		debugImages = config.get("general", "debug skins", false, "Save generated skins to files.").getBoolean(false);

		/*
		 * LanguageRegistry.addName(statue, "Statue"); LanguageRegistry.addName(showcase, "Showcase"); LanguageRegistry.addName(itemShowcase, "Showcase");
		 * 
		 * LanguageRegistry.addName(hammer, "Hammer"); LanguageRegistry.addName(itemPalette, "Palette");
		 */

		GameRegistry.addShapedRecipe(new ItemStack(StatuesItems.hammer, 1), " I ", " SI", "S  ", 'S', Items.STICK, 'I', Items.IRON_INGOT);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesItems.itemShowcase, 1), "GGG", "W W", "S S", 'S', Items.STICK, 'W', Blocks.PLANKS, 'G', Blocks.GLASS_PANE);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesItems.itemPalette, 1), "GB", "RW", 'W', Blocks.PLANKS, 'R', new ItemStack(Items.DYE, 1, 1), 'G', new ItemStack(Items.DYE, 1, 2), 'B', new ItemStack(Items.DYE, 1, 4));

		// TODO
		// MinecraftForge.setBlockHarvestLevel(showcase, "axe", 0);

		guiShowcase = new GuiHandler("showcase")
		{
			@Override
			public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
			{
				TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

				if(!(tileEntity instanceof TileEntityShowcase))
					return null;

				TileEntityShowcase e = (TileEntityShowcase) tileEntity;

				return new ContainerShowcase(player.inventory, e);
			}

			@Override
			public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
			{
				TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

				if(!(tileEntity instanceof TileEntityShowcase))
					return null;

				TileEntityShowcase e = (TileEntityShowcase) tileEntity;

				return new GuiShowcase(player.inventory, e, world, x, y, z);
			}
		};

		guiStatue = new GuiHandler("statue")
		{
			@Override
			public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
			{
				TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

				if(!(tileEntity instanceof TileEntityStatue))
					return null;

				TileEntityStatue e = (TileEntityStatue) tileEntity;

				return new ContainerStatue(player.inventory, e);
			}

			@Override
			public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
			{
				TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

				if(!(tileEntity instanceof TileEntityStatue))
					return null;

				TileEntityStatue e = (TileEntityStatue) tileEntity;

				return new GuiStatue(player.inventory, e, world, x, y, z);
			}
		};

		guiSculpt = new GuiHandler("sculpt")
		{
			@Override
			public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
			{
				return new DummyContainer();
			}

			@Override
			public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
			{
				int face = MathHelper.floor_double((player.rotationYaw * 4F) / 360F + 0.5D) & 3;

				return new GuiSculpt(world, x, y, z, player, face);
			}
		};

		GuiHandler.register(this);

		// TODO
		// PacketHandler.register(this);

		config.save();
	}

	public static boolean canSculpt(IBlockState state, World world, BlockPos pos)
	{
		Block block = state.getBlock();
		if(block == null)
			return false;
		if(block.equals(Blocks.BEDROCK))
			return false;
		if(state.getMaterial() == Material.CIRCUITS)
			return false;
		if(state.getMaterial() == Material.FIRE)
			return false;
		if(state.getMaterial() == Material.LAVA)
			return false;
		if(state.getMaterial() == Material.WATER)
			return false;
		if(world.getTileEntity(pos) != null)
			return false;

		return true;
	}

	@Override
	public String getModID()
	{
		return MODID;
	}

	@Override
	public String getName()
	{
		return NAME;
	}

	@Override
	public String getVersion()
	{
		return VERSION;
	}
}
