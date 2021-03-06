package com.mjm.Touchwire.Entities;

import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.GameManager;

import java.util.ArrayList;
import java.util.List;

//Battery subclass of Component
public class Battery extends Component
{
    public boolean isCircuitClosed;

    //List of Components connected to this batterys
    public List<Component> ComponentsInSeries = new ArrayList<Component>(); //dynamic list of Component(s)
    private boolean offSwitch;


    //constructor inherits from parent, but sets texture
    public Battery(Vector2 pos)
    {
        super(pos, GameManager.batteryTexture);
    }

    @Override
    public void Update()
    {
        super.Update();

        //Batteries always gots the power!
        isPowered = true;

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
        if (ComponentsInSeries.size() > 0 && ComponentsInSeries.get(ComponentsInSeries.size()-1).posTerminal.wire == this.negTerminal.wire && this.negTerminal.wire != null && !checkOffSwitch())
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

    private boolean checkOffSwitch()
    {
        for(Component comp : ComponentsInSeries)
        {
            if (comp instanceof Switch && !comp.transferPower)
            {
                return true;
            }
        }

        return false;
    }
}

