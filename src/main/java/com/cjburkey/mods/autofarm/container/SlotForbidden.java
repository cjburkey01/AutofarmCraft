package com.cjburkey.mods.autofarm.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotForbidden extends Slot {

	public SlotForbidden(IInventory inv, int i, int x, int y) {
		super(inv, i, x, y);
	}
	
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
	
}