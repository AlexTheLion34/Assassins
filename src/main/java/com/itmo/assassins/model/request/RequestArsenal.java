package com.itmo.assassins.model.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "request_arsenal")
@Getter
@Setter
@NoArgsConstructor
public class RequestArsenal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private RequestInfo requestInfo;
}
