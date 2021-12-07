package com.itmo.assassins.service.impl.request;

import com.itmo.assassins.model.request.RequestDifficulty;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.service.request.RequestDifficultyComputationService;
import org.springframework.stereotype.Service;

@Service
public class RequestDifficultyComputationServiceImpl implements RequestDifficultyComputationService {

    @Override
    public RequestDifficulty computeDifficulty(RequestInfo requestInfo) {

        //TODO: - remove stub

        return RequestDifficulty.MEDIUM;
    }
}
