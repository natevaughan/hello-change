package hello.change.currency;

import hello.change.InsufficientCurrencyException;

/**
 * Created by nate on 1/8/17.
 */
public interface Currency {
    void take(Integer count) throws InsufficientCurrencyException;
    void put(Integer count);
    Integer getCount();
    Double getDenomination();
    Double getValue();
}
