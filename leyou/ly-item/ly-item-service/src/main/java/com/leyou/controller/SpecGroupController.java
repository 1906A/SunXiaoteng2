package com.leyou.controller;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecGroupController {

    @Autowired
    SpecGroupService specGroupService;

    /**
     * 查询规格参数列表
     *
     * @param cid
     */
    @RequestMapping("groups/{cid}")
    public List<SpecGroup> findSpceGroupList(@PathVariable("cid") Long cid){
        return specGroupService.findSpceGroupList(cid);
    }

    @RequestMapping("group")
    public void saveSpecGroup(@RequestBody SpecGroup specGroup){
        if (specGroup.getId()==null){
            specGroupService.saveSpecGroup(specGroup);
        }else {
            specGroupService.updateSpecGroup(specGroup);
        }

    }

    @RequestMapping("group/{id}")
    public void deleteBySpecGroupId(@PathVariable("id") Long id){

        specGroupService.deleteBySpecGroupId(id);
    }

    @RequestMapping("params")
    public List<SpecParam> findSpecParamByGid(@RequestParam("gid") Long gid ){
        return specGroupService.findSpceParamByGid(gid);
    }

    //根据分类id查询分组信息
    /*@RequestMapping("groups/{cid}")
    public List<SpecGroup> findSpecGroupByCid(@PathVariable("cid") Long cid){
        return specGroupService.findSpecGroupByCid(cid);
    }*/


}
