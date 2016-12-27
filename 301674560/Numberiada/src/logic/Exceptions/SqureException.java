package logic.Exceptions;

/**
 * Created by erez on 17/12/2016.
 */
public class SqureException extends  Exception {

    private static final String INVALID = "Square Problem. one of The square has invalid Row or col";

    public SqureException()
    {
        super(INVALID);
    }
}
