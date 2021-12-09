package com.itmo.assassins.model.user;

import com.itmo.assassins.model.request.Request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "executor")
@Getter
@Setter
@NoArgsConstructor
public class Executor extends User {

    @OneToOne(mappedBy = "executor", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Request currentTask;

    private Integer balance;

    private Boolean busy;

    private Double rating;
}
