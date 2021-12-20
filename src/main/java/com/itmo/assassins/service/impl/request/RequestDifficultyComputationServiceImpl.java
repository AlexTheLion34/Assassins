package com.itmo.assassins.service.impl.request;

import com.itmo.assassins.model.request.RequestDifficulty;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.service.request.RequestDifficultyComputationService;
import org.springframework.stereotype.Service;

@Service
public class RequestDifficultyComputationServiceImpl implements RequestDifficultyComputationService {

    @Override
    public RequestDifficulty computeDifficulty(RequestInfo requestInfo) {

        Double longitude = requestInfo.getPossibleLongitude();
        Double latitude = requestInfo.getPossibleLatitude();

        Hemisphere southernOrNorthern;

        if (latitude >= 0.0) {
            southernOrNorthern = Hemisphere.NORTHERN;
        } else {
            southernOrNorthern = Hemisphere.SOUTHERN;
        }

        Hemisphere westernOrEastern;

        if (longitude >= 0) {
            westernOrEastern = Hemisphere.EASTERN;
        } else {
            westernOrEastern = Hemisphere.WESTERN;
        }

        if (southernOrNorthern == Hemisphere.NORTHERN && westernOrEastern == Hemisphere.EASTERN) {
            return RequestDifficulty.HARD;
        } else if (southernOrNorthern == Hemisphere.NORTHERN) {
            return RequestDifficulty.MEDIUM;
        } else if (westernOrEastern == Hemisphere.EASTERN) {
            return RequestDifficulty.MEDIUM;
        } else {
            return RequestDifficulty.LOW;
        }
    }

    private enum Hemisphere {
        NORTHERN, SOUTHERN, WESTERN, EASTERN
    }
}
