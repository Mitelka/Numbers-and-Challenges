package logic.Exceptions;

/**
 * Created by erez on 17/12/2016.
 */
public class GameTypeException extends Exception {
    private static final String INVALID = "This is Not valid Game Type";

    public GameTypeException()
    {
        super(INVALID);
    }
}
