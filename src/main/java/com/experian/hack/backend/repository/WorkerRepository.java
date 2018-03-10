package com.experian.hack.backend.repository;

import com.experian.hack.backend.node.Worker;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface WorkerRepository extends Neo4jRepository<Worker, Long> {

    Optional<Worker> findByEmail(String email);

}
