package com.leyou.controller;

import com.leyou.pojo.Category;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("list")
    public List<Category> list(@RequestParam("pid") Long pid){
        Category category = new Category();
        category.setParentId(pid);
        return categoryService.findCategory(category);
    }

    @RequestMapping("id")
    public Object findCate(){
        return categoryService.findCate(1);
    }

    @RequestMapping("add")
    public String add(@RequestBody Category category){
        String result = "SUCC";
        try {
            categoryService.addCategory(category);
        } catch (Exception e) {
            System.out.println("商品添加信息异常");
            result ="FAIL";
        }
        return result;
    }

    @RequestMapping("update")
    public String update(@RequestBody Category category){
        String result = "SUCC";

        try {
            categoryService.updateCategory(category);
        } catch (Exception e) {
            System.out.println("删除信息异常");
            result ="FAIL";
        }
        return result;
    }

    @RequestMapping("delete")
    public void delete(@RequestParam("id") Long id){
        String result = "SUCC";
        try {
            categoryService.deleteCategory(id);
        } catch (Exception e) {
            System.out.println("删除信息异常");
            result ="FAIL";
        }
    }

    @RequestMapping("findCategoryById")
    public Category findCategoryById(@RequestParam("id") Long id){
        return categoryService.findCategoryById(id);
    }

    //根据分类id查询分类名称
    @RequestMapping("findCategoryByCids")
    public List<Category> findCategoryByCids(@RequestBody List<Long> ids){
        return categoryService.findCategoryByCids(ids);
    }

}
