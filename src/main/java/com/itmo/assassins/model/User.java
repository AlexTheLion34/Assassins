package com.itmo.assassins.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Request> requests = new ArrayList<>();

    @OneToOne
    private Request currentTask;

    private boolean busy;

    private Integer balance;

    public User() {
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean bussy) {
        this.busy = bussy;
    }

    public Request getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Request currentTask) {
        this.currentTask = currentTask;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> curatedGroups) {
        this.requests = curatedGroups;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}