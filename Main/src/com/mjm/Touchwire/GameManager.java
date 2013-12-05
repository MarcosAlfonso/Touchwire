package com.mjm.Touchwire;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mjm.Touchwire.Entities.Board;
import com.mjm.Touchwire.States.SandboxState;
import com.mjm.Touchwire.States.MainMenuState;
import com.mjm.Touchwire.States.SandboxInput;
import com.mjm.Touchwire.Utililities.DebugDisplay;
import com.mjm.Touchwire.Utililities.DebugDisplayTimed;
import com.mjm.Touchwire.Utililities.GameState;

public class GameManager implements ApplicationListener
{
    public static int ResolutionResolver;

    //Screen resolution
    public static final int ScreenX = 1280;
    public static final int ScreenY = 800;

    //State Stuff
    public enum GameStates
    {
        MainMenu, Sandbox,
    }

    public GameManager(platformSpecific platform)
    {
        Platform = platform;
    }

    public static platformSpecific Platform;

    static GameStates curStateName = GameStates.MainMenu;
    public static GameState curState;

    public static SandboxState sandboxState = new SandboxState();
    public static MainMenuState mainMenuState = new MainMenuState();

    //Basic stuff
    public static SpriteBatch spriteBatch;
    public static ShapeRenderer shapeRender;
    public static SandboxInput input = new SandboxInput();

    //Textures
    //Entities
    public static Texture batteryTexture;
    public static Texture lightTextureOff;
    public static Texture lightTextureOn;
    public static Texture NegativeTerminalTexture;
    public static Texture PositiveTerminalTexture;
    public static Texture tangibleZone;
    public static Texture tangibleLightOff;
    public static Texture tangibleLightOn;
    public static Texture openSwitchTexture;
    public static Texture closeSwitchTexture;

    //Fonts
    public static BitmapFont fontSmall;
    public static BitmapFont fontMedium;
    public static BitmapFont fontLarge;

    //GUI
    public static Texture clearButtonTexture;
    public static Texture tangibleZoneButton;
    public static Texture backButton;
    public static Texture batteryButtonTexture;
    public static Texture lightButtonTexture;
    public static Texture SelectedTerminalTexture;
    public static Texture SwitchButton;
    public static Texture Credits;
    public static Texture EXIT;
    public static Texture CREDITS;
    public static Texture LEARNING;
    public static Texture SANDBOX;
    public static Texture TitleScreen;

    public static DebugDisplay debugText;
    public static DebugDisplayTimed debugTimed;

    public static OrthographicCamera cam;

    @Override
    //On application creation
    public void create()
    {
        //Debug constructors
        debugText = new DebugDisplay(20, 780);
        debugTimed = new DebugDisplayTimed(800, 780);

        //Texture Loading
        //Entities
        batteryTexture = new Texture(Gdx.files.internal("Entities/newBatteryPack.png"));
        lightTextureOff = new Texture(Gdx.files.internal("Entities/DigitalLightOff.png"));
        lightTextureOn = new Texture(Gdx.files.internal("Entities/DigitalLightOn.png"));
        NegativeTerminalTexture = new Texture(Gdx.files.internal("Entities/newNegativeTerminal.png"));
        PositiveTerminalTexture = new Texture(Gdx.files.internal("Entities/newPositiveTerminal.png"));
        tangibleZone = new Texture(Gdx.files.internal("Entities/TangibleZone.png"));
        tangibleLightOff = new Texture(Gdx.files.internal("Entities/TangibleLightOff.png"));
        tangibleLightOn = new Texture(Gdx.files.internal("Entities/TangibleLightOn.png"));
        openSwitchTexture = new Texture(Gdx.files.internal("Entities/OpenSwitch.png"));
        closeSwitchTexture = new Texture(Gdx.files.internal("Entities/ClosedSwitch.png"));

        //Fonts
        fontSmall = new BitmapFont(Gdx.files.internal("Fonts/Helv25.fnt"), false);
        fontMedium = new BitmapFont(Gdx.files.internal("Fonts/Helv65.fnt"), false);
        fontLarge = new BitmapFont(Gdx.files.internal("Fonts/Helv200.fnt"), false);

        //GUI
        SelectedTerminalTexture = new Texture(Gdx.files.internal("GUI/terminalSelect.png"));
        clearButtonTexture = new Texture(Gdx.files.internal("GUI/newclearButton.png"));
        batteryButtonTexture = new Texture(Gdx.files.internal("GUI/newBatteryButton.png"));
        lightButtonTexture = new Texture(Gdx.files.internal("GUI/LightButton.png"));
        tangibleZoneButton = new Texture(Gdx.files.internal("GUI/newTangibleZoneButton.png"));
        backButton = new Texture(Gdx.files.internal("GUI/newbackButton.png"));
        SwitchButton = new Texture(Gdx.files.internal("GUI/SwitchButton.png"));
        Credits = new Texture(Gdx.files.internal("GUI/credits.png"));
        EXIT = new Texture(Gdx.files.internal("GUI/EXIT.png"));
        CREDITS = new Texture(Gdx.files.internal("GUI/CREDITSbutton.png"));
        LEARNING = new Texture(Gdx.files.internal("GUI/LEARNING.png"));
        SANDBOX = new Texture(Gdx.files.internal("GUI/SANDBOX.png"));
        TitleScreen = new Texture(Gdx.files.internal("GUI/mainTitle.png"));

        //Sets up custom input processing
        Gdx.input.setInputProcessor(input);

        //Camera initializing
        cam = new OrthographicCamera(ScreenX, ScreenY);

        Platform.setResolutionResolver();

        spriteBatch = new SpriteBatch();
        shapeRender = new ShapeRenderer();

        setState(GameStates.MainMenu);
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
        Gdx.gl.glClearColor(.24f, .34f, .65f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        //Sets campera position and updates/applies
        cam.position.x = ScreenX / 2;
        cam.position.y = ScreenY / 2;
        cam.update();
        spriteBatch.setProjectionMatrix(cam.combined);

        //Sprite batch drawing
        spriteBatch.begin();
        debugTimed.Draw();
        debugText.Draw();
        curState.render();
        spriteBatch.end();

        //Fucking shape render lines mess everything up, but them in their own batch for now
        spriteBatch.begin();
        curState.lateRender();
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

    //Sets the state
    public static void setState(GameStates newState)
    {
        curStateName = newState;

        switch (newState)
        {
            case MainMenu:
                curState = mainMenuState;
                mainMenuState.create();
                break;

            case Sandbox:
                curState = sandboxState;
                sandboxState.create();
                break;
        }
    }

    //Convenience funtion for drawing rectangles
    public static void DrawRect(Rectangle rect, float r, float g, float b, float a, boolean solid)
    {
        shapeRender.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        shapeRender.setTransformMatrix(spriteBatch.getTransformMatrix());
        Gdx.gl.glLineWidth(2);

        if (solid)
            shapeRender.begin(ShapeRenderer.ShapeType.FilledRectangle);
        else
            shapeRender.begin(ShapeRenderer.ShapeType.Rectangle);
        shapeRender.setColor(r, g, b, a);
        shapeRender.rect(rect.x, rect.y, rect.width, rect.height);
        shapeRender.end();
    }

    public interface platformSpecific
    {
        public void ExitGame();
        public void setResolutionResolver();
    }
}
