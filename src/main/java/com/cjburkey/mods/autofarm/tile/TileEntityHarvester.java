package com.cjburkey.mods.autofarm.tile;

import java.util.ArrayList;
import java.util.List;
import com.cjburkey.mods.autofarm.cfg.ModConfig;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityHarvester extends TileEntity implements ITickable, IInventory, IEnergyReceiver, IEnergyStorage {
	
	private List<Operation> todo;
	private long lastOperation;
	private long lastCheck;
	
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
	
	private int energy = 0;
	
	public void onLoad() {
		todo = new ArrayList<>();
	}
	
	public void update() {
		if(!world.isRemote) {
			if(lastOperation >= ModConfig.harvesterTicksBetweenActions) {
				lastOperation = 0;
				doLatestOperation();
			}
			if(lastCheck >= ModConfig.harvesterTicksBetweenScans) {
				lastCheck = 0;
				checkCrops();
			}
			lastOperation ++;
			lastCheck ++;
		}
	}
	
	private void checkCrops() {
		if(takeEnergy(ModConfig.harvesterRfPerScan, false)) {
			for(int x = pos.getX() - 4; x <= pos.getX() + 4; x ++) {
				for(int z = pos.getZ() - 4; z <= pos.getZ() + 4; z ++) {
					BlockPos p = new BlockPos(x, pos.getY() + 2, z);
					IBlockState state = world.getBlockState(p);
					if(state.getBlock() instanceof IGrowable) {
						IGrowable ig = (IGrowable) state.getBlock();
						if(!ig.canGrow(world, p, state, world.isRemote)) {
							harvestCrop(state, p);
						}
					}
				}
			}
		}
	}
	
	private boolean harvestCrop(IBlockState state, BlockPos pos) {
		boolean worked = false;
		if(takeEnergy(ModConfig.harvesterRfPerHarvest, false)) {
			for(ItemStack s : state.getBlock().getDrops(world, pos, state, 0)) {
				worked = addItemStackToInventory(s);
			}
			if(worked) {
				addOperation(pos, state.getBlock().getDefaultState());
			}
		}
		return worked;
	}
	
	private void addOperation(BlockPos pos, IBlockState state) {
		for(Operation op : todo) {
			if(op.getPos().equals(pos)) return;
		}
		todo.add(new Operation(pos, state));
	}
	
	private void doLatestOperation() {
		if(todo.size() > 0) {
			Operation operation = todo.get(0);
			todo.remove(0);
			world.notifyNeighborsOfStateChange(pos, blockType, true);
			operation.call(world);
		}
	}
	
	private static final class Operation {
		private BlockPos pos;
		private IBlockState state;
		public Operation(BlockPos pos, IBlockState state) {
			this.pos = pos;
			this.state = state;
		}
		public BlockPos getPos() { return pos; }
		public final void call(World world) { world.setBlockState(pos, state); }
	}
	
	// -- INVENTORY -- //
	
	public String getName() {
		return "container.harvester";
	}
	
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(getName());
	}
	
	public boolean hasCustomName() {
		return false;
	}
	
	public int getSizeInventory() {
		return 9;
	}
	
	public boolean isEmpty() {
		for(ItemStack stack : inventory) {
			if(!stack.equals(ItemStack.EMPTY)) return false;
		}
		return true;
	}
	
	public ItemStack getStackInSlot(int index) {
		if(index >= 0 && index < getSizeInventory()) {
			return inventory.get(index);
		}
		return ItemStack.EMPTY;
	}
	
	public ItemStack decrStackSize(int index, int count) {
		if(!getStackInSlot(index).equals(ItemStack.EMPTY)) {
			ItemStack stack;
			if(getStackInSlot(index).getCount() <= count) {
				stack = getStackInSlot(index);
				setInventorySlotContents(index, ItemStack.EMPTY);
				return stack;
			} else {
				stack = getStackInSlot(index).splitStack(count);
				if(getStackInSlot(index).getCount() <= 0) {
					setInventorySlotContents(index, ItemStack.EMPTY);
				} else {
					setInventorySlotContents(index, getStackInSlot(index));
				}
				return stack;
			}
		}
		return ItemStack.EMPTY;
	}
	
	public ItemStack removeStackFromSlot(int index) {
		return getStackInSlot(index);
	}
	
	public void setInventorySlotContents(int index, ItemStack stack) {
		if(index >= 0 && index < getSizeInventory()) {
			if(stack.getCount() > getInventoryStackLimit()) stack.setCount(getInventoryStackLimit());
			if(stack.getCount() <= 0) stack = ItemStack.EMPTY;
			inventory.set(index, stack);
			markDirty();
		}
	}
	
	public boolean addItemStackToInventory(ItemStack stack) {
		if(!stack.equals(ItemStack.EMPTY)) {
			for(int i = 0; i < getSizeInventory(); i ++) {
				if(getStackInSlot(i).getItem().equals(stack.getItem())) {
					while(getStackInSlot(i).getCount() < getInventoryStackLimit() && stack.getCount() > 0) {
						stack.setCount(stack.getCount() - 1);
						getStackInSlot(i).setCount(getStackInSlot(i).getCount() + 1);
					}
					if(stack.getCount() <= 0) return true;
				} else if(getStackInSlot(i).equals(ItemStack.EMPTY)) {
					this.setInventorySlotContents(i, stack);
					return true;
				}
			}
		}
		return false;
	}
	
	public int getInventoryStackLimit() {
		return 64;
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}
	
	public void openInventory(EntityPlayer player) {  }
	public void closeInventory(EntityPlayer player) {  }
	
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}
	
	public int getField(int id) { return 0; }
	public void setField(int id, int value) {  }
	public int getFieldCount() { return 0; }
	
	public void clear() {
		for(int i = 0; i < getSizeInventory(); i ++) {
			setInventorySlotContents(i, ItemStack.EMPTY);
		}
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); i ++) {
			if(getStackInSlot(i) != ItemStack.EMPTY) {
				NBTTagCompound stack = new NBTTagCompound();
				stack.setByte("Slot", (byte) i);
				getStackInSlot(i).writeToNBT(stack);
				list.appendTag(stack);
			}
		}
		nbt.setTag("Items", list);
		nbt.setInteger("Energy", energy);
		return super.writeToNBT(nbt);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("Items", 10);
		for(int i = 0; i < list.tagCount(); i ++) {
			NBTTagCompound stack = list.getCompoundTagAt(i);
			int slot = stack.getByte("Slot") & 255;
			setInventorySlotContents(slot, new ItemStack(stack));
		}
		energy = nbt.getInteger("Energy");
	}
	
	// -- ENERGY -- //

	public int getEnergyStored(EnumFacing from) {
		return energy;
	}

	public int getMaxEnergyStored(EnumFacing from) {
		return ModConfig.harvesterMaxRf;
	}

	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	public int receiveEnergy(EnumFacing from, int rec, boolean simulate) {
		int available = ModConfig.harvesterMaxRf - energy;
		if(available >= rec) {
			if(!simulate) energy += rec;
			markDirty();
			return rec;
		} else {
			if(!simulate) energy = ModConfig.harvesterMaxRf;
			markDirty();
			return available;
		}
	}
	
	public boolean takeEnergy(int amt, boolean simulate) {
		if(energy >= amt) {
			if(!simulate) energy -= amt;
			markDirty();
			return true;
		}
		return false;
	}

	public int receiveEnergy(int rec, boolean sim) {
		return receiveEnergy(EnumFacing.NORTH, rec, sim);
	}

	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

	public int getEnergyStored() {
		return energy;
	}

	public int getMaxEnergyStored() {
		return ModConfig.harvesterMaxRf;
	}

	public boolean canExtract() {
		return true;
	}

	public boolean canReceive() {
		return true;
	}
	
}