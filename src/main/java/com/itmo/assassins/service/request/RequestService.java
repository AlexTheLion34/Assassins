package com.itmo.assassins.service.request;


import java.util.List;
import java.util.Optional;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.model.user.Customer;
import com.itmo.assassins.model.user.User;

public interface RequestService {
	List<Request> getRequestsByUser(User user);
	Optional<Request> getRequestById(long id);
	void saveRequest(Request request);
	void createRequest(RequestInfo requestInfo, Customer currentUser);
}