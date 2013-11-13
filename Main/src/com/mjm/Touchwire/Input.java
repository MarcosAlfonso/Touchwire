package com.mjm.Touchwire;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class Input implements InputProcessor
{

    private static Terminal lastTerminal = null;

    @Override
    public boolean keyDown(int keycode)
    {
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
        //Flips y because you have to okay?
        int flippedY = Main.ScreenY - screenY / Main.PCvsAndroid;

        //Hacky shit to make resolution work on both desktop and tablet
        int halfX = screenX / Main.PCvsAndroid;
        int halfY = flippedY;

        //BUTTON STUFF
        if (GUI.BatteryButton.contains(halfX, halfY))
        {
            Main.debugTimed.addDebug("Battery Spawned", 1);
            Main.board.components.add(new Battery(new Vector2(halfX, halfY)));

        }
        else if (GUI.LightButton.contains(halfX, halfY))
        {
            Main.debugTimed.addDebug("Light Spawned", 1);
            Main.board.components.add(new Light(new Vector2(halfX, halfY)));
        }
        else if (GUI.ClearButton.contains(halfX, halfY))
        {
            Main.board.components.clear();
            lastTerminal = null;
            Main.debugTimed.addDebug("Board Cleared", 3);
        }
        else if (GUI.TangibleButton.contains(halfX, halfY))
        {
            Main.debugTimed.addDebug("Tangible Zone Spawned", 1);
            Main.board.components.add(new Zone(new Vector2(halfX, halfY)));

        }


        //Iterate through all the components on the board, so we can check if anything is getting touched
        for (Component comp : Main.board.components)
        {
            //If they touch an existing component, add pointer to component touchList
            if (comp.Bounds.contains(halfX, halfY))
            {
                comp.touchList.add(pointer);
                return true;
            }

            //WIRING STUFF
            //If they touch a positive terminal
            else if (comp.posTerminal.Bounds.contains(halfX, halfY))
            {
                //If no terminal selected yet
                if (lastTerminal == null)
                {
                    Main.debugTimed.addDebug("Now select a negative terminal", 5);
                    lastTerminal = comp.posTerminal;
                }
                //If they select a positive terminal after starting with positive, error
                else if (lastTerminal.isPositive)
                {
                    Main.debugTimed.addDebug("ERROR: Please select a negative terminal", 3);

                    //If terminal is negative, and not from the same component, wire
                }
                else if (!lastTerminal.isPositive && comp != lastTerminal.Component)
                {
                    Main.debugTimed.addDebug("DEBUG: Wire Created", 3);
                    Wire newWire = new Wire(comp.posTerminal,lastTerminal);
                    lastTerminal.wire = newWire;
                    comp.posTerminal.wire = newWire;
                    lastTerminal = null;
                }
                return true;
            }
            //If they touch a negative terminal
            else if (comp.negTerminal.Bounds.contains(halfX, halfY))
            {

                //If no terminal selected yet
                if (lastTerminal == null)
                {
                    Main.debugTimed.addDebug("Now select a positive terminal", 5);
                    lastTerminal = comp.negTerminal;
                }

                //If they select a negative terminal after starting with negative, error
                else if (!lastTerminal.isPositive)
                {
                    Main.debugTimed.addDebug("ERROR: Please select a positive terminal", 3);

                    //If terminal is negative, and not from the same component, wire
                }
                else if (lastTerminal.isPositive && comp != lastTerminal.Component)
                {
                    Main.debugTimed.addDebug("DEBUG: Wire Created", 3);
                    Wire newWire = new Wire(lastTerminal, comp.negTerminal);
                    lastTerminal.wire = newWire;
                    comp.negTerminal.wire = newWire;
                    lastTerminal = null;
                }
                return true;
            }
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
