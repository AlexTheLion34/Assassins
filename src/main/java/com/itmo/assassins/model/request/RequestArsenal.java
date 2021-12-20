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

    // TODO: - add fields like num swords, archers, knives, shields

    @Id
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Request request;
}
