package com.snow.service.impl.center;

import com.snow.mapper.UsersMapper;
import com.snow.pojo.Users;
import com.snow.pojo.bo.center.CenterUserBO;
import com.snow.service.center.CenterUserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CenterUserServiceImpl implements CenterUserService {

    @Autowired
    public UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        Users centerUser = usersMapper.selectByPrimaryKey(userId);
        centerUser.setPassword(null);
        return centerUser;
    }

    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {

        Users centerUser = new Users();
        BeanUtils.copyProperties(centerUserBO, centerUser);
        centerUser.setId(userId);
        centerUser.setUpdatedTime(new Date());

        usersMapper.updateByPrimaryKeySelective(centerUser);
        return queryUserInfo(userId);
    }
}
