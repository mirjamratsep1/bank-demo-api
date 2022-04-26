package ee.bankapi.web.rest.controller.dto;

import ee.bankapi.model.Transaction;

import java.math.BigDecimal;

public class TransactionDto {
    private int id;
    private BigDecimal amount;
    private String direction;
    private String currency;
    private int accountId;
    private String error;

    public TransactionDto(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.direction = transaction.getDirection();
        this.currency = transaction.getCurrency();
        this.accountId = transaction.getAccountId();
    }

    public TransactionDto(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public TransactionDto(int id, BigDecimal amount, String direction, String currency, int accountId) {
        this.id = id;
        this.amount = amount;
        this.direction = direction;
        this.currency = currency;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
