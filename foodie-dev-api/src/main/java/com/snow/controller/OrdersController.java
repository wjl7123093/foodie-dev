package com.snow.controller;

import com.snow.pojo.bo.SubmitOrderBO;
import com.snow.service.OrderService;
import com.snow.utils.CookieUtils;
import com.snow.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "订单", tags = {"订单相关 api 接口"})
@RequestMapping("orders")
@RestController
public class OrdersController extends BaseController {

    @Autowired
    public OrderService orderService;

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult list(
            @ApiParam(name = "submitOrderBO", value = "用户下单信息", required = true)
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request,
            HttpServletResponse response) {

        System.out.println(submitOrderBO.toString());

        // 1. 创建订单
        String orderId = orderService.createOrder(submitOrderBO);

        // 2. 创建订单以后，移除购物车中已结算（已提交）的商品
        // TODO 整合 radis 之后，完善购物车中的已结算商品清除，并且同步到前端的 cookie
//        CookieUtils.setCookie(request, response, FOODIE_SHOP_CART, "");

        // 3. 向支付中心发送当前订单，用于保存支付中心的订单数据

        return IMOOCJSONResult.ok(orderId);
    }

}
