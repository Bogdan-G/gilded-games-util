package com.gildedgames.playerhook.common.networking.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.gildedgames.playerhook.common.PlayerHookManager;
import com.gildedgames.playerhook.common.player.IPlayerHook;

public class MessagePlayerHookClient implements IMessage
{

	private IPlayerHook playerHook;

	private ByteBuf buf;

	public MessagePlayerHookClient()
	{

	}

	public MessagePlayerHookClient(IPlayerHook playerHook)
	{
		this.playerHook = playerHook;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.buf = buf;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		PlayerHookManager.writeReference(this.playerHook, buf);
		
		this.playerHook.getProfile().writeToServer(buf);
		this.playerHook.writeToServer(buf);
	}
	
	public static class Handler implements IMessageHandler<MessagePlayerHookClient, IMessage>
	{
	        
        @Override
        public IMessage onMessage(MessagePlayerHookClient message, MessageContext ctx)
        {
        	if (ctx.side.isServer())
        	{
        		EntityPlayer player = ctx.getServerHandler().playerEntity;
        		
        		IPlayerHook playerHook = PlayerHookManager.readReference(player, message.buf);
        		
        		playerHook.getProfile().readFromClient(message.buf);
        		playerHook.readFromClient(message.buf);
        	}

        	return null;
        }
        
	}

}
