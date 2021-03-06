package Domain;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameLogic {
    //Attributes
    private Room currentRoom;
    private static int[] scoreToNextLevel = {60,120,240,360,480,600};
    private static int roomCount;

    //Constructor
    public GameLogic() {
        createRooms();
    }

    //Methods

    //Method to create rooms, and set keyword for next room
    public void createRooms() {
        Room level1, level2, level3, level4, level5, level6;

        level1 = new Room("at the first level", scoreToNextLevel[0]);
        level2 = new Room("now at level 2", scoreToNextLevel[1]);
        level3 = new Room("now at level 3", scoreToNextLevel[2]);
        level4 = new Room("now at level 4", scoreToNextLevel[3]);
        level5 = new Room("now at level 5", scoreToNextLevel[4]);
        level6 = new Room("now at the final level", scoreToNextLevel[5]);

        level1.setExit("", level2);
        level2.setExit("", level3);
        level3.setExit("", level4);
        level4.setExit("", level5);
        level5.setExit("", level6);

        currentRoom = level1;
    }

    //Movement for player
    public String goInGrid(String direction) throws PlayerIsDeadException, IllegalMoveException {
       return movement(direction);
    }

    //Method to change room
    public String goToNextRoom() {

        //Makes placeholder of current grid
        Grid placeholder = currentRoom.getMap();

        //Switches room
        currentRoom = currentRoom.getExit("");

        //Makes player from placholder grid, equal to player from new grid
        currentRoom.getMap().movePlayerToNextLevel(placeholder);

        roomCount++;
        return getLongDescription();
    }

    public void resetRoomCount(){
        roomCount = 0;
    }

    //method that makes all enemies move
    public void enemyTurn() throws PlayerIsDeadException {
        //finds all enemies
        ArrayList<Enemies> enemies = currentRoom.getMap().getAllEnemies();
        int tries = 0;

        //loops though the list making each move
        for (int i = 0; i < enemies.size(); ) {
            try {//tries moving
                currentRoom.getMap().gridMovement(enemies.get(i), enemyAI(enemies.get(i)));
                i++;
            } catch (IllegalMoveException e) {// if that fails it tries again but only limited times
                tries++;
            } finally { //controls if the one enemy has tried to many times
                if (tries > 4) {
                    i++;
                    tries = 0;
                }
            }
        }
    }

    private String enemyAI(Enemies enemy) {
        Random choiceMaker = new Random();
        int move = 0;

        if (choiceMaker.nextBoolean()) {// 50/50 chance to make a good move or random
            //finds the players position
            ArrayList<Integer> playerPosition = currentRoom.getMap().getPosition(findPlayer());

            //gets the position of the enemy
            ArrayList<Integer> enemyPosition = currentRoom.getMap().getPosition(enemy);

            //2 variables
            int moveableX = playerPosition.get(0) - enemyPosition.get(0);
            int moveableY = playerPosition.get(1) - enemyPosition.get(1);

            if (moveableY != 0) { //moves enemy on the y-axis if it's not equal to players y-value
                if (moveableY > 0) {
                    move = 0;
                } else {
                    move = 1;
                }
            } else if (moveableX != 0) {//moves enemy on the x-axis if it's not equal to players x-value
                if (moveableX > 0) {
                    move = 2;
                } else {
                    move = 3;
                }
            }
        }else { // moves randomly
            move = choiceMaker.nextInt(3);
        }
        //creates the string
        String enemyMove = null;
        if(move == 0){
            enemyMove = "down";
        }else if(move == 1){
            enemyMove = "up";
        }else if(move == 2){
            enemyMove = "right";
        }else if (move == 3){
            enemyMove = "left";
        }

        return enemyMove;
    }

    public static int getRoomCount() {
        return roomCount;
    }

    public int scoreToNextLevel() {
        return currentRoom.scoreToNextLevel();
    }

    public GameObjects[][] getMap(){return currentRoom.getMap().getGrid();}

    public void getMaintenance() {
        currentRoom.getMap().maintenance();
    }

    public boolean isAlive() {
        return findPlayer().status();
    }

    public void killPlayer(){findPlayer().triggerDeath();}

    public int getPlayerTotalTurns() {
        return findPlayer().getTotalTurns();
    }

    public int getPlayerEnergy() {
        return findPlayer().getEnergy();
    }

    public int getPlayerPollutionValue(){
        return findPlayer().getPollutionValue();
    }

    public int getPlayerScore() {
        return findPlayer().getScore();
    }

    public String getLongDescription() {
        return currentRoom.getLongDescription();
    }

    private String movement(String direction) throws IllegalMoveException, PlayerIsDeadException {
        return currentRoom.getMap().gridMovement(findPlayer(), direction);
    }

    public Player findPlayer() {
        return currentRoom.getMap().findPlayer();
    }

    public ArrayList<BufferedImage> listOfImages(){
        ArrayList<BufferedImage> placeholder = new ArrayList<>();

        GameObjects[][] map = getMap();

        for (int column = 0; column < map.length; column++) {
            for (int row = 0; row < map[column].length; row++) {
                placeholder.add(map[column][row].getImage());
            }
        }

        return placeholder;
    }
}
