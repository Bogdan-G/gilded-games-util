package com.gildedgames.util.core.gui.viewing;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.gildedgames.util.ui.data.TickInfo;

public class MinecraftUIWrapperTick implements TickInfo
{

	protected Minecraft mc = Minecraft.getMinecraft();
	
	protected int ticks;
	
	@SubscribeEvent
	public void tickStart(TickEvent.ClientTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			this.ticks++;
			
			if (this.mc.currentScreen instanceof MinecraftUIWrapper)
			{
				MinecraftUIWrapper viewer = (MinecraftUIWrapper)this.mc.currentScreen;
				
				viewer.getFrame().tick(MinecraftUIWrapper.INPUT, this);
			}
		}
	}

	@Override
	public int getTotalTicks()
	{
		return this.ticks;
	}
	
}