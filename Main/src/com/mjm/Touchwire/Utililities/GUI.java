package com.mjm.Touchwire.Utililities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.GameManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Marcos
 * Date: 10/21/13
 * Time: 2:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class GUI
{
    public List<Button> Buttons = new ArrayList<Button>();

    public void AddButton(String action, Rectangle rectangle, Texture graphic, Button.Justifications just)
    {
        Buttons.add(new Button(action,rectangle,graphic,just));
    }

    public Button getButton(String id)
    {
        for(Button button : Buttons)
        {
            if (button.ID.equals(id))
                return button;
        }

        return null;
    }

    //Draws all them buttons
    public void Draw()
    {
        for(Button button : Buttons)
        {
            button.Draw();
        }
    }

    public void DebugDraw()
    {
        for(Button button : Buttons)
        {
            button.DebugDraw();
        }
    }

    public static void DrawCenteredString(BitmapFont font, String text, Vector2 Pos)
    {
        BitmapFont.TextBounds textBound = font.getBounds(text);
        Vector2 size = new Vector2(textBound.width,textBound.height);
        size = size.mul(0.5f);
        Vector2 origin = new Vector2(Pos.x - size.x, Pos.y + size.y);

        font.draw(GameManager.spriteBatch, text, origin.x, origin.y);
    }

}

