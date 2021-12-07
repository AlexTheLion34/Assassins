package com.itmo.assassins.service.impl.request;

import java.util.List;
import java.util.Optional;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.model.request.RequestStatus;
import com.itmo.assassins.model.request.RequestTeam;
import com.itmo.assassins.model.user.User;
import com.itmo.assassins.repository.request.RequestRepository;
import com.itmo.assassins.service.request.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {

	private final RequestRepository requestRepository;

	@Autowired
	public RequestServiceImpl(RequestRepository requestRepository) {

		this.requestRepository = requestRepository;
	}

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

	@Override
	public void createRequest(RequestInfo requestInfo, RequestTeam requestTeam, User currentUser) {

		Request request = new Request();

		request.setOwner(currentUser);

		requestTeam.getExecutor()
				.getUserInfo()
				.setCurrentTask(request);

		request.setRequestInfo(requestInfo);
		requestInfo.setRequest(request);

		request.setRequestTeam(requestTeam);
		requestTeam.setRequest(request);

		request.getRequestInfo()
				.setStatus(RequestStatus.CONFIRMING);

		saveRequest(request);
	}
}