package com.snow.service;

import com.snow.pojo.Carousel;
import com.snow.pojo.bo.SubmitOrderBO;

import java.util.List;

/**
 * 订单相关接口
 */
public interface OrderService {

    /**
     * 创建订单
     * @param submitOrderBO
     */
    public String createOrder(SubmitOrderBO submitOrderBO);

}
