package com.jiwell.item.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tb_brand")
/**
 * @author:li
 *
 */
public class Brand implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 品牌名称
     */
    private String name;
    /**
     * 品牌图片
     */
    private String image;
    private Character letter;
    private String title;
    private String incategory;
    private Boolean newbrand;
    private Boolean hotbrand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIncategory() { return incategory; }

    public void setIncategory(String incategory) {
        this.incategory = incategory;
    }

    public Boolean getNewbrand() {
        return newbrand;
    }

    public void setNewbrand(Boolean newbrand) {
        this.newbrand = newbrand;
    }

    public Boolean getHotbrand() {
        return hotbrand;
    }

    public void setHotbrand(Boolean hotbrand) {
        this.hotbrand = hotbrand;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", letter=" + letter +'\'' +
                ", title=" + title +'\'' +
                ", incategory=" + incategory +
                ", new_brand=" + newbrand +
                ", hot_brand=" + hotbrand +
                '}';
    }
}
