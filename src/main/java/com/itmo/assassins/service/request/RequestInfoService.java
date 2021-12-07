package com.itmo.assassins.service.request;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestInfo;

public interface RequestInfoService {
    RequestInfo findByRequest(Request request);
}
