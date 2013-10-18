package com.mjm.Touchwire.Utililities;

import com.mjm.Touchwire.Main;

import java.util.ArrayList;
import java.util.List;

public class DebugDisplay
{
    static List<String> strings	= new ArrayList<String>();
    int					x;
    int					y;

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
            Main.font.draw(Main.spriteBatch, s, x, (y - (i * 30)));
        }

        strings.clear();
    }

}
