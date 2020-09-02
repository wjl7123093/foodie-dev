package com.snow.service.center;

import com.snow.pojo.Users;
import com.snow.pojo.bo.center.CenterUserBO;

/**
 * 用户中心接口
 */
public interface CenterUserService {

    /**
     * 根据用户 ID 查询用户信息
     *
     * @param userId
     * @return
     */
    public Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     *
     * @param userId
     * @param centerUserBO
     */
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    /**
     * 更新用户头像
     *
     * @param userId
     * @param faceUrl
     * @return
     */
    public Users updateUserFace(String userId, String faceUrl);
}
