package com.platform.core.userdetail;

import com.platform.common.pojo.admin.ProductUser;
import com.platform.core.mapper.ProductUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductUserService {

    @Autowired
    ProductUserMapper productUserMapper;

    public ProductUserService() {
    }

    public ProductUser queryPcmcUser(String username) {
        return productUserMapper.selectById(username);
    }

}
