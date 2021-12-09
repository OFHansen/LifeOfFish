package Domain;

import Save.HighScore;

public class Player extends GameObjects{
    //Attributes
    private int score;
    private boolean alive;
    private int totalTurns;

    //Constructor
    public Player() {
        super("Tuna",10,"\uD83D\uDC1F",0);
        this.alive = true;
        this.totalTurns = 0;
        this.score = 0;
    }


    //Methods
    public int getScore(){
        return this.score;
    }

    public int getTotalTurns() {
        return totalTurns;
    }

    public void addTotalTurns(int i) {
        this.totalTurns += i;
    }

    public void calculateScore() {
        this.score = this.totalTurns * 10 - getPollutionValue();
    }

    public void triggerDeath() {
        HighScore.createSave(getScore(), getTotalTurns(), getPollutionValue());
        this.alive = false;
    }

    //Checks if player is alive
    public boolean status(){
        boolean placeholder = this.alive;
        return placeholder;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "    Current score: " + getScore() +
                "    Current turn: " + getTurns() + "    Your current pollution level: "
                + getPollutionValue();
    }

}
