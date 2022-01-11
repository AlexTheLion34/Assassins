package com.itmo.assassins.service.request;

import java.util.Optional;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestArsenal;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.model.request.RequestRoadEquipment;
import com.itmo.assassins.model.user.Customer;
import com.itmo.assassins.model.user.Master;
import com.itmo.assassins.model.user.User;

public interface RequestService {
	Optional<Request> getRequestById(long id);
	void saveRequest(Request request);
	void createRequest(RequestInfo requestInfo, Customer currentUser);
	void changeRequestTeam(Request request, User userToChange);
	void confirmRequest(Request request, Master master);
	Request putRatingForRequest(Request request, int rating);
	void addArsenal(Request request, RequestArsenal arsenal);
	void addRoadEquipment(Request request, RequestRoadEquipment equipment);
}