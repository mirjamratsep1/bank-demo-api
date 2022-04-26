package ee.bankapi.web.rest.controller;

import ee.bankapi.service.AccountService;
import ee.bankapi.web.rest.controller.dto.AccountDto;
import ee.bankapi.web.rest.controller.requestbody.AccountRequest;
import ee.bankapi.model.AccountModel.AllowedCurrencies;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountRequest accountRequest) {
        try {
            validateInput(accountRequest);
            AccountDto accountDto = accountService.insertAccount(accountRequest);
            return ResponseEntity.ok(accountDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AccountDto(e.getMessage()));
        }
    }

    private void validateInput(AccountRequest req) throws Exception {
        if (req.getCustomer() == null || req.getCustomer().equals("")) {
            throw new Exception("Customer ID is missing.");
        }
        if (req.getCountry() == null || req.getCountry().equals("")) {
            throw new Exception("Country is missing.");
        } else if (req.getCountry().length() != 2) {
            throw new Exception("Country code does not match ISO standard.");
        }
        if (req.getCurrency().isEmpty()) {
            throw new Exception("Currency is missing.");
        }

        for (String currency : req.getCurrency()) {
            boolean exists = false;
            for (AllowedCurrencies allowedCurrency : AllowedCurrencies.values()) {
                if (allowedCurrency.toString().equalsIgnoreCase(currency)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                throw new Exception("Provided currency: " + currency + " is not supported.");
            }
        }

    }

}
