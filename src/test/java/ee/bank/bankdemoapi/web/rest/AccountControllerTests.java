package ee.bankapi.web.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import ee.bankapi.service.AccountService;
import ee.bankapi.web.rest.controller.AccountController;
import ee.bankapi.web.rest.controller.dto.AccountDto;
import ee.bankapi.web.rest.controller.dto.BalancesDto;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
@Import({Utils.class})
public class AccountControllerTests {

    private static final TypeReference<AccountDto> ACCOUNT_DTO_TYPE_REFERENCE =
            new TypeReference<>() {
            };

    @MockBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Utils utils;

    @Test
    public void createAccount_whenCustomerMissing_thenReturnBadRequest() throws Exception {
        final ResultActions result = mockMvc.perform(
                post("/api/v1/account/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customer\": null, \"country\": \"EE\", \"currency\" : [\"EUR\"] }")
        );

        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.error").value("Customer ID is missing."));
    }

    @Test
    public void createAccount_inputValidAndInsertSuccessful_thenReturnOk() throws Exception {
        List<BalancesDto> balancesDtoList = List.of(new BalancesDto(BigDecimal.ZERO, "EUR"));
        AccountDto returnedAccount = new AccountDto(1, "1", "EE", balancesDtoList);
        when(accountService.insertAccount(any())).thenReturn(returnedAccount);

        final ResultActions result = mockMvc.perform(
                post("/api/v1/account/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customer\": \"1\", \"country\": \"EE\", \"currency\" : [\"EUR\"] }")
        );

        result.andExpect(status().isOk());

        AccountDto accountDto = parseAccountResponse(result);

        assertThat(accountDto.getCustomer()).isEqualTo("1");
        assertThat(accountDto.getCountry()).isEqualTo("EE");
        assertThat(accountDto.getBalances()).hasSize(1);
        assertThat(accountDto.getBalances().get(0).getBalance()).isEqualTo(BigDecimal.ZERO);
        assertThat(accountDto.getBalances().get(0).getCurrency()).isEqualTo("EUR");
    }

    private AccountDto parseAccountResponse(ResultActions result) {
        return utils.parse(result, ACCOUNT_DTO_TYPE_REFERENCE);
    }
}
