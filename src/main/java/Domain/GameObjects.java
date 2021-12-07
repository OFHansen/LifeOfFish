package Domain;




import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class GameObjects {
    //Attributes
    private String name;
    private int turnValue;
    private String symbol;
    private int pollutionValue;
    private BufferedImage image;

    //Constructor
    public GameObjects(String name, int turnValue, String symbol, int pollutionValue){
        this.name = name;
        this.turnValue = turnValue;
        this.symbol = symbol;
        this.pollutionValue = pollutionValue;
        this.image = getObjectImage(name);
    }

    //Methods
    public String getName(){
        return name;
    }

    public int getTurnValue() {
        return turnValue;
    }

    public int getPollutionValue() {
        return pollutionValue;
    }

    public void addPollutionValue(double pollutionValue) {
        this.pollutionValue += pollutionValue;
    }

    public void addTurns(int gain){
        this.turnValue += gain;
    }

    public void removeTurns(int loss){
        this.turnValue -= loss;
    }

    public int getTurns(){
        return this.turnValue;
    }

    public String getSymbol(){return symbol;}

    private BufferedImage getObjectImage(String name){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src\\main\\resources\\Images\\"+name+".png"));
        } catch (IOException e) {
        }
        return img;
    }

    public BufferedImage getImage() {
        return image;
    }
}
