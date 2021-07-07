package com.platform.common.pojo.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * (ProductRole)表实体类
 *
 * @author zengzheng
 * @since 2021-06-20 09:20:47
 */
@Data
@TableName("product_role")
public class ProductRole implements Serializable {


    private static final long serialVersionUID = -48475389032767502L;
                          
    @TableId(type = IdType.AUTO)
    private Long id;
                    
    /**
    * 角色名称
    */                        
    private String roleName;
                    
    /**
    * 角色编码
    */                        
    private String roleCode;
                    
    /**
    * 备注
    */                        
    private String remark;
                    
    /**
    * 创建时间
    */                        
    private LocalDateTime createTime;
                    
    /**
    * 结束时间
    */                        
    private LocalDateTime updateTime;
                    
    /**
    * 状态
    */                        
    private String isEnabled;
            
}