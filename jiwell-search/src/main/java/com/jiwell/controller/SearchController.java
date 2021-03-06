package com.jiwell.controller;

import com.jiwell.client.GoodsClient;
import com.jiwell.common.pojo.PageResult;
import com.jiwell.item.bo.SpuBo;
import com.jiwell.repository.GoodsRepository;
import com.jiwell.service.impl.SearchServiceImpl;
import com.jiwell.bo.SearchRequest;
import com.jiwell.pojo.Goods;
import com.jiwell.vo.SearchResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 98050
 * Time: 2018-10-12 20:21
 * Feature:
 */
@RestController
@RequestMapping
public class SearchController implements InitializingBean {

    @Autowired
    private SearchServiceImpl searchService;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    //@RequestParam(value = "key", required = false) String key,
    @PostMapping("page1")
    public ResponseEntity<PageResult<Goods>> search1(SearchRequest searchRequest ){
        SearchResult<Goods> result = this.searchService.search(searchRequest);
        if (result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("page")
    public ResponseEntity<PageResult<Goods>> search(@RequestBody SearchRequest searchRequest){
        SearchResult<Goods> result = this.searchService.search(searchRequest);
        if (result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return ResponseEntity.ok(result);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 创建索引
        this.elasticsearchTemplate.createIndex(Goods.class);
        // 配置映射
        this.elasticsearchTemplate.putMapping(Goods.class);

        //加载数据
        List<SpuBo> list = new ArrayList<>();
        int page = 1;
        int row = 100;
        int size;
        do {
            //分页查询数据
            PageResult<SpuBo> result = this.goodsClient.querySpuByPage(page, row, null, true, null, true,-1);
            List<SpuBo> spus = result.getItems();
            size = spus.size();
            page ++;
            list.addAll(spus);
        }while (size == 100);

        //创建Goods集合
        List<Goods> goodsList = new ArrayList<>();
        //遍历spu
        int spuCount = 0;
        for (SpuBo spu : list) {
            try {
                spuCount++;
                if(spuCount >= 180){
                    break;
                }
                Goods goods = this.searchService.buildGoods(spu);
                goodsList.add(goods);
                System.out.println("查询成功：" + spu.getId() +  "查询成功count：" + spuCount);
            } catch (IOException e) {
                System.out.println("查询失败：" + spu.getId());
            }
        }
        this.goodsRepository.saveAll(goodsList);
    }
}
