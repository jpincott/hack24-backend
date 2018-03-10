package com.experian.hack.backend.repository;

import com.experian.hack.backend.node.Opportunity;
import com.experian.hack.backend.node.Worker;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface OpportunityRepository extends Neo4jRepository<Opportunity, Long> {
}
