package com.platform.support.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface CommonMapper {
    @Update("update test_user set money = money + #{money} where id = '1'")
    int addUser(@Param("money") double money);

    @Update("update company set money = money - #{money} where id = '1'")
    int addCompany(@Param("money") double money);

    @Update("select count(1) from company where transId = #{transId}")
    int checkCompany(@Param("transId") String transId);
}
