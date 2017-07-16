package me.jacky1356400.statues.client.gui;

import net.minecraft.client.gui.GuiTextField;

public class GuiEdit extends GuiElement
{
	private GuiTextField field;
	private String tempString = "";
	private int id;

	public GuiEdit(int id, int x, int y, int w, int h)
	{
		super(x, y, w, h);
		this.id = id;
	}

	@Override
	public void onAdded()
	{
		field = new GuiTextField(id, gui.fontRendererObj(), x, y, w, h);
		setText(tempString);
	}

	public String getText()
	{
		if(field == null)
		{
			return tempString;
		}
		else
		{
			return field.getText();
		}
	}

	public void setText(String text)
	{
		if(field == null)
		{
			tempString = text;
		}
		else
		{
			field.setText(text);
		}
	}

	@Override
	public void render()
	{
		field.drawTextBox();
	}

	@Override
	public void mouseDown(InputMouseEvent ev)
	{
		field.mouseClicked(ev.x, ev.y, ev.button);

		if(isMouseOver(ev))
		{
			ev.handled = true;
		}
	}

	@Override
	public void keyPressed(InputKeyboardEvent ev)
	{
		field.textboxKeyTyped(ev.character, ev.key);

		if(field.isFocused())
		{
			ev.handled = true;
		}
	}
}
