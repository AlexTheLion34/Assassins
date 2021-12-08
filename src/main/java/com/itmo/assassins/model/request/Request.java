package com.itmo.assassins.model.request;

import com.itmo.assassins.model.report.Report;
import com.itmo.assassins.model.user.Customer;
import com.itmo.assassins.model.user.Executor;
import com.itmo.assassins.model.user.Cabman;
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
			fetch = FetchType.LAZY)
	private RequestInfo requestInfo;

	@OneToOne(mappedBy = "request", cascade = CascadeType.ALL,
	fetch = FetchType.LAZY)
	private Report report;

	@OneToOne(mappedBy = "request", cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	private RequestArsenal arsenal;

	@OneToOne(mappedBy = "request", cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	private RequestRoadEquipment roadEquipment;


	@ManyToOne
	@JoinColumn(name="owner_id")
	private Customer owner;

	@ManyToOne
	@JoinColumn(name="master_id")
	private Cabman master;

	@ManyToOne
	@JoinColumn(name="gunsmith_id")
	private Cabman gunsmith;

	@ManyToOne
	@JoinColumn(name="cabman_id")
	private Cabman cabman;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="executor_id")
	private Executor executor;
}