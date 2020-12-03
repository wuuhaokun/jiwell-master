package com.jiwell.favorite.controller;

import com.jiwell.favorite.service.FavoriteService;
import com.jiwell.favorite.pojo.Favorite;
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
//@RequestMapping("myFavorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    /**
     * 查询我的最愛
     * @param userId
     * @return
     */
    ///List<Brand> queryBrandByBrandIds(<ListLong> ids);
    @GetMapping("{userId}")
    public ResponseEntity<List<SpuBo>> queryMyFavoriteSpuByUserId(@PathVariable("userId") Long userId){
    //public ResponseEntity<SpuBo> queryMyFavoriteSpuByUserId(@PathVariable("userId") Long userId){
        List<SpuBo> spuBoList = this.favoriteService.queryMyFavoriteSpuByUserId(userId);
        if (spuBoList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(spuBoList);
//        SpuBo spuBo = new SpuBo();
//        spuBo.setId((long) 1);
//        spuBo.setCid1((long) 1);
//        spuBo.setCid2((long)2);
//        spuBo.setCid3((long)3);
//        return ResponseEntity.ok(spuBo);
        //return ResponseEntity.ok(spuBoList);

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
     * @param favorite
     * @return
     */
    @PostMapping
    public ResponseEntity<Boolean> saveFavorite(@RequestBody Favorite favorite){
        Boolean result = this.favoriteService.saveFavorite(favorite);
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
    public ResponseEntity<Boolean> deleteFavorite(@RequestBody Favorite favorite){
        Boolean result = this.favoriteService.deleteFavorite(favorite);
        if(result == null || !result){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }


//    @GetMapping("isFavorite")
    @GetMapping("isFavorite/{spuId}")
    public ResponseEntity<Boolean> queryFavoriteIsExist(@PathVariable("spuId") Long spuId){
        Boolean result = this.favoriteService.queryFavoriteIsExist(spuId);
        if (result == null || !result) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //return ResponseEntity.ok(result);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
