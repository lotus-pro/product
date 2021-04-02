package com.platform.admin.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface CommonMapper {
    @Update("update company set money = money - #{money},trans_id = #{transId} where id = '1'")
    int addCompany(@Param("transId") String transId, @Param("money") double money);

    @Update("update company set money = money - #{money} where id = '1'")
    int checkCompany(@Param("transId") String transId);
}
