package com.mjm.Touchwire.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.Entities.Battery;
import com.mjm.Touchwire.Entities.Board;
import com.mjm.Touchwire.Entities.Component;
import com.mjm.Touchwire.Entities.Zone;
import com.mjm.Touchwire.GameManager;
import com.mjm.Touchwire.Utililities.Button;
import com.mjm.Touchwire.Utililities.GUI;
import com.mjm.Touchwire.Utililities.GameState;

public class SandboxState extends GameState
{
    //GUI and Buttons
    public static GUI gui;

    public enum Buttons
    {
        Battery,Light,Tangible,Clear,Back
    }

    public static InputProcessor input = new SandboxInput();
    public static Board board;

    @Override
    public void create()
    {
        //Set up the UI
        gui = new GUI();
        gui.AddButton(Buttons.Battery.name(), new Rectangle(0,0,96,96), GameManager.batteryButtonTexture, Button.Justifications.BottomLeft);
        gui.AddButton(Buttons.Light.name(), new Rectangle(128,0,96,96), GameManager.lightButtonTexture, Button.Justifications.BottomLeft);
        gui.AddButton(Buttons.Tangible.name(), new Rectangle(256,0,96,96), GameManager.tangibleZoneButton, Button.Justifications.BottomLeft);
        gui.AddButton(Buttons.Clear.name(), new Rectangle(GameManager.ScreenX-96,0,96,96), GameManager.clearButtonTexture, Button.Justifications.BottomLeft);
        gui.AddButton(Buttons.Back.name(), new Rectangle(GameManager.ScreenX-96,GameManager.ScreenY-96,96,96), GameManager.backButton, Button.Justifications.BottomLeft);

        //Sets up custom input processing
        Gdx.input.setInputProcessor(input);
        board = new Board();
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
        //Sprite batch drawing
        board.Draw();
        gui.Draw();
    }

    @Override
    public void lateRender()
    {
        //Fucking shape render lines mess everything up, but them in their own batch for now
        for (Component comp : board.components)
        {
            if (comp.negTerminal.wire != null)
            {
                comp.negTerminal.wire.Draw();
            }

            if (comp.posTerminal.wire != null)
            {
                comp.posTerminal.wire.Draw();
            }
        }

        //Uncomment to debug buttons
        //gui.DebugDraw();
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

    public static void LoadOne()
    {
        Board.components.clear();
        Board.components.add(new Battery(new Vector2(153,388)));
        Board.components.add(new Zone(new Vector2(950,432)));

    }

    public static void LoadTwo()
    {
        Board.components.clear();
    }

}
