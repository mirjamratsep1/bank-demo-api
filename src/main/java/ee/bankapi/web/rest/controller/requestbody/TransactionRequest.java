package ee.bankapi.web.rest.controller.requestbody;

import java.math.BigDecimal;

public class TransactionRequest {

    public TransactionRequest(int accountId, BigDecimal amount, String currency, String direction) {
        this.accountId = accountId;
        this.amount = amount;
        this.currency = currency;
        this.direction = direction;
    }

    private int accountId;
    private BigDecimal amount;
    private String currency;
    private String direction;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
