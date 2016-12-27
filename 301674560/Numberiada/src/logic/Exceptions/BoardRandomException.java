package logic.Exceptions;

/**
 * Created by erez on 17/12/2016.
 */
public class BoardRandomException extends Exception {
    private static final String MESSAGE = "Board size is not under the instructions" ;

    public BoardRandomException() {
        super(MESSAGE);
    }

    public BoardRandomException(String message) {
        super(message);
    }
}
