package com.itmo.assassins.repository.request;

import java.util.List;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{
	List<Request> findByOwner(User owner);
}
