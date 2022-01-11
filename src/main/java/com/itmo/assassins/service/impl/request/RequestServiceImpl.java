package com.itmo.assassins.service.impl.request;

import java.util.List;
import java.util.Optional;

import com.itmo.assassins.model.request.*;
import com.itmo.assassins.model.user.*;
import com.itmo.assassins.repository.request.RequestRepository;
import com.itmo.assassins.service.request.RequestService;
import com.itmo.assassins.service.user.UserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    private final UserTeamService teamService;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository,
                              UserTeamService teamService) {

        this.requestRepository = requestRepository;
        this.teamService = teamService;
    }

    @Override
    public Optional<Request> getRequestById(long id) {
        return requestRepository.findById(id);
    }

    @Override
    public void saveRequest(Request request) {
        requestRepository.save(request);
    }

    @Override
    public void createRequest(RequestInfo requestInfo, Customer currentUser) {

        Request request = new Request();

        request.setOwner(currentUser);

        Executor executor = teamService.findExecutorForRequest(requestInfo.getDifficulty());

        request.setExecutor(executor);
        executor.setBusy(true);


        request.setRequestInfo(requestInfo);
        requestInfo.setRequest(request);

        request.setMaster(teamService.findMasterForRequest());
        request.setCabman(teamService.findCabmanForRequest());
        request.setGunsmith(teamService.findGunsmithForRequest());

        request.getRequestInfo()
                .setStatus(RequestStatus.MODERATING);

        saveRequest(request);
    }

    @Override
    public void changeRequestTeam(Request request, User userToChange) {

        switch (userToChange.getRole()) {
            case EXECUTOR:
                request.getExecutor().setBusy(false);
                ((Executor) userToChange).setBusy(true);
                request.setExecutor(((Executor) userToChange));
                break;
            case CABMAN:
                request.setCabman((Cabman) userToChange);
                break;
            case GUNSMITH:
                request.setGunsmith((Gunsmith) userToChange);
                break;
        }

        saveRequest(request);
    }

    @Override
    public void confirmRequest(Request request, Master master) {

            List<Request> masterRequests = master.getRequests();
            masterRequests.remove(request);
            master.setRequests(masterRequests);
            request.getRequestInfo().setStatus(RequestStatus.PACKING_1);
            request.setMaster(null);

        saveRequest(request);
    }

    @Override
    public Request putRatingForRequest(Request request, int rating) {
        request.getRequestInfo().setRating(rating);
        request.getRequestInfo().setStatus(RequestStatus.PAYMENT_CONFIRMING);
        return request;
    }

    @Override
    public void addArsenal(Request request, RequestArsenal arsenal) {

        arsenal.setRequest(request);
        request.setArsenal(arsenal);

        request.getGunsmith().getRequests().remove(request);
        request.setGunsmith(null);

        request.getRequestInfo().setStatus(RequestStatus.PACKING_2);

        saveRequest(request);
    }

    @Override
    public void addRoadEquipment(Request request, RequestRoadEquipment equipment) {

        equipment.setRequest(request);
        request.setRoadEquipment(equipment);

        request.getCabman().getRequests().remove(request);
        request.setCabman(null);

        request.getRequestInfo().setStatus(RequestStatus.EXECUTING);

        saveRequest(request);
    }
}