package com.ink.provider.service;

import cn.hutool.core.util.StrUtil;
import com.ink.common.model.User;
import com.ink.common.service.UserService;

/**
 * 用户服务的实现类
 */
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        System.out.println("用户名为:"+user.getName());
        return user;
    }

    @Override
    public User getRevertUserName(User user) {
        String name = user.getName();
        String reverseName = StrUtil.reverse(name);
        return new User(reverseName);
    }

}
