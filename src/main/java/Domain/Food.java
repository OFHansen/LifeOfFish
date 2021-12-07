package Domain;

public class Food extends GameObjects{
    //Constructor
    private Food(String name, int turnValue, int pollutionValue) {
        super(name, turnValue, "\uD83E\uDD90", pollutionValue);
    }

    public static Food create(int type){
        Food placeholder = null;

        if(type == 0){
            placeholder = new Food("Shrimp",4,1);
        }else if(type == 1){
            placeholder = new Food("Crab",5,3);
        }else if(type == 2){
            placeholder = new Food("Small Fish",3,6);
        }

        return placeholder;
    }

    //Methods
    @Override
    public String toString(){
        return "Food: " + getName() + "    "+getName()+"'s pollution level is: " + getPollutionValue() +
                "    Consuming this will make you lose " + getTurnValue() + " turns.";
    }
}
