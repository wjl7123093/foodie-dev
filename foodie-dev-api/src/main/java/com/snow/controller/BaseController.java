package com.snow.controller;

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

}
