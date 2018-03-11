package com.experian.hack.backend.web;

import com.experian.hack.backend.node.Customer;
import com.experian.hack.backend.node.Opportunity;
import com.experian.hack.backend.node.Worker;
import com.experian.hack.backend.repository.CustomerRepository;
import com.experian.hack.backend.repository.OpportunityRepository;
import com.experian.hack.backend.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private WorkerRepository workers;
    private OpportunityRepository opportunities;
    private CustomerRepository customers;

    @Autowired
    public CustomerController(WorkerRepository workers, OpportunityRepository opportunities, CustomerRepository customers) {
        this.workers = workers;
        this.opportunities = opportunities;
        this.customers = customers;
    }

    @PostMapping("/me/opportunities")
    public Opportunity createOpportunity(
            @RequestHeader String email,
            @RequestBody Opportunity opportunity) {

        opportunity = opportunities.save(opportunity);

        Customer customer = customers.findByEmail(email).orElseThrow(ResourceNotFoundException::new);
        customer.create(opportunity);
        customers.save(customer);

        return opportunity;
    }

}
