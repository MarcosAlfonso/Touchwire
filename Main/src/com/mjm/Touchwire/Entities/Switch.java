package com.mjm.Touchwire.Entities;

import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.GameManager;

/**
 * Created with IntelliJ IDEA.
 * User: JoshuaStrunk
 * Date: 11/21/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */

public class Switch extends Component
{
    public boolean isClosed;

    public Switch(Vector2 pos)
    {
        super(pos, GameManager.openSwitchTexture);
        posTerminal.offset = new Vector2(-64,32);
        negTerminal.offset = new Vector2(128,32);
    }

    public void Update()
    {
        super.Update();

        transferPower = isClosed;

        if (isClosed)
        {
            texture = GameManager.closeSwitchTexture;
        }
        else
        {
            texture = GameManager.openSwitchTexture;
        }

    }
}
