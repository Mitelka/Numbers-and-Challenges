package logic.Exceptions;

/**
 * Created by erez on 28/11/2016.
 */
public class BoardSizeException extends Exception {

    private static final String INVALID = "Board size must be between 5 to 50";

    public BoardSizeException()
    {
        super(INVALID);
    }
}
