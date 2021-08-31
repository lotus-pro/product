package com.platform.product.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (SysRoleMenu)表实体类
 *
 * @author zengzheng
 * @since 2021-07-07 13:41:27
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenu implements Serializable {


    private static final long serialVersionUID = -23164646688319530L;
                          
    @TableId(type = IdType.AUTO)
    private Long id;
                    
    /**
    * 角色编码
    */                        
    private String roleCode;
                    
    /**
    * 菜单id
    */                        
    private Long menuId;
            
}