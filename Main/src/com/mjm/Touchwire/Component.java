package com.mjm.Touchwire;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import javax.xml.bind.util.ValidationEventCollector;

/**
 * Created with IntelliJ IDEA.
 * User: Marcos
 * Date: 10/17/13
 * Time: 11:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Component
{
    public Vector2 Position;
    public Texture texture = Main.defaultTexture;

    public Rectangle posTerminal;
    public Rectangle negTerminal;


    public Component(Vector2 pos)
    {
        Position = pos;

        posTerminal = new Rectangle(pos.x+70,pos.y+45,32,32);
        negTerminal = new Rectangle(pos.x+70,pos.y-45,32,32);


    }

    public void Draw()
    {
        Main.spriteBatch.draw(texture, Position.x-texture.getWidth()/2, Position.y-texture.getHeight()/2);
        Main.spriteBatch.draw(Main.NegativeTerminalTexture, negTerminal.x, negTerminal.y);
        Main.spriteBatch.draw(Main.PositiveTerminalTexture, posTerminal.x, posTerminal.y);
    }

}

class Battery extends Component
{
    public int Power = 100;

    public Battery(Vector2 pos)
    {
        super(pos);
        texture = Main.batteryTexture;
    }


}

class Light extends Component
{
    public int Power = 100;

    public Light(Vector2 pos)
    {
        super(pos);
        texture = Main.lightTexture;
    }


}
