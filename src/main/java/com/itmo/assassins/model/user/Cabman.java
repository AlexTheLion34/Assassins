package com.itmo.assassins.model.user;

import com.itmo.assassins.model.request.Request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "cabman")
@Getter
@Setter
@NoArgsConstructor
public class Cabman extends User {

    @OneToMany(mappedBy = "cabman")
    private List<Request> requests;
}
