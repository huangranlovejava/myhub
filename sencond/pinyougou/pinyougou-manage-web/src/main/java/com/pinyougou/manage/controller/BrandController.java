package com.pinyougou.manage.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/brand")
@RestController
public class BrandController {
    @Reference
    private BrandService brandService;

    @GetMapping("/findAll")
    public List<TbBrand> queryAll() {
        //  return brandService.queryAll();
        return brandService.findAll();
    }

    @GetMapping("/testPage")
    public List<TbBrand> testPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "rows", defaultValue = "10") Integer rows) {

        // return brandService.testPage(page, rows);
        return (List<TbBrand>) brandService.findPage(page, rows).getRows();

    }

    @GetMapping("/findPage")
    public PageResult findPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "rows", defaultValue = "10") Integer rows) {

        // return brandService.testPage(page, rows);
        return brandService.findPage(page, rows);

    }

    @PostMapping("/add")
    public Result add(@RequestBody TbBrand brand) {

        try {
            brandService.add(brand);
            return Result.ok("添加品牌成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("添加品牌失败");
    }

    @GetMapping("/findOne")
    public TbBrand findOne(Long id) {

        return brandService.findOne(id);

    }

    @PostMapping("/update")
    public Result update(@RequestBody TbBrand brand) {

        try {
            brandService.update(brand);
            return Result.ok("更新品牌成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("更新品牌失败");
    }

    @PostMapping("/search")
    public PageResult search(@RequestBody TbBrand brand, @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "rows", defaultValue = "10") Integer rows) {

        return brandService.search(page, rows, brand);
    }

    @GetMapping("/delete")
    public Result delete(Long[] ids) {

        try {
            brandService.deleteByIds(ids);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.fail("删除失败");
    }
    //$http.get("../brand/selectOptionList.do");
    @GetMapping("/selectOptionList")
    public List<Map<String,String>> selectOptionList() {

        return brandService.selectOptionList();

    }
}
