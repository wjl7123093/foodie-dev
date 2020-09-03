package com.snow.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.snow.enums.OrderStatusEnum;
import com.snow.enums.YesOrNo;
import com.snow.mapper.*;
import com.snow.pojo.OrderItems;
import com.snow.pojo.OrderStatus;
import com.snow.pojo.Orders;
import com.snow.pojo.bo.center.OrderItemsCommentBO;
import com.snow.pojo.vo.MyCommentVO;
import com.snow.pojo.vo.MyOrdersVO;
import com.snow.service.center.MyCommentsService;
import com.snow.service.center.MyOrdersService;
import com.snow.service.impl.BaseServiceImpl;
import com.snow.utils.PagedGridResult;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
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
public class MyCommentsServiceImpl extends BaseServiceImpl implements MyCommentsService {

    @Autowired
    public ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    @Autowired
    public OrdersMapper ordersMapper;

    @Autowired
    public OrderItemsMapper orderItemsMapper;

    @Autowired
    public OrderStatusMapper orderStatusMapper;

    @Autowired
    public Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComments(String orderId) {

        OrderItems orderItems = new OrderItems();
        orderItems.setOrderId(orderId);

        return orderItemsMapper.select(orderItems);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String userId, String orderId, List<OrderItemsCommentBO> list) {

        // 1. 保存评价 item_comments
        for (OrderItemsCommentBO oic : list) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("userId", userId);
        queryMap.put("commentList", list);
        itemsCommentsMapperCustom.saveComments(queryMap);

        // 2. 修改订单表 -> 已评价 orders
        Orders commentOrder = new Orders();
        commentOrder.setId(orderId);
        commentOrder.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(commentOrder);

        // 3. 修改订单状态表的留言时间 order_status
        OrderStatus commentStatus = new OrderStatus();
        commentStatus.setOrderId(orderId);
        commentStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(commentStatus);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("userId", userId);

        PageHelper.startPage(page, pageSize);

        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(queryMap);

        return setterPagedGrid(list, page);
    }


}
