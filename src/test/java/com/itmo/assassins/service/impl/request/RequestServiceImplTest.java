package com.itmo.assassins.service.impl.request;

import com.itmo.assassins.model.request.*;
import com.itmo.assassins.model.user.*;
import com.itmo.assassins.repository.request.RequestRepository;
import com.itmo.assassins.service.request.RequestService;
import com.itmo.assassins.service.user.UserTeamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
@Import(RequestServiceImpl.class)
class RequestServiceImplTest {

    @MockBean
    private RequestRepository requestRepository;

    @MockBean
    private UserTeamService userTeamService;

    @Autowired
    private RequestService requestService;

    @Test
    void testGetRequestById() {
        requestService.getRequestById(anyLong());
        Mockito.verify(requestRepository, Mockito.times(1))
                .findById(any());
    }

    @Test
    void testSaveRequest() {
        requestService.saveRequest(any());
        Mockito.verify(requestRepository, Mockito.times(1))
                .save(any());
    }

    @Test
    void testCreateRequest() {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setDifficulty(RequestDifficulty.HARD);

        Customer customer = new Customer();

        Mockito.when(userTeamService.findExecutorForRequest(requestInfo.getDifficulty()))
                        .thenReturn(new Executor());

        requestService.createRequest(requestInfo, customer);

        Mockito.verify(userTeamService, Mockito.times(1))
                .findExecutorForRequest(requestInfo.getDifficulty());

        Mockito.verify(userTeamService, Mockito.times(1))
                .findMasterForRequest();
        Mockito.verify(userTeamService, Mockito.times(1))
                .findCabmanForRequest();
        Mockito.verify(userTeamService, Mockito.times(1))
                .findGunsmithForRequest();

        Mockito.verify(requestRepository, Mockito.times(1))
                .save(any());
    }

    @ParameterizedTest
    @MethodSource("provideUsersToChange")
    void testChangeRequestTeam(User userToChange) {

        Request request = new Request();
        request.setExecutor(new Executor());

        requestService.changeRequestTeam(request, userToChange);

        Mockito.verify(requestRepository, Mockito.times(1))
                .save(any());
    }

    @Test
    void testConfirmRequest() {

        Request request = new Request();

        RequestInfo requestInfo = new RequestInfo();
        request.setRequestInfo(requestInfo);

        Master master = new Master();

        master.setRequests(new ArrayList<>(Collections.singletonList(request)));

        requestService.confirmRequest(request, master);

        Mockito.verify(requestRepository, Mockito.times(1))
                .save(request);
    }

    @Test
    void testPutRatingForRequest() {

        Request request = new Request();
        request.setRequestInfo(new RequestInfo());

        Request expectedRequest = new Request();
        expectedRequest.setRequestInfo(new RequestInfo());
        expectedRequest.getRequestInfo().setRating(4);
        expectedRequest.getRequestInfo().setStatus(RequestStatus.PAYMENT_CONFIRMING);

        assertEquals(expectedRequest.getRequestInfo().getRating(),
                requestService.putRatingForRequest(request, 4).getRequestInfo().getRating());
    }

    @Test
    void testAddArsenal() {

        Request request = new Request();
        request.setRequestInfo(new RequestInfo());

        Gunsmith gunsmith = new Gunsmith();
        gunsmith.setRequests(new ArrayList<>(Collections.singletonList(request)));

        request.setGunsmith(gunsmith);

        requestService.addArsenal(request, new RequestArsenal());

        Mockito.verify(requestRepository, Mockito.times(1))
                .save(request);
    }

    @Test
    void testAddRoadEquipment() {

        Request request = new Request();
        request.setRequestInfo(new RequestInfo());

        Cabman cabman = new Cabman();
        cabman.setRequests(new ArrayList<>(Collections.singletonList(request)));

        request.setCabman(cabman);

        requestService.addRoadEquipment(request, new RequestRoadEquipment());

        Mockito.verify(requestRepository, Mockito.times(1))
                .save(request);
    }

    private static Stream<User> provideUsersToChange() {

        Executor executor = new Executor();
        executor.setRole(UserRole.EXECUTOR);

        Cabman cabman = new Cabman();
        cabman.setRole(UserRole.CABMAN);

        Gunsmith gunsmith = new Gunsmith();
        gunsmith.setRole(UserRole.GUNSMITH);

        List<User> users = new ArrayList<>();
        users.add(executor);
        users.add(cabman);
        users.add(gunsmith);

        return users.stream();
    }
}