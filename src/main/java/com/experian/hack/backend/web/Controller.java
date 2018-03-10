package com.experian.hack.backend.web;

import com.experian.hack.backend.node.Opportunity;
import com.experian.hack.backend.node.Worker;
import com.experian.hack.backend.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/workers")
public class Controller {

    private WorkerRepository repo;

    @Autowired
    public Controller(WorkerRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/me/jobs")
    public Set<Opportunity> getJobs(@RequestHeader String email) {

        Optional<Worker> worker = repo.findByEmail(email);
        return worker.map(Worker::getJobs).orElse(Collections.emptySet());

    }
}
