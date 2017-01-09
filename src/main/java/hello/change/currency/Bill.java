package hello.change.currency;

import hello.change.InsufficientCurrencyException;

/**
 * Created by nate on 1/8/17.
 */
public class Bill implements Currency {

    private final Double denomination;
    private Integer count;

    public Bill(double denomination) {
        this(denomination, 0);
    }

    public Bill(double denomination, Integer count) {
        this.denomination = denomination;
        this.count = count;
    }

    public void take(Integer i) throws InsufficientCurrencyException {
        if (i > count) {
            throw new InsufficientCurrencyException("Could not take " + i + " bills. Count was " + count);
        }
        count -= i;
    }

    public void put(Integer count) {
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
     * Bill equality depends only on denomination to facilitate
     * only one object to contain the count of all bills of that
     * denomination
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bill bill = (Bill) o;

        return denomination.equals(bill.denomination);
    }

    @Override
    public int hashCode() {
        return denomination.hashCode();
    }
}
