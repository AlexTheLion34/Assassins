package com.itmo.assassins.service.impl.user;

import com.itmo.assassins.service.user.UserSecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(UserSecurityServiceImpl.class)
class UserSecurityServiceImplTest {

    @Autowired
    private UserSecurityService securityService;

    @Test
    @WithMockUser(username = "username")
    void testGetLoggedInUsername() {
        assertEquals("username", securityService.getLoggedInUserName());
    }
}