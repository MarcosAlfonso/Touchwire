package com.mjm.Touchwire.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.GameManager;
import com.mjm.Touchwire.Utililities.Button;
import com.mjm.Touchwire.Utililities.GUI;
import com.mjm.Touchwire.Utililities.GameState;

public class MainMenuState extends GameState
{
    //GUI and Debug
    public static GUI gui;

    public enum Buttons
    {
        Learning,Sandbox,Exit
    }

    public static InputProcessor input = new MainMenuInput();

    @Override
    public void create()
    {
        //Sets up custom input processing
        Gdx.input.setInputProcessor(input);

        gui = new GUI();
        gui.AddButton(Buttons.Learning.name(), new Rectangle(GameManager.ScreenX/2,360,200,96), null, Button.Justifications.Center);
        gui.AddButton(Buttons.Sandbox.name(), new Rectangle(GameManager.ScreenX/2,240,200,96), null, Button.Justifications.Center);
        gui.AddButton(Buttons.Exit.name(), new Rectangle(GameManager.ScreenX/2,120,200,96), null, Button.Justifications.Center);
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
        GUI.DrawCenteredString(GameManager.fontLarge,"Touchwire", new Vector2(GameManager.ScreenX/2,575));
        gui.Draw();
    }

    @Override
    public void lateRender()
    {
         gui.DebugDraw();
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
