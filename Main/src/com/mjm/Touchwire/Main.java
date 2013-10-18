package com.mjm.Touchwire;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.Utililities.DebugDisplay;
import com.mjm.Touchwire.Utililities.DebugDisplayTimed;

public class Main implements ApplicationListener
{

    public static final int ScreenX = 1280;
    public static final int ScreenY = 800;

    public static SpriteBatch spriteBatch;
    public static ShapeRenderer shapeRender;
    public static InputProcessor input = new Input();

    public static Texture batteryTexture;
    public static Texture defaultTexture;
    public static Texture lightTexture;
    public static Texture NegativeTerminalTexture;
    public static Texture PositiveTerminalTexture;
    public static Texture blank;
    public static BitmapFont font;

    public static DebugDisplay debugText;
    public static DebugDisplayTimed debugTimed;

    public static String compMode = "Battery";

    public static Board board;

    static Vector2 lastClick = new Vector2(0,0);


    @Override
    public void create()
    {
        debugText = new DebugDisplay(20, 780);
        debugTimed = new DebugDisplayTimed(800, 780);

        batteryTexture = new Texture(Gdx.files.internal("BatteryPack.png"));
        defaultTexture = new Texture(Gdx.files.internal("defaultTexture.png"));
        lightTexture = new Texture(Gdx.files.internal("DigitalLight.png"));
        NegativeTerminalTexture = new Texture(Gdx.files.internal("NegativeTerminal.png"));
        PositiveTerminalTexture = new Texture(Gdx.files.internal("PositiveTerminal.png"));
        blank = new Texture(Gdx.files.internal("blank.png"));


        font = new BitmapFont(Gdx.files.internal("Helv25.fnt"), false);
        Gdx.input.setInputProcessor(input);

        spriteBatch = new SpriteBatch();
        shapeRender = new ShapeRenderer();

        board = new Board();
    }

    @Override
    public void resize(int width, int height)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(.53f, .75f, .89f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        Main.debugText.addDebug("Component Mode: " + compMode);
        spriteBatch.begin();
        debugTimed.Draw();
        debugText.Draw();
        board.Draw();
        spriteBatch.end();

        //Fucking shape render lines mess everything up, but them in their own batch for now
        spriteBatch.begin();
        for(Wire wire : board.wires)
        {
            wire.Draw();
        }
        spriteBatch.end();


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
