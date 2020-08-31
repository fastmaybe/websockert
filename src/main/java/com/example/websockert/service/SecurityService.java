package com.example.websockert.service;

import com.example.websockert.model.vo.ResponseJson;

import javax.servlet.http.HttpSession;

/**
 * @Author: liulang
 * @Date: 2020/8/26 17:10
 */
public interface SecurityService {

    ResponseJson login(String username, String password, HttpSession session);

    ResponseJson logout(HttpSession session);
}
