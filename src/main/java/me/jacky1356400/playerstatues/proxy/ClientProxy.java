package me.jacky1356400.playerstatues.proxy;

import me.jacky1356400.playerstatues.entity.EntityTextureFX;
import me.jacky1356400.playerstatues.render.RenderShowcase;
import me.jacky1356400.playerstatues.render.RenderStatue;
import me.jacky1356400.playerstatues.render.RenderTextureFX;
import me.jacky1356400.playerstatues.tile.TileEntityShowcase;
import me.jacky1356400.playerstatues.tile.TileEntityStatue;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit() {

	}

	@Override
	public void init() {

		TileEntityRendererDispatcher.instance.mapSpecialRenderers.put(TileEntityStatue.class, new RenderStatue());
		TileEntityRendererDispatcher.instance.mapSpecialRenderers.put(TileEntityShowcase.class, new RenderShowcase());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityTextureFX.class, new RenderTextureFX());

	}
}
