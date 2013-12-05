package com.mjm.Touchwire.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.Entities.*;
import com.mjm.Touchwire.GameManager;
import com.mjm.Touchwire.Utililities.Button;
import com.mjm.Touchwire.Utililities.GUI;
import com.mjm.Touchwire.Utililities.GameState;

public class SandboxState extends GameState {
    //GUI and Buttons
    public static GUI gui;
    public static GUI sandboxGui;

    public enum Buttons {
        Battery, Light, Tangible, Switch, Clear, Back
    }

    public static InputProcessor input = new SandboxInput();
    public static Board board;

    //Learning Mode Stuff
    public static Boolean learningMode = false;
    public static String tutorialText1 = "";
    public static String tutorialText2 = "";

    @Override
    public void create() {

        //Set up the UI
        //No need to create sandbox GUI
        sandboxGui = new GUI();
        sandboxGui.AddButton(Buttons.Battery.name(), new Rectangle(0, 0, 96, 96), GameManager.batteryButtonTexture, Button.Justifications.BottomLeft);
        sandboxGui.AddButton(Buttons.Light.name(), new Rectangle(128, 0, 96, 96), GameManager.lightButtonTexture, Button.Justifications.BottomLeft);
        sandboxGui.AddButton(Buttons.Tangible.name(), new Rectangle(256, 0, 96, 96), GameManager.tangibleZoneButton, Button.Justifications.BottomLeft);
        sandboxGui.AddButton(Buttons.Switch.name(), new Rectangle(384, 0, 96, 96), GameManager.SwitchButton, Button.Justifications.BottomLeft);

        gui = new GUI();
        gui.AddButton(Buttons.Clear.name(), new Rectangle(GameManager.ScreenX - 96, 0, 96, 96), GameManager.clearButtonTexture, Button.Justifications.BottomLeft);
        gui.AddButton(Buttons.Back.name(), new Rectangle(GameManager.ScreenX - 96, GameManager.ScreenY - 96, 96, 96), GameManager.backButton, Button.Justifications.BottomLeft);

        //Sets up custom input processing
        Gdx.input.setInputProcessor(input);
        board = new Board();

        learningMode = false;
    }

    @Override
    public void resize(int width, int height) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    //Run every frame
    public void render() {
        GameManager.debugText.addDebug(tutorialText1);
        GameManager.debugText.addDebug(tutorialText2);

        //Sprite batch drawing
        board.Draw();
        gui.Draw();

        if (!learningMode)
            sandboxGui.Draw();
    }

    @Override
    public void lateRender() {
        //Fucking shape render lines mess everything up, but them in their own batch for now
        for (Component comp : board.components) {
            if (comp.negTerminal.wire != null) {
                comp.negTerminal.wire.Draw();
            }

            if (comp.posTerminal.wire != null) {
                comp.posTerminal.wire.Draw();
            }
        }

        if (SandboxInput.tempWireID != -1 && SandboxInput.lastTerminal != null) {
            //Flips y because you have to okay?
            Vector2 touchLoc = new Vector2(Gdx.input.getX(SandboxInput.tempWireID), Gdx.input.getY(SandboxInput.tempWireID));
            int flippedY = (int) (GameManager.ScreenY - touchLoc.y / GameManager.ResolutionResolver);

            //Hacky shit to make resolution work on both desktop and tablet
            int halfX = (int) (touchLoc.x / GameManager.ResolutionResolver);
            int halfY = flippedY;

            GameManager.shapeRender.begin(ShapeRenderer.ShapeType.Line);
            GameManager.shapeRender.setColor(Color.RED);
            Gdx.gl.glLineWidth(10 * GameManager.ResolutionResolver);
            GameManager.shapeRender.line(SandboxInput.lastTerminal.Bounds.x*GameManager.ResolutionResolver , SandboxInput.lastTerminal.Bounds.y*GameManager.ResolutionResolver, halfX*GameManager.ResolutionResolver, halfY*GameManager.ResolutionResolver-32);
            GameManager.shapeRender.end();
        }

        //Uncomment to debug buttons
        //gui.DebugDraw();
    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public static void LoadOne() {
        Board.components.clear();
        Board.components.add(new Battery(new Vector2(153, 388)));
        Board.components.add(new Zone(new Vector2(950, 432)));

        learningMode = true;

        tutorialText1 = "Touch and drag wires between the positive (+) and negative (-) symbols on each object to make a wire";
        //tutorialText2 = "Test Level 1";

        //Lock them components
        for (Component comp : Board.components) {
            comp.lockPosition = true;
        }

    }

    public static void LoadTwo() {
        Board.components.clear();
        Board.components.add(new Battery(new Vector2(153, 588)));
        Board.components.add(new Zone(new Vector2(950, 432)));
        Board.components.add(new Switch(new Vector2(364, 200)));

        learningMode = true;

        tutorialText1 = "Using the same dragging from before, wire up all the components";
        tutorialText2 = "Tap the Switch to turn the circuit on and off";

        //Lock them components
        for (Component comp : Board.components) {
            comp.lockPosition = true;
        }
    }

}
