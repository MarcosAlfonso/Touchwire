package com.mjm.Touchwire;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Marcos
 * Date: 10/18/13
 * Time: 1:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class Wire
{
    Vector2 startPos;
    Vector2 endPos;

    public Wire(Vector2 sPos, Vector2 ePos)
    {
        startPos = sPos;
        endPos = ePos;
    }

    public void Draw()
    {
        Main.shapeRender.begin(ShapeRenderer.ShapeType.Line);
        Main.shapeRender.setColor(Color.RED);
        Main.shapeRender.line(startPos.x,startPos.y,endPos.x,endPos.y);
        Main.shapeRender.end();
    }
}
