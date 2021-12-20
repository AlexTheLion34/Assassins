package com.itmo.assassins.service.impl.request;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestStatus;
import com.itmo.assassins.model.user.Customer;
import com.itmo.assassins.model.user.Executor;
import com.itmo.assassins.service.request.PaymentService;
import com.itmo.assassins.service.request.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final RequestService requestService;

    @Autowired
    public PaymentServiceImpl(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public void processPayment(Request request) {
        Integer price = request.getRequestInfo().getPrice();

        Customer owner = request.getOwner();
        Executor executor = request.getExecutor();

        owner.setBalance(owner.getBalance() - price);
        executor.setBalance(executor.getBalance() + price);

        request.getRequestInfo().setStatus(RequestStatus.EVALUATING);

        requestService.saveRequest(request);
    }

    @Override
    public void confirmPayment(Request request) {
        Executor executor = request.getExecutor();

        request.getRequestInfo().setStatus(RequestStatus.DONE);

        executor.setCurrentTask(null);
        executor.setBusy(false);
        request.setExecutor(null);

        requestService.saveRequest(request);
    }
}
