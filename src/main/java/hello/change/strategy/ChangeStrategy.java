package hello.change.strategy;

import hello.change.currency.CurrencyDenomination;
import hello.change.currency.InsufficientCurrencyException;

import java.util.Set;
import java.util.SortedSet;

/**
 * Created by nate on 1/8/17.
 */
public interface ChangeStrategy {
    SortedSet<CurrencyDenomination> getChange(Set<CurrencyDenomination> availableCurrency, Double amount) throws InsufficientCurrencyException;
}
