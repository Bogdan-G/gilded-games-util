package com.gildedgames.util.core.io;

import com.gildedgames.util.modules.group.common.core.Group;
import com.gildedgames.util.modules.group.common.player.GroupMember;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Collection;

public class NetworkWrapper
{

	private SimpleNetworkWrapper internal;

	private int discriminator;

	public void init(String modId)
	{
		this.internal = NetworkRegistry.INSTANCE.newSimpleChannel(modId);
	}

	public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side)
	{
		this.internal.registerMessage(messageHandler, requestMessageType, this.discriminator++, side);
	}

	public void sendToAll(IMessage message)
	{
		this.internal.sendToAll(message);
	}

	public void sendTo(IMessage message, EntityPlayerMP player)
	{
		this.internal.sendTo(message, player);
	}

	public void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point)
	{
		this.internal.sendToAllAround(message, point);
	}

	public void sendToDimension(IMessage message, int dimensionId)
	{
		this.internal.sendToDimension(message, dimensionId);
	}

	public void sendToServer(IMessage message)
	{
		this.internal.sendToServer(message);
	}

	public void sendToGroup(IMessage message, Group group)
	{
		for (GroupMember member : group.getMemberData())
		{
			if (member.getEntity() != null)
			{
				this.sendTo(message, (EntityPlayerMP) member.getEntity());
			}
		}
	}

	public void sendToList(IMessage message, Collection<EntityPlayerMP> players)
	{
		for (EntityPlayerMP player : players)
		{
			this.sendTo(message, player);
		}
	}

}
