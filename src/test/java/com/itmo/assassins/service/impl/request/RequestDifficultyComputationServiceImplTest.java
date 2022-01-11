package com.itmo.assassins.service.impl.request;

import com.itmo.assassins.model.request.RequestDifficulty;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.service.request.RequestDifficultyComputationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(RequestDifficultyComputationServiceImpl.class)
class RequestDifficultyComputationServiceImplTest {

    @Autowired
    private RequestDifficultyComputationService difficultyComputationService;

    @Test
    void testComputeDifficultyHard() {

        RequestInfo requestInfo = new RequestInfo();

        requestInfo.setPossibleLatitude(15.6);
        requestInfo.setPossibleLongitude(16.7);

        assertEquals(difficultyComputationService.computeDifficulty(requestInfo), RequestDifficulty.HARD);
    }

    @Test
    void testComputeDifficultyMediumWestern() {

        RequestInfo requestInfo = new RequestInfo();

        requestInfo.setPossibleLatitude(15.6);
        requestInfo.setPossibleLongitude(-16.7);

        assertEquals(difficultyComputationService.computeDifficulty(requestInfo), RequestDifficulty.MEDIUM);
    }

    @Test
    void testComputeDifficultyMediumSouthern() {

        RequestInfo requestInfo = new RequestInfo();

        requestInfo.setPossibleLatitude(-15.6);
        requestInfo.setPossibleLongitude(16.7);

        assertEquals(difficultyComputationService.computeDifficulty(requestInfo), RequestDifficulty.MEDIUM);
    }

    @Test
    void testComputeDifficultyLow() {

        RequestInfo requestInfo = new RequestInfo();

        requestInfo.setPossibleLatitude(-15.6);
        requestInfo.setPossibleLongitude(-16.7);

        assertEquals(difficultyComputationService.computeDifficulty(requestInfo), RequestDifficulty.LOW);
    }
}