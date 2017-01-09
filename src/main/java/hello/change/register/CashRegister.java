package hello.change.register;

import hello.change.currency.CurrencyDenomination;

import java.util.Collection;

/**
 * Created by nate on 1/8/17.
 */
public interface CashRegister {
    public Collection<CurrencyDenomination> getCurrencies();
}
