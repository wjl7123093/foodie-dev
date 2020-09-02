package com.snow.controller;

import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

    // 前端 cookie 名
    public static final String FOODIE_SHOP_CART = "shopcart";

    public static final Integer COMMENT_PAGE_SIZE = 10;

    public static final Integer PAGE_SIZE = 20;

    // 支付中心回调 url
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    // 微信支付成功 -> 支付中心 -> 天天吃货平台
    //                        |-> 回调通知 url
    String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";

}
