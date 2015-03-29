package com.gildedgames.util.notifications;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.notifications.common.core.INotification;
import com.gildedgames.util.notifications.common.player.PlayerNotification;
import com.gildedgames.util.notifications.common.util.PopupNotification;
import com.gildedgames.util.player.PlayerCore;

public class NotificationCore implements ICore
{

	public final static NotificationCore INSTANCE = new NotificationCore();

	@SidedProxy(clientSide = "com.gildedgames.util.notifications.ClientProxy", serverSide = "com.gildedgames.util.notifications.ServerProxy")
	public static ServerProxy proxy;

	private final SidedObject<NotificationServices> serviceLocator = proxy.createServices();

	public static NotificationServices locate()
	{
		return NotificationCore.INSTANCE.serviceLocator.instance();
	}

	public static PlayerNotification getPlayerNotifications(EntityPlayer player)
	{
		return NotificationCore.locate().getPlayers().get(player);
	}

	public static PlayerNotification getPlayerNotifications(UUID uuid)
	{
		return NotificationCore.locate().getPlayers().get(uuid);
	}

	public static void sendNotification(INotification notification)
	{
		locate().getDispatcher().sendNotification(notification);
	}

	public static void sendPopup(String message, EntityPlayer sender, EntityPlayer receiver)
	{
		locate().getDispatcher().sendNotification(new PopupNotification(message, sender, receiver));
	}

	public static EntityPlayer playerFromUUID(UUID uuid)
	{
		return getPlayerNotifications(uuid).getProfile().getEntity();
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		PlayerCore.INSTANCE.registerPlayerPool(this.serviceLocator.client().getPlayers(), this.serviceLocator.server().getPlayers());
	}

	@Override
	public void init(FMLInitializationEvent event)
	{

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
	public void serverStopping(FMLServerStoppingEvent event)
	{

	}

	@Override
	public void serverStopped(FMLServerStoppedEvent event)
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
}
