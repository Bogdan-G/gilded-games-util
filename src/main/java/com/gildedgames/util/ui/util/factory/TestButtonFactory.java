package com.gildedgames.util.ui.util.factory;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.core.gui.util.UIFactoryUtil;
import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.data.Position2D;
import com.google.common.collect.ImmutableList;

public class TestButtonFactory implements ContentFactory
{

	@Override
	public List<UIElement> provideContent(ImmutableList<UIElement> currentContent)
	{
		List<UIElement> buttons = new ArrayList<UIElement>();
		
		UIFactoryUtil factory = new UIFactoryUtil();
		
		for (int count = 0; count < 100; count++)
		{
			buttons.add(factory.createButton(new Position2D(), 60, "Button " + (count + 1), false));
		}
		
		return buttons;
	}

}