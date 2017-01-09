package hello.change;

import hello.change.currency.CurrencyDenomination;
import hello.change.currency.InsufficientCurrencyException;
import hello.change.strategy.GreedyChangeStrategy;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by nate on 1/8/17.
 *
 * Tests GreedyChangeStrategy using known scenarios with expected output
 */
public class GreedyChangeStrategyTest {

    private final static Double[]  TEST_VALUES_1      = new Double[]  { 5.0, 2.0 };
    private final static Integer[] TEST_COUNTS_1      = new Integer[] { 1,   4   };
    private final static Integer[] SOLUTION_COUNTS_1  = new Integer[] { 0,   4   };
    private final static Double    AMOUNT_TO_CHANGE_1 = 8.0;

    private final static Double[]  TEST_VALUES_2      = new Double[]  { 20.0, 10.0, 5.0, 2.0, 1.0 };
    private final static Integer[] TEST_COUNTS_2      = new Integer[] { 1,    2,    3,   3,   0   };
    private final static Integer[] SOLUTION_COUNTS_2  = new Integer[] { 0,    1,    1,   3,   0   };
    private final static Double    AMOUNT_TO_CHANGE_2 = 21.0;


    private GreedyChangeStrategy changeStrategy = new GreedyChangeStrategy();

    @Test
    public void makeChangeScenarioTest1() throws InsufficientCurrencyException {
        runScenario(TEST_VALUES_1, TEST_COUNTS_1, SOLUTION_COUNTS_1, AMOUNT_TO_CHANGE_1);
    }

    @Test
    public void makeChangeScenarioTest2() throws InsufficientCurrencyException {
        runScenario(TEST_VALUES_2, TEST_COUNTS_2, SOLUTION_COUNTS_2, AMOUNT_TO_CHANGE_2);
    }

    public void runScenario(Double[] testValues, Integer[] testCounts, Integer[] solutionCounts, Double amountToChange) throws InsufficientCurrencyException {
        NavigableSet<CurrencyDenomination> expectedSet = createCurrencySet(testValues, solutionCounts);
        SortedSet<CurrencyDenomination> solutionSet = changeStrategy.getChange(createCurrencySet(testValues, testCounts), amountToChange);
        assertNotNull(solutionSet);

        // answer should have same denominators as available currency, even if counts are 0
        for (CurrencyDenomination sol : solutionSet) {
            CurrencyDenomination expected = expectedSet.pollFirst();
                assertTrue(expected.getDenomination().equals(sol.getDenomination()));
            assertTrue(expected.getCount().equals(sol.getCount()));
        }
    }


    @Test(expected = InsufficientCurrencyException.class)
    public void cannotMakeChangeTest() throws InsufficientCurrencyException {
        changeStrategy.getChange(createCurrencySet(TEST_VALUES_1, TEST_COUNTS_1), 42.0);
    }

    /**
     * Utility method to create currency objects for testing.
     * Will result in ArrayIndexOutOfBoundsException if input
     * arrays are not equal length. Be thee warned.
     */
    private TreeSet<CurrencyDenomination> createCurrencySet(Double[] doubles, Integer[] integers) {
        TreeSet<CurrencyDenomination> currencyDenominationSet = new TreeSet<>(new CurrencyDenomination.DescendingComaparator());
        for (int i = 0; i < doubles.length; ++i) {
            CurrencyDenomination c = new CurrencyDenomination(doubles[i], integers[i]);
            currencyDenominationSet.add(c);
        }
        return currencyDenominationSet;
    }
}
