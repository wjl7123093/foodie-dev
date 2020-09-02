package com.snow.controller.center;

import com.snow.pojo.Users;
import com.snow.pojo.bo.center.CenterUserBO;
import com.snow.service.center.CenterUserService;
import com.snow.utils.CookieUtils;
import com.snow.utils.IMOOCJSONResult;
import com.snow.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "center - 用户信息", tags = {"用户信息相关 api 接口"})
@RequestMapping("userInfo")
@RestController
public class CenterUserController {

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

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("/update")
    public IMOOCJSONResult update(
            @ApiParam(name = "userId", value = "用户 ID", required = true)
            @RequestParam String userId,
            @RequestBody @Valid CenterUserBO centerUserBO,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) {

        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return IMOOCJSONResult.errorMap(errorMap);
        }

        Users userRes = centerUserService.updateUserInfo(userId, centerUserBO);

        userRes = setNullProperty(userRes);
        // 加密保存 cookie
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userRes), true);

        // TODO 后续要改，增加令牌 token，会整合进 radis，分布式会话

        return IMOOCJSONResult.ok();
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            // 发生验证错误所对应的某一个属性
            String errorField = error.getField();
            // 验证错误的信息
            String errorMsg = error.getDefaultMessage();

            map.put(errorField, errorMsg);
        }
        return map;
    }

    // 设置 Users 中敏感信息为 null
    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

}
