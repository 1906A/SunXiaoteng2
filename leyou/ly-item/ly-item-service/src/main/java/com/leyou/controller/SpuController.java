package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.service.SpuService;
import com.leyou.vo.SpuVo;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spu")
public class SpuController {
    @Autowired
    SpuService spuService;
    /*@RequestMapping("page")
    public PageResult<Spu> findSpuByPage(@RequestParam("key") String key,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("rows") Integer rows,
                                         @RequestParam(required = false,value = "saleable") boolean saleable){
        System.out.println(key+"=="+page+"=="+rows+"=="+saleable);
        PageResult<Spu> spuPageResult = spuService.findSpuByLimit(key,page,rows,saleable);
        return spuPageResult;
    }*/

    @RequestMapping("page")
    public PageResult<Spu> findSpuByPage(@RequestParam("key") String key,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("rows") Integer rows,
                                         @RequestParam(required = false ,value = "saleable") Integer saleable){
        System.out.println(key+"=="+page+"=="+rows+"=="+saleable);
        PageResult<Spu> spuPageResult = spuService.findSpuByPage(key,saleable,page,rows);
        return spuPageResult;
    }

    @RequestMapping("saveOrUpdateGoods")
    public void saveSpuDetail(@RequestBody SpuVo spuVo){
        if(spuVo.getId()!=null){
            spuService.updateSpuDetail(spuVo);
        }else {
            spuService.saveSpuDetail(spuVo);
        }

    }

    @RequestMapping("detail/{spuId}")
    public SpuDetail findSpuDetailBySpuId(@PathVariable("spuId") Long spuId){
        return spuService.findSpuDetailBySpuId(spuId);
    }

    @RequestMapping("deleteById/{spuId}")
    public void deleteSpuBySpuId(@PathVariable("spuId") Long spuId){
        spuService.deleteSpuBySpuId(spuId);
    }

    @RequestMapping("upOrDown")
    public void upOrDown(@RequestParam("spuId") Long spuId,@RequestParam("saleable") Integer saleable){
        spuService.upOrDown(spuId,saleable);
    }

    /**根据SpuId查spu
     * @param id
     * @return
     */
    @RequestMapping("findSpuById")
    public Spu findSpuById(@RequestParam("SpuId") Long id){

        return  spuService.findSpuById(id);
    }

    @RequestMapping("findSpuBySpuId")
    public SpuVo findSpuBySid(@RequestParam("spuId") Long spuId) {
         return spuService.findSpuBySId(spuId);
    }

}
