package ee.bankapi.web.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import ee.bankapi.web.rest.controller.TransactionController;
import ee.bankapi.web.rest.controller.dto.TransactionDto;
import ee.bankapi.service.TransactionService;
import ee.bankapi.web.rest.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
@Import({Utils.class})
public class TransactionControllerTests {

    private static final TypeReference<TransactionDto> TRANSACTION_DTO_TYPE_REFERENCE =
            new TypeReference<>() {};

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Utils utils;

    @Test
    public void createTransaction_whenAccountIdMissing_thenReturnBadRequest() throws Exception {
        final ResultActions result = mockMvc.perform(
                post("/api/v1/transaction/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"accountId\": null, \"amount\": \"500\", \"currency\" : \"EUR\", \"direction\": \"IN\" }")
        );

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void createTransaction_whenInputValidAndInsertSuccessful_thenReturnOk() throws Exception {
        TransactionDto transactionDto = new TransactionDto(1, BigDecimal.valueOf(500), "IN", "EUR", 1);

        when(transactionService.insertTransaction(any())).thenReturn(transactionDto);

        final ResultActions result = mockMvc.perform(
                post("/api/v1/transaction/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"accountId\": 1, \"amount\": \"500\", \"currency\" : \"EUR\", \"direction\": \"IN\" }")
        );
        result.andExpect(status().isOk());

        TransactionDto dto = parseTransactionResponse(result);
        assertThat(dto.getAccountId()).isEqualTo(1);
        assertThat(dto.getCurrency()).isEqualTo("EUR");
        assertThat(dto.getAmount()).isEqualTo(500);
        assertThat(dto.getDirection()).isEqualTo("IN");
    }

    private TransactionDto parseTransactionResponse(ResultActions result) {
        return utils.parse(result, TRANSACTION_DTO_TYPE_REFERENCE);
    }

}
