package Domain;




import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public abstract class GameObjects {
    //Attributes
    private String name;
    private int energy;
    private int pollutionValue;
    private BufferedImage image;
    private String article;

    //Constructor
    public GameObjects(String name, int energy, int pollutionValue, String article){
        this.name = name;
        this.energy = energy;
        this.pollutionValue = pollutionValue;
        this.image = getObjectImage(name);
        this.article = article;
    }

    //Methods
    public String getName(){
        return name;
    }

    public String getArticle() {
        return article;
    }

    public int getEnergy() {
        return energy;
    }

    public int getPollutionValue() {
        return pollutionValue;
    }

    public void addPollutionValue(double pollutionValue) {
        this.pollutionValue += pollutionValue;
    }

    public void addEnergy(int gain){
        this.energy += gain;
    }

    public void removeEnergy(int loss){
        this.energy -= loss;
    }

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
