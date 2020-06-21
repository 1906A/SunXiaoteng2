package com.leyou.dao;

import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.pojo.Spu;
import com.leyou.vo.SpuVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface SpuMapper extends Mapper<Spu> {

    List<Spu> findSpuLimit(@Param("key") String key,
                               @Param("page") int page,
                               @Param("rows") Integer rows,
                               @Param("saleable") boolean saleable);

    Long findSpuCount(@Param("key") String key,
                        @Param("saleable") boolean saleable);

    List<Spu> findSpuPage(@Param("key") String key, @Param("saleable") Integer saleable);

    void saveSpuDetail(SpuVo spuVo);


    SpuVo findSpuBySId(@Param("spuId") Long spuId);
}
