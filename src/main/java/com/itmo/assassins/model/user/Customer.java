package com.itmo.assassins.model.user;

import com.itmo.assassins.model.request.Request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends User {

    @OneToMany(mappedBy = "owner")
    private List<Request> requests;

    private Integer balance;
}
