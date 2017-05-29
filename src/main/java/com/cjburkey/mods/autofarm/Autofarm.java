package com.cjburkey.mods.autofarm;

import com.cjburkey.mods.autofarm.gui.GuiHandler;
import com.cjburkey.mods.autofarm.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = ModInfo.NAME, modid = ModInfo.MODID, version = ModInfo.VERSION, dependencies = "required-after:forge@[12.30.0.2296,]", useMetadata = false,
	clientSideOnly = false, serverSideOnly = false, acceptedMinecraftVersions = "[1.11.2]", acceptableRemoteVersions = "[1.0.0,]",
	acceptableSaveVersions = "[1.0.0,]", certificateFingerprint = "", modLanguage = "java", modLanguageAdapter = "", canBeDeactivated = false, guiFactory = "",
	updateJSON = "", customProperties = {  })
public class Autofarm {
	
	@Instance
	public static Autofarm instance;
	
	@SidedProxy(clientSide = ModInfo.PROXY_PACKAGE + ".ClientProxy", serverSide = ModInfo.PROXY_PACKAGE + ".ServerProxy")
	public static CommonProxy proxy;
	
	public static GuiHandler guiHandler;
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent e) { proxy.preinit(e); }
	
	@EventHandler
	public void init(FMLInitializationEvent e) { proxy.init(e); }

	@EventHandler
	public void postinit(FMLPostInitializationEvent e) { proxy.postinit(e); }
	
}