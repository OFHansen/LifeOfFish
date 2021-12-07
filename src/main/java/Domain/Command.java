package Domain;

public class Command {
    //Attributes
    private CommandWord commandWord;
    private String secondWord;

    //Constructor
    public Command(CommandWord commandWord, String secondWord)
    {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }

    //Methods
    public CommandWord getCommandWord()
    {
        return commandWord;
    }

    public String getSecondWord()
    {
        return secondWord;
    }

    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }

}
