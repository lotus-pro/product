package com.platform.core.userdetail;

import com.platform.core.mapper.DetailUserMapper;
import com.platform.product.entity.admin.ProductUser;
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
