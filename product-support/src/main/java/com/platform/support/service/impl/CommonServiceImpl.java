package com.platform.support.service.impl;

import com.platform.support.mapper.CommonMapper;
import com.platform.support.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    CommonMapper commonMapper;

    @Override
    public int addUser(String transId,double money) {
        return commonMapper.addUser(transId,money);
    }
}
