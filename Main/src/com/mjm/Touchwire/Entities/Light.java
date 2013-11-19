package com.mjm.Touchwire.Entities;

import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.GameManager;

//Light subclass of Component
public class Light extends Component
{
    //constructor inherits from parent, but sets texture
    public Light(Vector2 pos)
    {
        super(pos, GameManager.lightTextureOff);
    }


    @Override
    public void Update()
    {
        super.Update();

        if (isPowered)
            texture = GameManager.lightTextureOn;
        else
            texture = GameManager.lightTextureOff;

    }


}
