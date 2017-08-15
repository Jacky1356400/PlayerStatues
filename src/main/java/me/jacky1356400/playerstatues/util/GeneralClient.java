package me.jacky1356400.playerstatues.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Random;

public class GeneralClient {

	public static Random rand = new Random();

	public static HashMap<String,ResourceLocation> resources= new HashMap<>();

	public static void bind(String textureName) {
		ResourceLocation res = resources.get(textureName);
		
		if(res == null) {
			res = new ResourceLocation(textureName);
			resources.put(textureName, res);
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(res);
	}

}
