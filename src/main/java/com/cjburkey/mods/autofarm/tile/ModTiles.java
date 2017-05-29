package com.cjburkey.mods.autofarm.tile;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTiles {
	
	public static final void commonPreinit() {
		GameRegistry.registerTileEntity(TileEntityHarvester.class, "tileentity_harvester");
	}
	
}