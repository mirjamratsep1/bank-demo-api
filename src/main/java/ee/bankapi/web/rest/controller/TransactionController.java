package ee.bankapi.web.rest.controller;

import ee.bankapi.web.rest.controller.dto.TransactionDto;
import ee.bankapi.web.rest.controller.requestbody.TransactionRequest;
import ee.bankapi.model.AccountModel.AllowedCurrencies;
import ee.bankapi.model.AccountModel.DirectionType;
import ee.bankapi.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/add")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionRequest request) {
        try {
            validateInput(request);
            TransactionDto transactionDto = transactionService.insertTransaction(request);
            return ResponseEntity.ok(transactionDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new TransactionDto(e.getMessage()));
        }
    }

    private void validateInput(TransactionRequest req) throws Exception {
        if (req.getCurrency() == null) {
            throw new Exception("Currency is not provided.");
        }
        if (req.getAccountId() == 0) {
            throw new Exception("Account ID is not provided.");
        }
        if (req.getDirection() == null) {
            throw new Exception("Direction is not provided.");
        }
        if (req.getAmount().equals(BigDecimal.ZERO)) {
            throw new Exception("Amount is not provided.");
        }
        boolean correctCurr = false;
        for (AllowedCurrencies currency : AllowedCurrencies.values()) {
            if (req.getCurrency().equalsIgnoreCase(currency.toString())) {
                correctCurr = true;
                break;
            }
        }
        if (!correctCurr) {
            throw new Exception("Provided currency: " + req.getCurrency() + " is not supported.");
        }
        boolean correctDirection = false;
        for (DirectionType directionType : DirectionType.values()) {
            if (req.getDirection().equalsIgnoreCase(directionType.toString())) {
                correctDirection = true;
                break;
            }
        }
        if (!correctDirection) {
            throw new Exception("Provided direction: " + req.getDirection() + "is not supported.");
        }
        if (req.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("Provided amount: " + req.getAmount() + "is negative.");
        }
    }

}
