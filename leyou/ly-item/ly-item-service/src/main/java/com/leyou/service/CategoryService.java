package com.leyou.service;

import com.leyou.dao.CategoryMapper;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    public List<Category> findCategory(Category category){
        return categoryMapper.select(category);
    }

    public Category findCate(int id){
        return categoryMapper.findCate(id);
    }

    public void addCategory(Category category){
        categoryMapper.insert(category);
    }

    public void updateCategory(Category category){
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    public void deleteCategory(Long id){
        Category category = new Category();
        category.setId(id);
        categoryMapper.deleteByPrimaryKey(category);
    }

    public Category findCategoryById(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    //根据分类id查询分类名称
    public List<Category> findCategoryByCids(List<Long> ids) {
        List<Category> categoryList = new ArrayList<>();
        ids.forEach(cid->{
            categoryList.add(categoryMapper.selectByPrimaryKey(cid));
        });
        return categoryList;
    }
}
