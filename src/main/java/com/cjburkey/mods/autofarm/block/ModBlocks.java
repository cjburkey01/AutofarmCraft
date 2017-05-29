package com.cjburkey.mods.autofarm.block;

import java.util.HashMap;
import com.cjburkey.mods.autofarm.ModInfo;
import com.cjburkey.mods.autofarm.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	private static HashMap<Block, ItemBlock> blocks = new HashMap<>();
	
	private static Block blockHarvester;
	
	public static final void commonPreinit() {
		blockHarvester = registerBlock(new BlockHarvester(), "block_harvester");
	}
	
	private static final Block registerBlock(Block block, String name) {
		block.setUnlocalizedName(name);
		block.setRegistryName(ModInfo.MODID, name);
		GameRegistry.register(block);
		blocks.put(block, (ItemBlock) ModItems.registerItem(new ItemBlock(block), name));
		return block;
	}
	
}