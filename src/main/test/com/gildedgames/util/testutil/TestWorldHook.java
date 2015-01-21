package com.gildedgames.util.testutil;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.world.common.IWorldHook;
import com.gildedgames.util.world.common.world.IWorld;

public class TestWorldHook implements IWorldHook
{

	IWorld w;

	public TestWorldHook(IWorld w)
	{
		this.w = w;
	}

	@Override
	public void write(NBTTagCompound output)
	{
	}

	@Override
	public void read(NBTTagCompound input)
	{
	}

	@Override
	public void onLoad()
	{
	}

	@Override
	public void onUnload()
	{
	}

	@Override
	public void onSave()
	{
	}

	@Override
	public void onUpdate()
	{
	}

	@Override
	public IWorld getWorld()
	{
		return this.w;
	}

	@Override
	public boolean equals(Object obj)
	{
		// TODO Auto-generated method stub
		if (super.equals(obj))
		{
			return true;
		}

		if (obj instanceof TestWorldHook)
		{
			return this.w.equals(((TestWorldHook) obj).getWorld());
		}
		return false;

	}

}
