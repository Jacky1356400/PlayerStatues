package me.jacky1356400.statues.utilities;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public abstract class GuiHandler implements Comparable<GuiHandler>
{
	private static ArrayList<GuiHandler> items = new ArrayList<GuiHandler>();

	private int index;
	private Object mod;
	private String name;

	public GuiHandler(String n)
	{
		items.add(this);
		name = n;
	}

	public void open(EntityPlayer player, World world, BlockPos pos)
	{
		player.openGui(mod, index, world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public int compareTo(GuiHandler a)
	{
		return name.compareTo(a.name);
	}

	public static void register(Object mod)
	{
		Collections.sort(items);
		int index = 0;

		for(GuiHandler h : items)
		{
			h.mod = mod;
			h.index = index++;
		}

		NetworkRegistry.INSTANCE.registerGuiHandler(mod, new IGuiHandler()
		{
			@Override
			public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
			{
				if(id < 0 || id >= items.size())
				{
					return null;
				}

				return items.get(id).getServerGuiElement(id, player, world, x, y, z);
			}

			@Override
			public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
			{
				if(id < 0 || id >= items.size())
				{
					return null;
				}

				return items.get(id).getClientGuiElement(id, player, world, x, y, z);
			}
		});
	}

	public abstract Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);

	public abstract Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);
}
