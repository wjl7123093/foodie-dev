package com.snow.service.center;

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
}
