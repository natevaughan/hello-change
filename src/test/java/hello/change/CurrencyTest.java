package hello.change;

import hello.change.currency.Bill;
import hello.change.currency.Currency;
import org.junit.Test;

/**
 * Created by nate on 1/8/17.
 */
public class CurrencyTest {

    @Test (expected = InsufficientCurrencyException.class)
    public void takeTooManyBillsException() throws InsufficientCurrencyException {
        Currency twenties = new Bill(20.0, 1);
        twenties.take(2);
    }

}
