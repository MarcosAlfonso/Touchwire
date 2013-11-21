package com.mjm.Touchwire.States;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.Entities.*;
import com.mjm.Touchwire.GameManager;

public class MainMenuInput implements InputProcessor
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
        //Main Menu
        if (!MainMenuState.onLevelSelect)
        {
            if (MainMenuState.curGui.getButton(MainMenuState.mainButtons.Learning.name()).Bounds.contains(halfX, halfY))
            {
                GameManager.debugTimed.addDebug("Level Select", 1);
                MainMenuState.onLevelSelect = true;
                MainMenuState.curGui = MainMenuState.levelGui;
            }
            else if (MainMenuState.curGui.getButton(MainMenuState.mainButtons.Sandbox.name()).Bounds.contains(halfX, halfY))
            {
                GameManager.debugTimed.addDebug("Sandbox Mode Activated", 1);
                SandboxState.board.components.clear();
                GameManager.setState(GameManager.GameStates.Sandbox);
            }
            else if (MainMenuState.curGui.getButton(MainMenuState.mainButtons.Exit.name()).Bounds.contains(halfX, halfY))
            {
                GameManager.Platform.ExitGame();
            }
        }
        //Level Select
        else
        {
            if (MainMenuState.curGui.getButton(MainMenuState.levelButtons.Level1.name()).Bounds.contains(halfX, halfY))
            {
                GameManager.debugTimed.addDebug("Load Level 1", 1);
                GameManager.setState(GameManager.GameStates.Sandbox);
                SandboxState.LoadOne();
            }
            else if (MainMenuState.curGui.getButton(MainMenuState.levelButtons.Level2.name()).Bounds.contains(halfX, halfY))
            {
                GameManager.debugTimed.addDebug("Load Level 2", 1);
                GameManager.setState(GameManager.GameStates.Sandbox);
                SandboxState.LoadTwo();

            }
            else if (MainMenuState.curGui.getButton(MainMenuState.levelButtons.Back.name()).Bounds.contains(halfX, halfY))
            {
                MainMenuState.onLevelSelect = false;
                MainMenuState.curGui = MainMenuState.mainGui;
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
