package com.platform.support.service;

import com.platform.common.pojo.admin.Company;

public interface CommonService {

    int addUser(double money);

    int addCompany(double money);

    Company queryOne(Integer id);

}
