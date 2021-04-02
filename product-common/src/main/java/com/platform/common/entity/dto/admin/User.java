package com.platform.common.entity.dto.admin;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -3692486468628060805L;

    private Integer id;

    private String userCode;

    private Double money;
}
