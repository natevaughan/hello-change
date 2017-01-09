package hello.change;

import hello.change.currency.Bill;
import hello.change.currency.Currency;
import hello.change.strategy.GreedyChangeStrategy;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Created by nate on 1/8/17.
 */
public class GreedyChangeStrategyTest {

    private Double[]  testValues1 = new Double[]  { 5.0, 2.0 };
    private Integer[] testCounts1 = new Integer[] { 1,   8   };

    private Double[]  testValues2 = new Double[]  { 20.0, 10.0, 5.0, 2.0 };
    private Integer[] testCounts2 = new Integer[] { 1,    1,    3,   3   };

    private GreedyChangeStrategy changeStrategy = new GreedyChangeStrategy();

    @Test
    public void testGetSortedTreeSet() {
        Set<Currency> currencies = createCurrencySet(testValues2, testCounts2);

        Set<Currency> sorted = changeStrategy.getDenominationSortedTreeSet(currencies);

        Currency cur;
        Currency prev;

        Iterator<Currency> it = sorted.iterator();
        cur = it.next();
        while (it.hasNext()) {
            prev = cur;
            cur = it.next();
            assertTrue(cur.getDenomination() < prev.getDenomination());
        }
    }

    /**
     * Utility method to create currency objects for testing.
     * Will result in ArrayIndexOutOfBoundsException if input
     * arrays are not equal length. Be thee warned.
     */
    private Set<Currency> createCurrencySet(Double[] doubles, Integer[] integers) {
        Set<Currency> currencySet = new HashSet<>();
        for (int i = 0; i < doubles.length; ++i) {
            Currency c = new Bill(doubles[i], integers[i]);
            currencySet.add(c);
        }
        return currencySet;
    }
}
