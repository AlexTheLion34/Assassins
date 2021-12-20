package com.itmo.assassins.model.request;

import com.itmo.assassins.model.report.Report;
import com.itmo.assassins.model.user.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
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
	private Master master;

	@ManyToOne
	@JoinColumn(name="gunsmith_id")
	private Gunsmith gunsmith;

	@ManyToOne
	@JoinColumn(name="cabman_id")
	private Cabman cabman;

	@OneToOne(fetch = FetchType.LAZY)
	private Executor executor;
}