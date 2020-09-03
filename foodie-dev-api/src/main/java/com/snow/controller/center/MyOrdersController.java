package com.snow.controller.center;

import com.snow.controller.BaseController;
import com.snow.pojo.Orders;
import com.snow.pojo.Users;
import com.snow.pojo.bo.center.CenterUserBO;
import com.snow.pojo.vo.OrderStatusCountsVO;
import com.snow.resource.FileUpload;
import com.snow.service.center.CenterUserService;
import com.snow.service.center.MyOrdersService;
import com.snow.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "用户中心我的订单", tags = {"用户中心我的订单 api 接口"})
@RequestMapping("myorders")
@RestController
public class MyOrdersController extends BaseController {

    @Autowired
    public MyOrdersService myOrdersService;

    @Autowired
    public FileUpload fileUpload;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/query")
    public IMOOCJSONResult query(
            @ApiParam(name = "userId", value = "用户 ID", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单状态", required = true)
            @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = myOrdersService.queryMyOrders(userId,
                                                             orderStatus,
                                                             page,
                                                             pageSize);

        return IMOOCJSONResult.ok(grid);
    }

    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "POST")
    @PostMapping("/deliver")
    public IMOOCJSONResult deliver(
            @ApiParam(name = "orderId", value = "订单 ID", required = true)
            @RequestParam String orderId) {

        if (StringUtils.isBlank(orderId)) {
            return IMOOCJSONResult.errorMsg("订单 ID 不能为空");
        }

        myOrdersService.updateDeliverOrderStatus(orderId);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "确认收货", notes = "确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public IMOOCJSONResult confirmReceive(
            @ApiParam(name = "orderId", value = "订单 ID", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户 ID", required = true)
            @RequestParam String userId) {

        IMOOCJSONResult checkRes = checkUserOrder(userId, orderId);
        if (!checkRes.isOK()) {
            return checkUserOrder(userId, orderId);
        }

        boolean updateRes = myOrdersService.updateReceiveOrderStatus(orderId);
        if (!updateRes) {
            return IMOOCJSONResult.errorMsg("订单确认收货失败！");
        }

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "删除订单", notes = "删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public IMOOCJSONResult delete(
            @ApiParam(name = "orderId", value = "订单 ID", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户 ID", required = true)
            @RequestParam String userId) {

        IMOOCJSONResult checkRes = checkUserOrder(userId, orderId);
        if (!checkRes.isOK()) {
            return checkUserOrder(userId, orderId);
        }

        boolean deleteRes = myOrdersService.deleteOrder(userId, orderId);
        if (!deleteRes) {
            return IMOOCJSONResult.errorMsg("订单删除失败！");
        }

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "查询用户订单状态数概况", notes = "查询用户订单状态数概况", httpMethod = "POST")
    @PostMapping("/statusCounts")
    public IMOOCJSONResult statusCounts(
            @ApiParam(name = "userId", value = "用户 ID", required = true)
            @RequestParam String userId) {

        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        OrderStatusCountsVO vo = myOrdersService.getOrderStatusCounts(userId);

        return IMOOCJSONResult.ok(vo);
    }

    @ApiOperation(value = "查询订单动向", notes = "查询订单动向", httpMethod = "POST")
    @PostMapping("/trend")
    public IMOOCJSONResult trend(
            @ApiParam(name = "userId", value = "用户 ID", required = true)
            @RequestParam String userId,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = myOrdersService.getOrdersTrend(userId,
                                                                page,
                                                                pageSize);

        return IMOOCJSONResult.ok(grid);
    }

}
