package com.gildedgames.util.universe;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
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
import com.gildedgames.util.player.PlayerCore;
import com.gildedgames.util.universe.common.UniverseAPI;
import com.gildedgames.util.universe.common.networking.messages.MessageTravelUniverse;
import com.gildedgames.util.universe.common.universe.UniverseFog;
import com.gildedgames.util.universe.common.util.TeleporterGeneric;

public class UniverseCore implements ICore
{

	public static UniverseCore INSTANCE = new UniverseCore();

	public static TeleporterGeneric GENERIC_TELEPORTER;
	
	private SidedObject<UniverseServices> serviceLocator = new SidedObject<UniverseServices>(new UniverseServices(), new UniverseServices());
	
	public static UniverseServices locate()
	{
		return UniverseCore.INSTANCE.serviceLocator.instance();
	}

	@Override
    public void preInit(FMLPreInitializationEvent event)
	{
		UtilCore.NETWORK.registerMessage(MessageTravelUniverse.Handler.class, MessageTravelUniverse.class, Side.SERVER);
	
		UniverseAPI.instance().register(UniverseAPI.instance().getMinecraftUniverseID(), UniverseAPI.instance().getMinecraftUniverse());
		UniverseAPI.instance().register("thefog:thefog", new UniverseFog());
		
		PlayerCore.INSTANCE.registerPlayerPool(this.serviceLocator.client().getPlayers(), this.serviceLocator.server().getPlayers());
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
		
	}
	
	@Override
	public void serverStarted(FMLServerStartedEvent event)
	{
		GENERIC_TELEPORTER = new TeleporterGeneric(MinecraftServer.getServer().worldServerForDimension(0));
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
	
	public static void teleportToDimension(EntityPlayerMP player, int dimension)
	{
		ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
		scm.transferPlayerToDimension((EntityPlayerMP) player, dimension, UniverseCore.GENERIC_TELEPORTER);
	}
	
}