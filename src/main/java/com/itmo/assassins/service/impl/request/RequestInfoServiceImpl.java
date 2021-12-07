package com.itmo.assassins.service.impl.request;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.repository.request.RequestInfoRepository;
import com.itmo.assassins.service.request.RequestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestInfoServiceImpl implements RequestInfoService {

    private final RequestInfoRepository requestInfoRepository;

    @Autowired
    public RequestInfoServiceImpl(RequestInfoRepository requestInfoRepository) {
        this.requestInfoRepository = requestInfoRepository;
    }

    @Override
    public RequestInfo findByRequest(Request request) {
        return requestInfoRepository.findByRequest(request);
    }
}
