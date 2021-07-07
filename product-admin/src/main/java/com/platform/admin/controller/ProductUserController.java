package com.platform.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.admin.service.ProductRoleService;
import com.platform.admin.service.ProductUserService;
import com.platform.common.pojo.admin.ProductUser;
import com.platform.common.util.BeanUtil;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import com.platform.core.entity.UserDetailInfo;
import com.platform.core.util.AuthenticationUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户表(ProductUser)表控制层
 *
 * @author zengzheng
 * @since 2021-04-23 08:52:46
 */
@Slf4j
@Api(tags = {"自定义"})
@RestController
@RequestMapping("/productUser")
public class ProductUserController extends BaseController {

    @Autowired
    private ProductUserService productUserService;
    @Autowired
    private ProductRoleService productRoleService;

    @ApiOperation("列表分页查询")
    @GetMapping("/page")
    public ResponseResult queryList(Map<String, Object> params) {
        Object pageSize = params.get("pageSize");
        Object currentPage = params.get("currentPage");
        boolean isPage = true;
        if (pageSize == null && currentPage == null) {
            isPage = false;
        }
        IPage<ProductUser> page = getIPage(params);
        List<ProductUser> resList = new ArrayList<ProductUser>();
        ProductUser productUser = BeanUtil.mapToBean(params, ProductUser.class);
        if (isPage) {
            page = productUserService.page(page);
            return result(page);
        } else {
//            resList = productUserService.queryList(productUser);
            return result(resList);
        }
    }

    @ApiOperation("新增")
    @PostMapping("/add")
    public ResponseResult saveDataInfo(@RequestBody ProductUser productUser) {
//        productUserService.save(productUser);
        return result();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public ResponseResult updateDataInfo(@RequestBody ProductUser productUser) {
//        productUserService.updateById(productUser);
        return result();
    }

    @ApiOperation("获取用户分页列表")
    @GetMapping("/list")
//    @PreAuthorize("hasAuthority('sys:user:list')")
    public ResponseResult list(Map<String, Object> params) {
        IPage<ProductUser> iPage = getIPage(params);
        ProductUser productUser = BeanUtil.mapToBean(params, ProductUser.class);
        iPage =  productUserService.queryPage(iPage, productUser);

        iPage.getRecords().forEach(u -> {

            u.setSysRoles(productRoleService.listRolesByUserId(u.getUserCode()));
        });

        return result(iPage);
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public ResponseResult deleteDataInfo(@RequestParam String id) {
        return result();
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/userInfo")
    public ResponseResult getUserInfo() {
        UserDetailInfo currentUser = AuthenticationUtils.getCurrentUser();
        return result(currentUser);
    }

}