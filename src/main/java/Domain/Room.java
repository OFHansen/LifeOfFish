package Domain;

import java.util.HashMap;

public class Room {
    //Attributes
    private String description;
    private HashMap<String, Room> exits;
    private int scoreToNextLevel;
    private Grid grid;
    private static int roomCount = 0;

    //The following code describes the difficulty and size of the individual levels.
    //The first two integers determine the grid size.
    //The third integer determines the quantity of enemies
    //The fourth integer determines the quantity of food
    //The fifth integer determines the quantity of obstacles
    private static int[][] difficulty = {{8,8,2,5,3},{7,7,2,4,5},{6,6,1,4,7},{5,5,1,4,7},{5,5,1,3,8},{5,5,1,3,9}};

    //Constructor
    public Room(String description, int scoreToNextLevel) {
        this.description = description;
        this.exits = new HashMap<String, Room>();
        this.scoreToNextLevel = scoreToNextLevel;

        grid = new Grid(difficulty[roomCount][0],difficulty[roomCount][1],difficulty[roomCount][2],
                difficulty[roomCount][3],difficulty[roomCount][4]);
        roomCount ++;
        if(roomCount == 6){
            roomCount = 0;
        }
    }

    //Methods
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    public Grid getMap() {
        return grid;
    }

    public String getLongDescription()
    {
        return "You are " + description + ".\n";
    }

    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    public int scoreToNextLevel() {
        return this.scoreToNextLevel;
    }
}
