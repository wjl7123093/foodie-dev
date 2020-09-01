package com.snow.controller;

import com.snow.pojo.Users;
import com.snow.pojo.bo.ShopcartBO;
import com.snow.pojo.bo.UserBO;
import com.snow.service.UserService;
import com.snow.utils.CookieUtils;
import com.snow.utils.IMOOCJSONResult;
import com.snow.utils.JsonUtils;
import com.snow.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 购物车 Controller
 */
@Api(value = "购物车", tags = {"购物车的相关接口"})
@RestController
@RequestMapping("shopcart")
public class ShopcartController {

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(
            @RequestParam String userId,
            @RequestBody ShopcartBO shopcartBO,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        System.out.println(shopcartBO);

        // TODO 前端用户在登录的情况下添加商品到购物车，会同时在后端同步购物车到 radis 缓存


        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "删除购物车中商品", notes = "删除购物车中商品", httpMethod = "POST")
    @PostMapping("/del")
    public IMOOCJSONResult del(
            @RequestParam String userId,
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        // TODO 前端用户删除购物车中的商品数据，如果此时用户已登录，则需要同时删除后端购物车数据库中的商品数据


        return IMOOCJSONResult.ok();
    }

}
