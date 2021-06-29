package com.itmo.assassins.model;

import javax.persistence.*;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Report() {
    }

    @OneToOne(fetch = FetchType.EAGER)
    private Request request;

    private String path;

    public Long getId() {
        return id;
    }

    public Request getRequest() {
        return request;
    }

    public String getPath() {
        return path;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void setPath(String text) {
        this.path = text;
    }
}
