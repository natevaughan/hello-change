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

        NavigableSet<CurrencyDenomination> available = new TreeSet<>(new CurrencyDenomination.DescendingComaparator<>());
        available.addAll(availableCurrency);

        Map<Double, CurrencyDenomination> solution = new HashMap<>();

        return fillGreedily(amount, available, solution);
    }

    private SortedSet<CurrencyDenomination> fillGreedily(Double amountRemaining, NavigableSet<CurrencyDenomination> availableCurrencySet, Map<Double, CurrencyDenomination> solutionMap) throws InsufficientCurrencyException {

        if (amountRemaining.equals(0.0)) {
            // Found a solution!
            if (!availableCurrencySet.isEmpty()) {
                // explicitly fill zero for any remaining CurrencyDenominations
                Double denom = availableCurrencySet.pollFirst().getDenomination();
                solutionMap.put(denom, new CurrencyDenomination(denom));
            }

            SortedSet<CurrencyDenomination> answer = new TreeSet<>(new CurrencyDenomination.DescendingComaparator());
            answer.addAll(solutionMap.values());

            return answer;
        }

        if (availableCurrencySet.isEmpty()) {
            throw new InsufficientCurrencyException("Solution not found");
        }

        CurrencyDenomination nextAvailable = availableCurrencySet.first();
        CurrencyDenomination solution = new CurrencyDenomination(nextAvailable.getDenomination());
        solutionMap.put(nextAvailable.getDenomination(), solution);

        // increment this CurrencyDenomination until there are no more available
        // or we would exceed the amountRemaining
        while(solution.getDenomination() * (solution.getCount() + 1) <= amountRemaining
                && nextAvailable.getCount() > solution.getCount()) {
            solution.add(1);
        }

        NavigableSet<CurrencyDenomination> tailSet = availableCurrencySet.tailSet(nextAvailable, false);

        while (true) {
            try {
                // try a recursive call using remaining (smaller) CurrencyDenominations
                return fillGreedily(amountRemaining - solution.getValue(), tailSet, solutionMap);
            } catch (InsufficientCurrencyException e ) {
                // if we can no longer decrement this CurrencyDenomination
                // or there are no remaining CurrencyDenominations, move up a level
                if (solution.getCount().equals(0) || tailSet.isEmpty()) {
                    throw e;
                }
                // decrement and try again
                solution.take(1);
            }
        }
    }
}
