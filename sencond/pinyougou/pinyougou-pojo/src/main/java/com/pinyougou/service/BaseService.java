package com.pinyougou.service;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.vo.PageResult;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {
    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    T findOne(Serializable id);

    List<T> findAll();

    List<T> findByWhere(T t);

    void add(T t);

    void update(T t);

    void deleteByIds(Serializable[] ids);

    PageResult findPage(Integer page, Integer rows);

    PageResult findPage(Integer page, Integer rows, T t);



}
