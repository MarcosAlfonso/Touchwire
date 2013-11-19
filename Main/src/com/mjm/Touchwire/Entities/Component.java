package com.mjm.Touchwire.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.GameManager;
import com.mjm.Touchwire.Utililities.DebugDisplay;
import com.mjm.Touchwire.Utililities.GUI;

import java.util.ArrayList;
import java.util.List;

//The component class is the overarching class of objects to be placed on the board to be wired up
public class Component
{
    public Rectangle Bounds; //Rectangle bounds represent size and location of components
    public Texture texture = GameManager.defaultTexture;

    //The positive and negative terminals
    public Terminal posTerminal;
    public Terminal negTerminal;

    public List<Integer> touchList = new ArrayList<Integer>();

    public DebugDisplay Debug;

    public boolean isPowered;

    //Constructor, needs a position as an argument
    public Component(Vector2 pos, Texture image)
    {
        texture = image;
        //Sets bounds rectangle position to the position provided, centered based of texture size, size is set to texture
        Bounds = new Rectangle(pos.x-texture.getWidth()/2,pos.y-texture.getWidth()/2,texture.getWidth(),texture.getHeight());

        Debug = new DebugDisplay((int)pos.x,(int)pos.y);

        //sets up the terminals
        posTerminal = new Terminal(this, true);
        negTerminal = new Terminal(this, false);
    }

    public void Update()
    {
        //Debugs
        Debug.addDebug("TouchList: " + touchList.toString());
        processTouch();
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

    //Deletion function makes sure everything gets deleted, wires and all
    public void Delete()
    {
        if (posTerminal.wire!=null)
            posTerminal.wire.Delete();

        if (negTerminal.wire!=null)
            negTerminal.wire.Delete();

        if (negTerminal == GameManager.input.lastTerminal || posTerminal == GameManager.input.lastTerminal)
            GameManager.input.lastTerminal = null;


        GameManager.board.components.remove(this);
    }

    public void processTouch()
    {
        List<Integer> removeList = new ArrayList<Integer>();

        for(int i : touchList)
        {
            int screenX = Gdx.input.getX(i);
            int screenY = Gdx.input.getY(i);

            //Flips y because you have to okay?
            int flippedY = GameManager.ScreenY - screenY / GameManager.PCvsAndroid;

            //Hacky shit to make resolution work on both desktop and tablet
            int halfX = screenX / GameManager.PCvsAndroid;
            int halfY = flippedY;

            if (!Gdx.input.isTouched(i) || !Bounds.contains(halfX,halfY))
                removeList.add(i);

            if (touchList.get(0) != null)
                SetPosition(halfX,halfY);

        }



        touchList.removeAll(removeList);
    }

}
