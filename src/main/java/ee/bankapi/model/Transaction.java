package ee.bankapi.model;

import ee.bankapi.web.rest.controller.requestbody.TransactionRequest;

import java.math.BigDecimal;

public class Transaction {
    private int id;
    private BigDecimal amount;
    private String direction;
    private String currency;
    private int accountId;

    public Transaction() {

    }

    public Transaction(TransactionRequest req) {
        this.amount = req.getAmount();
        this.direction = req.getDirection();
        this.currency = req.getCurrency();
        this.accountId = req.getAccountId();
    }

    public Transaction(int id, BigDecimal amount, String direction, String currency, int accountId) {
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
