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
    public Set<Opportunity> getJobs(
            @RequestHeader String email,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end) {

        Set<Opportunity> jobs = opportunities.findByStartIsBetweenAndWorkerEmail(start, end, email);
//        Optional<Worker> worker = workers.findByEmail(email);
//        Set<Opportunity> jobs = worker.map(Worker::getJobs).orElse(emptySet()).stream()
//                .filter(isAfter(LocalDateTime.parse(start)))
//                .filter(isBefore(LocalDateTime.parse(end)))
//                .collect(toSet());

        return jobs;
    }

    private Predicate<? super Opportunity> isAfter(LocalDateTime start) {
        return o -> start.isBefore(o.getStart());
    }

    private Predicate<? super Opportunity> isBefore(LocalDateTime end) {
        return o -> end.isAfter(o.getStart());
    }

}
