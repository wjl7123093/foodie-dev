package com.snow.controller;

import com.snow.pojo.Orders;
import com.snow.service.center.MyOrdersService;
import com.snow.utils.IMOOCJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
public class BaseController {

    // 前端 cookie 名
    public static final String FOODIE_SHOP_CART = "shopcart";

    public static final Integer COMMON_PAGE_SIZE = 10;

    public static final Integer PAGE_SIZE = 20;

    // 支付中心回调 url
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    // 微信支付成功 -> 支付中心 -> 天天吃货平台
    //                        |-> 回调通知 url
    String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";

    // 用户头像本地保存地址
    public static final String IMAGE_USER_FACE_LOCATION = File.separator + "workspaces" +
                                                            File.separator + "images" +
                                                            File.separator + "foodie-shop" +
                                                            File.separator + "faces";
//    public static final String IMAGE_USER_FACE_LOCATION = "/workspaces/images/foodie-shop/faces";


    @Autowired
    public MyOrdersService myOrdersService;

    /**
     * 用于验证用户和订单是否存在关联关系，避免非法用户调用
     * @param userId
     * @param orderId
     * @return
     */
    public IMOOCJSONResult checkUserOrder(String userId, String orderId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(orderId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        Orders myOrder = myOrdersService.queryMyOrder(userId, orderId);
        if (myOrder == null) {
            return IMOOCJSONResult.errorMsg("订单不存在！");
        }

        return IMOOCJSONResult.ok(myOrder);
    }

}
