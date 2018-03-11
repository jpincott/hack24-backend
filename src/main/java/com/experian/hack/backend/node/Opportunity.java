package com.experian.hack.backend.node;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Date;

@NodeEntity
@Getter @Setter @Accessors(chain = true)
public class Opportunity {

    @Id @GeneratedValue
    private Long id;

    private String description;
    private LocalDateTime start;
    private String location;
    
}
