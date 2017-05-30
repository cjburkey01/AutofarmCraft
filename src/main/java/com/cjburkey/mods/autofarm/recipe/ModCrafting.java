package com.cjburkey.mods.autofarm.recipe;

import com.cjburkey.mods.autofarm.block.ModBlocks;
import com.cjburkey.mods.autofarm.item.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModCrafting {
	
	public static final void commonInit() {
		addShaped(new ItemStack(ModItems.itemGear, 1), new Object[] { " x ", "xxx", " x ", 'x', "ingotIron" });
		addShaped(new ItemStack(ModBlocks.blockHarvester, 1), new Object[] { "xax", "yzy", "xyx", 'x', "cobblestone", 'y', "dustRedstone", 'z', "gearIron", 'a', new ItemStack(Items.DIAMOND_HOE, 1, Short.MAX_VALUE) });
	}
	
	private static final void addShaped(ItemStack output, Object[] recipe) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, recipe));
	}
	
	private static final void addShapeless(ItemStack output, ItemStack... recipe) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(output, (Object[]) recipe));
	}
	
}