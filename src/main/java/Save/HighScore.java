package Save;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HighScore implements Serializable {

    //attributes
    private static File file = new File("Highscore.txt");
    private static ArrayList<HighScore> saves = new ArrayList<>();
    private int score;
    private int totalTurns;
    private int pollutionValue;
    private String time;
    private static HighScoreComparator sort = new HighScoreComparator();


    //constructors
    public HighScore(int score, int totalTurns, int pollutionValue){
        this.score = score;
        this.totalTurns = totalTurns;
        this.pollutionValue = pollutionValue;
        if(this.score == 0){
            System.out.println("The score was 0 therefore it has not been saved.");
        } else {
            clone(score, totalTurns, pollutionValue);
        }
    }

    private HighScore(int score, int totalTurns, int pollutionValue, String dingoBob){
        this.score = score;
        this.totalTurns = totalTurns;
        this.pollutionValue = pollutionValue;
        this.time = new SimpleDateFormat("HH:mm dd/MM/yyyy").format(new Date());
    }

    //methods
    private static void clone(int score, int totalTurns, int pollutionValue){
        HighScore placeholder = new HighScore(score,totalTurns,pollutionValue,"");

        //Loads the existing HighScore objects saved in Highscore.txt
        HighScore.load();

        //Adds the next HighScore object to the saves ArrayList
        saves.add(placeholder);

        //Writes the HighScore objects back in to the Highscore.txt file
        HighScore.save();
    }

    public static void save(){

        FileOutputStream f = null;
        ObjectOutputStream o = null;

        //Sorts the scores by highest first
        saves.sort(sort);

        try {
            f = new FileOutputStream(file);
            o = new ObjectOutputStream(f);

            for(int i = 0; i < 10; i++){
                try{
                    o.writeObject(saves.get(i));
                }catch (IndexOutOfBoundsException ex){}
            }
            f.close();
            o.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){}
    }

    public static void load(){
        saves.clear();
        FileInputStream f;
        ObjectInputStream o;

        try {
            f = new FileInputStream(file);
            o = new ObjectInputStream(f);

            while (o.available() >= 0) {
                saves.add((HighScore) o.readObject());
            }
            f.close();
            o.close();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {}

        //Sorts the scores by highest first
        saves.sort(sort);
    }

    public static String highestScore() {
        HighScore.load();
        try{
            return saves.get(0).toString();
        }catch (IndexOutOfBoundsException ex){
            return null;
        }
    }

    public int getScore() {
        return score;
    }

    public int getTotalTurns() {
        return totalTurns;
    }

    public int getPollutionValue() {
        return pollutionValue;
    }

    public String getTime() {
        return time;
    }

    public static String[][] highscoreFormatning(){
        load();

        int numberOfSaves = saves.size();

        String[][] placholder = new String[4][numberOfSaves];

        for(int i = 0; i<numberOfSaves; i++){
            try{
                placholder[0][i] = String.valueOf(saves.get(i).getScore());
                placholder[1][i] = String.valueOf(saves.get(i).getPollutionValue());
                placholder[2][i] = String.valueOf(saves.get(i).getTotalTurns());
                placholder[3][i] = saves.get(i).getTime();
            } catch(IndexOutOfBoundsException ex){}
        }

        return placholder;
    }

    @Override
    public String toString(){
        String placeholder = "Score: " + getScore() + "  Turns used: " + getTotalTurns()
                + "\nPollution level: " + getPollutionValue();

        return placeholder;
    }
}
