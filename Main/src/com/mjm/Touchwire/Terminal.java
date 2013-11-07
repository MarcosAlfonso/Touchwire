package com.mjm.Touchwire;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//Terminal can be positive or negative, for connecting components with wires
public class Terminal
{
    public Component Component; //Component that owns the terminals
    public boolean isPositive; //Is it a positive or negative terminal
    public Rectangle Bounds; //Position and Size rectangle
    public Wire wire; //Wire that is attached to this terminal, if there is one

    //Constructor needs a Component and need to set polarity
    public Terminal(Component comp, boolean isPos)
    {
        Component = comp;
        isPositive = isPos;

        //Different positions based off polarity
        if (isPositive)
            Bounds = new Rectangle(Component.Bounds.x+128,Component.Bounds.y+80,48,48);
        else
            Bounds = new Rectangle(Component.Bounds.x+128,Component.Bounds.y,48,48);
    }

    //Keeps terminals snapped to parent component
    public void updatePos()
    {
        Bounds.x =  Component.Bounds.x+128;

        if (isPositive)
            Bounds.y = Component.Bounds.y+80;
        else
            Bounds.y = Component.Bounds.y;
    }

    //Draws the 2 terminals
    public void Draw()
    {
        if (isPositive)
            Main.spriteBatch.draw(Main.PositiveTerminalTexture, Bounds.x, Bounds.y);
        else
            Main.spriteBatch.draw(Main.NegativeTerminalTexture, Bounds.x, Bounds.y);

    }

}
