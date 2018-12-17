package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.sellergoods.service.SpecificationService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

@Service(interfaceClass = SpecificationService.class)
public class SpecificationServiceImpl extends BaseServiceImpl<TbSpecification> implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Override
    public PageResult search(Integer page, Integer rows, TbSpecification specification) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbSpecification.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(specification.getSpecName())) {
            criteria.andLike("specName", "%" + specification.getSpecName() + "%");
        }

        List<TbSpecification> list = specificationMapper.selectByExample(example);
        PageInfo<TbSpecification> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void add(Specification specification) {
        add(specification.getSpecification());
        List<TbSpecificationOption> optionList = specification.getSpecificationOptionList();
        if (optionList != null && optionList.size() > 0) {
            for (TbSpecificationOption option : optionList) {
                option.setSpecId(specification.getSpecification().getId());
                specificationOptionMapper.insertSelective(option);
            }
        }
    }

    @Override
    public Specification findOne(Long id) {
        Specification specification = new Specification();
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        TbSpecificationOption option = new TbSpecificationOption();
        option.setSpecId(id);
        List<TbSpecificationOption> optionList = specificationOptionMapper.select(option);
        specification.setSpecificationOptionList(optionList);
        specification.setSpecification(tbSpecification);
        return specification;
    }

    @Override
    public void update(Specification specification) {
        specificationMapper.updateByPrimaryKeySelective(specification.getSpecification());

        TbSpecificationOption  option= new TbSpecificationOption();
        option.setSpecId(specification.getSpecification().getId());
        specificationOptionMapper.delete(option);

        if (specification.getSpecificationOptionList()!=null&&specification.getSpecificationOptionList().size()>0){
            for (TbSpecificationOption tbSpecificationOption : specification.getSpecificationOptionList()) {
                tbSpecificationOption.setSpecId(specification.getSpecification().getId());
                specificationOptionMapper.insertSelective(tbSpecificationOption);
            }
        }

    }

    @Override
    public void deleteSpecificationByIds(Long[] ids) {

        deleteByIds(ids);


        Example example=new Example(TbSpecificationOption.class);
        example.createCriteria().andIn("specId", Arrays.asList(ids));
        specificationOptionMapper.deleteByExample(example);

    }
}
