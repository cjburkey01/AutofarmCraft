package com.cjburkey.mods.autofarm.container;

import com.cjburkey.mods.autofarm.gui.GuiProgressBar;
import com.cjburkey.mods.autofarm.tile.TileEntityHarvester;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHarvester extends Container {
	
	private IInventory playerInventory;
	private TileEntityHarvester farmer;
	private final int size;
	private final int slotSize = 18;
	
	public ContainerHarvester(IInventory plyInv, TileEntityHarvester te) {
		playerInventory = plyInv;
		farmer = te;
		size = farmer.getSizeInventory();
		
		initFarmerSlots();
		initPlayerSlots();
		initHotbarSlots();
	}
	
	private void initFarmerSlots() {
		int xOffset = 62;
		int yOffset = 17;
		for(int y = 0; y < 3; y ++) {
			for(int x = 0; x < 3; x ++) {
				int i = x + y * 3;
				addSlotToContainer(new SlotForbidden(farmer, i, xOffset + slotSize * x, yOffset + slotSize * y));
			}
		}
	}
	
	private void initPlayerSlots() {
		int xOffset = 8;
		int yOffset = 84;
		for(int y = 0; y < 3; y ++) {
			for(int x = 0; x < 9; x ++) {
				int i = 9 + x + y * 9;
				addSlotToContainer(new Slot(playerInventory, i, xOffset + slotSize * x, yOffset + slotSize * y));
			}
		}
	}
	
	private void initHotbarSlots() {
		int xOffset = 8;
		int yOffset = 142;
		for(int x = 0; x < 9; x ++) {
			addSlotToContainer(new Slot(playerInventory, x, xOffset + slotSize * x, yOffset));
		}
	}
	
	public ItemStack transferStackInSlot(EntityPlayer ply, int fromSlot) {
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(fromSlot);
		if(slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();
			if(fromSlot < size) {
				if(!mergeItemStack(current, size, size + 36, true)) {
					return ItemStack.EMPTY;
				}
			} else {
				return ItemStack.EMPTY;
			}
			if(current.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if(current.getCount() == previous.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(ply, current);
		}
		return previous;
	}
	
	public boolean canInteractWith(EntityPlayer ply) {
		return farmer.isUsableByPlayer(ply);
	}
	
}