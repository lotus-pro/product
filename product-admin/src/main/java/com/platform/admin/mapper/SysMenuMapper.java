package com.platform.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.common.pojo.admin.SysMenu;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * (SysMenu)表数据库访问层
 *
 * @author zengzheng
 * @since 2021-05-12 16:31:53
 */
@Component
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> queryRoleOfMenu(String roleCode);
}