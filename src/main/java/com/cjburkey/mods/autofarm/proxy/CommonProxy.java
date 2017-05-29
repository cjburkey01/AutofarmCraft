package com.cjburkey.mods.autofarm.proxy;

import com.cjburkey.mods.autofarm.Autofarm;
import com.cjburkey.mods.autofarm.block.ModBlocks;
import com.cjburkey.mods.autofarm.cfg.ModConfig;
import com.cjburkey.mods.autofarm.gui.GuiHandler;
import com.cjburkey.mods.autofarm.item.ModItems;
import com.cjburkey.mods.autofarm.packet.ModPackets;
import com.cjburkey.mods.autofarm.tile.ModTiles;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent e) {
		ModConfig.commonPreinit(e);
		ModBlocks.commonPreinit();
		ModItems.commonPreinit();
		ModTiles.commonPreinit();
		ModPackets.commonPreinit();
		Autofarm.guiHandler = new GuiHandler();
	}
	
	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(Autofarm.instance, Autofarm.guiHandler);
	}

	public void postinit(FMLPostInitializationEvent e) {
		
	}
	
}