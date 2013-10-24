package com.mjm.Touchwire;

import com.badlogic.gdx.math.Vector2;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Joshua Strunk
 * Date: 10/23/13
 * Time: 7:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class Zone {

    private Map<Integer, Vector2> touches = new HashMap<Integer, Vector2>();
    private List<Vector2> sortedPoints;
    private Component linkedComponent;
    private int componentState;

    //Following 3 fucntions are used to update internal list of touchpoints

    //Should be called for touch down and dragged
    public void addTouch(Vector2 p, int idNum){
        touches.put(idNum, p);
    }
    //Should be called for touch up
    public void removeTouch(int idNum){
        touches.remove(idNum);
    }

    //!!!Beware Shit below
    //Checks touches in the zone to identify the component by its touch id
    //!!!NOT TESTED PROB BROKE SHOULD SORT POINTS TL TR BL BR AND GENERATE A COMPOENENT ID NUMBER
    public void identifyComponent(){
        List<Vector2> points  = new ArrayList(Arrays.asList((Vector2[]) touches.values().toArray())); //WHEN SHIT HITS FAN CHECK HERE FOR SHIT!!!


        //Should sort list upper vectors first the lower
        boolean sorted = false;
        while(!sorted){
            sorted = true;
            for(int i=0; i<points.size()-1; ++i){
                if(points.get(i).y < points.get(i+1).y){ // Check need to be > or < based on how we pass points in
                    Vector2 temp = points.get(i);
                    points.set(i,points.get(i+1));
                    points.set(i+1, temp);
                }
            }
        }

        //Sort to get top left as 0 index and top right as 1 index
        if(points.get(0).x > points.get(1).x){
            Vector2 temp = points.get(0);
            points.set(0,points.get(1));
            points.set(1,temp);
        }

        //If more 4 or more points organize point index 2 and 3 to be lowest left 2 and lowest right 3
        if(points.size() >= 4){
            if(points.get(2).x > points.get(3).x){
                Vector2 temp = points.get(2);
                points.set(2, points.get(3));
                points.set(3, temp);
            }
        }

        //really dumb should prob just find way to get first 2 indexes of the points to figure out orintation
        sortedPoints = points;
        //identify component based on distance between top left point and bottom left
        float componentID = points.get(0).dst(points.get(3));
    }

    //Positions the component in or
    public void positionComponent(){

    }


}
