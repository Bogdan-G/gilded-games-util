package com.gildedgames.util.playerhook;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.playerhook.common.IPlayerHookPool;
import com.gildedgames.util.playerhook.common.PlayerEventHandler;
import com.gildedgames.util.playerhook.common.networking.messages.MessagePlayerHook;
import com.gildedgames.util.playerhook.common.networking.messages.MessagePlayerHookClient;
import com.gildedgames.util.playerhook.common.networking.messages.MessagePlayerHookRequest;
import com.gildedgames.util.playerhook.server.PlayerHookSaveHandler;

public class PlayerHookCore implements ICore
{

	public static final PlayerHookCore INSTANCE = new PlayerHookCore();

	public PlayerHookSaveHandler playerHookSaveHandler = new PlayerHookSaveHandler();

	public PlayerEventHandler playerEventHandler = new PlayerEventHandler();

	private SidedObject<PlayerHookServices> serviceLocator = new SidedObject<PlayerHookServices>(new PlayerHookServices(), new PlayerHookServices());

	private PlayerHookCore()
	{

	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		UtilCore.NETWORK.registerMessage(MessagePlayerHook.Handler.class, MessagePlayerHook.class, Side.CLIENT);
		UtilCore.NETWORK.registerMessage(MessagePlayerHookClient.Handler.class, MessagePlayerHookClient.class, Side.SERVER);
		UtilCore.NETWORK.registerMessage(MessagePlayerHookRequest.Handler.class, MessagePlayerHookRequest.class, Side.SERVER);
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this.playerHookSaveHandler);
		FMLCommonHandler.instance().bus().register(this.playerHookSaveHandler);

		MinecraftForge.EVENT_BUS.register(this.playerEventHandler);
		FMLCommonHandler.instance().bus().register(this.playerEventHandler);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	@Override
	public void serverAboutToStart(FMLServerAboutToStartEvent event)
	{

	}

	@Override
	public void serverStarting(FMLServerStartingEvent event)
	{

	}

	@Override
	public void serverStarted(FMLServerStartedEvent event)
	{

	}

	@Override
	public void serverStopping(FMLServerStoppingEvent event)
	{
		this.playerHookSaveHandler.flushData();

		for (IPlayerHookPool manager : PlayerHookCore.locate().getPools())
		{
			if (manager != null)
			{
				manager.clear();
			}
			else
			{
				/** TO-DO: error log here, manager should never be null **/
			}
		}
	}

	@Override
	public void serverStopped(FMLServerStoppedEvent event)
	{

	}

	public static PlayerHookServices locate()
	{
		return INSTANCE.serviceLocator.instance();
	}

	public void registerPlayerPool(IPlayerHookPool client, IPlayerHookPool server)
	{
		INSTANCE.serviceLocator.client().registerPlayerHookPool(client);
		INSTANCE.serviceLocator.server().registerPlayerHookPool(server);
	}

}
