package com.platform.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.admin.mapper.SysMenuMapper;
import com.platform.common.pojo.admin.SysMenu;
import com.platform.admin.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * (SysMenu)表服务实现类
 *
 * @author zengzheng
 * @since 2021-05-12 16:31:53
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    
    @Override
    public IPage<SysMenu> queryPage(IPage page, SysMenu sysMenu) {
        LambdaQueryWrapper<SysMenu> wrapper = new QueryWrapper<SysMenu>().lambda();
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public List<SysMenu> queryList(SysMenu sysMenu) {
        LambdaQueryWrapper<SysMenu> wrapper = new QueryWrapper<SysMenu>().lambda();
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<SysMenu> queryRoleOfMenu(String roleCode) {
        return baseMapper.queryRoleOfMenu(roleCode);
    }

    @Override
    public List<SysMenu> buildTreeMenu(List<SysMenu> menus) {
        List<SysMenu> finalMenus = new ArrayList<>();
        // 先各自寻找到各自的孩子
        for (SysMenu menu : menus) {
            for (SysMenu e : menus) {
                if (menu.getId() == e.getParentId()) {
                    menu.getChildren().add(e);
                }
            }
            // 提取出父节点
            if (menu.getParentId() == 0L) {
                finalMenus.add(menu);
            }
        }
        return finalMenus;
    }
}