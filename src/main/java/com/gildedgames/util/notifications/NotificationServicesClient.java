package com.gildedgames.util.notifications;

import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.notifications.common.core.INotification;
import com.gildedgames.util.notifications.common.core.NotificationDispatcher;
import com.gildedgames.util.notifications.common.core.NotificationDispatcherClient;
import com.gildedgames.util.notifications.common.core.NotificationQueue;

public class NotificationServicesClient extends NotificationServices
{

	private NotificationQueue queue = new NotificationQueue();

	private NotificationDispatcher dispatcher;

	protected NotificationServicesClient()
	{
		super(Side.CLIENT);
	}

	@Override
	public void queueNotificationForDisplay(INotification notification)
	{
		this.queue.addNotification(notification);
	}

	@Override
	public void onRenderOverlay()
	{
		this.queue.tick();
	}

	@Override
	public NotificationDispatcher getDispatcher()
	{
		if (this.dispatcher == null)
		{
			this.dispatcher = new NotificationDispatcherClient();
		}

		return this.dispatcher;
	}

	@Override
	public void onServerTick()
	{
	}

}