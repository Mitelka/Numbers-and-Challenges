package logic.Exceptions;

/**
 * Created by erez on 07/12/2016.
 */
public class JAXBException extends Exception {

    public JAXBException() {
        String error = "invalid XML path";
        System.out.println(error);
    }
}
