package logic.Exceptions;

/**
 * Created by erez on 15/12/2016.
 */
public class PlayerOutOfBoundsException extends Exception {

    private static final String OUT_OF_BOUNS = "Your Choose is out of board range";

    public PlayerOutOfBoundsException()
    {
        super(OUT_OF_BOUNS);
    }
}
