/**
 * Block class for the statue
 */

package me.jacky1356400.playerstatues.block;

import me.jacky1356400.playerstatues.PlayerStatues;
import me.jacky1356400.playerstatues.tile.TileEntityShowcase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pl.asie.lib.util.ItemUtils;

public class BlockShowcase extends BlockContainer {
	public BlockShowcase(Material material) {
		super(material);
	}

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        NonNullList<ItemStack> ret = NonNullList.create();
        ret.add(new ItemStack(PlayerStatues.itemShowcase,1));
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.PUSH_ONLY;
    }

	/**
	 * The type of render function that is called for this block
	 */
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	/**
	 * return false if the block isn't a full 1*1 cube
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	/**
	 * return false if the block mustn't be rendered as a normal block
	 */
	@Override
    public boolean isNormalCube(IBlockState state) {
		return false;
	}

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	int meta = state.getBlock().getMetaFromState(state);

    	switch(meta){
    	case 0|4: return new AxisAlignedBB(0.0F, 0.0F, 0.0F, 0.5F, 1.5f, 1.0F); break;
    	case 2|4: return new AxisAlignedBB(0.5F, 0.0F, 0.0F, 1.0F, 1.5f, 1.0F); break;
    	case 1|4: return new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.5f, 0.5F); break;
    	case 3|4: return new AxisAlignedBB(0.0F, 0.0F, 0.5F, 1.0F, 1.5f, 1.0F); break;
	    default: return new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.5f, 1.0F); break;
    	}
    }
    
    @Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack par6ItemStack) {
        int x=pos.getX(),y=pos.getY(),z=pos.getZ();
        int meta = MathHelper.floor((entityliving.rotationYaw * 4F) / 360F + 0.5D) & 3;
		int dx,dz;
		
		switch(meta){
		default:
		case 0: case 2: dx=1; dz=0; break;
		case 1: case 3: dx=0; dz=1; break;
		}
		
		world.setBlockMetadataWithNotify(x, y, z, meta, 3);
		if(meta>=2) meta-=2;
		world.setBlock(x+dx, y, z+dz, this, meta|4, 3);
		world.setBlock(x-dx, y, z-dz, this, (meta+2)|4, 3);
    }

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entityplayer, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        int x=pos.getX(),y=pos.getY(),z=pos.getZ();
	    int meta = state.getBlock().getMetaFromState(state);
		
		if((meta&4)!=0){
			switch(meta&3){
			default:
			case 0: case 2:
				if(isCenterBlock(world, new BlockPos(x-1, y, z), state)) x--;
				else if(isCenterBlock(world, new BlockPos(x+1, y, z), state)) x++;
				break;
			case 1: case 3:
				if(isCenterBlock(world, new BlockPos(x, y, z-1), state)) z--;
				else if(isCenterBlock(world, new BlockPos(x, y, z+1), state)) z++;
				break;
			}
		}

		if(! isCenterBlock(world, pos, state))
			return true;
		
		TileEntityShowcase teshowcase = (TileEntityShowcase) world.getTileEntity(pos);
		if (teshowcase instanceof TileEntityShowcase && !world.isRemote)
			PlayerStatues.guiShowcase.open(entityplayer, world, pos.getX(), pos.getY(), pos.getZ());
		
		return true;
	}

	boolean isCenterBlock(World world, BlockPos pos, IBlockState state){
		return (world.getBlockState(pos).equals(this)) && (state.getBlock().getMetaFromState(state)&4)==0;
	}
	
	boolean isCenterBlock(Block block, int meta){
		return (block.equals(this)) && (meta & 4) == 0;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		int x=pos.getX(),y=pos.getY(),z=pos.getZ();
		boolean found=true;
		int meta = state.getBlock().getMetaFromState(state);
				
		if(isCenterBlock(state.getBlock(), meta)){
			TileEntity tile = world.getTileEntity(pos);
			if(tile instanceof TileEntityShowcase) ItemUtils.dropItems(world, xx, yy, zz, ((TileEntityShowcase) tile));
		} else{
			switch(meta&3){
			default:
			case 0: case 2:
				if(isCenterBlock(world, new BlockPos(x-1, y, z), state))
					x--;
				else if(isCenterBlock(world, new BlockPos(x+1, y, z), state))
					x++;
				else
					found=false;
				break;
			case 1: case 3:
				if(isCenterBlock(world, new BlockPos(x, y, z-1), state))
					z--;
				else if(isCenterBlock(world, new BlockPos(x, y, z+1), state))
					z++;
				else
					found=false;
				break;
			}
		}
		
		if(!found && ! isCenterBlock(state.getBlock(), meta))
			return;
		
		world.setBlockToAir(pos);
		
		switch(meta&3){
		case 0: case 2:
		    world.setBlockToAir(new BlockPos(x+1, y, z));
		    world.setBlockToAir(new BlockPos(x-1, y, z));
			break;
		case 1: case 3:
		    world.setBlockToAir(new BlockPos(x, y, z+1));
		    world.setBlockToAir(new BlockPos(x, y, z-1));
			break;
		}
		
		super.breakBlock(world, pos, state);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int metadata) {
		return new TileEntityShowcase();
	}

}
