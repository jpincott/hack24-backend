package com.experian.hack.backend.repository;

import com.experian.hack.backend.node.Customer;
import com.experian.hack.backend.node.Opportunity;
import com.experian.hack.backend.node.Worker;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

import static java.time.LocalDateTime.parse;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomQueryTest {

    @Autowired
    private OpportunityRepository opportunities;
    @Autowired
    private WorkerRepository workers;
    @Autowired
    private CustomerRepository customers;

    @Test
    public void testStartBetweenAndAssignedTo() {

        Opportunity o1 = this.opportunities.save(new Opportunity().setDescription("test1").setStart(parse("2017-03-10T10:00")));
        Opportunity o2 = this.opportunities.save(new Opportunity().setDescription("test2").setStart(parse("2017-03-10T12:00")));
        Opportunity o3 = this.opportunities.save(new Opportunity().setDescription("test3").setStart(parse("2017-03-10T14:00")));

        Worker w1 = this.workers.save(new Worker().setEmail("w1@example.com"));
        Worker w2 = this.workers.save(new Worker().setEmail("w2@example.com"));

        Set<Opportunity> found = this.opportunities.findByStartIsBetween(parse("2017-03-10T11:00"), parse("2017-03-10T15:00"));
        assertThat(found, hasSize(2));

        found = this.opportunities.findByStartIsBetweenAndWorkerEmail("2017-03-10T11:00", "2017-03-10T15:00", "w1@example.com");
        assertThat(found, hasSize(0));

        w1.assignTo(o2);
        w1 = workers.save(w1);
        found = this.opportunities.findByStartIsBetweenAndWorkerEmail("2017-03-10T11:00", "2017-03-10T15:00", "w1@example.com");
        assertThat(found, hasSize(1));

        this.opportunities.deleteAll(Arrays.asList(o1,o2,o3));
        this.workers.deleteAll(Arrays.asList(w1,w2));
    }
}

