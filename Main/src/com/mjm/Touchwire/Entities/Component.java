package com.mjm.Touchwire.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.GameManager;
import com.mjm.Touchwire.States.SandboxState;
import com.mjm.Touchwire.Utililities.DebugDisplay;

import java.util.ArrayList;
import java.util.List;

//The component class is the overarching class of objects to be placed on the board to be wired up
public class Component
{
    public Rectangle Bounds; //Rectangle bounds represent size and location of components
    public Texture texture;

    //The positive and negative terminals
    public Terminal posTerminal;
    public Terminal negTerminal;

    public List<Integer> touchList = new ArrayList<Integer>();
    public List<Integer> removeList = new ArrayList<Integer>();

    public DebugDisplay Debug;

    public boolean isPowered;
    public boolean transferPower = true;
    public boolean lockPosition;

    public Component(Vector2 pos, Texture image)
    {
        texture = image;
        //Sets bounds rectangle position to the position provided, centered based of texture size, size is set to texture
        Bounds = new Rectangle(pos.x-texture.getWidth()/2,pos.y-texture.getWidth()/2,texture.getWidth(),texture.getHeight());

        Debug = new DebugDisplay((int)(pos.x-Bounds.width/2),(int)(pos.y-Bounds.height/2));

        //sets up the terminals
        posTerminal = new Terminal(this,new Vector2(128,64), true);
        negTerminal = new Terminal(this, new Vector2(128,0), false);
    }

    public Component(Vector2 pos, Texture image, Boolean lockPos)
    {
        this(pos, image);
        lockPosition = lockPos;
    }

    public void Update()
    {
        //Debugs
        //Debug.addDebug("TouchList: " + touchList.toString());
        //Debug.addDebug("Pos: " + Bounds.x + "," + Bounds.y);
        processTouch();

        if (Bounds.overlaps(SandboxState.gui.getButton(SandboxState.Buttons.Clear.name()).Bounds))
        {
            Board.deleteList.add(this);
        }

        if (!lockPosition)
            UpdatePosition();
    }

    public void Draw()
    {
        //Draws the texture to position of the Bounds
        GameManager.spriteBatch.draw(texture, Bounds.x, Bounds.y);

        Debug.Draw();

        //Draws the terminals
        posTerminal.Draw();
        negTerminal.Draw();
    }

    //Special position setting function so terminals follow it around
    public void SetPosition(int x, int y)
    {
        Bounds.x = x-Bounds.width/2;
        Bounds.y = y-Bounds.height/2;

        posTerminal.updatePos();
        negTerminal.updatePos();
        Debug.x = (int)(x-Bounds.width/2);
        Debug.y = (int)(y-Bounds.height/2);
    }

    public void UpdatePosition()
    {
        if (touchList.size() > 0)
        {
            int screenX = Gdx.input.getX(touchList.get(0));
            int screenY = Gdx.input.getY(touchList.get(0));

            //Flips y because you have to okay?
            int flippedY = GameManager.ScreenY - screenY / GameManager.ResolutionResolver;

            //Hacky shit to make resolution work on both desktop and tablet
            int halfX = screenX / GameManager.ResolutionResolver;
            int halfY = flippedY;
            SetPosition(halfX,halfY);
        }
    }

    //Deletion function makes sure everything gets deleted, wires and all
    public void Delete()
    {
        if (posTerminal.wire!=null)
            posTerminal.wire.Delete();

        if (negTerminal.wire!=null)
            negTerminal.wire.Delete();

        if (negTerminal == GameManager.input.lastTerminal || posTerminal == GameManager.input.lastTerminal)
            GameManager.input.lastTerminal = null;


        SandboxState.board.components.remove(this);
    }

    public void processTouch()
    {
        removeList.clear();

        for(int i : touchList)
        {
            int screenX = Gdx.input.getX(i);
            int screenY = Gdx.input.getY(i);

            //Flips y because you have to okay?
            int flippedY = GameManager.ScreenY - screenY / GameManager.ResolutionResolver;

            //Hacky shit to make resolution work on both desktop and tablet
            int halfX = screenX / GameManager.ResolutionResolver;
            int halfY = flippedY;

            if (!Gdx.input.isTouched(i))
                removeList.add(i);
        }

        touchList.removeAll(removeList);
    }



}

