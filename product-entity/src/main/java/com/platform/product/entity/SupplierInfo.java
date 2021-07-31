package com.platform.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 供应商信息表(SupplierInfo)表实体类
 *
 * @author zengzheng
 * @since 2021-07-27 09:57:59
 */
@Data
@TableName("supplier_info")
public class SupplierInfo implements Serializable {


    private static final long serialVersionUID = -17473607495978997L;
        
    /**
    * 主键
    */                      
    @TableId(type = IdType.AUTO)
    private Integer id;
                    
    /**
    * 供应商编号
    */                        
    private String suprNo;
                    
    /**
    * 供应商全称
    */                        
    private String suprFullname;
                    
    /**
    * 供应商名称简称
    */                        
    private String suprAbb;
                    
    /**
    * 供应商有效状态标识
    */                        
    private String suprEffectIdnt;
                    
    /**
    * 供应商联系人姓名
    */                        
    private String suprContName;
                    
    /**
    * 供应商联系人联系方式
    */                        
    private String suprContMode;
                    
    /**
    * 供应商联系人邮箱
    */                        
    private String suprContMailbox;
                    
    /**
    * 创建人
    */                        
    private String createUser;
                    
    /**
    * 创建时间
    */                        
    private LocalDateTime createTime;
                    
    /**
    * 最后更新用户
    */                        
    private String lastUpdateUser;
                    
    /**
    * 最后更新时间
    */                        
    private LocalDateTime lastUpdateTime;

}