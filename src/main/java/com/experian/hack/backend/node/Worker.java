package com.experian.hack.backend.node;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

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
    private String email;

    @Relationship(type = "ASSIGNED_TO")
    private Set<Opportunity> jobs = new HashSet<>();

}
