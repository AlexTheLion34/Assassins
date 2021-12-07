package com.itmo.assassins.model.request;

import com.itmo.assassins.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne(mappedBy = "request", cascade = CascadeType.ALL,
			fetch = FetchType.LAZY, optional = false)
	private RequestInfo requestInfo;

	@OneToOne(mappedBy = "request", cascade = CascadeType.ALL,
			fetch = FetchType.LAZY, optional = false)
	private RequestTeam requestTeam;

	@ManyToOne
	private User owner;
}