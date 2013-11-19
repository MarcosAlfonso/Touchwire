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
            float lValue = 0;// Used to find the top-left point "should" be lowest value
            float rValue = 0;// Used to find the top-right point "should" be lowest value
            float componentKey = 0;// Used to figure out what component this is dst of all points added together
            int topLeftTouch = -1;// "should" become pointer to topLeftTouch if stays -1 serious error has occurred
            int topRightTouch = -1;// "should" become pointer to topRightTouch if stays -1 serious error has occurred

            //Loop through all the touches on object to calculate top-left point, top-right point and component key
            for(int i = 0; i < touchList.size(); i++)
            {
                int currentTouch = touchList.get(i);// Main touch being looked at
                int nextTouch = touchList.get(i+1 % touchList.size());// Next touch (looped) to be looked at
                Vector2 currentPoint = new Vector2(Gdx.input.getX(currentTouch), Gdx.input.getY(currentTouch));// Vector of current point
                Vector2 nextPoint = new Vector2(Gdx.input.getX(nextTouch), Gdx.input.getY(nextTouch));// Vector of next point

                //Find the top left point (HACKY)
                float leftKeyValue = currentPoint.x * currentPoint.y;
                if(leftKeyValue < lValue){
                    lValue = leftKeyValue;
                    topLeftTouch = currentTouch;
                }
                //Find the top right point (HACKY)
                float rightKeyValue = (GameManager.ScreenX - currentPoint.x) * currentPoint.y;
                if(rightKeyValue < rValue)
                {
                    rValue = rightKeyValue;
                    topRightTouch = currentTouch;
                }
                //Calculate the componentKey
                componentKey += currentPoint.dst(nextPoint);
            }

            //Calculates a Vector to represent x-axis in the local space of the tangible using the top-l and top-r points
            Vector2 tangibleX = new Vector2(Gdx.input.getX(topRightTouch) - Gdx.input.getX(topLeftTouch), Gdx.input.getY(topRightTouch) - Gdx.input.getY(topLeftTouch));

            //Angle of rotation to the local space of the tangible from the world space
            float angleOfRot = tangibleX.angle();

            //For button/switch on tangible states
            if(touchList.size() == 4)
                componentKey = 9001;
            //Check for an error state
            if(touchList.size() >4)
                componentKey = -1; //error state;
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
