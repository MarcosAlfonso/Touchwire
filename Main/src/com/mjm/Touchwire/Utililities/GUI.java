package com.mjm.Touchwire.Utililities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
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

        throw new NullPointerException();
    }

    //Draws all them buttons
    public void Draw()
    {
        for(Button button : Buttons)
        {
            button.Draw();
        }
    }
}

