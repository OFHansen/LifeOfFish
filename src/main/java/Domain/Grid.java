package Domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Grid {
    //Attributes
    private int column;
    private int row;
    private int playerQuantity = 1;
    private int enemiesQuantity;
    private int foodQuantity;
    private int obstaclesQuantity;
    private GameObjects[][] grid;

    //Constructor
    public Grid(int column, int row, int enemiesQuantity, int foodQuantity, int obstaclesQuantity) {
        this.column = column;
        this.row = row;
        this.enemiesQuantity = enemiesQuantity;
        this.foodQuantity = foodQuantity;
        this.obstaclesQuantity = obstaclesQuantity;
        createGrid();
    }

    //Methods
    private void createGrid() {

        //a list of the quantity of different entities
        int totalQuantity = playerQuantity + enemiesQuantity +
                foodQuantity + obstaclesQuantity;


        //a number that shows where en the Array there's room
        int placeCounter = 0;

        //Array where all entities are going in
        GameObjects[] objectList = new GameObjects[totalQuantity];

        //creating the player
        objectList[placeCounter] = new Player();
        placeCounter++;


        Random decider = new Random();
        //loop that creates enemies and places them in the array
        for (int i = 0; i < enemiesQuantity; i++) {
            objectList[placeCounter] = Enemies.create(decider.nextInt(2));
            placeCounter++;
        }
        //loop that creates food and places them in the array
        for (int i = 0; i < foodQuantity; i++) {
            objectList[placeCounter] = Food.create(decider.nextInt(2));
            placeCounter++;
        }
        //loop that creates obstacles and places them in the array
        for (int i = 0; i < obstaclesQuantity; i++) {
            objectList[placeCounter] = Obstacles.create(decider.nextInt(2));
            placeCounter++;
        }

        //creates the 2D grid
        GameObjects[][] grid = new GameObjects[column][row];

        //creates a 1D grad copy with a ArrayList
        ArrayList<GameObjects> gridCopy = new ArrayList<>();

        //places all entities in the gridCopy
        for (int i = 0; i < column * row; i++) {
            try {
                gridCopy.add(objectList[i]);
            } catch (ArrayIndexOutOfBoundsException ex) {
                gridCopy.add(new Water());
            }
        }
        //shuffles all entities in the ArrayList
        Collections.shuffle(gridCopy);

        //marge 1D grid with 2D grid
        int count = 0;
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                grid[j][i] = gridCopy.get(count);
                count++;
            }
        }

        this.grid = grid;

    }

    //Method to print grid
    public void printGrid() {
        for (int column = 0; column < this.grid.length; column++) {
            for (int row = 0; row < this.grid[column].length; row++) {
                System.out.print(" " + this.grid[row][column].getSymbol() + " ");
            }
            System.out.println("");
        }
    }

    public ArrayList<Integer> getPosition(GameObjects entity) {

        //list that's going to get returned
        ArrayList<Integer> position = new ArrayList<>();

        //checks all positions in the 2D array for the entity
        for (int column = 0; column < this.grid.length; column++) {
            for (int row = 0; row < this.grid[column].length; row++) {
                if (this.grid[row][column] == entity) {
                    position.add(row);
                    position.add(column);
                    break;
                }
            }
        }
        return position;
    }

    //Method to find player object
    public Player findPlayer() {

        //list that's going to get returned
        ArrayList<Integer> position = new ArrayList<>();

        //checks all positions in the 2D array for the player
        for (int column = 0; column < this.column; column++) {
            for (int row = 0; row < this.grid[column].length; row++) {
                if (this.grid[row][column] instanceof Player) {
                    position.add(row);
                    position.add(column);
                    break;
                }
            }
        }
        return (Player) this.grid[position.get(0)][position.get(1)];
    }

    //Method that finds all Enemies and returns them in an Arraylist
    public ArrayList<Enemies> getAllEnemies(){
        //crates the arraylist
        ArrayList<Enemies> enemies = new ArrayList<>();

        //loops through the grid, looking for enemies
        for (int column = 0; column < this.grid.length; column++) {
            for (int row = 0; row < this.grid[column].length; row++) {
                if (this.grid[row][column] instanceof Enemies) {
                    enemies.add((Enemies) this.grid[row][column]);
                }
            }
        }
        return enemies;
    }

    //Movement in grid
    public void gridMovement(GameObjects entity, Command direction) throws IllegalMoveException, IllegalCommandException,
            PlayerIsDeadException {

        //2 values that represent where the entity is going
        int row = 0;
        int column = 0;

        //manipulation of the 2 values according to the action
        if(!direction.getSecondWord().equals("right")&&!direction.getSecondWord().equals("left")&&
                !direction.getSecondWord().equals("up")&&!direction.getSecondWord().equals("down")) {
            throw new IllegalMoveException("spell better lol");
        }

        if (direction.getSecondWord().equals("right")) {
            row = 1;
        } else if (direction.getSecondWord().equals("left")) {
            row = -1;
        } else if (direction.getSecondWord().equals("up")) {
            column = -1;
        } else if (direction.getSecondWord().equals("down")) {
            column = 1;
        }

        //method that finds the entity position in the grid
        ArrayList<Integer> entityPosition = getPosition(entity);

        //takes whatever there is on the position the entity is about to go to at stores it
        GameObjects placeholder = null;
        try {
            placeholder = this.grid[entityPosition.get(0) + row][entityPosition.get(1) + column];
        } catch (IndexOutOfBoundsException ex){
            GameLogic.setInformationString("Your fish tried to move, but were pushed back by the current.");
            throw new IllegalMoveException("This is a illegal move, try something else.");
        }

        //overrides that position with the entity
        this.grid[entityPosition.get(0) + row][entityPosition.get(1) + column] = entity;

        //places water where original the entity was
        if(entity instanceof Enemies) {
            this.grid[entityPosition.get(0)][entityPosition.get(1)] = placeholder;
        }else{
            this.grid[entityPosition.get(0)][entityPosition.get(1)] = new Water();
        }
        if(entity instanceof Player){
            if(!(findPlayer().getScore() >= GameLogic.getScoreToNextLevel()[GameLogic.getRoomCount()])){
                findPlayer().addTotalTurns(1);
            }
            findPlayer().removeTurns(1);
        }


        //checks if the entity is the player
        if (entity instanceof Player) {
            if (placeholder instanceof Food) { //checks if the player collided with a food
                ((Player) entity).addTurns(placeholder.getTurnValue());
                ((Player) entity).addPollutionValue(placeholder.getPollutionValue());

                GameLogic.setInformationString("You ate a " + placeholder.getName() + "." + "" +
                        "\nFor this action you have gained some energy." + "" +
                        "\n...and maybe something more.");

            } else if (placeholder instanceof Obstacles) { //checks if the player collided with an obstacle
                ((Player) entity).addTurns(placeholder.getTurnValue());
                ((Player) entity).addPollutionValue(placeholder.getPollutionValue());
                GameLogic.setInformationString("You accidentally ate a " + placeholder.getName() + ".");

                if(((Player) entity).getTurnValue() <= 0){
                    ((Player) entity).triggerDeath();

                    GameLogic.setInformationString("You have eaten too much waste, therefore you have run out of energy.");
                } else {
                    GameLogic.setInformationString("For this action you will lose some energy. " +
                            "\n...and maybe something more. ");
                }
            } else if (placeholder instanceof Water) { //checks if the player collided with some water
                ((Player) entity).addPollutionValue(placeholder.getPollutionValue());

                GameLogic.setInformationString("There is nothing.");
                if(((Player) entity).getTurnValue() <= 0){
                    ((Player) entity).triggerDeath();

                    GameLogic.setInformationString("You have not eaten enough food and have thus run out of energy.");
                } else {
                    GameLogic.setInformationString("This action will yield you nothing." +
                            "\n... or maybe it will.");
                }

            } else if (placeholder instanceof Enemies) { //checks if the player collided with an enemy
                ((Player) entity).triggerDeath();

                GameLogic.setInformationString("Too bad." +
                        "\nYou have confronted a " + placeholder.getName() + "." +
                        "\nThis action has resulted in your death.");
            }
        } else if (entity instanceof Enemies) { //checks if the entity is an enemy
            if (placeholder instanceof Player) { //checks if the enemy collided with the player
                ((Player)placeholder).triggerDeath();

                GameLogic.setInformationString("Too bad" +
                        "\nA " + entity.getName() + " has caught you." +
                        "\nThis action has resulted in your death.");
                throw new PlayerIsDeadException("lol");
            } else if(placeholder instanceof Enemies){//checks if the enemy collided with another enemy
                this.grid[entityPosition.get(0)][entityPosition.get(1)] = placeholder;

            }
        }

        if(entity instanceof Player) {
            findPlayer().calculateScore();
        }

    }

    //Moves player to next level
    public void movePlayerToNextLevel(Grid grid){

        //list that's going to get returned
        ArrayList<Integer> position = new ArrayList<>();

        //checks all positions in the 2D array for the player
        for (int column = 0; column < grid.getGrid().length; column++) {
            for (int row = 0; row < grid.getGrid()[column].length; row++) {
                if (grid.getGrid()[row][column] instanceof Player) {
                    position.add(row);
                    position.add(column);
                    break;
                }
            }
        }

        this.grid[getPosition(findPlayer()).get(0)][getPosition(findPlayer()).get(1)]
                = (Player) grid.getGrid()[position.get(0)][position.get(1)];
    }

    public GameObjects[][] getGrid() {
        return grid;
    }

    //method that checks if the map needs to refill some elements
    public void maintenance(){

        int foodCount = 0;
        int obstacleCount = 0;

        //checks how much food there is on the map
        for (int column = 0; column < this.column; column++) {
            for (int row = 0; row < this.grid[column].length; row++) {
                if (this.grid[row][column] instanceof Food) {
                    foodCount++;
                }
            }
        }

        //checks how many obstacles there are on the map
        for (int column = 0; column < this.column; column++) {
            for (int row = 0; row < this.grid[column].length; row++) {
                if (this.grid[row][column] instanceof Obstacles) {
                    obstacleCount++;
                }
            }
        }

        Random decider = new Random();
        //loop that sends new food objects in on the map with the method maintenanceAdder
        for(int i = 0; i < this.foodQuantity - foodCount; i++){
            maintenanceAdder(Food.create(decider.nextInt(2)));
        }

        //loop that sends new obstacle objects in on the map with the method maintenanceAdder
        for(int i = 0; i < this.obstaclesQuantity - obstacleCount; i++){
            maintenanceAdder(Obstacles.create(decider.nextInt(2)));
        }
    }

    private void maintenanceAdder(GameObjects object){
        Random random = new Random();

        //counts how many tiles is water
        int waterCount = 0;
        for (int column = 0; column < this.column; column++) {
            for (int row = 0; row < this.grid[column].length; row++) {
                if (this.grid[row][column] instanceof Water) {
                    waterCount++;
                }
            }
        }

        //finds a random number between o and how much water there is
        int count = random.nextInt(waterCount);
        int placement = 0;

        //loops though the grid while counting up to the random number where there is water them placing set object there
        for (int column = 0; column < this.column; column++) {
            for (int row = 0; row < this.grid[column].length; row++) {
                if (this.grid[row][column] instanceof Water) {
                    placement++;
                    if(count == placement){
                        this.grid[row][column] = object;
                    }
                }
            }
        }
    }
}
