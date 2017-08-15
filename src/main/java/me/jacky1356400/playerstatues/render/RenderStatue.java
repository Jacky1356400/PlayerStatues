/**
 * Render class for the statue
 */

package me.jacky1356400.playerstatues.render;

import me.jacky1356400.playerstatues.entity.EntityStatuePlayer;
import me.jacky1356400.playerstatues.tile.TileEntityStatue;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class RenderStatue extends TileEntitySpecialRenderer {

	RenderPlayerStatue renderer=new RenderPlayerStatue(new RenderManager());
	
	public RenderStatue() {
		renderer.setRenderManager(RenderManager.instance);
	}

	@Override
    public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		TileEntityStatue tile=(TileEntityStatue)te;
		int meta = tile.getWorld().getBlockState(new BlockPos(x, y, z))
                .getBlock().getMetaFromState(tile.getWorld().getBlockState(new BlockPos(x, y, z)));
		
		if((meta&4)!=0)
			return;

		EntityStatuePlayer player=tile.getStatue();
		if(player==null) return;

		GL11.glPushMatrix();
		
		if(meta==0) meta=2;
		else if(meta==2) meta=0;
		
		GL11.glTranslatef(0.5f, 1.65f, 0.5f);
		GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(90*meta, 0.0F, 1.0F, 0.0F);
		
		renderer.doRender(player, 0, 0, 0, 0, partialTicks);
		GL11.glPopMatrix();
	}

}
