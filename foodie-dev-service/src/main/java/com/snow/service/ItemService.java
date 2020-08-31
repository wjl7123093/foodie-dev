package com.snow.service;

import com.snow.pojo.*;
import com.snow.pojo.vo.CommentLevelCountsVO;
import com.snow.pojo.vo.ItemCommentVO;
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

}
