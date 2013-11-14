package com.mjm.Touchwire;

import com.badlogic.gdx.Gdx;
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
    //The two terminals that the wire connect
    Terminal posTerminal;
    Terminal negTerminal;

    //Constructor needs 2 terminals
    public Wire(Terminal posTerm, Terminal negTerm)
    {
        if (posTerm.wire != null)
            posTerm.wire.Delete();

        if (negTerm.wire != null)
            negTerm.wire.Delete();

        posTerminal = posTerm;
        negTerminal = negTerm;
    }

    //Nasty drawing using the shapeRenderer... gross
    public void Draw()
    {
        Main.shapeRender.begin(ShapeRenderer.ShapeType.Line);
        Main.shapeRender.setColor(Color.RED);
        Gdx.gl.glLineWidth(10);
        Main.shapeRender.line(posTerminal.Bounds.x*Main.PCvsAndroid+posTerminal.Bounds.width/2,posTerminal.Bounds.y*Main.PCvsAndroid+posTerminal.Bounds.height/2,negTerminal.Bounds.x*Main.PCvsAndroid+posTerminal.Bounds.width/2,negTerminal.Bounds.y*Main.PCvsAndroid+posTerminal.Bounds.height/2);
        Main.shapeRender.end();
    }

    public void Delete()
    {
        //Makes sure components know that this wire is gone, no longer connected
        posTerminal.Component.posTerminal.wire = null;
        negTerminal.Component.negTerminal.wire = null;
    }
}
