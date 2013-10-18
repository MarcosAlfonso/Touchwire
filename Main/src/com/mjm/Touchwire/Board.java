package com.mjm.Touchwire;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Marcos
 * Date: 10/17/13
 * Time: 11:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Board
{
    public static List<Component> components = new ArrayList<Component>();
    public static List<Wire> wires = new ArrayList<Wire>();

    public Board()
    {

    }


    public void Draw()
    {
        Main.debugText.addDebug("Number of Components: " + components.size());

        for(Component comp : components)
        {
            comp.Draw();
        }



    }


}
