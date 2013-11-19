package com.mjm.Touchwire.Entities;
import com.mjm.Touchwire.GameManager;

import java.util.ArrayList;
import java.util.List;

//The board class represents the surface upon touchwire components are created on
public class Board
{

    public static List<Component> components = new ArrayList<Component>(); //dynamic list of Component(s)
    public static List<Component> deleteList = new ArrayList<Component>(); //dynamic list of Component(s)

    public void Draw()
    {
        GameManager.debugText.addDebug("Number of Components: " + components.size());
        GameManager.debugText.addDebug("Last Terminal: " + GameManager.input);

        //iterates through all components and draws them
        for(Component comp : components)
        {
            comp.Draw();
            comp.Update();
        }

        for( Component comp : deleteList)
        {
            comp.Delete();
        }

        deleteList.clear();

        if(GameManager.input.lastTerminal != null)
        {
            GameManager.spriteBatch.draw(GameManager.SelectedTerminalTexture, GameManager.input.lastTerminal.Bounds.x, GameManager.input.lastTerminal.Bounds.y);
        }
    }
}
