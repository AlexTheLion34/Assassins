package com.itmo.assassins.service.request;

import com.itmo.assassins.model.request.Request;

public interface PaymentService {
    void processPayment(Request request);
    void confirmPayment(Request request);
}
