package me.jacky1356400.statues.proxy;

import com.theprogrammingturkey.gobblecore.proxy.IBaseProxy;

import net.minecraft.entity.player.EntityPlayer;

public class Proxy implements IBaseProxy
{

	@Override
	public boolean isClient()
	{
		return false;
	}

	@Override
	public void registerGuis()
	{

	}

	@Override
	public void registerRenderings()
	{

	}

	@Override
	public void registerEvents()
	{

	}

	@Override
	public EntityPlayer getClientPlayer()
	{
		return null;
	}
}
