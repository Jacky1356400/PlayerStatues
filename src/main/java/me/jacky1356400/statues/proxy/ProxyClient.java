package me.jacky1356400.statues.proxy;

import me.jacky1356400.statues.blocks.tileentities.TileEntityShowcase;
import me.jacky1356400.statues.blocks.tileentities.TileEntityStatue;
import me.jacky1356400.statues.client.renderer.RenderShowcase;
import me.jacky1356400.statues.client.renderer.RenderStatue;
import me.jacky1356400.statues.client.renderer.RenderTextureFX;
import me.jacky1356400.statues.entities.EntityTextureFX;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ProxyClient extends Proxy
{
	@Override
	public void registerRenderings()
	{
		TileEntityRendererDispatcher.instance.mapSpecialRenderers.put(TileEntityStatue.class, new RenderStatue());
		TileEntityRendererDispatcher.instance.mapSpecialRenderers.put(TileEntityShowcase.class, new RenderShowcase());
	}
}
