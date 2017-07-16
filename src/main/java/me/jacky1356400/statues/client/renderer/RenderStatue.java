/**
 * Render class for the statue
 */

package me.jacky1356400.statues.client.renderer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import org.lwjgl.opengl.GL11;

import me.jacky1356400.statues.blocks.tileentities.TileEntityStatue;
import me.jacky1356400.statues.entities.EntityStatuePlayer;

public class RenderStatue extends TileEntitySpecialRenderer<TileEntityStatue>
{
	RenderPlayerStatue renderer = new RenderPlayerStatue();

	@Override
	public void renderTileEntityAt(TileEntityStatue tile, double x, double y, double z, float partialTicks, int destroyStage)
	{

		IBlockState state = tile.getWorld().getBlockState(new BlockPos(x, y, z));
		int meta = state.getBlock().getMetaFromState(state);

		if((meta & 4) != 0)
			return;

		EntityStatuePlayer player = tile.getStatue();
		if(player == null)
			return;

		GL11.glPushMatrix();

		if(meta == 0)
			meta = 2;
		else if(meta == 2)
			meta = 0;

		GL11.glTranslatef(0.5f, 1.65f, 0.5f);
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(90 * meta, 0.0F, 1.0F, 0.0F);

		renderer.doRender(player, 0, 0, 0, 0, frame);
		GL11.glPopMatrix();
	}
}
