package com.experian.hack.backend.relationship;

import com.experian.hack.backend.node.Opportunity;
import com.experian.hack.backend.node.Worker;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type="ASSIGNED_TO")
@Getter @Setter @Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Job {

    @Id @GeneratedValue
    private Long id;

    @StartNode
    private Worker worker;

    @EndNode
    private Opportunity opportunity;

}
