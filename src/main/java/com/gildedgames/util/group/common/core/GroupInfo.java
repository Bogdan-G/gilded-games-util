package com.gildedgames.util.group.common.core;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.permissions.IGroupPerms;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.util.IOUtil;

public class GroupInfo implements NBT
{

	private IGroupPerms permissions;

	private String name;

	private GroupMember owner;

	public GroupInfo(IGroupPerms permissions, String name, GroupMember owner)
	{
		this.name = name;
		this.owner = owner;
		this.permissions = permissions;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		NBTFactory factory = new NBTFactory();
		output.setString("name", this.name);
		IOCore.io().set("permissions", output, factory, this.permissions);

		IOUtil.setUUID(this.owner.getProfile(), output, "owner");
	}

	@Override
	public void read(NBTTagCompound input)
	{
		NBTFactory factory = new NBTFactory();
		this.name = input.getString("name");
		this.permissions = IOCore.io().get("permissions", input, factory);
		this.owner = GroupCore.getGroupMember(IOUtil.getUUID(input, "owner"));
	}

	public String getName()
	{
		return this.name;
	}

	public IGroupPerms getPermissions()
	{
		return this.permissions;
	}

	public GroupMember getOwner()
	{
		return this.owner;
	}

}