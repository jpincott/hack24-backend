package com.experian.hack.backend.web;

import com.experian.hack.backend.node.Opportunity;
import com.experian.hack.backend.node.Worker;
import com.experian.hack.backend.repository.OpportunityRepository;
import com.experian.hack.backend.repository.WorkerRepository;
import esendex.sdk.java.EsendexException;
import esendex.sdk.java.model.domain.request.SmsMessageRequest;
import esendex.sdk.java.service.BasicServiceFactory;
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
    private BasicServiceFactory serviceFactory;

    @Autowired
    public WorkerController(
            WorkerRepository workers,
            OpportunityRepository opportunities,
            BasicServiceFactory serviceFactory) {
        this.workers = workers;
        this.opportunities = opportunities;
        this.serviceFactory = serviceFactory;
    }

    @GetMapping("/me/jobs")
    public Set<Opportunity> getJobs(@RequestHeader String email, @RequestParam String start, @RequestParam String end) {
        return opportunities.findByStartIsBetweenAndWorkerEmail(start, end, email);
    }

    @GetMapping("/me/jobs/value")
    public int getValueForPeriod(@RequestHeader String email, @RequestParam String start, @RequestParam String end) {
        return opportunities.sumValueByStartIsBetweenAndWorkerEmail(start, end, email);
    }

    @PostMapping("/me/swaprequest/{id}")
    public void swapRequestAndNotify(@PathVariable Long id, @RequestHeader String email) throws EsendexException {
        String phoneNumber = workers.findPhoneNumberByOpportunityId(id);

        SmsMessageRequest smsMessageRequest = new SmsMessageRequest(phoneNumber, "Job has been changed!");
        this.serviceFactory.getMessagingService().sendMessage(email, smsMessageRequest);
    }
}
