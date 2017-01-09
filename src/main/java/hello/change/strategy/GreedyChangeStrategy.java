package hello.change.strategy;


import hello.change.currency.InsufficientCurrencyException;
import hello.change.currency.CurrencyDenomination;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;
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
    public SortedSet<CurrencyDenomination> getChange(Set<CurrencyDenomination> availableCurrency, Double amount) throws InsufficientCurrencyException {

        NavigableSet<CurrencyDenomination> available = new TreeSet<>(new CurrencyDenomination.DescendingComaparator<CurrencyDenomination>());
        available.addAll(availableCurrency);

        Map<Double, CurrencyDenomination> solution = new HashMap<>();

        return fillGreedily(amount, available, solution);
    }

    private SortedSet<CurrencyDenomination> fillGreedily(Double amountRemaining, NavigableSet<CurrencyDenomination> availableCurrencyTailSet, Map<Double, CurrencyDenomination> solutionMap) throws InsufficientCurrencyException {

        if (amountRemaining.equals(0.0)) {
            SortedSet<CurrencyDenomination> answer = new TreeSet<>(new CurrencyDenomination.DescendingComaparator());
            answer.addAll(solutionMap.values());

            // explicitly fill zero denominations for each available CurrencyDenomination
            if (!availableCurrencyTailSet.isEmpty()) {
                Double denom = availableCurrencyTailSet.pollFirst().getDenomination();
                solutionMap.put(denom, new CurrencyDenomination(denom));
            }
            return answer;
        }

        if (availableCurrencyTailSet.isEmpty()) {
            throw new InsufficientCurrencyException("Solution not found");
        }

        CurrencyDenomination availableCurrency = availableCurrencyTailSet.first();

        CurrencyDenomination solutionCurrency = new CurrencyDenomination(availableCurrency.getDenomination());
        solutionMap.put(availableCurrency.getDenomination(), solutionCurrency);

        // increment this CurrencyDenomination
        while(availableCurrency.getDenomination() * (solutionCurrency.getCount() + 1) <= amountRemaining
                && availableCurrency.getCount() > solutionCurrency.getCount()) {
            solutionCurrency.add(1);
        }

        NavigableSet<CurrencyDenomination> tailSet = availableCurrencyTailSet.tailSet(availableCurrency, false);

        while (true) {
            try {
                // try a recursive call to fill using remaining (smaller) CurrencyDenominations
                return fillGreedily(amountRemaining - solutionCurrency.getValue(), tailSet, solutionMap);
            } catch (InsufficientCurrencyException e ) {
                // we can no longer decrement this CurrencyDenomination
                // or there are no remaining CurrencyDenominations
                if (solutionCurrency.getCount().equals(0) || tailSet.isEmpty()) {
                    throw e;
                }
                // decrement the count of the this CurrencyDenomination and try again
                solutionCurrency.take(1);
            }
        }
    }
}
