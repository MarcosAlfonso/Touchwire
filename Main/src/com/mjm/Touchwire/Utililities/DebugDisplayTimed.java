package com.mjm.Touchwire.Utililities;

import com.badlogic.gdx.utils.TimeUtils;
import com.mjm.Touchwire.GameManager;

import java.util.ArrayList;
import java.util.Iterator;

public class DebugDisplayTimed extends DebugDisplay
{
    class TimedDebug
    {
        public long		StartTime;
        public long		Age;
        public String string;

        TimedDebug(String s, long a)
        {
            string = s;
            Age = a * 1000000000;
            StartTime = TimeUtils.nanoTime();
        }
    }

    static ArrayList<TimedDebug> timedStrings	= new ArrayList<TimedDebug>();

    public DebugDisplayTimed(int xpos, int ypos)
    {
        super(xpos, ypos);
        // TODO Auto-generated constructor stub
    }

    public void addDebug(String str, int sec)
    {
        timedStrings.add(new TimedDebug(str, sec));
    }

    @Override
    public void Draw()
    {

        for(TimedDebug s : timedStrings)
        {
            int i = timedStrings.indexOf(s);
            GameManager.fontSmall.draw(GameManager.spriteBatch, s.string, x, (y - (i * 30)));
            if ((TimeUtils.nanoTime() - s.StartTime) > s.Age)
            {
                s.StartTime = -1;
            }

        }

        Iterator<TimedDebug> iter = timedStrings.iterator();
        while (iter.hasNext())
        {
            if (iter.next().StartTime == -1)
            {
                iter.remove();
            }
        }
    }
}
