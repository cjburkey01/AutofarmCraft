package com.cjburkey.mods.autofarm.block;

import com.cjburkey.mods.autofarm.Autofarm;
import com.cjburkey.mods.autofarm.tile.TileEntityHarvester;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHarvester extends BlockContainer implements ITileEntityProvider {
	
	public BlockHarvester() {
		super(Material.GROUND);
		this.setHardness(1.0f);
		this.setHarvestLevel("pickaxe", 0);
		this.setSoundType(SoundType.STONE);
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ply, EnumHand hand, EnumFacing facing, float x, float y, float z) {
		if(!world.isRemote) {
			ply.openGui(Autofarm.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityHarvester farmer = (TileEntityHarvester) world.getTileEntity(pos);
		if(farmer != null) {
			InventoryHelper.dropInventoryItems(world, pos, farmer);
		}
		super.breakBlock(world, pos, state);
	}
	
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	public TileEntity createNewTileEntity(World world, int data) {
		return new TileEntityHarvester();
	}
	
}