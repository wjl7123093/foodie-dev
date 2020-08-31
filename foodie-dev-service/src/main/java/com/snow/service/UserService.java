package com.snow.service;

import com.snow.pojo.Users;
import com.snow.pojo.bo.UserBO;

/**
 * 用户接口
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return true 存在，false 不存在
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     */
    public Users createUser(UserBO userBO);

    /**
     * 检索用户名和密码是否匹配，用于登录
     */
    public Users queryUserForLogin(String username, String password);
}
