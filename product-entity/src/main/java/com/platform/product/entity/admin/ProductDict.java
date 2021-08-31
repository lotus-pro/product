package com.platform.product.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统字典表(ProductDict)表实体类
 *
 * @author zengzheng
 * @since 2021-08-25 15:54:29
 */
@Data
@TableName("product_dict")
public class ProductDict implements Serializable {


    private static final long serialVersionUID = -17102357114554065L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
    * 字典类型
    */                      
    private String dictType;
                                
    /**
    * 字典编码
    */                        
    private String dictCode;

    /**
    * 字典名称
    */                        
    private String dictName;
                                
    /**
    * 顺序号
    */                        
    private Integer sortNo;
                                
    /**
    * 国际化语言编码
    */                        
    private String i18nCode;
                                
    /**
    * 说明
    */                        
    private String description;
                                
    /**
    * 是否启用
    */                        
    private String isEnabled;
                                
    /**
    * 自
    */                        
    private Object startDateActive;
                                
    /**
    * 至
    */                        
    private Object endDateActive;
                                
    /**
    * 创建日期
    */                        
    private Date createDate;
                                
    /**
    * 创建用户编码
    */                        
    private String createBy;
                                
    /**
    * 最后更新日期
    */                        
    private Date lastUpdateDate;
                                
    /**
    * 最后更新用户编码
    */                        
    private String lastUpdateBy;

    @TableField(exist = false)
    private List<ProductDict> children;
                                
}