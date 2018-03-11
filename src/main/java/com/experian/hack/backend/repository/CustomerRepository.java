package com.experian.hack.backend.repository;

import com.experian.hack.backend.node.Customer;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface CustomerRepository extends Neo4jRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
