package ee.bankapi.repository;


import ee.bankapi.model.Account;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.math.BigDecimal;

@Mapper
public interface AccountRepository {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "customer", column = "customer"),
            @Result(property = "country", column = "country"),
            @Result(property = "status", column = "status"),
            @Result(property = "balanceEur", column = "balance_eur"),
            @Result(property = "balanceSek", column = "balance_sek"),
            @Result(property = "balanceGbp", column = "balance_gbp"),
            @Result(property = "balanceUsd", column = "balance_usd")
    })
    @Select("SELECT * FROM bank.account WHERE id = #{id}")
    Account findById(@Param("id") int id);

    @Options(statementType = StatementType.PREPARED, useGeneratedKeys=true, keyColumn = "id", keyProperty = "id", flushCache = Options.FlushCachePolicy.TRUE)
    @Insert("INSERT INTO bank.account(customer, country, status, balance_eur, balance_sek, balance_gbp, balance_usd) " +
            " VALUES (#{customer}, #{country}, #{status}, #{balanceEur}, #{balanceSek}, #{balanceGbp}, #{balanceUsd}) ")
    @SelectKey(resultType = int.class, keyProperty = "id", keyColumn = "id", statement = "SELECT currval('bank.account_pk_seq')", before = false)
    int insert(Account account);

    @Update("UPDATE bank.account SET balance_eur = balance_eur + #{amount} WHERE id = #{id}")
    void addToEurBalance(@Param("id") int id, @Param("amount") BigDecimal amount);

    @Update("UPDATE bank.account SET balance_sek = balance_sek + #{amount} WHERE id = #{id}")
    void addToSekBalance(@Param("id") int id, @Param("amount") BigDecimal amount);

    @Update("UPDATE bank.account SET balance_gbp = balance_gbp + #{amount} WHERE id = #{id}")
    void addToGbpBalance(@Param("id") int id, @Param("amount") BigDecimal amount);

    @Update("UPDATE bank.account SET balance_usd = balance_usd + #{amount} WHERE id = #{id}")
    void addToUsdBalance(@Param("id") int id, @Param("amount") BigDecimal amount);

    @Update("UPDATE bank.account SET balance_eur = balance_eur - #{amount} WHERE id = #{id}")
    void subtractFromEurBalance(@Param("id") int id, @Param("amount") BigDecimal amount);

    @Update("UPDATE bank.account SET balance_sek = balance_sek - #{amount} WHERE id = #{id}")
    void subtractFromSekBalance(@Param("id") int id, @Param("amount") BigDecimal amount);

    @Update("UPDATE bank.account SET balance_gbp = balance_gbp - #{amount} WHERE id = #{id}")
    void subtractFromGbpBalance(@Param("id") int id, @Param("amount") BigDecimal amount);

    @Update("UPDATE bank.account SET balance_usd = balance_usd - #{amount} WHERE id = #{id}")
    void subtractFromUsdBalance(@Param("id") int id, @Param("amount") BigDecimal amount);
}
