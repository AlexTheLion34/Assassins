package com.itmo.assassins.service;

import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.model.request.RequestTeam;

public interface TeamFormingService {
    RequestTeam formTeamForRequest(RequestInfo requestInfo);
}
