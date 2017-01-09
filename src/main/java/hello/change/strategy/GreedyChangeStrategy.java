package hello.change.strategy;


import hello.change.InsufficientCurrencyException;
import hello.change.currency.Currency;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by nate on 1/8/17.
 *
 * Starts by looking for the naive greedy solution.  If that
 * fails, this strategy begins by decrementing the count of the
 * each denomination, starting with the second-smallest,
 * to see if a solution emerges.
 */
public class GreedyChangeStrategy implements ChangeStrategy {

    @Override
    public Set<Currency> getChange(Set<Currency> availableCurrency, Double amount) throws InsufficientCurrencyException {
        SortedSet<Currency> available = getDenominationSortedTreeSet(availableCurrency);



        return null;
    }

    // sorts descending
    public SortedSet<Currency> getDenominationSortedTreeSet(Set<Currency> availableCurrency) {
        SortedSet<Currency> currencies = new TreeSet<>(new Comparator<Currency>() {
            @Override
            public int compare(Currency o1, Currency o2) {
                return -1 * o1.getDenomination().compareTo(o2.getDenomination());
            }
        });

        currencies.addAll(availableCurrency);
        return currencies;
    }
}
