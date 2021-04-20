package com.platform.support.service.impl;

import com.platform.common.pojo.admin.Company;
import com.platform.support.mapper.CommonMapper;
import com.platform.support.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    CommonMapper commonMapper;

    @Override
    public int addUser(double money) {
        return commonMapper.addUser(money);
    }

    @Override
    public int addCompany(double money) {
        return commonMapper.addCompany(money);
    }

    @Override
    public Company queryOne(Integer id) {
        return commonMapper.queryOne(id);
    }

}
