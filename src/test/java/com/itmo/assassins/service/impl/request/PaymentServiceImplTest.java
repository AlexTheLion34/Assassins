package com.itmo.assassins.service.impl.request;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.model.user.Customer;
import com.itmo.assassins.model.user.Executor;
import com.itmo.assassins.service.request.PaymentService;
import com.itmo.assassins.service.request.RequestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(PaymentServiceImpl.class)
class PaymentServiceImplTest {

    @MockBean
    private RequestService requestService;

    @Autowired
    private PaymentService paymentService;

    @Test
    void testProcessPayment() {

        Request request = new Request();

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setPrice(200);

        Customer customer = new Customer();
        customer.setBalance(300);

        Executor executor = new Executor();
        executor.setBalance(100);

        request.setExecutor(executor);
        request.setOwner(customer);

        request.setRequestInfo(requestInfo);

        paymentService.processPayment(request);

        Mockito.verify(requestService, Mockito.times(1)).saveRequest(request);
    }

    @Test
    void testConfirmPayment() {

        Request request = new Request();

        RequestInfo requestInfo = new RequestInfo();

        Executor executor = new Executor();

        request.setExecutor(executor);

        request.setRequestInfo(requestInfo);

        paymentService.confirmPayment(request);

        Mockito.verify(requestService, Mockito.times(1)).saveRequest(request);
    }
}