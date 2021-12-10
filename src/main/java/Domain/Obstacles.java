package Domain;

public class Obstacles extends GameObjects{
    //Constructor
    private Obstacles(String name, int turnValue, int pollutionValue, String article) {
        super(name, turnValue, pollutionValue, article);
    }

    //Methods
    public static Obstacles create(int type){
        Obstacles placeholder = null;
        if(type == 0){
            placeholder = new Obstacles("Fishing Net",-3,5, "a");
        }else if(type == 1){
            placeholder = new Obstacles("Fishing Hook",-4,0,"a");
        }else if(type == 2){
            placeholder = new Obstacles("plastic bag",-2,20,"a");
        }
        return placeholder;
    }

    @Override
    public String toString(){
        return "Obstacle: " + getName() + "    "+getName()+"'s pollution level is:" + getPollutionValue() + "    Eating this will make you gain " + getEnergy() + " turns.";
    }
}
