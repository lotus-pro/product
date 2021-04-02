package com.platform.admin.service.impl;

import com.platform.admin.mapper.CommonMapper;
import com.platform.admin.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    CommonMapper commonMapper;

    @Override
    public int addCompany(String transId,double money) {
        return commonMapper.addCompany(transId,money);
    }

    @Override
    public int checkCompany(String transId) {
        return commonMapper.checkCompany(transId);
    }
}
