package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.Stock;
import com.leyou.vo.SpuVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Service
public class SpuService {
    @Autowired
    SpuMapper spuMapper;
    @Autowired
    SpuDetailMapper spuDetailMapper;
    @Autowired
    SkuMapper skuMapper;
    @Autowired
    StockMapper stockMapper;
    @Autowired
    AmqpTemplate amqpTemplate;

    public PageResult<Spu> findSpuByLimit(String key, Integer page, Integer rows, boolean saleable) {
        Long spuCount =  spuMapper.findSpuCount(key,saleable);
        List<Spu> spuList = spuMapper.findSpuLimit(key,(page-1)*rows,rows,saleable);
        return new PageResult<Spu>(spuCount,spuList);
    }

    public PageResult<Spu> findSpuByPage(String key, Integer saleable, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        PageInfo<Spu> list = new PageInfo<Spu>(spuMapper.findSpuPage(key,saleable));
        return new PageResult<Spu>(list.getTotal(),list.getList());
    }

    public void saveSpuDetail(SpuVo spuVo) {
        Date date = new Date();


        Spu spu = new Spu();
        spu.setTitle(spuVo.getTitle());
        spu.setSubTitle(spuVo.getSubTitle());
        spu.setBrandId(spuVo.getBrandId());
        spu.setCid1(spuVo.getCid1());
        spu.setCid2(spuVo.getCid2());
        spu.setCid3(spuVo.getCid3());
        spu.setSaleable(false);
        spu.setValid(true);
        spu.setCreateTime(date);
        spu.setLastUpdateTime(date);
        spuMapper.insert(spu);

        SpuDetail spuDetail = spuVo.getSpuDetail();
        spuDetail.setSpuId(spu.getId());

        /*SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(spuVo.getId());
        spuDetail.setAfterService(spuVo.getSpuDetail().getAfterService());
        spuDetail.setDescription(spuVo.getSpuDetail().getDescription());
        spuDetail.setGenericSpec(spuVo.getSpuDetail().getGenericSpec());
        spuDetail.setPackingList(spuVo.getSpuDetail().getPackingList());*/
        spuDetailMapper.insert(spuDetail);

        List<Sku> skus = spuVo.getSkus();
        skus.forEach(sku -> {
            sku.setSpuId(spuVo.getId());
            sku.setEnable(true);
            sku.setCreateTime(date);
            sku.setLastUpdateTime(date);
            skuMapper.insert(sku);

            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        });

        amqpTemplate.convertAndSend("item-exchanges","item.insert",spu.getId());
        this.sendMsg("insert",spu.getId());

    }

    public void sendMsg(String type,Long spuId){
        amqpTemplate.convertAndSend("item-exchanges","item."+type,spuId);
    }

    public SpuDetail findSpuDetailBySpuId(Long spuId) {
        return spuDetailMapper.selectByPrimaryKey(spuId);
    }

    public void updateSpuDetail(SpuVo spuVo) {
        Date date = new Date();

        spuVo.setCreateTime(null);
        spuVo.setLastUpdateTime(date);
        spuVo.setSaleable(null);
        spuVo.setValid(null);
        spuMapper.updateByPrimaryKeySelective(spuVo);

        SpuDetail spuDetail = spuVo.getSpuDetail();
        spuDetail.setSpuId(spuVo.getId());
        spuDetailMapper.updateByPrimaryKeySelective(spuDetail);

        List<Sku> skus = spuVo.getSkus();
        List<Sku> skusBySpuId = skuMapper.findSkusBySpuId(spuVo.getId());
        skusBySpuId.forEach(s -> {
            s.setEnable(false);
            skuMapper.deleteByPrimaryKey(s);
            stockMapper.deleteByPrimaryKey(s.getId());
        });

        List<Sku> skus1 = spuVo.getSkus();
        skus1.forEach(sku -> {
            sku.setSpuId(spuVo.getId());
            sku.setEnable(true);
            sku.setCreateTime(date);
            sku.setLastUpdateTime(date);
            skuMapper.insert(sku);

            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        });
        this.sendMsg("update",spuVo.getId());

    }



    public void deleteSpuBySpuId(Long spuId) {
        List<Sku> skusBySpuId = skuMapper.findSkusBySpuId(spuId);
        skusBySpuId.forEach(s -> {
            s.setEnable(false);
            skuMapper.updateByPrimaryKeySelective(s);
            stockMapper.deleteByPrimaryKey(s.getId());
        });
        //删除spuDetail
        spuDetailMapper.deleteByPrimaryKey(spuId);
        //删除spu
        spuMapper.deleteByPrimaryKey(spuId);
        //删除商品消息发送mq
        this.sendMsg("delete",spuId);
    }

    public void upOrDown(Long spuId,int saleable) {
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setSaleable(saleable ==1?true:false);
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    public Spu findSpuBySpuId(Long spuId){
        return spuMapper.selectByPrimaryKey(spuId);
    }

    public Spu findSpuById(Long id) {
        return  spuMapper.selectByPrimaryKey(id);
    }


    public SpuVo findSpuBySId(Long spuId) {
        return spuMapper.findSpuBySId(spuId);
    }
}
