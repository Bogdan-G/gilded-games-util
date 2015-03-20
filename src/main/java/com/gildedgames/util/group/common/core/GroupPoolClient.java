package com.gildedgames.util.group.common.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.IGroupPoolListener;
import com.gildedgames.util.group.common.IGroupPoolListenerClient;
import com.gildedgames.util.group.common.player.GroupMember;

//Basically just asks the server to do his work for him :) :) :)
//Won't ever change its state on its own to ensure clients not acting weirdly
@SideOnly(Side.CLIENT)
public class GroupPoolClient extends GroupPool
{

	public GroupPoolClient(String id)
	{
		super(id);
	}

	@Override
	public Group create(String name, EntityPlayer creating)
	{
		UtilCore.NETWORK.sendToServer(new PacketAddGroup(this, name, creating));
		return null;
	}

	@Override
	public void addMember(EntityPlayer player, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		GroupMember member = GroupCore.getGroupMember(player);
		UtilCore.NETWORK.sendToServer(new PacketAddMember(this, group, member));
	}

	@Override
	public void removeMember(EntityPlayer player, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		GroupMember member = GroupCore.getGroupMember(player);
		UtilCore.NETWORK.sendToServer(new PacketRemoveMember(this, group, member));
	}

	@Override
	public void remove(Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		UtilCore.NETWORK.sendToServer(new PacketRemoveGroup(this, group));
	}

	@Override
	public void invite(EntityPlayer player, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		GroupMember member = GroupCore.getGroupMember(player);
		UtilCore.NETWORK.sendToServer(new PacketAddInvite(this, group, member));
	}

	@Override
	public void removeInvitation(EntityPlayer player, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
	}

	@Override
	public void changeOwner(EntityPlayer newOwner, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		GroupMember newOwnerM = GroupCore.getGroupMember(newOwner);
		UtilCore.NETWORK.sendToServer(new PacketChangeOwner(this, group, newOwnerM));
	}

	@Override
	protected void removeMemberDirectly(Group group, GroupMember member)
	{
		super.removeMemberDirectly(group, member);
		if (member.equals(this.thePlayer()))
		{
			this.onLeave(group);
		}
	}

	protected void join(Group group, MemberData members)
	{
		group.setMemberData(members);
		members.setHooks(this.createHooks(group));
		this.addMemberDirectly(group, this.thePlayer());
		for (IGroupPoolListenerClient<?> listener : this.getClientListeners())
		{
			listener.onJoin(group);
		}
	}

	protected void inviteReceived(Group group)
	{
		this.thePlayer().addInvite(group);
		for (IGroupPoolListenerClient<?> listener : this.getClientListeners())
		{
			listener.onInvited(group);
		}
	}

	private void onLeave(Group group)
	{
		for (IGroupPoolListenerClient<?> listener : this.getClientListeners())
		{
			listener.onLeave(group);
		}
		group.setMemberData(null);
	}

	private List<IGroupPoolListenerClient<?>> getClientListeners()
	{
		List<IGroupPoolListenerClient<?>> listeners = new ArrayList<IGroupPoolListenerClient<?>>();
		for (IGroupPoolListener<?> listener : this.listeners)
		{
			if (listener instanceof IGroupPoolListenerClient)
			{
				listeners.add((IGroupPoolListenerClient<?>) listener);
			}
		}
		return listeners;
	}

	private GroupMember thePlayer()
	{
		return GroupCore.getGroupMember(Minecraft.getMinecraft().thePlayer);
	}

	@Override
	protected boolean assertValidGroup(Group group)
	{
		if (!super.assertValidGroup(group))
		{
			return false;
		}
		if (!this.thePlayer().groupsInFor(this).contains(group))
		{
			UtilCore.print("Client trying to modify group he's not in");
			return false;
		}
		return true;
	}

}