package Domain;

public class PlayerIsDeadException extends Exception {
    public PlayerIsDeadException(String message){
        super(message);
    }
}
