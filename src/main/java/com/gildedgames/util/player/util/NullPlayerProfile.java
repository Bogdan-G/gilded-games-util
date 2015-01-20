package com.gildedgames.util.player.util;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.player.common.player.IPlayerProfile;

public class NullPlayerProfile implements IPlayerProfile
{

	private static NullPlayerProfile INSTANCE;

	private NullPlayerProfile()
	{

	}

	public static NullPlayerProfile instance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new NullPlayerProfile();
		}

		return INSTANCE;
	}

	@Override
	public void write(NBTTagCompound tag)
	{
	}

	@Override
	public void read(NBTTagCompound tag)
	{
	}

	@Override
	public void writeToClient(ByteBuf buf)
	{
	}

	@Override
	public void readFromServer(ByteBuf buf)
	{
	}

	@Override
	public void writeToServer(ByteBuf buf)
	{
	}

	@Override
	public void readFromClient(ByteBuf buf)
	{
	}

	@Override
	public void markDirty()
	{
	}

	@Override
	public void markClean()
	{
	}

	@Override
	public boolean isDirty()
	{
		return false;
	}

	@Override
	public void entityInit(EntityPlayer player)
	{
	}

	@Override
	public void onUpdate()
	{
	}

	@Override
	public boolean isLoggedIn()
	{
		return false;
	}

	@Override
	public void setLoggedIn(boolean isLoggedIn)
	{
	}

	@Override
	public String getUsername()
	{
		return "NULL";
	}

	@Override
	public UUID getUUID()
	{
		return null;
	}

	@Override
	public void setUUID(UUID uuid)
	{
	}

	@Override
	public EntityPlayer getEntity()
	{
		return null;
	}

	@Override
	public void setEntity(EntityPlayer player)
	{
	}

}
