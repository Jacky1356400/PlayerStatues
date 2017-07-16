package me.jacky1356400.playerstatues.client.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import me.jacky1356400.playerstatues.entities.EntityTextureFX;

public class RenderTextureFX extends Render<EntityTextureFX>
{

	protected RenderTextureFX(RenderManager renderManager)
	{
		super(renderManager);
	}

	@Override
	public void doRender(EntityTextureFX entity, double x, double y, double z, float unk, float frame)
	{
		EntityTextureFX e = (EntityTextureFX) entity;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, e.texture.getGlTextureId());
		Tessellator tessellator = Tessellator.getInstance();

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

		float u0 = e.pu;
		float u1 = e.pu + e.scaleU;
		float v0 = e.pv;
		float v1 = e.pv + e.scaleV;
		double s = 0.14;
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV(s, s, 0.0D, u1, v1);
		tessellator.addVertexWithUV(0.0, s, 0.0D, u0, v1);
		tessellator.addVertexWithUV(0.0, 0.0, 0.0D, u0, v0);
		tessellator.addVertexWithUV(s, 0.0, 0.0D, u1, v0);
		tessellator.draw();

		GL11.glPopMatrix();

	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTextureFX entity)
	{
		return null;
	}

}
