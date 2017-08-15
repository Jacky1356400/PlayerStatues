package me.jacky1356400.playerstatues.util;

import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class StatueTextureStatic extends AbstractTexture {

	ResourceLocation location;
	IImageBuffer effect;

	public StatueTextureStatic(ResourceLocation loc,IImageBuffer e) {
		location=loc;
		effect = e;
	}

	@Override
	public void loadTexture(IResourceManager manager) {
		InputStream inputstream = null;

		try {
			IResource resource = manager.getResource(location);
			inputstream = resource.getInputStream();
			BufferedImage bufferedimage = effect.parseUserSkin(ImageIO.read(inputstream));
			
			boolean blurred = false;
			boolean clamped = false;

			if (resource.hasMetadata()) {
				try {
					TextureMetadataSection texturemetadatasection = (TextureMetadataSection) resource.getMetadata("texture");

					if (texturemetadatasection != null) {
						blurred = texturemetadatasection.getTextureBlur();
						clamped = texturemetadatasection.getTextureClamp();
					}
				} catch (RuntimeException runtimeexception) {
					System.out.println("Failed reading metadata of: " + location);
					runtimeexception.printStackTrace();
				}
			}

			TextureUtil.uploadTextureImageAllocate(getGlTextureId(), bufferedimage, blurred, clamped);
		} catch(IOException e) { e.printStackTrace(); }
		finally {
			if (inputstream != null) {
				try {
					inputstream.close();
				} catch(Exception e) { }
			}
		}
	}

}
