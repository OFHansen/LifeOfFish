package presentation.lifeoffish;

import Domain.GameLogic;
import Domain.IllegalMoveException;
import Domain.PlayerIsDeadException;
import Save.HighScore;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;


import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GraphicalController implements Initializable {

    private GameLogic game = new GameLogic();
    private FadeTransition ft = new FadeTransition(new Duration(3000));
    private String actionInformation;

    private GridPane currentGridPane;

    private int currentLevel = 1;
    private String currentLevelString = ""+currentLevel;
    boolean atLastLevel = false;

    //Binding nodes from scene builder
    @FXML
    private GridPane gPane1,gPane2,gPane3,gPane4,gPane5,gPane6;
    @FXML
    private Text informationText;
    @FXML
    private Text stats;
    @FXML
    private AnchorPane gamePane;
    @FXML
    private AnchorPane deathPane;
    @FXML
    private AnchorPane mainMenuPane;
    @FXML
    private AnchorPane highscorePane;
    @FXML
    private AnchorPane helpPane;
    @FXML
    private Button next;
    @FXML
    private Text highscore;
    @FXML
    private Text statsEnd;
    @FXML
    private Text scoreText;
    @FXML
    private Text totalTurnsText;
    @FXML
    private Text pollutionValueText;
    @FXML
    private Text highscoreDateText;
    @FXML
    private Node gameBackground0;
    @FXML
    private Node gameBackground1;
    @FXML
    private Node gameBackground2;
    @FXML
    private Node gameBackground3;
    @FXML
    private Node gameBackground4;
    @FXML
    private Node gameBackground5;
    @FXML
    private Label deathMenuTitle;




    public void up(ActionEvent e){

        if(game.isAlive()) {
            try {
                 actionInformation = game.goInGrid("up");
            } catch (PlayerIsDeadException ex) {
                gameLoop();
            }catch (IllegalMoveException ex){
                actionInformation = ex.getMessage();
            }
            game.enemyTurn();
            gameLoop();
        }

    }
    public void down(ActionEvent e){

        if(game.isAlive()) {
            try {
               actionInformation = game.goInGrid("down");
            } catch (PlayerIsDeadException ex) {
                gameLoop();
            }catch (IllegalMoveException ex){
                actionInformation = ex.getMessage();
            }
            game.enemyTurn();
            gameLoop();
        }

    }
    public void left(ActionEvent e){

        if(game.isAlive()) {
            try {
                actionInformation = game.goInGrid("left");
            } catch (PlayerIsDeadException ex) {
                gameLoop();
            }catch (IllegalMoveException ex){
                actionInformation = ex.getMessage();
            }
            game.enemyTurn();
            gameLoop();

        }
    }

    public void right (ActionEvent event){

        if(game.isAlive()) {
            try {
                actionInformation = game.goInGrid("right");
            } catch (PlayerIsDeadException ex) {
                gameLoop();
            } catch (IllegalMoveException ex){
                actionInformation = ex.getMessage();
            }

            game.enemyTurn();
            gameLoop();
        }

    }


    public void next(ActionEvent e){

        if(game.isAlive() && game.getPlayerScore() >= game.scoreToNextLevel() && !atLastLevel) {
            ft.setNode(currentBackground());
            ft.setToValue(0);
            ft.play();
            System.out.println("NEXT");
            if (currentLevel == 5){
                currentLevelString = "Final level";
                next.setText("End");
                atLastLevel = true;
            } else {
                currentLevel++;

            }
            game.goRoom();
            gameLoop();
        } else if(game.getPlayerScore() >= game.scoreToNextLevel()){
            game.killPlayer();
            showdeathMenu();
            gamePane.setOpacity(0.5);
            atLastLevel = false;
        }




    }

    private Node currentBackground(){
        Node placeholder = gameBackground0;

        if(currentLevel==2){
            placeholder = gameBackground1;
        }else if(currentLevel==3){
            placeholder = gameBackground2;
        } else if(currentLevel==4){
            placeholder = gameBackground3;
        }else if(currentLevel==5){
            placeholder = gameBackground4;
        } else if(currentLevel==6){
            placeholder = gameBackground5;
        }

        return placeholder;
    }

    public void resetBackgrounds(){

        gameBackground0.setOpacity(1);
        gameBackground1.setOpacity(1);
        gameBackground2.setOpacity(1);
        gameBackground3.setOpacity(1);
        gameBackground4.setOpacity(1);
        gameBackground5.setOpacity(1);

    }

    public void setCurrentGridPane(){

        if(currentGridPane == null){
            currentGridPane = gPane1;
            currentGridPane.setVisible(true);
        }

        if(currentLevel == 1){
            currentGridPane = gPane1;
            currentGridPane.setVisible(true);

        } else if(currentLevel == 2){
            currentGridPane = gPane2;
            currentGridPane.setVisible(true);
            gPane1.setVisible(false);

        }else if(currentLevel == 3){
            currentGridPane = gPane3;
            currentGridPane.setVisible(true);
            gPane2.setVisible(false);

        }else if(currentLevel == 4){
            currentGridPane = gPane4;
            currentGridPane.setVisible(true);
            gPane3.setVisible(false);

        }else if(currentLevel == 5){
            currentGridPane = gPane5;
            currentGridPane.setVisible(true);
            gPane4.setVisible(false);

        } else{
            currentGridPane = gPane6;
            currentGridPane.setVisible(true);
            gPane5.setVisible(false);
        }
    }

    public void gameLoop(){
        setCurrentGridPane();
        game.getMaintenance();
        updateGrid();
        updateNextButton();

        if(!atLastLevel){

        }

        if(!game.isAlive()){
            showdeathMenu();
            gamePane.setOpacity(0.5);
        }
        informationText.setText(actionInformation);

    }


    public void updateGrid(){

        clearAll();

        ArrayList<BufferedImage> placeholder = game.listOfImages();
        ArrayList<Image> images = new ArrayList();
        for(int i = 0; i<placeholder.size(); i++){
            images.add(convertToFxImage(placeholder.get(i)));
        }

        int i = 0;
        for (int column = 0; column < game.getMap().length; column++) {
            for (int row = 0; row < game.getMap()[column].length; row++) {
                currentGridPane.add(new ImageView(images.get(i)),column,row);
                i++;
            }
        }
        updateText();

    }

    public void clearAll(){

        gPane1.getChildren().clear();
        gPane2.getChildren().clear();
        gPane3.getChildren().clear();
        gPane4.getChildren().clear();
        gPane5.getChildren().clear();
        gPane6.getChildren().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<BufferedImage> placeholder = game.listOfImages();
        ArrayList<Image> images = new ArrayList();
        for(int i = 0; i<placeholder.size(); i++){
            images.add(convertToFxImage(placeholder.get(i)));
        }

        setCurrentGridPane();
        int i = 0;
        for (int column = 0; column < game.getMap().length; column++) {
            for (int row = 0; row < game.getMap()[column].length; row++) {
                currentGridPane.add(new ImageView(images.get(i)),column,row);
                i++;
            }
        }
        printWelcome();
        updateText();
        printHighestScore();
    }

    public void printWelcome(){
        informationText.setText("Welcome to the Life of Fish!" +
                "\nYou are a fish, trying to survive at sea." +
                "\nYour goal is to survive without becoming polluted." + "\nPress the arrow buttons to move, " +
                "and press help if you need help.");
    }

    public void printHighestScore(){
        //highscore.setText(HighScore.highestScore());
    }

    public void updateText(){
        stats.setText("Score: " + game.findPlayer().getScore() + "/" +game.scoreToNextLevel()+
                "\nPollution value: " + game.findPlayer().getPollutionValue() +
                "\nEnergy: " + game.findPlayer().getTurnValue() +
                "\nTotal turns: "+ game.findPlayer().getTotalTurns() +
                "\nCurrent level: " + currentLevelString);

        statsEnd.setText("Your fish lived through "+GameLogic.getRoomCount()+ " decades" +
                "\nWhile your fish tried to survive, the filthy humans were destroying the ocean!"+
                "\nThat resulted in your fish getting a pollution value of: "+game.findPlayer().getPollutionValue() +
                "\nRemember that when you buy products from the fishing industry."+
                "\nTotal score: " + game.findPlayer().getScore() +
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
        hidedeathMenu();
        gamePane.setOpacity(1.0);
        updateGrid();
        printHighestScore();
        game.resetRoomCount();
        currentLevel = 1;
        currentLevelString = "" +currentLevel;
        next.setText("Next");
        gameLoop();
        resetBackgrounds();
        printWelcome();
    }

    public void mainMenu(ActionEvent e) {
        this.game = new GameLogic();
        hidedeathMenu();
        gamePane.setOpacity(1.0);
        hideGame();
        showMenu();
        flag = true;
        game.resetRoomCount();
        currentLevel = 1;
        currentLevelString = "" +currentLevel;
        next.setText("Next");
        resetBackgrounds();
    }

    public void exitHighscore(ActionEvent e) {
        hideHighscore();
        showMenu();
    }

    public void exitHelp(ActionEvent e) {
        hideHelp();
    }
    private Boolean flag = false;
    public void play(ActionEvent e) {
        if(flag){
            this.game = new GameLogic();
            updateGrid();
            printWelcome();
            flag = false;
        }
        hideMenu();
        showGame();
    }

    public void howToPlay(ActionEvent e) {
        showHelp();
    }

    public void showHighscore(ActionEvent e) {
        String[][] saves = HighScore.highscoreFormatning();

        scoreText.setText(highscoreString(saves,0));
        totalTurnsText.setText(highscoreString(saves,1));
        pollutionValueText.setText(highscoreString(saves,2));
        highscoreDateText.setText(highscoreString(saves,3));
        hideMenu();
        showHighscorePane();
    }

    private String highscoreString(String[][] saves, int i){
        String placeholder = saves[i][0]+"\n";

        for(int j = 1; j<10; j++){
            placeholder += saves[i][j];
            if(j<9){
                placeholder +="\n";
            }
        }
        return placeholder;
    }

    public void hideMenu(){
        mainMenuPane.setDisable(true);
        mainMenuPane.setVisible(false);
    }
    public void showMenu(){
        mainMenuPane.setDisable(false);
        mainMenuPane.setVisible(true);
    }
    public void hideGame(){
        gamePane.setDisable(true);
        gamePane.setVisible(false);
    }
    public void showGame(){
        gamePane.setDisable(false);
        gamePane.setVisible(true);
    }
    public void hidedeathMenu(){
        deathPane.setDisable(true);
        deathPane.setVisible(false);
    }
    public void showdeathMenu(){
        deathPane.setDisable(false);
        deathPane.setVisible(true);
        if(atLastLevel){
            deathMenuTitle.setText("You finished the game!");
        } else {deathMenuTitle.setText("You died!");}
    }
    public void hideHighscore(){
        highscorePane.setDisable(true);
        highscorePane.setVisible(false);
    }
    public void showHighscorePane(){
        highscorePane.setDisable(false);
        highscorePane.setVisible(true);
    }
    public void hideHelp(){
        helpPane.setDisable(true);
        helpPane.setVisible(false);
    }
    public void showHelp(){
        helpPane.setDisable(false);
        helpPane.setVisible(true);
    }
}