package com.experian.hack.backend.node;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@Getter @Setter @Accessors(chain = true)
public class Worker {

    @Id @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String phone;

    @Index(unique = true)
    private String email;

    private String latitude;
    private String longitude;

    private int clearance;

    @Relationship(type = "ASSIGNED_TO")
    private Set<Opportunity> jobs = new HashSet<>();

    public void assignTo(Opportunity opportunity) {
        jobs.add(opportunity);
    }
}
