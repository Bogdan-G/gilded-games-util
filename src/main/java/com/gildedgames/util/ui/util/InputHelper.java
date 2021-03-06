package com.gildedgames.util.ui.util;

import java.util.List;

import org.lwjgl.opengl.Display;

import com.gildedgames.util.ui.UiCore;
import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.input.InputProvider;

public class InputHelper
{
	
	public static float prevMouseX, prevMouseY;
	
	public static boolean prevResult;
	
	public static interface InputCondition
	{
		
		boolean isMet(Gui gui);
		
	}
	
	public static Pos2D cursorPos(InputProvider input)
	{
		return Pos2D.flush(input.getMouseX(), input.getMouseY());
	}
	
	public static boolean isHovered(final InputCondition condition, final InputProvider input)
	{
		if (prevMouseX != input.getMouseX() || prevMouseY != input.getMouseY())
		{
			prevMouseX = input.getMouseX();
			prevMouseY = input.getMouseY();
			
			if (UiCore.locate().hasFrame())
			{
				GuiFrame frame = UiCore.locate().getCurrentFrame();
				
				List<Gui> guis = frame.seekContent().queryAll();
				
				guis.addAll(frame.events().queryAll(input));
				
				for (Gui gui : guis)
				{
					if (condition.isMet(gui) && input.isHovered(gui.dim()))
					{
						prevResult = true;
						
						return true;
					}
				}
			}
			
			prevResult = false;
			
			return false;
		}
		
		return prevResult;
	}
	
	public static boolean isInsideScreen(InputProvider input, Rect rect)
	{
		if (rect.maxX() > input.getScreenWidth() || rect.maxY() > input.getScreenHeight())
		{
			return false;
		}
		
		return true;
	}
	
	public static Pos2D convertToOpenGL(InputProvider input, Pos2D pos)
	{
		double heightScaleFactor = Display.getHeight() / input.getScreenHeight();
		double widthScaleFactor = Display.getWidth() / input.getScreenWidth();
		
		return Pos2D.flush((int)(pos.x() * widthScaleFactor), (int)(pos.y() * heightScaleFactor));
	}

	public static Pos2D getCenter(InputProvider input)
	{
		return Pos2D.flush(input.getScreenWidth() / 2, input.getScreenHeight() / 2);
	}
	
	public static Pos2D getBottomCenter(InputProvider input)
	{
		return InputHelper.getCenter(input).clone().addY(input.getScreenHeight() / 2).flush();
	}
	
	public static Pos2D getBottomRight(InputProvider input)
	{
		return InputHelper.getBottomCenter(input).clone().addX(input.getScreenWidth() / 2).flush();
	}
	
	public static Pos2D getBottomLeft(InputProvider input)
	{
		return InputHelper.getBottomRight(input).clone().addX(-input.getScreenWidth()).flush();
	}
	
	public static Pos2D getCenterLeft(InputProvider input)
	{
		return InputHelper.getCenter(input).clone().addX(-input.getScreenWidth() / 2).flush();
	}
	
	public static Pos2D getCenterRight(InputProvider input)
	{
		return InputHelper.getCenterLeft(input).clone().addX(input.getScreenWidth()).flush();
	}
	
	public static Pos2D getTopCenter(InputProvider input)
	{
		return InputHelper.getCenter(input).clone().addY(-input.getScreenHeight() / 2).flush();
	}
	
	public static Pos2D getTopLeft(InputProvider input)
	{
		return InputHelper.getTopCenter(input).clone().addX(-input.getScreenWidth() / 2).flush();
	}
	
	public static Pos2D getTopRight(InputProvider input)
	{
		return InputHelper.getTopLeft(input).clone().addX(input.getScreenWidth()).flush();
	}
	
}
