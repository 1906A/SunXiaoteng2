package com.leyou.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.SpecParam;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.search.client.SkuClient;
import com.leyou.search.client.SpecClient;
import com.leyou.search.client.SpuClient;
import com.leyou.search.item.Goods;
import com.leyou.search.repository.GoodRepository;
import com.leyou.vo.SpuVo;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodService {

    @Autowired
    private SkuClient skuClient;
    @Autowired
    private SpecClient specClient;
    @Autowired
    private SpuClient spuClient;
    @Autowired
    private GoodRepository goodRepository;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public Goods convert(SpuVo spuVo) throws Exception {
        Goods goods = new Goods();
        // 设置参数
        goods.setId(spuVo.getId());
        goods.setSubTitle(spuVo.getSubTitle());

        goods.setBrandId(spuVo.getBrandId());
        goods.setCid1(spuVo.getCid1());
        goods.setCid2(spuVo.getCid2());
        goods.setCid3(spuVo.getCid3());
        goods.setCreateTime(spuVo.getCreateTime());
        //all 标题+分类+品牌
        goods.setAll(spuVo.getTitle()+" "+spuVo.getCname().replace("/"," ")+" "+spuVo.getBname());
        List<Sku> skuList = skuClient.findSkusBySpuId(spuVo.getId());

        List<Long> price = new ArrayList<>();
        skuList.forEach(sku -> {
            price.add(sku.getPrice());
        });
        goods.setPrice(price);
        goods.setSkus(MAPPER.writeValueAsString(skuList));
        Map<String,Object> specs = new HashMap<>();
        List<SpecParam> specParamList = specClient.findSpecParamByCidAndSearching(spuVo.getCid3());
        SpuDetail spuDetail = spuClient.findSpuDetailBySpuId(spuVo.getId());

        specParamList.forEach(specParam -> {
            if (specParam.getGeneric()){
                try {
                    Map<Long,Object> genericSpec = MAPPER.readValue(spuDetail.getGenericSpec(),new TypeReference<Map<Long,Object>>(){});
                    String value = genericSpec.get(specParam.getId()).toString();
                    if (specParam.getNumeric()) {
                        value = chooseSegment(value,specParam);
                    }
                    specs.put(specParam.getName(),value);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Map<Long , Object> specialSpec = new HashMap<>();

                try {
                    specialSpec = MAPPER.readValue(spuDetail.getSpecialSpec(), new TypeReference<Map<Long,Object>>(){});
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String value = specialSpec.get(specParam.getId()).toString();
                specs.put(specParam.getName(),value);
            }
        });
        goods.setSpecs(specs);
        return goods;
    }


    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    /**
     * @param spuId
     * rabbitmq监听消息修改ES数据
     */
    public void editEsData(Long spuId) throws Exception {
        //根据spuId查询spu
        SpuVo spuVo = spuClient.findSpuBySId(spuId);
        //spu转换成goods
        Goods goods = this.convert(spuVo);
        //持久化到es
        goodRepository.save(goods);

    }

    public void deleteEsData(Long spuId) {

        goodRepository.deleteById(spuId);
    }
}
