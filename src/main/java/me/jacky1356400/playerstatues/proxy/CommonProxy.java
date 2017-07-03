package me.jacky1356400.playerstatues.proxy;

import me.jacky1356400.playerstatues.PlayerStatues;
import me.jacky1356400.playerstatues.init.ModRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public static Configuration config;

	public void preInit(FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new ModRegistry());
	}

	public void init(FMLInitializationEvent e) {
		PlayerStatues.logger.info("proxy");
	}

	public void postInit(FMLPostInitializationEvent e) {
	}

}
