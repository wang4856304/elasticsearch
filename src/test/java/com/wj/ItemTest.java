package com.wj;

import com.alibaba.fastjson.JSONObject;
import com.wj.dao.ItemRepository;
import com.wj.entity.Item;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ESApplication.class)
public class ItemTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testCreateItem() {
        elasticsearchTemplate.createIndex(Item.class);
    }

    @Test
    public void saveItem() {
        Item item = new Item();
        item.setId(2L);
        item.setBrand("小米手机7");
        item.setCategory("手机");
        item.setTitle("小米");
        item.setPrice(3499.00);
        item.setImages("http://image.baidu.com/13123.jpg");

        itemRepository.save(item);
    }

    @Test
    public void saveItemList() {
        Item item = new Item();
        item.setId(5L);
        item.setBrand("华为");
        item.setCategory("手机");
        item.setTitle("华为mate");
        item.setPrice(3899.00);
        item.setImages("http://image.baidu.com/13123.jpg");

        Item item1 = new Item();
        item1.setId(4L);
        item1.setBrand("华为");
        item1.setCategory("手机");
        item1.setTitle("华为荣耀");
        item1.setPrice(3499.00);
        item1.setImages("http://image.baidu.com/13123.jpg");

        List<Item> list = new ArrayList<>();
        list.add(item);
        list.add(item1);

        itemRepository.saveAll(list);
    }

    @Test
    public void findItemById() {
        Item item = itemRepository.findItemById(1L);
        System.out.println(JSONObject.toJSONString(item));
    }


    /**
     * 基本查询
     */
    @Test
    public void testMatchQuery() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.matchQuery("title", "荣"));
        Page<Item> items = itemRepository.search(builder.build());
        long total = items.getTotalElements();
        System.out.println("total=" + total);
        for (Item item: items) {
            System.out.println(JSONObject.toJSONString(item));
        }
    }

    /**
     * 分页查询
     */
    @Test
    public void testQueryByPage() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.matchQuery("title", "荣"));
        int pageNo = 0;
        int pageSize = 10;
        builder.withPageable(PageRequest.of(pageNo, pageSize));
        Page<Item> items = itemRepository.search(builder.build());
        long total = items.getTotalElements();
        System.out.println("total=" + total);
        for (Item item: items) {
            System.out.println(JSONObject.toJSONString(item));
        }
    }

}
