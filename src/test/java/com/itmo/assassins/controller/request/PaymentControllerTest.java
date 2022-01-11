package com.itmo.assassins.controller.request;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.model.user.Customer;
import com.itmo.assassins.model.user.UserRole;
import com.itmo.assassins.service.request.PaymentService;
import com.itmo.assassins.service.request.RequestService;
import com.itmo.assassins.service.user.UserSecurityService;
import com.itmo.assassins.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentControllerTest {

    @MockBean
    private RequestService requestService;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private UserSecurityService securityService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetPayment() throws Exception {

        Customer customer = new Customer();
        customer.setRole(UserRole.CUSTOMER);
        customer.setRequests(new ArrayList<>(Collections.singletonList(new Request())));

        Mockito.when(securityService.getLoggedInUserName())
                .thenReturn("username");

        Mockito.when(userService.findUserByUserName("username"))
                .thenReturn(customer);


        Request request = new Request();
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setPrice(200);
        request.setRequestInfo(requestInfo);

        Mockito.when(requestService.getRequestById(1))
                        .thenReturn(Optional.of(request));

        mockMvc.perform(get("/payment?id=1"))
                .andExpect(status().is(200))
                .andExpect(view().name("payment"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/payment.jsp"))
                .andExpect(model().attribute("id", is("1")))
                .andExpect(model().attribute("user", is(customer)))
                .andExpect(model().attribute("price", is(requestInfo.getPrice())));
    }

    @Test
    void testPaymentProcess() throws Exception {

        Mockito.when(requestService.getRequestById(1))
                .thenReturn(Optional.of(new Request()));

        mockMvc.perform(post("/payment?id=1"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/profile"))
                .andExpect(redirectedUrl("/profile"));

        Mockito.verify(paymentService, Mockito.times(1))
                .processPayment(any());
    }

    @Test
    void testPaymentConfirm() throws Exception {

        Mockito.when(requestService.getRequestById(1))
                .thenReturn(Optional.of(new Request()));

        mockMvc.perform(post("/payment-confirm?id=1"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/profile"))
                .andExpect(redirectedUrl("/profile"));

        Mockito.verify(paymentService, Mockito.times(1))
                .confirmPayment(any());
    }
}