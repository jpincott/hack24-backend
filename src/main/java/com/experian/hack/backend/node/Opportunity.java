package com.experian.hack.backend.node;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
@Getter @Setter @Accessors(chain = true)
public class Opportunity {

    @Id @GeneratedValue
    private Long id;

    private LocalDateTime start;

    private String description;
    private String location;

    private Integer value;

    private String latitude;
    private String longitude;

    private int clearance;

    @Relationship(type = "ASSIGNED_TO", direction = "INCOMING")
    private Set<Worker> workers = new HashSet<>();

    public boolean isAvailable() {
        return workers.isEmpty();
    }

}
