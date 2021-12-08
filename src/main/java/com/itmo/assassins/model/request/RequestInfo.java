package com.itmo.assassins.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "request_info")
@Getter
@Setter
@NoArgsConstructor
public class RequestInfo {

    @Id
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Request request;

    private String type;

    private Double possibleLongitude;

    private Double possibleLatitude;

    private String aim;

    private String description;

    private Integer price;

    @Enumerated(EnumType.ORDINAL)
    private RequestDifficulty difficulty;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
