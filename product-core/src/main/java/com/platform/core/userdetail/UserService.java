package com.platform.core.userdetail;

import com.platform.common.pojo.admin.ProductUser;
import com.platform.core.mapper.DetailUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    DetailUserMapper productUserMapper;

    public UserService() {
    }

    public ProductUser queryProductUser(String username) {
        return productUserMapper.selectById(username);
    }

}
