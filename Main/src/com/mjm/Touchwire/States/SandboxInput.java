package com.mjm.Touchwire.States;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.Entities.*;
import com.mjm.Touchwire.GameManager;
import com.mjm.Touchwire.Entities.Terminal;

import java.io.Console;

public class SandboxInput implements InputProcessor
{

    public static Terminal lastTerminal = null;

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
        int flippedY = GameManager.ScreenY - screenY / GameManager.ResolutionResolver;

        //Hacky shit to make resolution work on both desktop and tablet
        int halfX = screenX / GameManager.ResolutionResolver;
        int halfY = flippedY;

        //BUTTON STUFF
        if (SandboxState.gui.getButton(SandboxState.Buttons.Battery.name()).Bounds.contains(halfX, halfY))
        {
            GameManager.debugTimed.addDebug("Battery Spawned", 1);
            GameManager.board.components.add(new Battery(new Vector2(halfX, halfY)));

        }
        else if (SandboxState.gui.getButton(SandboxState.Buttons.Light.name()).Bounds.contains(halfX, halfY))
        {
            GameManager.debugTimed.addDebug("Light Spawned", 1);
            GameManager.board.components.add(new Light(new Vector2(halfX, halfY)));
        }
        else if (SandboxState.gui.getButton(SandboxState.Buttons.Clear.name()).Bounds.contains(halfX, halfY))
        {
            GameManager.board.components.clear();
            lastTerminal = null;
            GameManager.debugTimed.addDebug("Board Cleared", 3);
        }
        else if (SandboxState.gui.getButton(SandboxState.Buttons.Tangible.name()).Bounds.contains(halfX, halfY))
        {
            GameManager.debugTimed.addDebug("Tangible Zone Spawned", 1);
            GameManager.board.components.add(new Zone(new Vector2(halfX, halfY)));
        }
        else if (SandboxState.gui.getButton(SandboxState.Buttons.Back.name()).Bounds.contains(halfX, halfY))
        {
            GameManager.setState(GameManager.GameStates.MainMenu);
        }
        else if (SandboxState.gui.getButton(SandboxState.Buttons.Save.name()).Bounds.contains(halfX, halfY))
        {
            SandboxState.board.Save();
        }
        else if (SandboxState.gui.getButton(SandboxState.Buttons.Load.name()).Bounds.contains(halfX, halfY))
        {
            SandboxState.board.Load();
        }

        //Iterate through all the components on the board, so we can check if anything is getting touched
        for (Component comp : GameManager.board.components)
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
                    GameManager.debugTimed.addDebug("Now select a negative terminal", 5);
                    lastTerminal = comp.posTerminal;
                }
                //If they select a positive terminal after starting with positive, error
                else if (lastTerminal.isPositive)
                {
                    GameManager.debugTimed.addDebug("ERROR: Please select a negative terminal", 3);

                    //If terminal is negative, and not from the same component, wire
                }
                else if (!lastTerminal.isPositive && comp != lastTerminal.Component)
                {
                    GameManager.debugTimed.addDebug("DEBUG: Wire Created", 3);
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
                    GameManager.debugTimed.addDebug("Now select a positive terminal", 5);
                    lastTerminal = comp.negTerminal;
                }

                //If they select a negative terminal after starting with negative, error
                else if (!lastTerminal.isPositive)
                {
                    GameManager.debugTimed.addDebug("ERROR: Please select a positive terminal", 3);

                    //If terminal is negative, and not from the same component, wire
                }
                else if (lastTerminal.isPositive && comp != lastTerminal.Component)
                {
                    GameManager.debugTimed.addDebug("DEBUG: Wire Created", 3);
                    Wire newWire = new Wire(lastTerminal, comp.negTerminal);
                    lastTerminal.wire = newWire;
                    comp.negTerminal.wire = newWire;
                    lastTerminal = null;
                }
                return true;
            }
        }

        lastTerminal = null;
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
