package com.snow.controller;

import com.snow.pojo.UserAddress;
import com.snow.pojo.bo.AddressBO;
import com.snow.service.AddressService;
import com.snow.utils.IMOOCJSONResult;
import com.snow.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "收货地址", tags = {"收货地址相关 api 接口"})
@RequestMapping("address")
@RestController
public class AddressController {

    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作：
     * 1. 查询用户的所有收货地址列表
     * 2. 新增收货地址
     * 3. 删除收货地址
     * 4. 修改收货地址
     * 5. 设置默认地址
     */

    @Autowired
    public AddressService addressService;

    @ApiOperation(value = "根据用户 ID 查询所有收货地址列表", notes = "根据用户 ID 查询所有收货地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public IMOOCJSONResult list(
            @ApiParam(name = "userId", value = "用户 id", required = true)
            @RequestParam String userId) {

        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        List<UserAddress> list = addressService.queryAll(userId);

        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value = "新增收货地址", notes = "新增收货地址", httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(
            @ApiParam(name = "addressBO", value = "收货地址信息", required = true)
            @RequestBody AddressBO addressBO) {

        // 1. 校验用户输入地址信息
        IMOOCJSONResult checkRes = checkAddress(addressBO);
        if (!checkRes.isOK()) {
            return checkRes;
        }

        // 2. 新增地址
        addressService.addNewUserAddress(addressBO);

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "修改收货地址", notes = "修改收货地址", httpMethod = "POST")
    @PostMapping("/update")
    public IMOOCJSONResult update(
            @ApiParam(name = "addressBO", value = "收货地址信息", required = true)
            @RequestBody AddressBO addressBO) {

        // 1. addressId 判空
        if (StringUtils.isBlank(addressBO.getAddressId())) {
            return IMOOCJSONResult.errorMsg("修改地址错误：addressId 不能为空");
        }

        // 2. 校验用户输入地址信息
        IMOOCJSONResult checkRes = checkAddress(addressBO);
        if (!checkRes.isOK()) {
            return checkRes;
        }

        // 3. 修改地址
        addressService.updateUserAddress(addressBO);

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "删除收货地址", notes = "删除收货地址", httpMethod = "POST")
    @PostMapping("/delete")
    public IMOOCJSONResult delete(
            @ApiParam(name = "userId", value = "用户 id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "addressId", value = "收货地址 id", required = true)
            @RequestParam String addressId) {

        // 1. 判空
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        // 2. 删除地址
        addressService.deleteUserAddress(userId, addressId);

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "设置默认收货地址", notes = "设置默认收货地址", httpMethod = "POST")
    @PostMapping("/setDefalut")
    public IMOOCJSONResult setDefalut(
            @ApiParam(name = "userId", value = "用户 id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "addressId", value = "收货地址 id", required = true)
            @RequestParam String addressId) {

        // 1. 判空
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        // 2. 删除地址
        addressService.updateUserAddressToBeDefault(userId, addressId);

        return IMOOCJSONResult.ok();
    }

    // 收货地址信息校验
    private IMOOCJSONResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return IMOOCJSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return IMOOCJSONResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return IMOOCJSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return IMOOCJSONResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return IMOOCJSONResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return IMOOCJSONResult.errorMsg("收货地址信息不能为空");
        }

        return IMOOCJSONResult.ok();
    }

}
