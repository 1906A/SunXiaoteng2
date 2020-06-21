package com.leyou.search.repository;

import com.leyou.search.item.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodRepository extends ElasticsearchRepository<Goods,Long> {

}
