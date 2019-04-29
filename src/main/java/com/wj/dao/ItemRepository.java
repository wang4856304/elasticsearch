package com.wj.dao;

import com.wj.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemRepository extends ElasticsearchRepository<Item,Long> {
    Item findItemById(Long id);
}
