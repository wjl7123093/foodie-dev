package com.snow.service.center;

import com.snow.pojo.OrderItems;
import com.snow.pojo.Orders;
import com.snow.pojo.bo.center.OrderItemsCommentBO;
import com.snow.pojo.vo.MyCommentVO;
import com.snow.utils.PagedGridResult;

import java.util.List;

/**
 * 个人中心 - 我的评论接口
 */
public interface MyCommentsService {

    /**
     * 根据订单 ID 查询等待评论的订单商品列表
     * @param orderId
     * @return
     */
    public List<OrderItems> queryPendingComments(String orderId);

    /**
     * 保存评论列表
     * @param userId
     * @param orderId
     * @param list
     */
    public void saveComments(String userId, String orderId,
                             List<OrderItemsCommentBO> list);

    /**
     * 查询我的评价
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
