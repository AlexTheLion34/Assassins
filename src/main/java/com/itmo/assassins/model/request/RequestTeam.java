package com.itmo.assassins.model.request;


import com.itmo.assassins.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "request_team")
@Getter
@Setter
@NoArgsConstructor
public class RequestTeam {

    @Id
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Request request;

    @ManyToOne
    private User master;

    @ManyToOne
    private User gunsmith;

    @ManyToOne
    private User cabman;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User executor;
}
