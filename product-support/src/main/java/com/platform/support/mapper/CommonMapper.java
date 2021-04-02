package com.platform.support.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface CommonMapper {
    @Update("update user set money = money + #{money},trans_id = #{transId} where id = '1'")
    int addUser(@Param("transId") String transId, @Param("money") double money);
}
