package com.experian.hack.backend.web;

import com.experian.hack.backend.node.Opportunity;
import com.experian.hack.backend.node.Worker;
import com.experian.hack.backend.repository.OpportunityRepository;
import com.experian.hack.backend.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/workers")
public class WorkerController {

    private WorkerRepository workers;
    private OpportunityRepository opportunities;

    @Autowired
    public WorkerController(WorkerRepository workers, OpportunityRepository opportunities) {
        this.workers = workers;
        this.opportunities = opportunities;
    }

    @GetMapping("/me/jobs")
    public Set<Opportunity> getJobs(@RequestHeader String email, @RequestParam String start, @RequestParam String end) {
        return opportunities.findByStartIsBetweenAndWorkerEmail(start, end, email);
    }

    @GetMapping("/me/jobs/value")
    public int getValueForPeriod(@RequestHeader String email, @RequestParam String start, @RequestParam String end) {
        return opportunities.sumValueByStartIsBetweenAndWorkerEmail(start, end, email);
    }
}
