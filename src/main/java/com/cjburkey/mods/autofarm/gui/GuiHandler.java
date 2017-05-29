package com.cjburkey.mods.autofarm.gui;

import java.util.ArrayList;
import java.util.List;
import com.cjburkey.mods.autofarm.container.ContainerHarvester;
import com.cjburkey.mods.autofarm.tile.TileEntityHarvester;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	private List<GuiShow> guis = new ArrayList<>();
	
	public GuiHandler() {
		addGuis();
	}
	
	private void addGuis() {
		// Farmer
		guis.add(0, (world, ply, pos, server) -> {
			TileEntity ent = world.getTileEntity(pos);
			if(ent != null && ent instanceof TileEntityHarvester) {
				TileEntityHarvester farmer = (TileEntityHarvester) ent;
				if(server) return new ContainerHarvester(ply.inventory, farmer);
				else return new GuiHarvester(ply, farmer);
			}
			return null;
		});
	}
	
	private Object openGui(int i, World world, EntityPlayer ply, BlockPos pos, boolean server) {
		if(i >= 0 && i < guis.size()) {
			return guis.get(i).showGui(world, ply, pos, server);
		}
		return null;
	}

	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return openGui(id, world, player, new BlockPos(x, y, z), true);
	}

	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return openGui(id, world, player, new BlockPos(x, y, z), false);
	}
	
	@FunctionalInterface
	static interface GuiShow {
		Object showGui(World world, EntityPlayer player, BlockPos pos, boolean server);
	}
	
}