package com.leyou.client;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RequestMapping("specParam")
public interface SpecClientServer {
    @RequestMapping("params")
    public List<SpecParam> findSpecParamByCidAndSearching(@RequestParam("cid") Long cid);
    @RequestMapping("paramsByCidGeneric")
    public List<SpecParam> findParamsByCidGeneric(@RequestParam("cid") Long cid,@RequestParam("generic") boolean generic);
    //根据分类id查询分组信息
    @RequestMapping("groups/{cid}")
    public List<SpecGroup> findSpecGroupByCid(@PathVariable("cid") Long cid);
}
