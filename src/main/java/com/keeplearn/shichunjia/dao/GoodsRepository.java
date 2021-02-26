package com.keeplearn.shichunjia.dao;

import com.keeplearn.shichunjia.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface GoodsRepository extends ElasticsearchRepository<Goods,String> {
}
