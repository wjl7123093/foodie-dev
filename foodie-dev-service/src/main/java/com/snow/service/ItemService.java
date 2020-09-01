package com.snow.service;

import com.snow.pojo.*;
import com.snow.pojo.vo.CommentLevelCountsVO;
import com.snow.pojo.vo.ItemCommentVO;
import com.snow.pojo.vo.ShopcartVO;
import com.snow.utils.PagedGridResult;

import java.util.List;

/**
 * 商品接口
 */
public interface ItemService {

    /**
     * 根据商品 ID 查询详情
     * @param itemId
     * @return
     */
    public Items queryItemById(String itemId);

    /**
     * 根据商品 ID 查询商品图片
     * @param itemId
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品 ID 查询商品规格
     * @param itemId
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品 ID 查询商品参数
     * @param itemId
     * @return
     */
    public ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品 ID 查询商品的评价等级数量
     * @param itemId
     */
    public CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品 ID 查询商品评价（分页）
     * @param itemId
     * @param level
     * @return
     */
    public PagedGridResult queryPagedComments(String itemId, Integer level,
                                              Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     * @param keywords  搜索关键字
     * @param sort  排序方式
     *                  ’c' 销量排序
     *                  ‘p‘ 价格排序
     *                  'k' 默认排序
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searchItems(String keywords, String sort,
                                              Integer page, Integer pageSize);

    /**
     * 根据分类 ID 搜索商品列表
     * @param catId  分类 ID
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searchItems(Integer catId, String sort,
                                       Integer page, Integer pageSize);

    /**
     * 根据 ids 查询最新的购物车中的商品数据（用于刷新渲染购物车中的商品数据）
     * @param specIds
     * @return
     */
    public List<ShopcartVO> queryItemsBySpecIds(String specIds);

}
