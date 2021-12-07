package com.itmo.assassins.repository.request;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestInfoRepository extends JpaRepository<RequestInfo, Long> {
    RequestInfo findByRequest(Request request);
}
