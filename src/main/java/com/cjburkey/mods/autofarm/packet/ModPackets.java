package com.cjburkey.mods.autofarm.packet;

import com.cjburkey.mods.autofarm.ModInfo;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModPackets {
	
	private static SimpleNetworkWrapper network;
	
	public static final void commonPreinit() {
		network = new SimpleNetworkWrapper(ModInfo.MODID);
		
		network.registerMessage(PacketHarvesterToServer.Handler.class, PacketHarvesterToServer.class, 0, Side.SERVER);
		network.registerMessage(PacketHarvesterToClient.Handler.class, PacketHarvesterToClient.class, 1, Side.CLIENT);
	}
	
	public static SimpleNetworkWrapper getNetwork() {
		return network;
	}
	
}