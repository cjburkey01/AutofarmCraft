package com.cjburkey.mods.autofarm.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiProgressBar {
	
	private int x;
	private int y;
	private int width;
	private int maxHeight;
	private int minValue;
	private int maxValue;
	private int value;
	
	private final int lowColor;
	private final int medColor;
	private final int maxColor;
	
	public GuiProgressBar(int bottomLeftX, int bottomLeftY, int width, int height, int minVal, int maxVal) {
		this(bottomLeftX, bottomLeftY, width, height, minVal, maxVal, 0xFFE03B00, 0xFFE0CD00, 0xFF009B02);
	}
	
	public GuiProgressBar(int bottomLeftX, int bottomLeftY, int width, int height, int minVal, int maxVal, int lc, int mc, int mac) {
		x = bottomLeftX;
		y = bottomLeftY;
		this.width = width;
		maxHeight = height;
		minValue = minVal;
		maxValue = maxVal;
		lowColor = lc;
		medColor = mc;
		maxColor = mac;
	}
	
	public void render(Minecraft mc, GuiScreen gui) {
		if(getCalcHeight() >= 1) {
			gui.drawRect(x, y - getCalcHeight() + 1, x + width, y + 1, getColor());
		}
	}
	
	public int getColor() {
		if(getProgress() <= 0.25d) return lowColor;
		if(getProgress() <= 0.5d) return medColor;
		return maxColor;
	}
	
	public void setValue(int val) {
		if(val >= maxValue) {
			value = maxValue;
			return;
		}
		if(val <= minValue) {
			value = minValue;
			return;
		}
		value = val;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setMax(int max) {
		maxValue = max;
	}
	
	public double getProgress() {
		int unmax = maxValue - minValue;
		int unval = value - minValue;
		double prog = ((double) value / (double) maxValue);
		if(prog < 0.0d) return 0.0d;
		if(prog > 1.0d) return 1.0d;
		return prog;
	}
	
	private int getCalcHeight() {
		double height = maxHeight * getProgress();
		return (int) Math.round(height);
	}
	
}