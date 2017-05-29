package com.cjburkey.mods.autofarm.packet;

import com.cjburkey.mods.autofarm.gui.GuiHarvester;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHarvesterToClient implements IMessage {
	
	private int energy;
	private int maxEnergy;
	
	public PacketHarvesterToClient() {  }
	public PacketHarvesterToClient(int energyAmt, int max) {
		energy = energyAmt;
		maxEnergy = max;
	}
	
	public void fromBytes(ByteBuf buf) {
		String[] s = ByteBufUtils.readUTF8String(buf).split(";");
		if(s.length == 2) {
			energy = Integer.parseInt(s[0]);
			maxEnergy = Integer.parseInt(s[1]);
		}
	}
	
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, energy + ";" + maxEnergy);
	}
	
	public static class Handler implements IMessageHandler<PacketHarvesterToClient, IMessage> {
		public IMessage onMessage(PacketHarvesterToClient msg, MessageContext ctx) {
			if(msg != null) {
				GuiHarvester.energy = msg.energy;
				GuiHarvester.maxEnergy = msg.maxEnergy;
			}
			return null;
		}
	}
	
}