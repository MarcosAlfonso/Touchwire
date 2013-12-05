package com.mjm.Touchwire.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mjm.Touchwire.GameManager;

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
        GameManager.shapeRender.begin(ShapeRenderer.ShapeType.Line);
        GameManager.shapeRender.setColor(Color.RED);
        Gdx.gl.glLineWidth(10* GameManager.ResolutionResolver);
        GameManager.shapeRender.line(posTerminal.Bounds.x*GameManager.ResolutionResolver,posTerminal.Bounds.y*GameManager.ResolutionResolver,negTerminal.Bounds.x*GameManager.ResolutionResolver,negTerminal.Bounds.y*GameManager.ResolutionResolver);
        GameManager.shapeRender.end();
    }

    public void Delete()
    {
        //Makes sure components know that this wire is gone, no longer connected
        posTerminal.Component.posTerminal.wire = null;
        negTerminal.Component.negTerminal.wire = null;
    }
}
