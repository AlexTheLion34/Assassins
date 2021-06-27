package com.itmo.assassins.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.itmo.assassins.model.Request;
import com.itmo.assassins.model.User;
import com.itmo.assassins.repository.RequestRepository;
import com.itmo.assassins.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {

	@Autowired
	private RequestRepository requestRepository;

	@Override
	public List<Request> getRequestsByUser(User user) {
		return requestRepository.findByOwner(user);
	}

	@Override
	public Optional<Request> getRequestById(long id) {
		return requestRepository.findById(id);
	}

	@Override
	public void saveRequest(Request request) {
		requestRepository.save(request);
	}
}