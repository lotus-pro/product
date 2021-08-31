package com.platform.product.entity.admin;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Data
@TableName(value = "company")
public class Company implements Serializable {
    private static final long serialVersionUID = -3692486468628060805L;

    private Integer id;

    private String companyCode;

    private Double money;

    private Instant crtDate;
}
