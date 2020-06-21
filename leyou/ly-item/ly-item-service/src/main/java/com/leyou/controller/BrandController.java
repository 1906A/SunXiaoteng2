package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.dao.BrandMapper;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.service.BrandService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @RequestMapping("page")
    public Object findBrandByPage(@RequestParam("key") String key,
                                  @RequestParam("page") Integer page,
                                  @RequestParam("rows") Integer rows,
                                  @RequestParam("sortBy") String sortBy,
                                  @RequestParam("desc") boolean desc){
        System.out.println(key+"=="+page+"=="+rows+"=="+sortBy+"=="+desc);
      //  PageResult<Brand> pageResult = brandService.findBrand(key,page,rows,sortBy,desc);
        PageResult<Brand> brandByLimit = brandService.findBrandByLimit(key, page, rows, sortBy, desc);
        return brandByLimit;
    }

    @RequestMapping("addOrEditBrand")
    public void addOrEditBrand(Brand brand,
                               @RequestParam("cids")List<Long> cids){
        if (brand.getId()!=null){
            //修改
            brandService.updateBrand(brand,cids);
        }else {
            brandService.addOrEditBrand(brand,cids);
        }

    }

    @RequestMapping("deleteById/{id}")
    public void delete(@PathVariable("id") Long id){
        brandService.deleteById(id);
    }


    @RequestMapping("bid/{id}")
    public List<Category> findCategoryByBrandId(@PathVariable("id") Long pid){
        return brandService.findCategoryByBrandId(pid);
    }

    @RequestMapping("cid/{cid}")
    public List<Brand> findBrandByCid(@PathVariable("cid") Long cid){
        return brandService.findBrandByCid(cid);
    }

    @RequestMapping("findBrandById")
    public Brand findBrandById(@RequestParam("id") Long id){
        return brandService.findBrandById(id);
    }

}












