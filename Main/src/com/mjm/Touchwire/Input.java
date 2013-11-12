package com.mjm.Touchwire;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import javafx.scene.input.TouchPoint;

public class Input implements InputProcessor {

    private static Terminal lastTerminal = null;
    private static Component dragComponent = null;

    @Override
    public boolean keyDown(int keycode) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean keyTyped(char character) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //Flips y because you have to okay?
        int flippedY = Main.ScreenY - screenY / Main.PCvsAndroid;

        //Hacky shit to make resolution work on both desktop and tablet
        int halfX = screenX / Main.PCvsAndroid;
        int halfY = flippedY;

        //Iterates through Buttons, checks if any are touched and does the shit based off that
        for (Button guiButton : Main.gui.Buttons) {
            if (guiButton.Bounds.contains(halfX, halfY)) {
                if (guiButton.Function.equals("Battery")) {
                    Main.debugTimed.addDebug("Battery Spawned", 1);
                    Component newComp = new Battery(new Vector2(halfX, halfY));
                    Main.board.components.add(newComp);
                    dragComponent = newComp;
                } else if (guiButton.Function.equals("Light")) {
                    Main.debugTimed.addDebug("Light Spawned", 1);
                    Component newComp = new Light(new Vector2(halfX, halfY));
                    Main.board.components.add(newComp);
                    dragComponent = newComp;
                } else if (guiButton.Function.equals("Clear")) {
                    Main.board.components.clear();
                    lastTerminal = null;
                    Main.debugTimed.addDebug("Board Cleared", 3);
                } else if (guiButton.Function.equals("Tangible")) {
                    Main.debugTimed.addDebug("Tangible Zone Spawned", 1);
                    Component newComp = new Zone(new Vector2(halfX, halfY));
                    Main.board.components.add(newComp);
                    dragComponent = newComp;
                }
                return true;
            }
        }

        //Iterate through all the components on the board, so we can check if anything is getting touched
        for (Component comp : Main.board.components) {
            //If they touch an existing component, drag it
            if (comp.Bounds.contains(halfX, halfY)) {
                if (!(comp instanceof Zone))
                    dragComponent = comp;

                //Tangible Detection Stuff
                if (comp instanceof Zone) {
                    ((Zone) comp).touchDownDetected(pointer);
                }

                return true;
            }

            //If they touch a positive terminal
            else if (comp.posTerminal.Bounds.contains(halfX, halfY)) {
                //If no terminal selected yet
                if (lastTerminal == null) {
                    Main.debugTimed.addDebug("Now select a negative terminal", 5);
                    lastTerminal = comp.posTerminal;
                }
                //If they select a positive terminal after starting with positive, error
                else if (lastTerminal.isPositive) {
                    Main.debugTimed.addDebug("ERROR: Please select a negative terminal", 3);
                } else if (!lastTerminal.isPositive && comp != lastTerminal.Component) {
                    Main.debugTimed.addDebug("DEBUG: Wire Created", 3);
                    Wire newWire = new Wire(lastTerminal, comp.posTerminal);
                    lastTerminal.wire = newWire;
                    comp.posTerminal.wire = newWire;
                    lastTerminal = null;
                }
                return true;
            }
            //If they touch a negative terminal
            else if (comp.negTerminal.Bounds.contains(halfX, halfY)) {
                //If no terminal selected yet
                if (lastTerminal == null) {
                    Main.debugTimed.addDebug("Now select a positive terminal", 5);
                    lastTerminal = comp.negTerminal;
                }
                //If they select a positive terminal after starting with positive, error
                else if (!lastTerminal.isPositive) {
                    Main.debugTimed.addDebug("ERROR: Please select a positive terminal", 3);
                } else if (lastTerminal.isPositive && comp != lastTerminal.Component) {
                    Main.debugTimed.addDebug("DEBUG: Wire Created", 3);
                    Wire newWire = new Wire(lastTerminal, comp.negTerminal);
                    lastTerminal.wire = newWire;
                    comp.negTerminal.wire = newWire;
                    lastTerminal = null;
                }
                return true;
            }
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //lift up stops dragging
        dragComponent = null;

        //Flips y because you have to okay?
        int flippedY = Main.ScreenY - screenY / Main.PCvsAndroid;

        //Hacky shit to make resolution work on both desktop and tablet
        int halfX = screenX / Main.PCvsAndroid;
        int halfY = flippedY;

        for (Component comp : Main.board.components) {
            if (comp.Bounds.contains(halfX, halfY)) {

                //Tangible Detection Stuff
                if (comp instanceof Zone && ((Zone) comp).touches.size() > 0) {
                    ((Zone) comp).touchUpDetected(pointer);
                }

                return true;
            }
        }

        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Flips y because you have to okay?
        int flippedY = Main.ScreenY - screenY / Main.PCvsAndroid;

        int halfX = screenX / Main.PCvsAndroid;
        int halfY = flippedY;

        //If a component was picked during touchDown, drag that puppy
        if (dragComponent != null) {
            dragComponent.SetPosition(halfX, halfY);

            if (dragComponent.Bounds.overlaps(Main.gui.ClearButton.Bounds))
                dragComponent.Delete();
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean scrolled(int amount) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
