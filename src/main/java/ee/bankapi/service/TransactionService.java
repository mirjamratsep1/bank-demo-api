package ee.bankapi.service;

import ee.bankapi.model.Transaction;
import ee.bankapi.repository.TransactionRepository;
import ee.bankapi.web.rest.controller.dto.TransactionDto;
import ee.bankapi.web.rest.controller.requestbody.TransactionRequest;
import ee.bankapi.model.Account;
import ee.bankapi.model.AccountModel.AllowedCurrencies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private final AccountService accountService;

    private final TransactionRepository transactionRepository;

    public TransactionService(AccountService accountService, TransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransactionDto insertTransaction(TransactionRequest req) throws Exception {
        Account acc = accountService.findById(req.getAccountId());
        if (acc == null) {
            throw new Exception("Provided account: " + req.getAmount() + " does not exist.");
        } else if (req.getDirection().equalsIgnoreCase("OUT")) {
            checkAmounts(req.getCurrency(), req.getAmount(), acc);
        }
        Transaction transaction = new Transaction();
        transaction.setAmount(req.getAmount());
        transaction.setAccountId(req.getAccountId());
        transaction.setCurrency(req.getCurrency());
        transaction.setDirection(req.getDirection());
        transactionRepository.insert(transaction);

        Transaction insertedTransaction = transactionRepository.findById(transaction.getId());
        TransactionDto dto = mapToDto(insertedTransaction);

        updateBalances(acc.getId(), req.getCurrency(), req.getDirection(), req.getAmount());
        return dto;
    }

    public void checkAmounts(String currency, BigDecimal amount, Account acc) throws Exception {
        switch (AllowedCurrencies.valueOf(currency)) {
            case EUR:
                if (acc.getBalanceEur().compareTo(amount) < 0) {
                    throw new Exception("There is not enough money on bank account.");
                }
                break;
            case SEK:
                if (acc.getBalanceSek().compareTo(amount) < 0) {
                    throw new Exception("There is not enough money on bank account.");
                }
                break;
            case GBP:
                if (acc.getBalanceGbp().compareTo(amount) < 0) {
                    throw new Exception("There is not enough money on bank account.");
                }
                break;
            case USD:
                if (acc.getBalanceUsd().compareTo(amount) < 0) {
                    throw new Exception("There is not enough money on bank account.");
                }
                break;
            default:
                throw new Exception("Unknown currency: " + amount);
        }
    }

    @Transactional
    public void updateBalances(int accountId, String currency, String direction, BigDecimal amount) {
        switch (currency) {
            case "EUR":
                if (direction.equalsIgnoreCase("IN")) {
                    accountService.addToEurBalance(accountId, amount);
                } else {
                    accountService.subtractFromEurBalance(accountId, amount);
                }
                break;
            case "SEK":
                if (direction.equalsIgnoreCase("IN")) {
                    accountService.addToSekBalance(accountId, amount);
                } else {
                    accountService.subtractFromSekBalance(accountId, amount);
                }
                break;
            case "GBP":
                if (direction.equalsIgnoreCase("IN")) {
                    accountService.addToGbpBalance(accountId, amount);
                } else {
                    accountService.subtractFromGbpBalance(accountId, amount);
                }
                break;
            case "USD":
                if (direction.equalsIgnoreCase("IN")) {
                    accountService.addToUsdBalance(accountId, amount);
                } else {
                    accountService.subtractFromUsdBalance(accountId, amount);
                }
                break;
        }
    }

    public TransactionDto mapToDto(Transaction transaction) {
        return new TransactionDto(transaction.getId(), transaction.getAmount(), transaction.getDirection(),
                transaction.getCurrency(), transaction.getAccountId());

    }

}
