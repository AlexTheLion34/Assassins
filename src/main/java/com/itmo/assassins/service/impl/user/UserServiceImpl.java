package com.itmo.assassins.service.impl.user;

import com.itmo.assassins.model.request.RequestStatus;
import com.itmo.assassins.model.user.Customer;
import com.itmo.assassins.model.user.Executor;
import com.itmo.assassins.model.user.UserRole;
import com.itmo.assassins.model.user.User;
import com.itmo.assassins.repository.user.UserRepository;
import com.itmo.assassins.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public Set<Executor> findExecutorsByBusy() {
        return userRepository.findByRole(UserRole.EXECUTOR)
                .stream()
                .filter(user -> user instanceof Executor)
                .map(u -> ((Executor) u))
                .filter(u -> !u.getBusy())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<User> findUserByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void countRating(Executor executor, int requestRating) {
        executor.setRating((executor.getRating() + requestRating) / (executor.getNumOfCompletedRequests() + 1));
        executor.setNumOfCompletedRequests(executor.getNumOfCompletedRequests() + 1);
        saveUser(executor);
    }

    @Override
    public Long countMaxAffordablePrice(Customer customer) {
        return (long) (customer.getBalance() - customer.getRequests()
                .stream()
                .filter(r -> r.getRequestInfo().getStatus() != RequestStatus.DONE)
                .map(r -> r.getRequestInfo().getPrice())
                .reduce(0, Integer::sum));
    }
}
