package com.cjburkey.mods.autofarm.packet;

import com.cjburkey.mods.autofarm.block.BlockHarvester;
import com.cjburkey.mods.autofarm.tile.TileEntityHarvester;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHarvesterToServer implements IMessage {
	
	private int x, y, z;
	
	public PacketHarvesterToServer() {  }
	public PacketHarvesterToServer(BlockPos p) {
		x = p.getX();
		y = p.getY();
		z = p.getZ();
	}
	public PacketHarvesterToServer(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void fromBytes(ByteBuf buf) {
		String[] s = ByteBufUtils.readUTF8String(buf).split(";");
		if(s.length == 3) {
			x = Integer.parseInt(s[0]);
			y = Integer.parseInt(s[1]);
			z = Integer.parseInt(s[2]);
		}
	}
	
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, x + ";" + y + ";" + z);
	}
	
	public static class Handler implements IMessageHandler<PacketHarvesterToServer, PacketHarvesterToClient> {
		public PacketHarvesterToClient onMessage(PacketHarvesterToServer msg, MessageContext ctx) {
			World world = ctx.getServerHandler().playerEntity.world;
			BlockPos pos = new BlockPos(msg.x, msg.y, msg.z);
			IBlockState state = world.getBlockState(pos);
			if(state.getBlock() instanceof BlockHarvester) {
				TileEntityHarvester harvester = (TileEntityHarvester) world.getTileEntity(pos);
				return new PacketHarvesterToClient(harvester.getEnergyStored(EnumFacing.NORTH), harvester.getMaxEnergyStored(EnumFacing.NORTH));
			}
			return null;
		}
	}
	
}