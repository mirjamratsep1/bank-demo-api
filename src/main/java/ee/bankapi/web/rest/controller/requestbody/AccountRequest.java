package ee.bankapi.web.rest.controller.requestbody;

import java.util.List;

public class AccountRequest {
    public AccountRequest(String customer, String country, List<String> currency) {
        this.customer = customer;
        this.country = country;
        this.currency = currency;
    }

    private String customer;

    private String country;

    private List<String> currency;

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

    public List<String> getCurrency() {
        return currency;
    }

    public void setCurrency(List<String> currency) {
        this.currency = currency;
    }
}
