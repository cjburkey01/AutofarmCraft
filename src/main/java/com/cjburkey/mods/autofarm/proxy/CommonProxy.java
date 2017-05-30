package com.cjburkey.mods.autofarm.proxy;

import com.cjburkey.mods.autofarm.Autofarm;
import com.cjburkey.mods.autofarm.block.ModBlocks;
import com.cjburkey.mods.autofarm.cfg.ModConfig;
import com.cjburkey.mods.autofarm.dictionary.ModOreDictionary;
import com.cjburkey.mods.autofarm.gui.GuiHandler;
import com.cjburkey.mods.autofarm.item.ModItems;
import com.cjburkey.mods.autofarm.packet.ModPackets;
import com.cjburkey.mods.autofarm.recipe.ModCrafting;
import com.cjburkey.mods.autofarm.tab.ModTabs;
import com.cjburkey.mods.autofarm.tile.ModTiles;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent e) {
		ModConfig.commonPreinit(e);
		ModTabs.commonPreinit();
		ModBlocks.commonPreinit();
		ModItems.commonPreinit();
		ModTiles.commonPreinit();
		ModPackets.commonPreinit();
		Autofarm.guiHandler = new GuiHandler();
		ModOreDictionary.commonPreinit();
	}
	
	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(Autofarm.instance, Autofarm.guiHandler);
		ModCrafting.commonInit();
	}

	public void postinit(FMLPostInitializationEvent e) {
		
	}
	
}