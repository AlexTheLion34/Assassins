package com.itmo.assassins.service.impl.request;

import java.util.List;
import java.util.Optional;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.model.request.RequestStatus;
import com.itmo.assassins.model.user.Customer;
import com.itmo.assassins.model.user.Executor;
import com.itmo.assassins.model.user.User;
import com.itmo.assassins.model.user.UserRole;
import com.itmo.assassins.repository.request.RequestRepository;
import com.itmo.assassins.service.request.RequestService;
import com.itmo.assassins.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {

	private final RequestRepository requestRepository;

    private final UserService userService;

	@Autowired
	public RequestServiceImpl(RequestRepository requestRepository, UserService userService) {

		this.requestRepository = requestRepository;
        this.userService = userService;
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
	public void createRequest(RequestInfo requestInfo, Customer currentUser) {

		Request request = new Request();

		request.setOwner(currentUser);

        Executor executor;

        switch (requestInfo.getDifficulty()) {

            case LOW:
                executor = userService.findExecutorsByBusy()
                        .stream()
                        .filter(u -> u.getRating() < 3.1)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException(""));
                break;
            case MEDIUM:
                executor = userService.findExecutorsByBusy()
                        .stream()
                        .filter(u -> u.getRating() >= 3.1 && u.getRating() <= 4.5)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException(""));
                break;
            default:
                executor = userService.findExecutorsByBusy()
                        .stream()
                        .filter(u -> u.getRating() > 4.5)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException(""));
                break;
        }

        request.setExecutor(executor);
        executor.setBusy(true);


		request.setRequestInfo(requestInfo);
		requestInfo.setRequest(request);

		request.getRequestInfo()
				.setStatus(RequestStatus.MODERATING);

		saveRequest(request);
	}
}