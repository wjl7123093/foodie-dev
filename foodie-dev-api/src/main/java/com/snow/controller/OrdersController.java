package com.snow.controller;

import com.snow.enums.OrderStatusEnum;
import com.snow.pojo.OrderStatus;
import com.snow.pojo.bo.SubmitOrderBO;
import com.snow.pojo.vo.MerchantOrdersVO;
import com.snow.pojo.vo.OrderVO;
import com.snow.service.OrderService;
import com.snow.utils.CookieUtils;
import com.snow.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "订单", tags = {"订单相关 api 接口"})
@RequestMapping("orders")
@RestController
public class OrdersController extends BaseController {

    @Autowired
    public OrderService orderService;

    @Autowired
    public RestTemplate restTemplate;

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult list(
            @ApiParam(name = "submitOrderBO", value = "用户下单信息", required = true)
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request,
            HttpServletResponse response) {

        System.out.println(submitOrderBO.toString());

        // 1. 创建订单
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();

        // 2. 创建订单以后，移除购物车中已结算（已提交）的商品
        // TODO 整合 radis 之后，完善购物车中的已结算商品清除，并且同步到前端的 cookie
//        CookieUtils.setCookie(request, response, FOODIE_SHOP_CART, "");

        // 3. 向支付中心发送当前订单，用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);

        // 为了方便测试购买，所有的支付金额统一改为1分钱
        merchantOrdersVO.setAmount(1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");
        // 账号 & 密码是不正确的。这里我们需要咨询慕课网，但现在没法咨询。（因为课程不是通过官方渠道买的）
        // 所以订单会创建失败，并返回 "502 该账户授权访问日期已失效！"

        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);

        ResponseEntity<IMOOCJSONResult> responseEntity =
                restTemplate.postForEntity(paymentUrl,
                                            entity,
                                            IMOOCJSONResult.class);

        IMOOCJSONResult paymentRes = responseEntity.getBody();
        if (!paymentRes.isOK()) {
            System.out.println(paymentRes.getStatus() + paymentRes.getMsg());
            return IMOOCJSONResult.errorMsg("支付中心订单创建失败！请联系管理员！");
        }

        return IMOOCJSONResult.ok(orderId);
    }

    // 可作为 支付中心 returnUrl
    @ApiOperation(value = "通知订单已支付", notes = "通知订单已支付", httpMethod = "POST")
    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(
            @ApiParam(name = "merchantOrderId", value = "用户订单 ID", required = true)
            String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @ApiOperation(value = "获取支付订单状态", notes = "获取支付订单状态", httpMethod = "GET")
    @PostMapping("/getPaidOrderInfo")
    public IMOOCJSONResult getPaidOrderInfo(
            @ApiParam(name = "orderId", value = "用户订单 ID", required = true)
            String orderId) {
        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return IMOOCJSONResult.ok(orderStatus);
    }



















}
