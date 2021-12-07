package Save;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class HighScore implements Serializable {

    //attributes
    private static File file = new File("Highscore.txt");
    private static ArrayList<HighScore> saves = new ArrayList<>();
    private int score;
    private int totalTurns;
    private double pollutionValue;
    private Date time;

    //constructors
    public HighScore(int score, int totalTurns, double pollutionValue){
        this.score = score;
        this.totalTurns = totalTurns;
        this.pollutionValue = pollutionValue;
        this.time = new Date();
        if(this.score == 0){
            System.out.println("The score was 0 therefore it has not been saved.");
        } else {
            clone(score, totalTurns, pollutionValue);
        }
    }

    private HighScore(int score, int totalTurns, double pollutionValue, String dingoBob){
        this.score = score;
        this.totalTurns = totalTurns;
        this.pollutionValue = pollutionValue;
        this.time = new Date();
    }

    //methods
    private static void clone(int score, int totalTurns, double pollutionValue){
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

        try {
            f = new FileOutputStream(file);
            o = new ObjectOutputStream(f);

            for(int i = 0; i < saves.size(); i++){
                o.writeObject(saves.get(i));
            }
            f.close();
            o.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
    }

    public static void load(){

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.print("");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Sorts the scores by highest first
        HighScoreComparator sort = new HighScoreComparator();
        saves.sort(sort);

    }

    public int getScore() {
        return score;
    }

    public int getTotalTurns() {
        return totalTurns;
    }

    public double getPollutionValue() {
        return pollutionValue;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public String toString(){
        String placeholder = null;

        placeholder = "\n\nScore: " + getScore() + "\nTurns used: " + getTotalTurns()
                + "\nPollution value: " + getPollutionValue() + "\nDate of score: " + getTime();

        return placeholder;
    }

}
