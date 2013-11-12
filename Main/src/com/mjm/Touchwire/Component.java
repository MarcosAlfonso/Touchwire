package com.mjm.Touchwire;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.Utililities.DebugDisplay;

import javax.xml.bind.util.ValidationEventCollector;
import java.util.ArrayList;
import java.util.List;

//The component class is the overarching class of objects to be placed on the board to be wired up
public class Component
{
    public Rectangle Bounds; //Rectangle bounds represent size and location of components
    public Texture texture = Main.defaultTexture;

    //The positive and negative terminals
    public Terminal posTerminal;
    public Terminal negTerminal;


    public DebugDisplay Debug;

    public boolean isPowered;

    //Constructor, needs a position as an argument
    public Component(Vector2 pos, Texture image)
    {
        texture = image;
        //Sets bounds rectangle position to the position provided, centered based of texture size, size is set to texture
        Bounds = new Rectangle(pos.x-texture.getWidth()/2,pos.y-texture.getWidth()/2,texture.getWidth(),texture.getHeight());

        Debug = new DebugDisplay((int)pos.x,(int)pos.y);

        //setups up the terminals
        posTerminal = new Terminal(this, true);
        negTerminal = new Terminal(this, false);
    }

    public void Update()
    {
        //Debugs
        Debug.addDebug("isPowered: " + isPowered);
    }


    public void Draw()
    {
        //Draws the texture to position of the Bounds
        Main.spriteBatch.draw(texture, Bounds.x, Bounds.y);

        Debug.Draw();

        //Draws the terminals
        posTerminal.Draw();
        negTerminal.Draw();
    }

    //Special position setting function so terminals follow it around
    public void SetPosition(int x, int y)
    {
        Bounds.x = x-Bounds.width/2;
        Bounds.y = y-Bounds.height/2;

        posTerminal.updatePos();
        negTerminal.updatePos();
        Debug.x = (int)(x-Bounds.width/2);
        Debug.y = (int)(y-Bounds.height/2);

    }

    //Deletion function makes sure everything gets deleted, wires and all
    public void Delete()
    {
        if (posTerminal.wire!=null)
            posTerminal.wire.Delete();

        if (negTerminal.wire!=null)
            negTerminal.wire.Delete();


        Main.board.components.remove(this);
    }

}

//Battery subclass of Component
class Battery extends Component
{
    public boolean isCircuitClosed;

    //List of Components connected to this batterys
    public List<Component> ComponentsInSeries = new ArrayList<Component>(); //dynamic list of Component(s)

    public static List<Component> connectedComponents = new ArrayList<Component>(); //list of components connected to this battery


    //constructor inherits from parent, but sets texture
    public Battery(Vector2 pos)
    {
        super(pos,Main.batteryTexture);
    }

    @Override
    public void Update()
    {
        super.Update();

        //Batteries always gots the power!
        isPowered = true;

        //Debug Stuff
        Debug.addDebug("Circuit Closed: " + isCircuitClosed);
        Debug.addDebug("# Connected: " + ComponentsInSeries.size());

        //Resets circuit so we never get old circuits surviving
        isCircuitClosed = false;
        for(Component comp : ComponentsInSeries)
        {
            comp.isPowered = false;
        }
        ComponentsInSeries.clear();

        //checks if series of components is closed
        getSeriesComponents(posTerminal);

        //if we have components, and the last component of the circuit is wired to this batteries neg terminal, and there is an actual wire between them
        if (ComponentsInSeries.size() > 0 && ComponentsInSeries.get(ComponentsInSeries.size()-1).posTerminal.wire == this.negTerminal.wire && this.negTerminal.wire != null)
        {
            //circuits closed! give all components power
            for(Component comp : ComponentsInSeries)
            {
                comp.isPowered = true;
            }

            isCircuitClosed = true;
        }

    }

    //Totally dope recursive function that gets all the components in the series, like serious it is beautiful
    private void getSeriesComponents(Terminal posTerm)
    {
        if (posTerm.wire != null && posTerm.wire.negTerminal.Component != this)
        {
            Component newComp = posTerm.wire.negTerminal.Component;
            ComponentsInSeries.add(newComp);
            getSeriesComponents(newComp.posTerminal);
        }
    }
}

//Light subclass of Component
class Light extends Component
{
    //constructor inherits from parent, but sets texture
    public Light(Vector2 pos)
    {
        super(pos,Main.lightTextureOff);
    }


    @Override
    public void Update()
    {
        super.Update();

        if (isPowered)
            texture = Main.lightTextureOn;
        else
            texture = Main.lightTextureOff;

    }


}

class Zone extends Component
{
    public enum TangibleTypes{
        NoTangible,
        LightTangible,
        SwitchTangible
    }

    /* Basically an Enum
     *  0 == No tangible detected
     *  1 == Light tangible detected
     *  2 == Switch tangible detected
     */
    private TangibleTypes tangibleType;
    //Stores the point to a touch
    public List<Integer> touches;

    private void detectTangibleType()
    {
        if(touches.isEmpty())
            tangibleType = TangibleTypes.NoTangible;
        else
            tangibleType = TangibleTypes.LightTangible;
    }

    public Zone(Vector2 pos)
    {
        super(pos,Main.tangibleZone);
        tangibleType = TangibleTypes.NoTangible;
        //stores the pointer to a touch
        touches = new ArrayList<Integer>();
    }

    @Override
    public void Update()
    {
        super.Update();
        detectTangibleType();
        switch(tangibleType){
            case NoTangible:
                    texture = Main.tangibleZone;
                    break;
            case LightTangible:
                    if(isPowered)
                        texture = Main.tangibleLightOn;
                    else
                        texture = Main.tangibleLightOff;
                    break;
            case SwitchTangible:
                    break;
            default:
                    //error statment should go here;
                    break;

        }

    }

    public void touchDownDetected(int touchPointer)
    {
        touches.add(touchPointer);
    }

    public void touchUpDetected(int touchPointer)
    {
        touches.remove(touchPointer);
    }
}