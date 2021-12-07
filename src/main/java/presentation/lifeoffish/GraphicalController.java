package presentation.lifeoffish;

import Domain.Command;
import Domain.CommandWords;
import Domain.GameLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;



import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GraphicalController implements Initializable {

    GameLogic game = new GameLogic();

    //Movement in grid with buttons
    Command movementCommand = null;
    CommandWords commands = new CommandWords();



    //Binding nodes from scene builder
    @FXML
    private GridPane gPane;

    @FXML
    private Text informationText;

    @FXML
    private Text stats;

    @FXML
    private AnchorPane gamePane;

    @FXML
    private AnchorPane deathPane;

    @FXML
    private Button next;


    public void up(ActionEvent e){

        if(game.isAlive()) {
            System.out.println("UP");
            movementCommand = new Command(commands.getCommandWord("go"), "up");
            game.goInGrid(movementCommand);
            game.enemyTurn();
            gameLoop();
        }

    }
    public void down(ActionEvent e){

        if(game.isAlive()) {
            System.out.println("DOWN");
            movementCommand = new Command(commands.getCommandWord("go"), "down");
            game.goInGrid(movementCommand);
            game.enemyTurn();
            gameLoop();
        }

    }
    public void left(ActionEvent e){

        if(game.isAlive()) {
            System.out.println("LEFT");
            movementCommand = new Command(commands.getCommandWord("go"), "left");
            game.goInGrid(movementCommand);
            game.enemyTurn();
            gameLoop();

        }
    }

    public void right (ActionEvent event){
      
        if(game.isAlive()) {
            System.out.println("RIGHT");
            movementCommand = new Command(commands.getCommandWord("go"), "right");
            game.goInGrid(movementCommand);
            game.enemyTurn();
            gameLoop();
        }

    }

    public void next(ActionEvent e){
      
        if(game.isAlive() && game.getPlayerScore() >= game.scoreToNextLevel()) {
            System.out.println("NEXT");
            movementCommand = new Command(commands.getCommandWord("next"), "");
            game.goRoom(movementCommand);
            gameLoop();
        }
    }

    public void gameLoop(){
        game.getMaintenance();
        updateGrid();
        updateNextButton();
        if(!game.isAlive()){
            deathPane.setDisable(false);
            deathPane.setVisible(true);
            gamePane.setOpacity(0.5);
            deathPane.setOpacity(1.0);
        }

    }

    public void help(ActionEvent e){
        printHelp();
    }

    public void updateGrid(){
        gPane.getChildren().clear();

        ArrayList<BufferedImage> placeholder = game.listOfImages();
        ArrayList<Image> images = new ArrayList();
        for(int i = 0; i<placeholder.size(); i++){
            images.add(convertToFxImage(placeholder.get(i)));
        }

        int i = 0;
        for (int column = 0; column < game.getMap().length; column++) {
            for (int row = 0; row < game.getMap()[column].length; row++) {
                gPane.add(new ImageView(images.get(i)),column,row);
                i++;
            }
        }
        updateStats();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<BufferedImage> placeholder = game.listOfImages();
        ArrayList<Image> images = new ArrayList();
        for(int i = 0; i<placeholder.size(); i++){
            images.add(convertToFxImage(placeholder.get(i)));
        }

        int i = 0;
        for (int column = 0; column < game.getMap().length; column++) {
            for (int row = 0; row < game.getMap()[column].length; row++) {
                gPane.add(new ImageView(images.get(i)),column,row);
                i++;
            }
        }
        printWelcome();
        updateStats();
    }

    public void printWelcome(){
        informationText.setText("Welcome to the Life of Fish!" +
                "\nYou are a fish, trying to survive at sea." +
                "\nYour goal is to survive without becoming polluted." + "\nPress the arrow buttons to move, " +
                "and press help if you need help.");
    }

    public void printHelp(){
        informationText.setText("You are a fish swimming at sea." +
                "\nYou must move around to gain score and consume food to not your lose energy." +
                "\nAvoid obstacles to not lose score." +
                "\nAvoid enemies at all costs!");
    }

    public void updateStats(){
        stats.setText("Score: " + game.findPlayer().getScore() + "/" +game.scoreToNextLevel()+
                "\nPolution value: " + game.findPlayer().getPollutionValue() +
                "\nEnergy: " + game.findPlayer().getTurnValue() +
                "\nTotal turns: "+ game.findPlayer().getTotalTurns());


    }

    public void updateNextButton(){
        if(game.scoreToNextLevel() <= game.getPlayerScore()) {
            next.setOpacity(1);
        }
        else{next.setOpacity(0.5);}

    }

    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

    public void playAgain(ActionEvent e) {
        this.game = new GameLogic();
        deathPane.setDisable(true);
        deathPane.setVisible(false);
        gamePane.setOpacity(1.0);
        deathPane.setOpacity(0.0);
        updateGrid();
        printHelp();
        updateStats();
    }

    public void mainMenu(ActionEvent e) {
        this.game = new GameLogic();
        deathPane.setDisable(true);
        deathPane.setVisible(false);
        gamePane.setOpacity(1.0);
        deathPane.setOpacity(0.0);
        updateGrid();
        printHelp();
        updateStats();
    }
}