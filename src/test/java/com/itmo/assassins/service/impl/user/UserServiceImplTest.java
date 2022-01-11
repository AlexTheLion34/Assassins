package com.itmo.assassins.service.impl.user;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.model.user.Customer;
import com.itmo.assassins.model.user.Executor;
import com.itmo.assassins.model.user.UserRole;
import com.itmo.assassins.repository.user.UserRepository;
import com.itmo.assassins.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@Import(UserServiceImpl.class)
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void testFIndUserByUsername() {
        userService.findUserByUserName("username");
        Mockito.verify(userRepository, Mockito.times(1))
                .findByUsername("username");
    }

    @Test
    void testFindUserById() {
        userService.findUserById(1L);
        Mockito.verify(userRepository, Mockito.times(1))
                .getById(1L);
    }

    @Test
    void testFindExecutorsByBusy() {

        Executor executor1 = new Executor();
        executor1.setBusy(false);

        Executor executor2 = new Executor();
        executor2.setBusy(true);

        Mockito.when(userRepository.findByRole(UserRole.EXECUTOR))
                .thenReturn(new HashSet<>(Arrays.asList(executor1, executor2)));

        assertEquals(1, userService.findExecutorsByBusy().size());
    }

    @Test
    void testFindUserByRole() {
        userService.findUserByRole(any());
        Mockito.verify(userRepository, Mockito.times(1))
                .findByRole(any());
    }

    @Test
    void testSaveUser() {
        userService.saveUser(any());
        Mockito.verify(userRepository, Mockito.times(1))
                .save(any());
    }

    @Test
    void testCountRating() {

        Executor executor = new Executor();
        executor.setRating(0.0);
        executor.setNumOfCompletedRequests(0);

        userService.countRating(executor, 4);
        Mockito.verify(userRepository, Mockito.times(1))
                .save(executor);
    }

    @Test
    void testCountMaxAffordableRequestPrice() {

        Customer customer = new Customer();
        customer.setBalance(2000);

        Request request = new Request();
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setPrice(1000);
        request.setRequestInfo(requestInfo);

        customer.setRequests(Collections.singletonList(request));

        assertEquals(1000, userService.countMaxAffordablePrice(customer));
    }
}