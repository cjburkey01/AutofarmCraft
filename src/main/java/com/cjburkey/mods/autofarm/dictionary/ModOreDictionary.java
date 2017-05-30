package com.cjburkey.mods.autofarm.dictionary;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModOreDictionary {
	
	private static List<Dict> dict;
	
	public static final void commonPreinit() {
		if(dict != null && dict.size() > 0) {
			for(Dict d : dict) d.register();
		}
	}
	
	public static final void register(ItemStack itemOrBlock, String name) {
		if(dict == null) dict = new ArrayList<>();
		dict.add(new Dict(name, itemOrBlock));
	}
	
	private static final class Dict {
		private String name;
		private ItemStack item;
		public Dict(String name, ItemStack item) {
			this.name = name;
			this.item = item;
		}
		public void register() { OreDictionary.registerOre(name, item); }
	}
	
}