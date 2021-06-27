package com.itmo.assassins.repository;

import java.util.List;

import com.itmo.assassins.model.Request;
import com.itmo.assassins.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RequestRepository extends JpaRepository<Request, Long>{
	List<Request> findByOwner(User owner);
}
