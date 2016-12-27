package logic.Exceptions;

/**
 * Created by erez on 13/12/2016.
 */
public class PlayerErrorException extends Exception {

    private static final String INVALID_MOVE = "invalid pick - You cant Pick The marker Square or Empty Square";

    public PlayerErrorException()
    {
        super(INVALID_MOVE);
    }
}