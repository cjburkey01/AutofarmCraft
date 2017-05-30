package com.cjburkey.mods.autofarm.tab;

import com.cjburkey.mods.autofarm.block.ModBlocks;
import com.cjburkey.mods.autofarm.item.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabAutoFarm extends CreativeTabs {

	public TabAutoFarm() {
		super("tab_autofarm");
	}

	public ItemStack getTabIconItem() {
		return new ItemStack(ModBlocks.blockHarvester);
	}
	
}