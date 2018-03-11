package com.experian.hack.backend.repository;

import com.experian.hack.backend.node.Worker;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;
import java.util.Set;

public interface WorkerRepository extends Neo4jRepository<Worker, Long> {

    Optional<Worker> findByEmail(String email);

    @Query("MATCH (w:Worker)-[:ASSIGNED_TO]->(o:Opportunity) " +
            "WHERE id(o) = {0} " +
            "RETURN w.phone")
    String findPhoneNumberByOpportunityId(Long id);

}
