package com.snow.controller.center;

import com.snow.pojo.Users;
import com.snow.service.center.CenterUserService;
import com.snow.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "center - 用户中心", tags = {"用户中心相关 api 接口"})
@RequestMapping("center")
@RestController
public class CenterController {

    @Autowired
    public CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    @GetMapping("/userInfo")
    public IMOOCJSONResult userInfo(
            @ApiParam(name = "userId", value = "用户 ID", required = true)
            @RequestParam String userId) {

        Users centerUser = centerUserService.queryUserInfo(userId);
        return IMOOCJSONResult.ok(centerUser);
    }

}
