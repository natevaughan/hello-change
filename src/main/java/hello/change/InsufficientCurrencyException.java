package hello.change;

/**
 * Created by nate on 1/8/17.
 */
public class InsufficientCurrencyException extends Exception {

    private static final long serialVersionUID = -8405748720233081364L;

    public InsufficientCurrencyException(String message) {
        super(message);
    }

}
