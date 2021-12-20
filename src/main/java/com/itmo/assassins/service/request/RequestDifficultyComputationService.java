package com.itmo.assassins.service.request;

import com.itmo.assassins.model.request.RequestDifficulty;
import com.itmo.assassins.model.request.RequestInfo;

public interface RequestDifficultyComputationService {
    RequestDifficulty computeDifficulty(RequestInfo requestInfo);
}
