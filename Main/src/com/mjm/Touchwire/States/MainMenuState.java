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
    public static GUI mainGui;
    public static GUI levelGui;
    public static GUI curGui;

    public static boolean onLevelSelect;

    public enum mainButtons
    {
        Learning,Sandbox,Exit
    }

    public enum levelButtons
    {
        Level1,Level2,Back
    }

    public static InputProcessor input = new MainMenuInput();

    @Override
    public void create()
    {
        //Sets up custom input processing
        Gdx.input.setInputProcessor(input);

        mainGui = new GUI();
        mainGui.AddButton(mainButtons.Learning.name(), new Rectangle(GameManager.ScreenX/2,360,200,96), null, Button.Justifications.Center);
        mainGui.AddButton(mainButtons.Sandbox.name(), new Rectangle(GameManager.ScreenX/2,240,200,96), null, Button.Justifications.Center);
        mainGui.AddButton(mainButtons.Exit.name(), new Rectangle(GameManager.ScreenX/2,120,200,96), null, Button.Justifications.Center);

        levelGui = new GUI();
        levelGui.AddButton(levelButtons.Level1.name(), new Rectangle(GameManager.ScreenX/2,360,200,96), null, Button.Justifications.Center);
        levelGui.AddButton(levelButtons.Level2.name(), new Rectangle(GameManager.ScreenX/2,240,200,96), null, Button.Justifications.Center);
        levelGui.AddButton(levelButtons.Back.name(), new Rectangle(GameManager.ScreenX/2,120,200,96), null, Button.Justifications.Center);

        onLevelSelect = false;
        curGui = mainGui;

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
        curGui.Draw();
    }

    @Override
    public void lateRender()
    {
        curGui.DebugDraw();
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
