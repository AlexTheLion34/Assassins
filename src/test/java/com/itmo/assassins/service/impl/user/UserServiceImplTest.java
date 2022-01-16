package com.itmo.assassins.service.impl.user;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.model.request.RequestStatus;
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
        requestInfo.setStatus(RequestStatus.PACKING_1);
        request.setRequestInfo(requestInfo);

        customer.setRequests(Collections.singletonList(request));

        assertEquals(1000, userService.countMaxAffordablePrice(customer));
    }

    @Test
    void testCountMaxAffordableRequestPriceWhenOneOfRequestsIsDone() {

        Customer customer = new Customer();
        customer.setBalance(2000);

        Request request1 = new Request();
        RequestInfo requestInfo1 = new RequestInfo();
        requestInfo1.setPrice(1000);
        requestInfo1.setStatus(RequestStatus.CONFIRMING);
        request1.setRequestInfo(requestInfo1);

        Request request2 = new Request();
        RequestInfo requestInfo2 = new RequestInfo();
        requestInfo2.setPrice(1000);
        requestInfo2.setStatus(RequestStatus.DONE);
        request2.setRequestInfo(requestInfo2);

        customer.setRequests(Arrays.asList(request1, request2));

        assertEquals(1000, userService.countMaxAffordablePrice(customer));
    }

    @Test
    void testCountMaxAffordableRequestPriceBalanceIsZero() {

        Customer customer = new Customer();
        customer.setBalance(0);

        assertEquals(0, userService.countMaxAffordablePrice(customer));
    }

    @Test
    void testCountMaxAffordableRequestPriceWhenSumOfCurrentRequestGreaterThanBalance() {

        Customer customer = new Customer();
        customer.setBalance(200);

        Request request1 = new Request();
        RequestInfo requestInfo1 = new RequestInfo();
        requestInfo1.setPrice(1000);
        requestInfo1.setStatus(RequestStatus.CONFIRMING);
        request1.setRequestInfo(requestInfo1);

        Request request2 = new Request();
        RequestInfo requestInfo2 = new RequestInfo();
        requestInfo2.setPrice(1000);
        requestInfo2.setStatus(RequestStatus.DONE);
        request2.setRequestInfo(requestInfo2);

        customer.setRequests(Arrays.asList(request1, request2));

        assertEquals(200, userService.countMaxAffordablePrice(customer));
    }

    @Test
    void testCountMaxAffordableRequestPriceWhenSumOfCurrentRequestEqualToBalance() {

        Customer customer = new Customer();
        customer.setBalance(200);

        Request request1 = new Request();
        RequestInfo requestInfo1 = new RequestInfo();
        requestInfo1.setPrice(200);
        requestInfo1.setStatus(RequestStatus.CONFIRMING);
        request1.setRequestInfo(requestInfo1);

        Request request2 = new Request();
        RequestInfo requestInfo2 = new RequestInfo();
        requestInfo2.setPrice(1000);
        requestInfo2.setStatus(RequestStatus.DONE);
        request2.setRequestInfo(requestInfo2);

        customer.setRequests(Arrays.asList(request1, request2));

        assertEquals(200, userService.countMaxAffordablePrice(customer));
    }

    @Test
    void testCountMaxAffordableRequestPriceWhenSumOfCurrentRequestLessThanBalance() {

        Customer customer = new Customer();
        customer.setBalance(2000);

        Request request1 = new Request();
        RequestInfo requestInfo1 = new RequestInfo();
        requestInfo1.setPrice(1800);
        requestInfo1.setStatus(RequestStatus.CONFIRMING);
        request1.setRequestInfo(requestInfo1);

        Request request2 = new Request();
        RequestInfo requestInfo2 = new RequestInfo();
        requestInfo2.setPrice(1000);
        requestInfo2.setStatus(RequestStatus.DONE);
        request2.setRequestInfo(requestInfo2);

        customer.setRequests(Arrays.asList(request1, request2));

        assertEquals(200, userService.countMaxAffordablePrice(customer));
    }
}