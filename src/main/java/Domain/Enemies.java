package Domain;

import javafx.scene.control.Label;

public class Enemies extends GameObjects{

    //Constructor
    private Enemies(String name, String article){
        super(name, 0, 0, article);
    }

    //Methods
    public static Enemies create(int type){
        Enemies placeholder = null;
        if(type == 0){
            placeholder = new Enemies("Octopus","an");
        }else if(type == 1){
            placeholder = new Enemies("Shark","a");
        }else if(type == 2){
            placeholder = new Enemies("Killer Whale","a");
        }
        return placeholder;
    }

    @Override
    public String toString(){
        return "Enemy: " + getName() + "    You must avoid " + getName() + " at all costs!";
    }
}
