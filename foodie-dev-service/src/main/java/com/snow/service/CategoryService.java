package com.snow.service;

import com.snow.pojo.Carousel;
import com.snow.pojo.Category;
import com.snow.pojo.vo.CategoryVO;
import com.snow.pojo.vo.NewItemsVO;

import java.util.List;

/**
 * 分类接口
 */
public interface CategoryService {

    /**
     * 查询所有一级分类
     */
    public List<Category> queryAllRootLevelCat();

    /**
     * 根据一级分类 id 查询子分类信息
     * @param rootCatId
     * @return
     */
    public List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的 6 条最新商品数据
     * @param rootCatId
     * @return
     */
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);

}
