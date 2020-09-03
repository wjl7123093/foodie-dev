package com.snow.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.snow.enums.OrderStatusEnum;
import com.snow.enums.YesOrNo;
import com.snow.mapper.OrderStatusMapper;
import com.snow.mapper.OrdersMapper;
import com.snow.mapper.OrdersMapperCustom;
import com.snow.pojo.OrderStatus;
import com.snow.pojo.Orders;
import com.snow.pojo.vo.MyOrdersVO;
import com.snow.pojo.vo.OrderStatusCountsVO;
import com.snow.service.center.MyOrdersService;
import com.snow.service.impl.BaseServiceImpl;
import com.snow.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyOrdersServiceImpl extends BaseServiceImpl implements MyOrdersService  {

    @Autowired
    public OrdersMapperCustom ordersMapperCustom;

    @Autowired
    public OrdersMapper ordersMapper;

    @Autowired
    public OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("userId", userId);
        if (orderStatus != null) {
            paramsMap.put("orderStatus", orderStatus);
        }

        PageHelper.startPage(page, pageSize);

        List<MyOrdersVO> list = ordersMapperCustom.queryMyOrders(paramsMap);

        return setterPagedGrid(list, page);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateDeliverOrderStatus(String orderId) {

        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());

        Example orderStatusExp = new Example(OrderStatus.class);
        Example.Criteria criteria = orderStatusExp.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);

        orderStatusMapper.updateByExampleSelective(updateOrder, orderStatusExp);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Orders queryMyOrder(String userId, String orderId) {

        Orders queryOrder = new Orders();
        queryOrder.setUserId(userId);
        queryOrder.setId(orderId);
        queryOrder.setIsDelete(YesOrNo.NO.type);

        return ordersMapper.selectOne(queryOrder);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {

        // 更新的状态信息
        OrderStatus updateStatus = new OrderStatus();
        updateStatus.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        updateStatus.setSuccessTime(new Date());

        // 查询条件
        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);

        int result = orderStatusMapper.updateByExampleSelective(updateStatus, example);

        return result == 1 ? true : false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteOrder(String userId, String orderId) {

        Orders delOrder = new Orders();
        delOrder.setIsDelete(YesOrNo.YES.type);
        delOrder.setUpdatedTime(new Date());

        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("id", orderId);

        int result = ordersMapper.updateByExampleSelective(delOrder, example);

        return result == 1 ? true : false;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("userId", userId);

        paramsMap.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
        int waitPayCounts = ordersMapperCustom.getMyOrderStatusCounts(paramsMap);

        paramsMap.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts = ordersMapperCustom.getMyOrderStatusCounts(paramsMap);

        paramsMap.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCounts = ordersMapperCustom.getMyOrderStatusCounts(paramsMap);

        paramsMap.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        paramsMap.put("isComment", YesOrNo.NO.type);
        int waitCommentCounts = ordersMapperCustom.getMyOrderStatusCounts(paramsMap);

        OrderStatusCountsVO vo = new OrderStatusCountsVO(waitPayCounts,
                                                         waitDeliverCounts,
                                                         waitReceiveCounts,
                                                         waitCommentCounts);

        return vo;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("userId", userId);

        PageHelper.startPage(page, pageSize);

        List<OrderStatus> list = ordersMapperCustom.getMyOrderTrend(paramsMap);

        return setterPagedGrid(list, page);
    }

}
