package com.leyou.service;

import com.leyou.dao.SpecGroupMapper;
import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecGroupService {
    @Autowired
    SpecGroupMapper specGroupMapper;
    @Autowired
    SpecParamMapper specParamMapper;

    public void saveSpecGroup(SpecGroup specGroup) {
        specGroupMapper.insert(specGroup);
    }

    public List<SpecGroup> findSpceGroupList(Long cateGoryId) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cateGoryId);
        return specGroupMapper.select(specGroup);
    }

    public void deleteBySpecGroupId(Long id) {
        specGroupMapper.deleteByPrimaryKey(id);
    }

    public void updateSpecGroup(SpecGroup specGroup) {
        specGroupMapper.updateByPrimaryKey(specGroup);
    }

    public List<SpecParam> findSpceParamByGid(Long gid) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        return specParamMapper.select(specParam);
    }

    public List<SpecGroup> findSpecGroupByCid(Long cid) {

        SpecGroup specGroup = new SpecGroup();
        //根据分类id 查询规格参数及组内的参数列表

        specGroup.setCid(cid);

        List<SpecGroup> groupList = specGroupMapper.select(specGroup);

        groupList.forEach(group -> {

            SpecParam param = new SpecParam();

            param.setGroupId(group.getId());
//            List<SpecParam> paramList = specParamMapper.select(param);
            group.setParams(specParamMapper.select(param));
        });
        return groupList;
    }
}
