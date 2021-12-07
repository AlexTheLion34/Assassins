package com.itmo.assassins.service.impl.user;

import com.itmo.assassins.model.user.User;
import com.itmo.assassins.model.user.UserInfo;
import com.itmo.assassins.repository.user.UserInfoRepository;
import com.itmo.assassins.service.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserInfo findByUser(User user) {
        return userInfoRepository.findByUser(user);
    }
}
