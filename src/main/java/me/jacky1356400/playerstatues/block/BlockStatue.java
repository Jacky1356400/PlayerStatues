/**
 * Block class for the statue
 */

package me.jacky1356400.playerstatues.block;

import me.jacky1356400.playerstatues.PlayerStatues;
import me.jacky1356400.playerstatues.tile.TileEntityStatue;
import me.jacky1356400.playerstatues.util.IHasModel;
import me.jacky1356400.playerstatues.util.ItemUtils;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockStatue extends BlockContainer implements IHasModel {

	public BlockStatue(Material material) {
		super(material);
        setRegistryName(PlayerStatues.MODID + ":statue");
        setUnlocalizedName(PlayerStatues.MODID + ".statue");
        setHardness(1.0F);
        setResistance(1.0F);
		setLightOpacity(0);
        setSoundType(SoundType.STONE);
        PlayerStatues.BLOCKS.add(this);
	}

    @Override
	public int quantityDropped(Random par1Random){
        return 0;
    }

	@Override @SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override @SuppressWarnings("deprecation")
    public boolean isNormalCube(IBlockState state) {
		return false;
	}
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
    @Override @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	int meta = state.getBlock().getMetaFromState(state);
    	
    	if((meta&4)==0){
            return new AxisAlignedBB(0.1F, 0F, 0.1F, 0.9F, 2F, 0.9F);
    	} else{
            return new AxisAlignedBB(0.1F, -1F, 0.1F, 0.9F, 1F, 0.9F);
    	}
    }
    
    public static boolean isMainBlock(IBlockAccess world, BlockPos pos, IBlockState state){
    	return (state.getBlock().getMetaFromState(state) & 4) == 0;
    }
    
    public static boolean isStatue(IBlockAccess world, BlockPos pos) {
    	return world.getBlockState(pos) instanceof BlockStatue;
    }
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entityplayer, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        int x=pos.getX(),y=pos.getY(),z=pos.getZ();
        if(world.isRemote) return true;
   	
    	if(! isMainBlock(world, pos, state))
    		y--;
		
		TileEntityStatue statue = (TileEntityStatue) world.getTileEntity(pos);
		if (statue != null)
			PlayerStatues.guiStatue.open(entityplayer, world, x, y, z);
		
		return true;
	}
	
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        int x=pos.getX(),y=pos.getY(),z=pos.getZ();
        int meta = state.getBlock().getMetaFromState(state);
		if((meta&4)==0){
		    world.setBlockToAir(new BlockPos(x, y+1, z));
			TileEntity tile = world.getTileEntity(pos);
			if(tile instanceof TileEntityStatue) {
				ItemUtils.dropItems(world, pos, (TileEntityStatue)tile);
			}
		} else{
            world.setBlockToAir(new BlockPos(x, y-1, z));
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int metadata) {
		return new TileEntityStatue();
	}

}
