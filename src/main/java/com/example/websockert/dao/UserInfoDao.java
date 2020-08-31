package com.example.websockert.dao;

import com.example.websockert.model.po.UserInfo;

/**
 * @Author: liulang
 * @Date: 2020/8/26 14:02
 */
public interface UserInfoDao {


    void loadUserInfo();

    UserInfo getByUsername(String username);

    UserInfo getByUserId(String userId);
}
