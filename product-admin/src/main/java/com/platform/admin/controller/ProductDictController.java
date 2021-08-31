package com.platform.admin.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.admin.service.ProductDictService;
import com.platform.common.constants.CacheConstants;
import com.platform.common.util.BeanUtil;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import com.platform.product.entity.admin.ProductDict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.runtime.GlobalConstants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统字典表(ProductDict)表控制层
 *
 * @author zengzheng
 * @since 2021-08-25 15:54:29
 */
@Slf4j
@Api(tags = {"字典"})
@RestController
@RequestMapping("/dict")
public class ProductDictController extends BaseController {

    @Autowired
    private ProductDictService productDictService;
    @Autowired
    RedissonClient redissonClient;

    @ApiOperation("字典分页查询")
    @GetMapping("/page")
    public ResponseResult queryPage(@RequestParam Map<String, Object> params) {
        IPage<ProductDict> page = getIPage(params);
        ProductDict productDict = BeanUtil.mapToBean(params, ProductDict.class);
        page = productDictService.queryPage(page, productDict);
        return result(page);
    }

    @ApiOperation("新增字典")
    @PostMapping("/add")
    public ResponseResult saveDataInfo(@RequestBody ProductDict dict) {
        productDictService.saveDataInfo(dict);
        return result();
    }

    @ApiOperation("修改字典")
    @PostMapping("/update")
    public ResponseResult updateDataInfo(@RequestBody ProductDict productDict) {
        productDictService.updateDataInfo(productDict);
        return result();
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public ResponseResult deleteDataInfo(@RequestBody Map<String, Object> params) {
//        ProductDict productDict = BeanUtil.mapToBean(params, ProductDict.class, false);
//        productDictService.removeById(productDict.getId());
        return result();
    }

    @ApiOperation("刷新字典到redis")
    @PostMapping("/reload")
    public ResponseResult reload() {
        LambdaQueryWrapper<ProductDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(ProductDict::getDictCode, "%");
        List<ProductDict> list = productDictService.list(wrapper);
        Map<String, List<ProductDict>> collect = list.stream().collect(Collectors.groupingBy(ProductDict::getDictType));
        Set<String> keySet = collect.keySet();
        for (String key : keySet) {
            List<ProductDict> productDicts = collect.get(key);
            RBucket<Object> bucket = this.redissonClient.getBucket(CacheConstants.CACHE_DICT + key);
            bucket.set(JSON.toJSONString(productDicts));
        }
        return result();
    }

}