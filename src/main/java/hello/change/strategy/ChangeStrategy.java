package hello.change.strategy;

import hello.change.currency.Currency;
import hello.change.InsufficientCurrencyException;

import java.util.Set;

/**
 * Created by nate on 1/8/17.
 */
public interface ChangeStrategy {
    Set<Currency> getChange(Set<Currency> availableCurrency, Double amount) throws InsufficientCurrencyException;
}
