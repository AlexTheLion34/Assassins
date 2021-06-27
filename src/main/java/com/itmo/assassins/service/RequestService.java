package com.itmo.assassins.service;

import java.util.Date;

import java.util.List;
import java.util.Optional;

import com.itmo.assassins.model.Request;
import com.itmo.assassins.model.User;

public interface RequestService {

	List<Request> getRequestsByUser(User user);

	Optional<Request> getRequestById(long id);
	
	void saveRequest(Request request);

}