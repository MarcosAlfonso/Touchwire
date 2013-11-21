package com.mjm.Touchwire.Utililities;

import com.mjm.Touchwire.GameManager;

import java.util.ArrayList;
import java.util.List;

public class DebugDisplay
{
    List<String> strings	= new ArrayList<String>();
    public int					x;
    public int					y;

    public DebugDisplay(int xpos, int ypos)
    {
        x = xpos;
        y = ypos;

    }

    public DebugDisplay(int xpos, int ypos, String str)
    {
        x = xpos;
        y = ypos;
        addDebug(str);
    }

    public void addDebug(String str)
    {
        strings.add(str);
    }

    public void Draw()
    {
        for(String s : strings)
        {
            int i = strings.indexOf(s);
            GameManager.fontSmall.draw(GameManager.spriteBatch, s, x, (y - (i * 30)));
        }

        strings.clear();
    }

}
