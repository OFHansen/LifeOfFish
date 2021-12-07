package Domain;

import javafx.scene.control.Label;

public class Enemies extends GameObjects{
    //Attributes
    private int speed;


    //Constructor
    private Enemies(String name){
        super(name, -1000,"\uD83D\uDC19", 0);


    }

    public static Enemies create(int type){
        Enemies placeholder = null;

        if(type == 0){
            placeholder = new Enemies("Octopus");
        }else if(type == 1){
            placeholder = new Enemies("Shark");
        }else if(type == 2){
            placeholder = new Enemies("Killer Whale");
        }

        return placeholder;
    }

    @Override
    public String toString(){
        return "Enemy: " + getName() + "    You must avoid " + getName() + " at all costs!";
    }
}
