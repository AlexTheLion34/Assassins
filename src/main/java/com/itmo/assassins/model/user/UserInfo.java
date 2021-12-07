package com.itmo.assassins.model.user;

import com.itmo.assassins.model.request.Request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@NoArgsConstructor
public class UserInfo {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Request> requests = new ArrayList<>();

    private Double rating;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Request currentTask;

    private Integer balance;
}
