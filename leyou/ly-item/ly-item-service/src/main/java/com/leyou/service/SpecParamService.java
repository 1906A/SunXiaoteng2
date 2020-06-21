package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.BrandMapper;
import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class SpecParamService {
    @Autowired
    SpecParamMapper specParamMapper;

    public void saveSpecParam(SpecParam specParam) {
        specParamMapper.insert(specParam);
    }

    public void updateSpecParam(SpecParam specParam) {
        specParamMapper.updateByPrimaryKey(specParam);
    }

    public void deleteSpecParamById(Long id) {
        specParamMapper.deleteByPrimaryKey(id);
    }

    public List<SpecParam> findSpecParamByCid(Long cid) {
        return specParamMapper.findSpecParamByCid(cid);
    }

    /**
     * 根据三级分类id+搜索条件为1的参数查询规格参数集合
     * @param cid
     * @return
     */
    public List<SpecParam> findSpecParamByCidAndSearching(Long cid) {
        SpecParam specParam = new SpecParam();
        specParam.setCid(cid);
        specParam.setSearching(true);
        return specParamMapper.select(specParam);
    }
    //根据三级分类id+是否通用参数值查询
    public List<SpecParam> findParamsByCidGeneric(Long cid, boolean generic) {
        SpecParam specParam = new SpecParam();
        specParam.setCid(cid);
        specParam.setGeneric(generic);
        return specParamMapper.select(specParam);
    }
}
