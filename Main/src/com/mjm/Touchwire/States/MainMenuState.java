package com.mjm.Touchwire.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.mjm.Touchwire.GameManager;
import com.mjm.Touchwire.Utililities.GUI;
import com.mjm.Touchwire.Utililities.GameState;

public class MainMenuState extends GameState
{
    //GUI and Debug
    public static GUI gui;
    public static InputProcessor input = new MainMenuInput();

    @Override
    public void create()
    {
        //Sets up custom input processing
        Gdx.input.setInputProcessor(input);
        gui = new GUI();
    }

    @Override
    public void resize(int width, int height)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    //Run every frame
    public void render()
    {
        GameManager.largeFont.draw(GameManager.spriteBatch,"Touchwire",GameManager.ScreenX/2-120,620);
        GameManager.font.draw(GameManager.spriteBatch,"Touch anywhere to enter Sandbox Mode",GameManager.ScreenX/2-215,300);
        gui.Draw();
    }

    @Override
    public void lateRender()
    {

    }

    @Override
    public void pause()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dispose()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }


}
