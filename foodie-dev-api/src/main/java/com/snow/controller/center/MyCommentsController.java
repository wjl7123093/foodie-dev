package com.snow.controller.center;

import com.snow.controller.BaseController;
import com.snow.enums.YesOrNo;
import com.snow.pojo.OrderItems;
import com.snow.pojo.Orders;
import com.snow.pojo.bo.center.OrderItemsCommentBO;
import com.snow.service.center.MyCommentsService;
import com.snow.utils.IMOOCJSONResult;
import com.snow.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "用户中心评论模块", tags = {"用户中心评论模块 api 接口"})
@RequestMapping("mycomments")
@RestController
public class MyCommentsController extends BaseController {

    @Autowired
    public MyCommentsService myCommentsService;

    @ApiOperation(value = "查询待评论订单商品列表", notes = "查询待评论订单商品列表", httpMethod = "POST")
    @PostMapping("/pending")
    public IMOOCJSONResult pending(
            @ApiParam(name = "userId", value = "用户 ID", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单 ID", required = true)
            @RequestParam String orderId) {

        // 判断用户和订单是否关联
        IMOOCJSONResult checkRes = checkUserOrder(userId, orderId);
        if (!checkRes.isOK()) {
            return checkUserOrder(userId, orderId);
        }
        // 判断该订单是否已评论
        Orders commentOrder = (Orders)checkRes.getData();
        if (commentOrder.getIsComment() == YesOrNo.YES.type) {
            return IMOOCJSONResult.errorMsg("该订单已评论！");
        }

        List<OrderItems> list = myCommentsService.queryPendingComments(orderId);

        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value = "保存评论列表", notes = "保存评论列表", httpMethod = "POST")
    @PostMapping("/saveList")
    public IMOOCJSONResult saveList(
            @ApiParam(name = "userId", value = "用户 ID", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单 ID", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "commentList", value = "订单商品评论列表", required = true)
            @RequestBody List<OrderItemsCommentBO> commentList) {

        // 判断用户和订单是否关联
        IMOOCJSONResult checkRes = checkUserOrder(userId, orderId);
        if (!checkRes.isOK()) {
            return checkUserOrder(userId, orderId);
        }
        // 判断评论内容 list 不能为空
        if (commentList == null || commentList.isEmpty() || commentList.size() == 0) {
            return IMOOCJSONResult.errorMsg("评论内容不能为空！");
        }

        myCommentsService.saveComments(userId, orderId, commentList);

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    @PostMapping("/query")
    public IMOOCJSONResult query(
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

        PagedGridResult grid = myCommentsService.queryMyComments(userId,
                                                                    page,
                                                                    pageSize);

        return IMOOCJSONResult.ok(grid);
    }

}
