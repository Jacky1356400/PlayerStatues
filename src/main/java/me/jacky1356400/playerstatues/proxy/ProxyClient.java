package me.jacky1356400.playerstatues.proxy;

import me.jacky1356400.playerstatues.blocks.tileentities.TileEntityShowcase;
import me.jacky1356400.playerstatues.blocks.tileentities.TileEntityStatue;
import me.jacky1356400.playerstatues.client.renderer.RenderShowcase;
import me.jacky1356400.playerstatues.client.renderer.RenderStatue;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class ProxyClient extends Proxy
{
	@Override
	public void registerRenderings()
	{
		TileEntityRendererDispatcher.instance.mapSpecialRenderers.put(TileEntityStatue.class, new RenderStatue());
		TileEntityRendererDispatcher.instance.mapSpecialRenderers.put(TileEntityShowcase.class, new RenderShowcase());
	}
}
