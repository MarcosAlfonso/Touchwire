package com.mjm.Touchwire.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.GameManager;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.io.IOUtils;

import java.awt.image.ComponentSampleModel;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//The board class represents the surface upon touchwire components are created on
public class Board
{

    public static List<Component> components = new ArrayList<Component>(); //dynamic list of Component(s)
    public static List<Component> deleteList = new ArrayList<Component>(); //dynamic list of Component(s)

    public void Draw()
    {
        //iterates through all components and draws them
        for (Component comp : components)
        {
            comp.Draw();
            comp.Update();
        }

        for (Component comp : deleteList)
        {
            comp.Delete();
        }

        deleteList.clear();

        if (GameManager.input.lastTerminal != null)
        {
            GameManager.spriteBatch.draw(GameManager.SelectedTerminalTexture, GameManager.input.lastTerminal.Bounds.x, GameManager.input.lastTerminal.Bounds.y);
        }
    }

    public void Save()
    {
        XStream xstream = new XStream();

        String xml = xstream.toXML(components);

        FileHandle file = Gdx.files.local("save.xml");
        file.writeString(xml,false);

        GameManager.debugTimed.addDebug("Game Saved!", 4);
    }

    public void Load()
    {
        XStream xstream = new XStream();

        FileHandle file = Gdx.files.internal("save.xml");
        String xml = file.readString();

        components = (List<Component>)xstream.fromXML(xml);

        GameManager.debugTimed.addDebug(xml, 1);


    }

}
