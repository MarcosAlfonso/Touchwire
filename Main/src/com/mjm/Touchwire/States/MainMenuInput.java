package com.mjm.Touchwire.States;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.Entities.*;
import com.mjm.Touchwire.GameManager;
import com.mjm.Touchwire.Utililities.GUI;

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
        int flippedY = GameManager.ScreenY - screenY / GameManager.PCvsAndroid;

        //Hacky shit to make resolution work on both desktop and tablet
        int halfX = screenX / GameManager.PCvsAndroid;
        int halfY = flippedY;

        GameManager.setState(GameManager.GameStates.Sandbox);

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
