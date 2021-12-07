package presentation.lifeoffish;


import Domain.Command;
import Domain.CommandWord;
import Domain.GameLogic;

public class CommandLineGame {
    private Parser parser;
    private GameLogic game;

    public CommandLineGame() {
        game = new GameLogic();
        parser = new Parser();
    }

    public static void main(String[] args) {
        CommandLineGame game = new CommandLineGame();
        game.play();

    }

    //Play method, flow of the game
    public void play() {

        //Uses printWelcome method
        printWelcome();

        //Boolean finished set to false, game runs while finished is not true
        boolean finished = false;
        while (!finished) {

            //Asks the player for a command
            Command command = parser.getCommand();

            //runs the players command
            finished = processCommand(command);

            //the enemies move if the player does not intend to go to the next level
            CommandWord commandWord = command.getCommandWord();
            if (command.getCommandWord() != commandWord.NEXT) {
                game.enemyTurn();
            }

            //checks if the player died that turn
            if (game.getPlayerTurns() <= 0 || !game.isAlive()) {
                finished = true;
            } else { //if the player did not die, then it updates and prints the map and stats for next turn
                game.getMaintenance();
                game.PrintMap();
                System.out.println("Score: " + game.getPlayerScore() +
                        "    Energy: " + game.getPlayerTurns() +
                        "    Turns used: " + game.getPlayerTotalTurns());

                //Checks if score is larger than or equal to scoreToNextLevel
                if (game.getPlayerScore() >= game.scoreToNextLevel()) {
                    System.out.println("You have reached a high enough score to go to the next level!");
                    System.out.println("Type 'next' to go to the next level! \nHowever you don't have to.");
                }
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }


    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the Life of Fish!");
        System.out.println("You are a fish, trying to survive at sea.");
        System.out.println("Your goal is to survive without becoming polluted.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(game.getLongDescription());

        game.PrintMap();
        game.findPlayer().calculateScore();
        System.out.println("Score: " + game.getPlayerScore() +
                "    Energy: " + game.getPlayerTurns() +
                "    Turns used: " + game.getPlayerTotalTurns());

    }



    //Guide for player
    private void printHelp() {
        System.out.println("You are a fish swimming at sea.");
        System.out.println("You must move around to gain score and consume food as to not lose energy.");
        System.out.println("Avoid obstacles to not lose score.");
        System.out.println("Avoid enemies at all costs!");
        System.out.println("\uD83D\uDC1F = You!                     \uD83E\uDD80 = Food, yum!");
        System.out.println("\uD83D\uDC19 = Enemy, steer clear!      \uD83D\uDDD1 = Obstacle, annoying!");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    //Method to process commands
    //Checks what commandword has been inserted, and acts on that
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == commandWord.GO) {
            game.goInGrid(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = game.quit(command);
        } else if (commandWord == commandWord.NEXT) {
            if (game.getPlayerScore() >= game.scoreToNextLevel()) {
                game.goRoom(command);
            } else {
                System.out.println("You cannot go to the next level right now, try getting a some more score.");
            }
        }


        return wantToQuit;
    }




}
