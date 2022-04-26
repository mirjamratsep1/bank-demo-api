package ee.bankapi.web.rest.controller.dto;

import java.math.BigDecimal;

public class BalancesDto {

    public BalancesDto(BigDecimal balance, String currency) {
        this.balance = balance;
        this.currency = currency;
    }

    private BigDecimal balance;
    private String currency;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
