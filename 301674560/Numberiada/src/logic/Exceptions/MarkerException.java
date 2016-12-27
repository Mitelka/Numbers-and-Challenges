package logic.Exceptions;

/**
 * Created by erez on 18/12/2016.
 */
public class MarkerException extends Exception {


    private static final String INVALID = "Marker Problem. One of The Marker filed has invalid Row or col";

    public MarkerException()
    {
        super(INVALID);
    }
}
