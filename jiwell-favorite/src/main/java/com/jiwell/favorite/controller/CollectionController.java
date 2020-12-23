package com.jiwell.favorite.controller;

import com.jiwell.favorite.service.CollectionService;
import com.jiwell.favorite.pojo.Collection;
import com.jiwell.item.bo.SpuBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * @author li
 */
@RestController
@RequestMapping("collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    /**
     * 查询我的最愛
     * @param userId
     * @return
     */
    ///List<Brand> queryBrandByBrandIds(<ListLong> ids);
    @GetMapping("{userId}")
    public ResponseEntity<List<SpuBo>> queryMyCollectionSpuByUserId(@PathVariable("userId") Long userId){
        List<SpuBo> spuBoList = this.collectionService.queryMyCollectionSpuByUserId(userId);
        if (spuBoList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //return ResponseEntity.ok(spuBoList);
//        SpuBo spuBo = new SpuBo();
//        spuBo.setId((long) 1);
//        spuBo.setCid1((long) 1);
//        spuBo.setCid1((long)2);
//        spuBo.setCid1((long)3);
        return ResponseEntity.ok(spuBoList);
        //return ResponseEntity.ok(spuBo);
    }

//    @GetMapping("{userId}")
//    public ResponseEntity<List<Favorite>> queryFavoriteByUserId(@PathVariable("userId") Long userId){
//        List<Favorite> favoriteList = this.favoriteService.queryByUserId(userId);
//        if (favoriteList == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(favoriteList);
//    }

    /**
     * 保存我的最愛
     * @param collection
     * @return
     */
    @PostMapping
    public ResponseEntity<Boolean> saveCollection(@RequestBody Collection collection){
        Boolean result = this.collectionService.saveCollection(collection);
        if(result == null || !result){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    /**
//     * 修改我的最愛
//     * @param favorite
//     * @return
//     */
//    @PutMapping
//    public ResponseEntity<Void> updateFavorite(@RequestBody Favorite favorite){
//        this.favoriteService.updateFavorite(favorite);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteCollection(@RequestBody Collection collection){
        Boolean result = this.collectionService.deleteCollection(collection);
        if(result == null || !result){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("isCollection/{spuId}")
    public ResponseEntity<Boolean> queryCollectionIsExist(@PathVariable("spuId") Long spuId){
        Boolean result = this.collectionService.queryCollectionIsExist(spuId);
        if (result == null || !result) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //return ResponseEntity.ok(result);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
