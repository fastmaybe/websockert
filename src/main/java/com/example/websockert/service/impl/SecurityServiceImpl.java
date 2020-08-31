package com.example.websockert.service.impl;

import com.example.websockert.dao.UserInfoDao;
import com.example.websockert.model.po.UserInfo;
import com.example.websockert.model.vo.ResponseJson;
import com.example.websockert.service.SecurityService;
import com.example.websockert.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.MessageFormat;

/**
 * @Author: liulang
 * @Date: 2020/8/26 17:10
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserInfoDao userInfoDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public ResponseJson login(String username, String password, HttpSession session) {
        UserInfo userInfo = userInfoDao.getByUsername(username);
        if (userInfo == null) {
            return new ResponseJson().error("不存在该用户名");
        }
        if (!userInfo.getPassword().equals(password)) {
            return new ResponseJson().error("密码不正确");
        }
        session.setAttribute(Constant.USER_TOKEN, userInfo.getUserId());
        return new ResponseJson().success();
    }

    @Override
    public ResponseJson logout(HttpSession session) {
        Object userId = session.getAttribute(Constant.USER_TOKEN);
        if (userId == null) {
            return new ResponseJson().error("请先登录！");
        }
        session.removeAttribute(Constant.USER_TOKEN);
        LOGGER.info(MessageFormat.format("userId为 {0} 的用户已注销登录!", userId));
        return new ResponseJson().success();
    }
}
