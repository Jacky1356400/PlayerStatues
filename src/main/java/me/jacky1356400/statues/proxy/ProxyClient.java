package me.jacky1356400.statues.proxy;

import me.jacky1356400.statues.blocks.tileentities.TileEntityShowcase;
import me.jacky1356400.statues.blocks.tileentities.TileEntityStatue;
import me.jacky1356400.statues.client.renderer.RenderShowcase;
import me.jacky1356400.statues.client.renderer.RenderStatue;
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
