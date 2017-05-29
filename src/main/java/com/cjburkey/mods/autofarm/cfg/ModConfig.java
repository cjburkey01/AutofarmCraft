package com.cjburkey.mods.autofarm.cfg;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModConfig {
	
	public static int harvesterMaxRf;
	public static int harvesterRfPerScan;
	public static int harvesterRfPerHarvest;
	public static int harvesterTicksBetweenScans;
	public static int harvesterTicksBetweenActions;
	
	public static final void commonPreinit(FMLPreInitializationEvent e) {
		File f = new File(e.getSuggestedConfigurationFile().getParentFile(), "/AutofarmCraft/main.cfg");
		Configuration cfg = new Configuration(f);
		cfg.load();
		loadConfig(cfg);
		cfg.save();
	}
	
	private static final void loadConfig(Configuration cfg) {
		harvesterMaxRf = cfg.getInt("harvesterMaxRf", "Harvester", 100000, 1000, 1000000, "The maximum RF that the harvester can hold");
		harvesterRfPerScan = cfg.getInt("harvesterRfPerScan", "Harvester", 10, 1, 100, "The RF taken per scan");
		harvesterRfPerHarvest = cfg.getInt("harvesterRfPerHarvest", "Harvester", 100, 10, 1000, "The RF taken per harvest of a crop");
		harvesterTicksBetweenScans = cfg.getInt("harvesterTicksBetweenScans", "Harvester", 10, 2, 100, "The number of ticks before the next scan");
		harvesterTicksBetweenActions = cfg.getInt("harvesterTicksBetweenActions", "Harvester", 5, 2, 100, "The number of ticks before the next action(next crop needs harvested, etc)");
	}
	
}