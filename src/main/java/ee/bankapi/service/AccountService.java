package ee.bankapi.service;

import ee.bankapi.web.rest.controller.dto.AccountDto;
import ee.bankapi.web.rest.controller.dto.BalancesDto;
import ee.bankapi.web.rest.controller.requestbody.AccountRequest;
import ee.bankapi.model.Account;
import ee.bankapi.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public AccountDto insertAccount(AccountRequest accountRequest) {
        Account account = new Account();
        account.setCustomer(accountRequest.getCustomer());
        account.setCountry(accountRequest.getCountry());
        List<String> currencies = accountRequest.getCurrency();
        for (String currency: currencies) {
            if (currency.equalsIgnoreCase("EUR")) {
                account.setBalanceEur(BigDecimal.ZERO);
            } else if (currency.equalsIgnoreCase("SEK")){
                account.setBalanceSek(BigDecimal.ZERO);
            } else if (currency.equalsIgnoreCase("GBP")){
                account.setBalanceGbp(BigDecimal.ZERO);
            } else if (currency.equalsIgnoreCase("USD")){
                account.setBalanceUsd(BigDecimal.ZERO);
            }
        }
        account.setStatus("CREATED");

        insertAccount(account);
        Account insertedAccount = findById(account.getId());
        return mapToDto(insertedAccount);
    }

    public Account findById(int id) {
        return accountRepository.findById(id);
    }
    private void insertAccount(Account account) {
        accountRepository.insert(account);
    }
    public void addToEurBalance(int id, BigDecimal amount) {
        accountRepository.addToEurBalance(id, amount);
    }
    public void addToSekBalance(int id, BigDecimal amount) {
        accountRepository.addToSekBalance(id, amount);
    }
    public void addToGbpBalance(int id, BigDecimal amount) {
        accountRepository.addToGbpBalance(id, amount);
    }
    public void addToUsdBalance(int id, BigDecimal amount) {
        accountRepository.addToUsdBalance(id, amount);
    }
    public void subtractFromEurBalance(int id, BigDecimal amount) {
        accountRepository.subtractFromEurBalance(id, amount);
    }
    public void subtractFromSekBalance(int id, BigDecimal amount) { accountRepository.subtractFromSekBalance(id, amount); }
    public void subtractFromGbpBalance(int id, BigDecimal amount) { accountRepository.subtractFromGbpBalance(id, amount); }
    public void subtractFromUsdBalance(int id, BigDecimal amount) { accountRepository.subtractFromUsdBalance(id, amount); }

    public AccountDto mapToDto(Account account) {
        List<BalancesDto> balances = new ArrayList<>();

        BalancesDto eurBalance = new BalancesDto(account.getBalanceEur(), "EUR");
        BalancesDto sekBalance = new BalancesDto(account.getBalanceSek(), "SEK");
        BalancesDto gbpBalance = new BalancesDto(account.getBalanceGbp(), "GBP");
        BalancesDto usdBalance = new BalancesDto(account.getBalanceUsd(), "USD");
        balances.add(eurBalance);
        balances.add(sekBalance);
        balances.add(gbpBalance);
        balances.add(usdBalance);

        return new AccountDto(account.getId(), account.getCustomer(), account.getCountry(), balances);
    }


}
