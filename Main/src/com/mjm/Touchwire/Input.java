package com.mjm.Touchwire;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import javafx.scene.input.TouchPoint;

public class Input implements InputProcessor
{

    private Terminal lastTerminal;
    private Component dragComponent;

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
        int flippedY = Main.ScreenY-screenY;

        //Hacky shit to make resolution work on both desktop and tablet
        int halfX = screenX;
        int halfY = flippedY;

        //Iterates through Buttons, checks if any are touched and does the shit based off that
        for (Button guiButton : Main.gui.Buttons)
        {
            if (guiButton.Bounds.contains(halfX,halfY))
            {
                if (guiButton.Function.equals("Battery"))
                {
                    Main.debugTimed.addDebug("Battery Spawned", 1);
                    Component newComp = new Battery(new Vector2(halfX, halfY));
                    Main.board.components.add(newComp);
                    dragComponent = newComp;
                }
                else if (guiButton.Function.equals("Light"))
                {
                    Main.debugTimed.addDebug("Light Spawned", 1);
                    Component newComp = new Light(new Vector2(halfX, halfY));
                    Main.board.components.add(newComp);
                    dragComponent = newComp;
                }
                else if (guiButton.Function.equals("Clear"))
                {
                    Main.board.components.clear();
                    lastTerminal = null;
                    Main.debugTimed.addDebug("Board Cleared", 3);
                }
                return true;
            }
        }

        //Iterate through all the components on the board, so we can check if anything is getting touched
        for(Component comp : Main.board.components)
        {
            //If they touch an existing component, drag it
            if (comp.Bounds.contains(halfX,halfY))
            {
                dragComponent = comp;

                //Tangible Detection Stuff
                if(comp instanceof Zone)
                {
                    ((Zone) comp).touchDownDetected(pointer);
                }

                return true;
            }

            //If they touch a terminal
            else if (comp.posTerminal.Bounds.contains(halfX, halfY) || comp.negTerminal.Bounds.contains(halfX, halfY))
            {
                //If no terminal selected yet
                if (lastTerminal == null)
                {
                    Main.debugTimed.addDebug("Now select a negative terminal", 3);
                    //lastTerminal = comp;
                }
                //If they select a positive terminal after already picking one, ERROR
                else
                {
                    Main.debugTimed.addDebug("ERROR: Positive terminal chosen",3);
                }
                return true;
            }
            //If they touch a negative terminal
            else if (comp.negTerminal.Bounds.contains(halfX, halfY))
            {
                //If no positive terminal has been picked yet, ERROR
                if (lastTerminal == null)
                {
                    Main.debugTimed.addDebug("ERROR: Please start with a positive terminal", 3);
                }
                //If positive terminal has been picked, create wire between it and this negative terminal
                else
                {
                    Main.debugTimed.addDebug("Wire created",3);
                    //Wire newWire = new Wire(lastTerminal.posTerminal,comp.negTerminal);
                    //lastTerminal.posTerminal.wire = newWire;
                    //comp.negTerminal.wire = newWire;

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

        //Flips y because you have to okay?
        int flippedY = Main.ScreenY-screenY;

        //Hacky shit to make resolution work on both desktop and tablet
        int halfX = screenX;
        int halfY = flippedY;

        for(Component comp : Main.board.components){
            if (comp.Bounds.contains(halfX,halfY))
            {
                //Tangible Detection Stuff
                if(comp instanceof Zone)
                {
                    ((Zone) comp).touchUpDetected(pointer);
                }

                return true;
            }
        }
        //lift up stops dragging
        dragComponent = null;

        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        //Flips y because you have to okay?
        int flippedY = Main.ScreenY-screenY;

        int halfX = screenX;
        int halfY = flippedY;

        //If a component was picked during touchDown, drag that puppy
        if (dragComponent != null)
        {
            dragComponent.SetPosition(halfX,halfY);

            if (dragComponent.Bounds.overlaps(Main.gui.ClearButton.Bounds))
                dragComponent.Delete();
        }
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
