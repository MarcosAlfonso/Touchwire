package com.mjm.Touchwire.States;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.Entities.*;
import com.mjm.Touchwire.GameManager;
import com.mjm.Touchwire.Entities.Terminal;

import java.io.Console;

public class SandboxInput implements InputProcessor {

    public static Terminal lastTerminal = null;
    public static int tempWireID = -1;

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
        int flippedY = GameManager.ScreenY - screenY / GameManager.ResolutionResolver;

        //Hacky shit to make resolution work on both desktop and tablet
        int halfX = screenX / GameManager.ResolutionResolver;
        int halfY = flippedY;

        //BUTTON STUFF
        //Sandbox Gui
        if (!SandboxState.learningMode) {
            if (SandboxState.sandboxGui.getButton(SandboxState.Buttons.Battery.name()).Bounds.contains(halfX, halfY)) {
                GameManager.debugTimed.addDebug("Battery Spawned", 1);
                SandboxState.board.components.add(new Battery(new Vector2(halfX, halfY)));
            } else if (SandboxState.sandboxGui.getButton(SandboxState.Buttons.Light.name()).Bounds.contains(halfX, halfY)) {
                GameManager.debugTimed.addDebug("Light Zone Spawned", 1);
                SandboxState.board.components.add(new Light(new Vector2(halfX, halfY)));
            } else if (SandboxState.sandboxGui.getButton(SandboxState.Buttons.Tangible.name()).Bounds.contains(halfX, halfY)) {
                GameManager.debugTimed.addDebug("Tangible Zone Spawned", 1);
                SandboxState.board.components.add(new Zone(new Vector2(halfX, halfY)));
            } else if (SandboxState.sandboxGui.getButton(SandboxState.Buttons.Switch.name()).Bounds.contains(halfX, halfY)) {
                GameManager.debugTimed.addDebug("Switch Spawned", 1);
                SandboxState.board.components.add(new Switch(new Vector2(halfX, halfY)));
            }

        }

        //Generic Gui
        if (SandboxState.gui.getButton(SandboxState.Buttons.Back.name()).Bounds.contains(halfX, halfY)) {
            GameManager.setState(GameManager.GameStates.MainMenu);
        } else if (SandboxState.gui.getButton(SandboxState.Buttons.Clear.name()).Bounds.contains(halfX, halfY)) {
            SandboxState.board.components.clear();
            lastTerminal = null;
            GameManager.debugTimed.addDebug("Board Cleared", 3);
        }

        //Iterate through all the components on the board, so we can check if anything is getting touched
        for (Component comp : SandboxState.board.components) {

            //If they touch an existing component, add pointer to component touchList
            if (comp.Bounds.contains(halfX, halfY)) {
                comp.touchList.add(pointer);
                if (comp instanceof Switch)
                    ((Switch) comp).isClosed = !((Switch) comp).isClosed;
                return true;
            }

            //WIRING STUFF
            //If they touch a positive terminal
            else if (comp.posTerminal.Bounds.contains(halfX, halfY)) {
                lastTerminal = comp.posTerminal;
                tempWireID = button;

                if (comp.posTerminal.wire != null)
                    comp.posTerminal.wire.Delete();

                return true;
            } else if (comp.negTerminal.Bounds.contains(halfX, halfY)) {
                lastTerminal = comp.negTerminal;
                tempWireID = button;

                if (comp.negTerminal.wire != null)
                    comp.negTerminal.wire.Delete();

                return true;
            }
        }

        lastTerminal = null;
        tempWireID = -1;
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //Flips y because you have to okay?
        int flippedY = GameManager.ScreenY - screenY / GameManager.ResolutionResolver;

        //Hacky shit to make resolution work on both desktop and tablet
        int halfX = screenX / GameManager.ResolutionResolver;
        int halfY = flippedY;

        //Iterate through all the components on the board, so we can check if anything is getting touched
        for (Component comp : SandboxState.board.components) {
            //WIRING STUFF
            //If they touch a positive terminal
            if (lastTerminal != null && lastTerminal.isPositive) {
                if (comp.negTerminal.Bounds.contains(halfX, halfY) && lastTerminal != null && button == tempWireID) {
                    if (comp != lastTerminal.Component) {
                        //GameManager.debugTimed.addDebug("DEBUG: Wire Created", 3);
                        Wire newWire = new Wire(lastTerminal, comp.negTerminal);
                        lastTerminal.wire = newWire;
                        comp.negTerminal.wire = newWire;
                        lastTerminal = null;
                    }
                }
                if (comp.posTerminal.Bounds.contains(halfX, halfY) && lastTerminal != null && button == tempWireID) {
                    GameManager.debugTimed.addDebug("Can't wire positive to positive");
                }
            }
            if (lastTerminal != null && !lastTerminal.isPositive) {
                if (comp.posTerminal.Bounds.contains(halfX, halfY) && lastTerminal != null && button == tempWireID) {
                    if (comp != lastTerminal.Component) {
                        //GameManager.debugTimed.addDebug("DEBUG: Wire Created", 3);
                        Wire newWire = new Wire(comp.posTerminal, lastTerminal);
                        lastTerminal.wire = newWire;
                        comp.posTerminal.wire = newWire;
                        lastTerminal = null;
                    }
                }
                if (comp.posTerminal.Bounds.contains(halfX, halfY) && lastTerminal != null && button == tempWireID) {
                    GameManager.debugTimed.addDebug("Can't wire negative to negative");
                }
            }
        }


        lastTerminal = null;
        tempWireID = -1;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
