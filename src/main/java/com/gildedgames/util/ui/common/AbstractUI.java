package com.gildedgames.util.ui.common;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.UIElementContainer;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;

public abstract class AbstractUI implements BasicUI
{
	
	private boolean visible = true, enabled = true, focused = false;

	private UIElementContainer listeners = new UIElementContainer();
	
	private BasicUI previousFrame;
	
	private Dim2D dimensions;

	public AbstractUI(Dim2D dimensions)
	{
		this(null, dimensions);
	}

	public AbstractUI(BasicUI previousFrame, Dim2D dimensions)
	{
		this.previousFrame = previousFrame;
		this.dimensions = dimensions;
	}
	
	@Override
	public UIElementContainer getListeners()
	{
		return this.listeners;
	}

	@Override
	public BasicUI getPreviousFrame()
	{
		return this.previousFrame;
	}

	@Override
	public boolean isVisible()
	{
		return this.visible;
	}

	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	@Override
	public Dim2D getDimensions()
	{
		return this.dimensions;
	}
	
	@Override
	public boolean isFocused()
	{
		return this.focused;
	}

	@Override
	public void setFocused(boolean focused)
	{
		this.focused = focused;
	}

	@Override
	public boolean query(Object... input)
	{
		return false;
	}
	
	@Override
	public boolean onKeyboardInput(KeyboardInputPool pool)
	{
		return false;
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		
	}
	
	@Override
	public void tick(InputProvider input, TickInfo tickInfo)
	{
		
	}

	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		
	}
	
	@Override
	public void onInit(UIElementContainer container, InputProvider input)
	{
		
	}
	
	@Override
	public void onResolutionChange(UIElementContainer container, InputProvider input)
	{
		
	}
	
	@Override
	public void write(NBTTagCompound output)
	{
		
	}

	@Override
	public void read(NBTTagCompound input)
	{
		
	}

}