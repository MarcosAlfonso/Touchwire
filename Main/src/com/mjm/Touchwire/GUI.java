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
    //Buttons
    public static Rectangle BatteryButton;
    public static Rectangle LightButton;
    public static Rectangle ClearButton;
    public static Rectangle TangibleButton;

    //Constructor sets up buttons
    public GUI()
    {
        BatteryButton = new Rectangle(0,0,96,96);
        LightButton = new Rectangle(128,0,96,96);
        ClearButton = new Rectangle(1280-96,0,96,96);
        TangibleButton = new Rectangle(256,0,96,96);

    }

    //Draws all them buttons
    public void Draw()
    {
        Main.spriteBatch.draw(Main.batteryButtonTexture, BatteryButton.x, BatteryButton.y);
        Main.spriteBatch.draw(Main.lightButtonTexture, LightButton.x, LightButton.y);
        Main.spriteBatch.draw(Main.clearButtonTexture, ClearButton.x, ClearButton.y);
        Main.spriteBatch.draw(Main.tangibleZoneButton, TangibleButton.x, TangibleButton.y);
    }
}