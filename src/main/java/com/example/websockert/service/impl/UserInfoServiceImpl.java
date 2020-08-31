package com.example.websockert.service.impl;

import com.example.websockert.dao.UserInfoDao;
import com.example.websockert.model.po.UserInfo;
import com.example.websockert.model.vo.ResponseJson;
import com.example.websockert.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;
    

    @Override
    public ResponseJson getByUserId(String userId) {
        UserInfo userInfo = userInfoDao.getByUserId(userId);
        return new ResponseJson().success()
                .setData("userInfo", userInfo);
    }

}
