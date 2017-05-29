package com.cjburkey.mods.autofarm.item;

import java.util.ArrayList;
import java.util.List;
import com.cjburkey.mods.autofarm.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	
	private static List<Item> items = new ArrayList<>();
	
	public static final void commonPreinit() {
		
	}
	
	public static final void clientInit() {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		for(Item item : items) {
			mesher.register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
	
	public static final Item registerItem(Item item, String name) {
		item.setUnlocalizedName(name);
		item.setRegistryName(ModInfo.MODID, name);
		GameRegistry.register(item);
		items.add(item);
		return item;
	}
	
}