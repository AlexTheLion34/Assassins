package com.itmo.assassins.model;

import javax.persistence.*;

@Entity
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String type;

	private Double possibleLongitude;

	private Double possibleLatitude;

	private String aim;

	private String description;

	private String status;

	private Integer price;

	@ManyToOne
	private User owner;

	@OneToOne
	private User executor;

	@OneToOne(fetch = FetchType.EAGER)
	private Report report;
	
	public Request() {
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public User getExecutor() {
		return executor;
	}

	public void setExecutor(User executor) {
		this.executor = executor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Double getPossibleLongitude() {
		return possibleLongitude;
	}

	public Double getPossibleLatitude() {
		return possibleLatitude;
	}

	public void setPossibleLongitude(Double possibleLongitude) {
		this.possibleLongitude = possibleLongitude;
	}

	public void setPossibleLatitude(Double possibleLatitude) {
		this.possibleLatitude = possibleLatitude;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAim() {
		return aim;
	}

	public void setAim(String aim) {
		this.aim = aim;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}