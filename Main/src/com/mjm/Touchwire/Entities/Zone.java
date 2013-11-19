package com.mjm.Touchwire.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mjm.Touchwire.GameManager;

import java.util.ArrayList;

public class Zone extends Component
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

    private void detectTangibleType()
    {
        if(touchList.isEmpty())
            tangibleType = TangibleTypes.NoTangible;
        else
            tangibleType = TangibleTypes.LightTangible;

        //Work in Progress below
        if(touchList.size() < 3)
            tangibleType = TangibleTypes.NoTangible;
        else
        {
            int value = 0;// Used to find the top-left corner "should" be lowest value
            int topLeftTouch;
            for(int i : touchList)
            {
                int touchX = Gdx.input.getX(i);
                int touchY = Gdx.input.getY(i);
                if(touchX * touchY < value){
                    value = touchX * touchY;
                    topLeftTouch = i;
                }
            }


        }


    }

    public Zone(Vector2 pos)
    {
        super(pos, GameManager.tangibleZone);
        tangibleType = TangibleTypes.NoTangible;
        //stores the pointer to a touch
        touchList = new ArrayList<Integer>();
    }

    @Override
    public void Update()
    {
        super.Update();
        detectTangibleType();
        switch(tangibleType){
            case NoTangible:
                    texture = GameManager.tangibleZone;
                    break;
            case LightTangible:
                    if(isPowered)
                        texture = GameManager.tangibleLightOn;
                    else
                        texture = GameManager.tangibleLightOff;
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
        touchList.add(touchPointer);
    }

    public void touchUpDetected(int touchPointer)
    {
        touchList.remove(touchPointer);
    }
}
