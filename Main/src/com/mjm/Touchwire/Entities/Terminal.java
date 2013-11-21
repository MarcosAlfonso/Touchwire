package com.mjm.Touchwire.Entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.Entities.Component;
import com.mjm.Touchwire.Entities.Wire;
import com.mjm.Touchwire.GameManager;

//Terminal can be positive or negative, for connecting components with wires
public class Terminal
{
    public com.mjm.Touchwire.Entities.Component Component; //Component that owns the terminals
    public boolean isPositive; //Is it a positive or negative terminal
    public Rectangle Bounds; //Position and Size rectangle
    public Wire wire; //Wire that is attached to this terminal, if there is one

    public Vector2 offset;

    //Constructor needs a Component and need to set polarity
    public Terminal(Component comp, Vector2 off ,boolean isPos)
    {
        Component = comp;
        isPositive = isPos;
        offset = off;
        Bounds = new Rectangle(Component.Bounds.x+offset.x,Component.Bounds.y+offset.y,64,64);

    }

    //Keeps terminals snapped to parent component
    public void updatePos()
    {
        Bounds.x = Component.Bounds.x+offset.x;
        Bounds.y = Component.Bounds.y+offset.y;
    }

    //Draws the 2 terminals
    public void Draw()
    {
        if (isPositive)
            GameManager.spriteBatch.draw(GameManager.PositiveTerminalTexture, Bounds.x, Bounds.y);
        else
            GameManager.spriteBatch.draw(GameManager.NegativeTerminalTexture, Bounds.x, Bounds.y);

    }

}
