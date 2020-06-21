package com.leyou.controller;

import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecParamService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("specParam")
public class SpecParamContoller {

    @Autowired
    private SpecParamService specParamService;

    @RequestMapping("param")
    public void saveSpecParam(@RequestBody SpecParam specParam){
        if (specParam.getId()!=null){
            specParamService.updateSpecParam(specParam);
        } else {
            specParamService.saveSpecParam(specParam);
        }

    }

    @RequestMapping("param/{id}")
    public void deleteSpecParamById(@PathVariable("id") Long id){
        specParamService.deleteSpecParamById(id);
    }


    @RequestMapping("params")
    public List<SpecParam> findSpecParamByCid(@RequestParam("cid") Long cid){
        return specParamService.findSpecParamByCid(cid);
    }

    @RequestMapping("paramByCid")
    public List<SpecParam> findSpecParamByCidAndSearching(@RequestParam("cid") Long cid){
        return specParamService.findSpecParamByCidAndSearching(cid);
    }

    //根据三级分类id+是否通用参数值查询
    @RequestMapping("paramsByCidGeneric")
    public List<SpecParam> findParamsByCidGeneric(@RequestParam("cid") Long cid,@RequestParam("generic") boolean generic){
        return specParamService.findParamsByCidGeneric(cid,generic);
    }
}
