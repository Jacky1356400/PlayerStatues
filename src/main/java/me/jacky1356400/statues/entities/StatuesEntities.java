package me.jacky1356400.statues.entities;

import com.theprogrammingturkey.gobblecore.entity.EntityLoader;
import com.theprogrammingturkey.gobblecore.entity.IEntityHandler;

import me.jacky1356400.statues.client.renderer.RenderTextureFX;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class StatuesEntities implements IEntityHandler
{

	@Override
	public void registerEntities(EntityLoader loader)
	{
		loader.registerEntity(name, entityClass, trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	@Override
	public void registerRenderings(EntityLoader loader)
	{
		loader.registerEntityRendering(EntityTextureFX.class, RenderTextureFX.class);
	}

}
