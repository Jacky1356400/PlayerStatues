package me.jacky1356400.playerstatues.proxy;

import me.jacky1356400.playerstatues.PlayerStatues;
import me.jacky1356400.playerstatues.entity.EntityTextureFX;
import me.jacky1356400.playerstatues.render.RenderShowcase;
import me.jacky1356400.playerstatues.render.RenderStatue;
import me.jacky1356400.playerstatues.render.RenderTextureFX;
import me.jacky1356400.playerstatues.tile.TileEntityShowcase;
import me.jacky1356400.playerstatues.tile.TileEntityStatue;
import me.jacky1356400.playerstatues.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

	@Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(this);
	}

    @SubscribeEvent
    public void onModelRegistry(ModelRegistryEvent event) {
        for (Item item : PlayerStatues.ITEMS)
            if (item instanceof IHasModel)
                ((IHasModel) item).initModel(event);
        for (Block block : PlayerStatues.BLOCKS)
            if (block instanceof IHasModel)
                ((IHasModel) block).initModel(event);
    }

	@Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
		TileEntityRendererDispatcher.instance.mapSpecialRenderers.put(TileEntityStatue.class, new RenderStatue());
		TileEntityRendererDispatcher.instance.mapSpecialRenderers.put(TileEntityShowcase.class, new RenderShowcase());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityTextureFX.class, new RenderTextureFX());
	}

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

}
