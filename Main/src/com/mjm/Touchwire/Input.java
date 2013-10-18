package com.mjm.Touchwire;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Marcos
 * Date: 10/18/13
 * Time: 12:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class Input implements InputProcessor
{

    private Rectangle lastPosTerminal = null;

    @Override
    public boolean keyDown(int keycode)
    {
        if (keycode == com.badlogic.gdx.Input.Keys.TAB)
        {
            if (Main.compMode.equals("Battery"))
            {
                Main.compMode = "Light";
            }
            else
            {
                Main.compMode = "Battery";
            }
        }
            return false;  //To change body of implemented methods use File | Settings | File Templates.

    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        int flippedY = Main.ScreenY-screenY;
        Main.lastClick = new Vector2(screenX,flippedY);

        for(Component comp : Main.board.components)
        {
            if (comp.posTerminal.contains(screenX,flippedY))
            {
                if (lastPosTerminal == null)
                {
                    Main.debugTimed.addDebug("Now select a negative terminal", 3);
                    lastPosTerminal = comp.posTerminal;
                }
                else
                {
                    Main.debugTimed.addDebug("ERROR: Positive terminal chosen",3);
                }

                return true;
            }

            if (comp.negTerminal.contains(screenX,flippedY))
            {
                if (lastPosTerminal == null)
                {
                    Main.debugTimed.addDebug("Please start with a positive terminal", 3);
                }
                else
                {
                    Main.debugTimed.addDebug("Wire created",3);
                    Main.board.wires.add(new Wire(new Vector2(lastPosTerminal.x,lastPosTerminal.y),new Vector2(comp.negTerminal.x,comp.negTerminal.y)));
                    lastPosTerminal = null;
                }

                return true;
            }
        }

        Main.debugTimed.addDebug("Component Spawn Registered", 1);

        if (Main.compMode.equals("Battery"))
        {
            Main.board.components.add(new Battery(new Vector2(screenX, flippedY)));
        }
        else
        {
            Main.board.components.add(new Light(new Vector2(screenX, flippedY)));
        }

        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
