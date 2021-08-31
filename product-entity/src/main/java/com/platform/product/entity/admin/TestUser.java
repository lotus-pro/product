package com.platform.product.entity.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestUser implements Serializable {
    private static final long serialVersionUID = -3692486468628060805L;

    private Integer id;

    private String userCode;

    private Double money;
}
