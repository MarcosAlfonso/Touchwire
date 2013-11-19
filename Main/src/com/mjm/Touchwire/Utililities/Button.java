package com.mjm.Touchwire.Utililities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mjm.Touchwire.GameManager;

public class Button
{
    public String ID;
    public Rectangle Bounds;
    public Texture Graphic;

    public enum Justifications
    {
        BottomLeft,Center
    }

    Button(String id, Rectangle rectangle, Texture graphic, Justifications just)
    {

        ID = id;
        Graphic = graphic;

        if (just == Justifications.BottomLeft)
            Bounds = rectangle;
        else
            Bounds = new Rectangle(rectangle.x-rectangle.width/2,rectangle.y-rectangle.height/2,rectangle.width,rectangle.height);
    }

    public void Draw()
    {
        if (Graphic != null)
            GameManager.spriteBatch.draw(Graphic, Bounds.x, Bounds.y);
    }

    public void DebugDraw()
    {
        
    }
}
