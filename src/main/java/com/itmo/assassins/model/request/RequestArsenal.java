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
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Request request;

    private int numOfSwords;

    private int numOfBows;

    private int numOfShields;

    private int numOfKnives;
}
