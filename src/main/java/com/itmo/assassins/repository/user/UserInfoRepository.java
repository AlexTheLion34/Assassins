package com.itmo.assassins.repository.user;

import com.itmo.assassins.model.user.User;
import com.itmo.assassins.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByUser(User user);
}
