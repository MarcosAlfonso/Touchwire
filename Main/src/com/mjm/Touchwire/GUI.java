package com.mjm.Touchwire;

import com.badlogic.gdx.math.Rectangle;

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
    //button list, can probably be removed though not really necessary
    public static List<Button> Buttons = new ArrayList<Button>();

    //Buttons
    public Button BatteryButton;
    public Button LightButton;
    public Button ClearButton;

    //Constructor sets up buttons
    public GUI()
    {
        BatteryButton = new Button( new Rectangle(0,0,96,96), "Battery");
        LightButton = new Button( new Rectangle(128,0,96,96), "Light");
        ClearButton = new Button( new Rectangle(1280-96,0,96,96), "Clear");

        Buttons.add(BatteryButton);
        Buttons.add(LightButton);
        Buttons.add(ClearButton);
    }

    //Draws all them buttons
    public void Draw()
    {
        Main.spriteBatch.draw(Main.batteryButtonTexture, BatteryButton.Bounds.x, BatteryButton.Bounds.y);
        Main.spriteBatch.draw(Main.lightButtonTexture, LightButton.Bounds.x, LightButton.Bounds.y);
        Main.spriteBatch.draw(Main.clearButtonTexture, ClearButton.Bounds.x, ClearButton.Bounds.y);

    }
}

//Potentially superfluous class, needs to be looked at
class Button
{
    Rectangle Bounds;
    String Function;

    Button( Rectangle rect, String func)
    {
        Bounds = rect;
        Function = func;
    }
}