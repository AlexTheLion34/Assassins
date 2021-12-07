package com.itmo.assassins.service.user;

import com.itmo.assassins.model.user.User;
import com.itmo.assassins.model.user.UserInfo;

public interface UserInfoService {
    UserInfo findByUser(User user);
}
