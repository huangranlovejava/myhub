package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service(interfaceClass=BrandService.class)
public class BrandServiceImpl extends BaseServiceImpl<TbBrand> implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<TbBrand> queryAll() {
        return brandMapper.queryAll();
    }

    @Override
    public List<TbBrand> testPage(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        List<TbBrand> brandList = brandMapper.selectAll();
        return brandList;
    }

    @Override
    public PageResult search(Integer page, Integer rows, TbBrand brand) {
        PageHelper.startPage(page,rows);
        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(brand.getFirstChar())){
            criteria.andEqualTo("firstChar",brand.getFirstChar());
        }
        if (!StringUtils.isEmpty(brand.getName())){
            criteria.andLike("name","%"+brand.getName()+"%");
        }
        List<TbBrand> brandList = brandMapper.selectByExample(example);
        PageInfo<TbBrand> pageInfo = new PageInfo<>(brandList);



        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }
}
