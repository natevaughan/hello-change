package hello.change.currency;

import java.util.Comparator;

/**
 * Created by nate on 1/8/17.
 */
public class CurrencyDenomination {

    private final Double denomination;
    private Integer count;

    public CurrencyDenomination(double denomination) {
        this(denomination, 0);
    }

    public CurrencyDenomination(double denomination, Integer count) {
        this.denomination = denomination;
        this.count = count;
    }

    public void take(Integer i) throws InsufficientCurrencyException {
        if (i > count) {
            throw new InsufficientCurrencyException("Could not take " + i + " bills. Count was " + count);
        }
        count -= i;
    }

    public void add(Integer count) {
        this.count += count;
    }

    public Double getDenomination() {
        return denomination;
    }

    public Integer getCount() {
        return count;
    }

    public Double getValue() {
        return this.count * this.denomination;
    }

    /**
     * CurrencyDenomination equality depends only on denomination value
     * to facilitate sorted sets
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyDenomination that = (CurrencyDenomination) o;

        return denomination.equals(that.denomination);
    }

    @Override
    public int hashCode() {
        return denomination.hashCode();
    }

    public static class DescendingComaparator<T extends CurrencyDenomination> implements Comparator<T> {
        @Override
        public int compare(T o1, T o2) {
            return -1 * o1.getDenomination().compareTo(o2.getDenomination());
        }
    }

}
