package com.example.websockert.service;

import com.example.websockert.model.vo.ResponseJson;

/**
 * @Author: liulang
 * @Date: 2020/8/26 17:14
 */
public interface UserInfoService {

    ResponseJson getByUserId(String userId);
}
