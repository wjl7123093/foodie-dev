package com.snow.service.center;

import com.snow.pojo.Orders;
import com.snow.pojo.Users;
import com.snow.pojo.bo.center.CenterUserBO;
import com.snow.utils.PagedGridResult;

/**
 * 个人中心 - 我的订单接口
 */
public interface MyOrdersService {

    /**
     * 查询我的订单列表
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryMyOrders(String userId,
                                         Integer orderStatus,
                                         Integer page,
                                         Integer pageSize);

    /**
     * 更新订单状态 -> 商家发货（模拟）
     * @param orderId
     */
    public void updateDeliverOrderStatus(String orderId);

    /**
     * 查询我的订单
     * @param userId
     * @param orderId
     * @return
     */
    public Orders queryMyOrder(String userId, String orderId);

    /**
     * 更新订单状态 -> 确认收货
     * @param orderId
     */
    public boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单（逻辑删除）
     * @param userId
     * @param orderId
     */
    public boolean deleteOrder(String userId, String orderId);
}
