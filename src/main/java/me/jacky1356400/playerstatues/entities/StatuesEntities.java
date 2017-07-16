package me.jacky1356400.playerstatues.entities;

import com.theprogrammingturkey.gobblecore.entity.EntityLoader;
import com.theprogrammingturkey.gobblecore.entity.IEntityHandler;

import me.jacky1356400.playerstatues.client.renderer.RenderTextureFX;

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
