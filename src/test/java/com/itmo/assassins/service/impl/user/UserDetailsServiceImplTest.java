package com.itmo.assassins.service.impl.user;

import com.itmo.assassins.model.user.User;
import com.itmo.assassins.model.user.UserRole;
import com.itmo.assassins.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@Import(UserDetailsServiceImpl.class)
class UserDetailsServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    void testLoadUserByUserName() {

        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(UserRole.EXECUTOR);

        Mockito.when(userRepository.findByUsername("username"))
                        .thenReturn(user);

       assertEquals(new org.springframework.security.core.userdetails.User(
               "username",
               "password",
               new HashSet<>(Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())))),
               userDetailsService.loadUserByUsername("username"));
    }
}