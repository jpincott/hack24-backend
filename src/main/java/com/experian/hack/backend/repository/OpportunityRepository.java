package com.experian.hack.backend.repository;

import com.experian.hack.backend.node.Opportunity;
import com.experian.hack.backend.node.Worker;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface OpportunityRepository extends Neo4jRepository<Opportunity, Long> {

    Set<Opportunity> findByStartIsBetween(LocalDateTime begin, LocalDateTime end);

    @Query("MATCH (n:Opportunity)<-[:ASSIGNED_TO]-(w:Worker{email:{2}})" +
            " WHERE n.start >= {0} AND n.start <= {1}" +
            " RETURN n" +
            " ORDER BY n.start")
    Set<Opportunity> findByStartIsBetweenAndWorkerEmail(String begin, String end, String email);

    @Query("MATCH (n:Opportunity)<-[:ASSIGNED_TO]-(w:Worker{email:{2}}) " +
            "WHERE n.start >= {0} AND n.start <= {1} " +
            "RETURN sum(n.value)")
    int sumValueByStartIsBetweenAndWorkerEmail(String begin, String end, String email);
}
