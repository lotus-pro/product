package com.platform.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.admin.mapper.SysRoleMenuMapper;
import com.platform.admin.service.SysRoleMenuService;
import com.platform.common.cache.Cache;
import com.platform.product.entity.admin.SysRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (SysRoleMenu)表服务实现类
 *
 * @author zengzheng
 * @since 2021-07-07 13:41:27
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu>
        implements SysRoleMenuService {

    @Autowired
    Cache cache;

    @Override
    public void clearUserAuthorityInfoByRoleId(String roleCode) {
//        List<SysRoleMenu> ids = this.list(new QueryWrapper<SysRoleMenu>()
//                .inSql("id", "select user_code from sys_user_role where role_code = " + roleCode));
//        ids.forEach( t -> {
//            cache.del(null);
//        });
        System.out.println(roleCode);
    }
}