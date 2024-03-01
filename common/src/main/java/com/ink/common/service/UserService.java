package com.ink.common.service;

import com.ink.common.model.User;

/**
 * 用户服务
 */
public interface UserService {
    /**
     * 获取用户
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 获取反转的用户名
     * @param user
     * @return
     */
    User getRevertUserName(User user);
}
