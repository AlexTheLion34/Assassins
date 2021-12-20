package com.itmo.assassins.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "request_road_equipment")
@Getter
@Setter
@NoArgsConstructor
public class RequestRoadEquipment {

    @Id
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Request request;

    private boolean carriageRequired;

    private int numOfHorses;
}
