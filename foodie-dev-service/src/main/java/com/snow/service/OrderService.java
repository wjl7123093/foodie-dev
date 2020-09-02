package com.snow.service;

import com.snow.pojo.Carousel;
import com.snow.pojo.OrderStatus;
import com.snow.pojo.bo.SubmitOrderBO;
import com.snow.pojo.vo.OrderVO;

import java.util.List;

/**
 * 订单相关接口
 */
public interface OrderService {

    /**
     * 创建订单
     * @param submitOrderBO
     */
    public OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    public void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态信息
     * @param orderId
     * @return
     */
    public OrderStatus queryOrderStatusInfo(String orderId);

}
