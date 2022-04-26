package ee.bankapi.web.rest.controller.dto;


import java.util.List;

public class AccountDto {

    public AccountDto(int id, String customer, String country, List<BalancesDto> balances) {
        this.id = id;
        this.customer = customer;
        this.country = country;
        this.balances = balances;
    }

    private int id;
    private String customer;
    private String country;
    private List<BalancesDto> balances;
    private String error;

    public AccountDto(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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

    public List<BalancesDto> getBalances() {
        return balances;
    }

    public void setBalances(List<BalancesDto> balances) {
        this.balances = balances;
    }
}
