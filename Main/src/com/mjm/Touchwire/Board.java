package com.mjm.Touchwire;

import java.util.ArrayList;
import java.util.List;
import com.mjm.Touchwire.Input;

//The board class represents the surface upon touchwire components are created on
public class Board
{

    public static List<Component> components = new ArrayList<Component>(); //dynamic list of Component(s)

    public void Draw()
    {
        Main.debugText.addDebug("Number of Components: " + components.size());
        Main.debugText.addDebug("Last Terminal: " + Main.input);

        //iterates through all components and draws them
        for(Component comp : components)
        {
            comp.Update();
            comp.Draw();
        }
    }
}
