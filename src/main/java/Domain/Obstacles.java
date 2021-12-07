package Domain;

public class Obstacles extends GameObjects{
    //Constructor
    private Obstacles(String name, int turnValue, int pollutionValue) {
        super(name, turnValue, "\uD83D\uDDD1", pollutionValue);
    }

    public static Obstacles create(int type){
        Obstacles placeholder = null;

        if(type == 0){
            placeholder = new Obstacles("Fishing Net",-3,0);
        }else if(type == 1){
            placeholder = new Obstacles("Fishing Hook",-4,0);
        }else if(type == 2){
            placeholder = new Obstacles("plastic bag",-2,20);
        }

        return placeholder;
    }

    //Methods
    @Override
    public String toString(){
        return "Obstacle: " + getName() + "    "+getName()+"'s pollution level is:" + getPollutionValue() + "    Eating this will make you gain " + getTurnValue() + " turns.";
    }
}
