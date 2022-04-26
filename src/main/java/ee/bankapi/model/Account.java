package ee.bankapi.model;

import java.math.BigDecimal;

public class Account {
    private int id;
    private String customer;
    private String country;
    private String status;
    private BigDecimal balanceEur;
    private BigDecimal balanceSek;
    private BigDecimal balanceGbp;
    private BigDecimal balanceUsd;

    public Account() {
    }

    public Account(int id, String customer, String country, String status, BigDecimal balanceEur,
                   BigDecimal balanceSek, BigDecimal balanceGbp, BigDecimal balanceUsd) {
        super();
        this.id = id;
        this.customer = customer;
        this.country = country;
        this.status = status;
        this.balanceEur = balanceEur;
        this.balanceSek = balanceSek;
        this.balanceGbp = balanceGbp;
        this.balanceUsd = balanceUsd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getBalanceEur() {
        return balanceEur;
    }

    public void setBalanceEur(BigDecimal balanceEur) {
        this.balanceEur = balanceEur;
    }

    public BigDecimal getBalanceSek() {
        return balanceSek;
    }

    public void setBalanceSek(BigDecimal balanceSek) {
        this.balanceSek = balanceSek;
    }

    public BigDecimal getBalanceGbp() {
        return balanceGbp;
    }

    public void setBalanceGbp(BigDecimal balanceGbp) {
        this.balanceGbp = balanceGbp;
    }

    public BigDecimal getBalanceUsd() {
        return balanceUsd;
    }

    public void setBalanceUsd(BigDecimal balanceUsd) {
        this.balanceUsd = balanceUsd;
    }
}


