package ee.bankapi.repository;

import ee.bankapi.model.Transaction;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

@Mapper
public interface TransactionRepository {

    @Select("SELECT * FROM bank.transaction WHERE id = #{id}")
    Transaction findById(int id);

    @Options(statementType = StatementType.PREPARED, useGeneratedKeys = true, keyColumn = "id", keyProperty = "id", flushCache = Options.FlushCachePolicy.TRUE)
    @Insert("INSERT INTO bank.transaction(amount, direction, currency, account_id) " +
            " VALUES (#{amount}, #{direction}, #{currency}, #{accountId})")
    @SelectKey(resultType = int.class, keyProperty = "id", keyColumn = "id", statement = "SELECT currval('bank.transaction_id_seq')", before = false)
    int insert(Transaction transaction);


}
