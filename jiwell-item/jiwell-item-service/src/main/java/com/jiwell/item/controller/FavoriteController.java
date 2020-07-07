package com.jiwell.item.controller;

import com.jiwell.item.service.FavoriteService;
import com.jiwell.item.pojo.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author li
 */
@RestController
@RequestMapping("favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    /**
     * 查询我的最愛
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Favorite>> queryFavoriteByUserId(@PathVariable("userId") Long userId){
        List<Favorite> favoriteList = this.favoriteService.queryByUserId(userId);
        if (favoriteList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(favoriteList);
    }

    /**
     * 保存我的最愛
     * @param favorite
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveFavorite(@RequestBody Favorite favorite){
        this.favoriteService.saveFavorite(favorite);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 修改我的最愛
     * @param favorite
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateFavorite(@RequestBody Favorite favorite){
        this.favoriteService.updateFavorite(favorite);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable("id") Long id){
        Favorite favorite = new Favorite();
        favorite.setId(id);
        this.favoriteService.deleteFavorite(favorite);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
