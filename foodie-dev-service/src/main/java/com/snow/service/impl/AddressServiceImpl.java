package com.snow.service.impl;

import com.snow.enums.YesOrNo;
import com.snow.mapper.UserAddressMapper;
import com.snow.pojo.UserAddress;
import com.snow.pojo.bo.AddressBO;
import com.snow.service.AddressService;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    public UserAddressMapper userAddressMapper;
    @Autowired
    public Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {

        Example addressExp = new Example(UserAddress.class);
        Example.Criteria criteria = addressExp.createCriteria();
        criteria.andEqualTo("userId", userId);

        return userAddressMapper.selectByExample(addressExp);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {

        // 1. 判断当前用户是否存在地址，如果没有，则新增为默认地址
        Integer isDefault = 0;
        List<UserAddress> addressList = this.queryAll(addressBO.getUserId());
        if (addressList == null || addressList.isEmpty() || addressList.size() == 0) {
            isDefault = 1;
        }

        // 2. 保存地址到数据库
        UserAddress newAddress = new UserAddress();
        // 属性拷贝
        BeanUtils.copyProperties(addressBO, newAddress);

        String addressId = sid.nextShort();
        newAddress.setId(addressId);
        newAddress.setIsDefault(isDefault);
        newAddress.setCreatedTime(new Date());
        newAddress.setUpdatedTime(new Date());

        userAddressMapper.insert(newAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBO addressBO) {

        UserAddress pendingAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, pendingAddress);

        pendingAddress.setId(addressBO.getAddressId());
        pendingAddress.setUpdatedTime(new Date());

        userAddressMapper.updateByPrimaryKeySelective(pendingAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {

        // 1. 判空
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return;
        }

        UserAddress delAddress = new UserAddress();
        delAddress.setId(addressId);
        delAddress.setUserId(userId);

        userAddressMapper.delete(delAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {

        // 1. 查找默认地址，设置为不默认
        List<UserAddress> addressList = this.queryAll(userId);
        for (UserAddress userAddress : addressList) {
            if (userAddress.getIsDefault() == YesOrNo.YES.type) {
                userAddress.setIsDefault(YesOrNo.NO.type);
                userAddressMapper.updateByPrimaryKeySelective(userAddress);
                break;
            }
        }

        // 2. 更新当前地址为默认地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setUserId(userId);
        defaultAddress.setId(addressId);
        defaultAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {

        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);

        return userAddressMapper.selectOne(userAddress);
    }
}
