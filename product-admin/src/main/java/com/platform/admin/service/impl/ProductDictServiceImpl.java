package com.platform.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.admin.mapper.ProductDictMapper;
import com.platform.admin.service.ProductDictService;
import com.platform.common.util.Assert;
import com.platform.common.util.BeanUtil;
import com.platform.product.entity.admin.ProductDict;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统字典表(ProductDict)表服务实现类
 *
 * @author zengzheng
 * @since 2021-08-25 15:54:29
 */
@Service
public class ProductDictServiceImpl extends ServiceImpl<ProductDictMapper, ProductDict> implements ProductDictService {
    
    @Override
    public IPage<ProductDict> queryPage(IPage<ProductDict> page, ProductDict productDict) {
        String dictType = productDict.getDictType();
        String dictName = productDict.getDictName();
        LambdaQueryWrapper<ProductDict> wrapper = new QueryWrapper<ProductDict>().lambda();
        wrapper.eq(StringUtils.isNotBlank(dictType), ProductDict::getDictType, dictType)
                .like(StringUtils.isNotBlank(dictName),ProductDict::getDictName,dictName)
                .eq(ProductDict::getDictCode, "%");
        page = baseMapper.selectPage(page, wrapper);
        List<ProductDict> records = page.getRecords();
        List<ProductDict> children = baseMapper.qryChildren(productDict);
        Map<String, List<ProductDict>> childrenMap = children.stream().collect(Collectors.groupingBy(ProductDict::getDictType));
        Set<String> keySet = childrenMap.keySet();
        for (ProductDict record : records) {
            for (String key : keySet) {
                if (record.getDictType().equals(key)) {
                    record.setChildren(childrenMap.get(key));
                    break;
                }
            }
        }
        return page;
    }

    @Override
    public void saveDataInfo(ProductDict dict) {
        String dictCode = dict.getDictCode();
        String dictType = dict.getDictType();
        String dictName = dict.getDictName();
        LambdaQueryWrapper<ProductDict> wrapper = new QueryWrapper<ProductDict>().lambda();
        wrapper.eq(ProductDict::getDictType, dictType);
        List<ProductDict> productDicts = baseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(productDicts)) {
            ProductDict parDict = new ProductDict();
            BeanUtil.copyProperties(dict, parDict);
            parDict.setDictCode("%");
            baseMapper.insert(dict);
            baseMapper.insert(parDict);
            return;
        }
        for (ProductDict productDict : productDicts) {
            String dictName1 = productDict.getDictName();
            String dictCode1 = productDict.getDictCode();
            if (dictCode1.equals("%") || dictCode.equals("%")) {
                continue;
            }
            Assert.isTrue(dictCode.equals(dictCode1), "product.support.error.00001", "字典类型的码值");
            Assert.isTrue(dictName.equals(dictName1), "product.support.error.00001", "字典类型的码值");
            baseMapper.insert(dict);
        }
    }

    @Override
    public void updateDataInfo(ProductDict dict) {
        String dictCode = dict.getDictCode();
        String dictType = dict.getDictType();
        String dictName = dict.getDictName();
        LambdaQueryWrapper<ProductDict> wrapper = new QueryWrapper<ProductDict>().lambda();
        wrapper.eq(ProductDict::getDictType, dictType).eq(ProductDict::getDictCode, dictCode)
            .ne(ProductDict::getId,dict.getId());
        ProductDict dict1 = baseMapper.selectOne(wrapper);
        Assert.isNotNull(dict1, "product.support.error.00001", "字典类型的码值");
        baseMapper.updateById(dict);
    }

}