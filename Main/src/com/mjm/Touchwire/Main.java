package com.mjm.Touchwire;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.Utililities.DebugDisplay;
import com.mjm.Touchwire.Utililities.DebugDisplayTimed;

public class Main implements ApplicationListener
{
    //PC = 1 : Android = 2
    public static int PCvsAndroid = 1;

    //Screen resolution
    public static final int ScreenX = 1280;
    public static final int ScreenY = 800;

    //Basic stuff
    public static SpriteBatch spriteBatch;
    public static ShapeRenderer shapeRender;
    public static InputProcessor input = new Input();

    //Textures
    public static Texture batteryTexture;
    public static Texture batteryButtonTexture;
    public static Texture defaultTexture;
    public static Texture lightTextureOff;
    public static Texture lightTextureOn;
    public static Texture lightButtonTexture;
    public static Texture NegativeTerminalTexture;
    public static Texture PositiveTerminalTexture;
    public static Texture blank;
    public static Texture clearButtonTexture;
    public static BitmapFont font;

    //adding the tangible zone textures
    public static Texture tangibleZone;
    public static Texture tangibleLightOff;
    public static Texture tangibleLightOn;
    public static Texture tangibleZoneButton;

    //GUI and Debug
    public static GUI gui = new GUI();
    public static DebugDisplay debugText;
    public static DebugDisplayTimed debugTimed;

    private OrthographicCamera cam;

    public static Board board;

    @Override
    //On application creation
    public void create()
    {
        //Debug constructors
        debugText = new DebugDisplay(20, 780);
        debugTimed = new DebugDisplayTimed(800, 780);

        //Texture loading
        batteryTexture = new Texture(Gdx.files.internal("BatteryPack.png"));
        batteryButtonTexture = new Texture(Gdx.files.internal("BatteryButton.png"));
        defaultTexture = new Texture(Gdx.files.internal("defaultTexture.png"));
        lightTextureOff = new Texture(Gdx.files.internal("DigitalLightOff.png"));
        lightTextureOn = new Texture(Gdx.files.internal("DigitalLightOn.png"));
        lightButtonTexture = new Texture(Gdx.files.internal("LightButton.png"));
        NegativeTerminalTexture = new Texture(Gdx.files.internal("NegativeTerminal.png"));
        PositiveTerminalTexture = new Texture(Gdx.files.internal("PositiveTerminal.png"));
        clearButtonTexture = new Texture(Gdx.files.internal("clearButton.png"));
        blank = new Texture(Gdx.files.internal("blank.png"));
        font = new BitmapFont(Gdx.files.internal("Helv25.fnt"), false);

        //Adding some temp Tangible Textures here
        tangibleZone = new Texture(Gdx.files.internal("TangibleZone.png"));
        tangibleLightOff = new Texture(Gdx.files.internal("TangibleLightOff.png"));
        tangibleLightOn = new Texture(Gdx.files.internal("TangibleLightOn.png"));
        tangibleZoneButton = new Texture(Gdx.files.internal("TangibleZoneButton.png"));

        //Sets up custom input processing
        Gdx.input.setInputProcessor(input);

        //Camera initializing
        cam = new OrthographicCamera(ScreenX,ScreenY);

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
    //Run every frame
    public void render()
    {
        //Clears screen
        Gdx.gl.glClearColor(.53f, .75f, .89f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        //Sets campera position and updates/applies
        cam.position.x = ScreenX/2;
        cam.position.y = ScreenY/2;
        cam.update();
        spriteBatch.setProjectionMatrix(cam.combined);

        //Sprite batch drawing
        spriteBatch.begin();
        debugTimed.Draw();
        debugText.Draw();
        board.Draw();
        gui.Draw();
        spriteBatch.end();


        //Fucking shape render lines mess everything up, but them in their own batch for now
        spriteBatch.begin();

        for(Component comp : board.components)
        {
            if (comp.negTerminal.wire != null)
                comp.negTerminal.wire.Draw();

            if (comp.posTerminal.wire != null)
                comp.posTerminal.wire.Draw();
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
