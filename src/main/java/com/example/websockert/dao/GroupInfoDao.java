package com.example.websockert.dao;

import com.example.websockert.model.po.GroupInfo;

/**
 * @Author: liulang
 * @Date: 2020/8/26 14:02
 */
public interface GroupInfoDao {


    void loadGroupInfo();

    GroupInfo getByGroupId(String groupId);
}
