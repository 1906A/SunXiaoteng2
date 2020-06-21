package com.leyou.vo;

import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;

import java.util.List;

public class SpuVo extends Spu {
    private String bname;
    private String cname;
    private SpuDetail spuDetail;
    private List<Sku> skus;

    @Override
    public String getBname() {
        return bname;
    }

    @Override
    public void setBname(String bname) {
        this.bname = bname;
    }

    @Override
    public String getCname() {
        return cname;
    }

    @Override
    public void setCname(String cname) {
        this.cname = cname;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    @Override
    public List<Sku> getSkus() {
        return skus;
    }

    @Override
    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }
}
