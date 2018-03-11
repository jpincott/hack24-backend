package com.experian.hack.backend.repository;

import com.experian.hack.backend.node.Opportunity;
import com.experian.hack.backend.node.Worker;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Set;

import static java.time.LocalDateTime.parse;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
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

    @After
    public void tearDown() {
        opportunities.deleteAll();
        workers.deleteAll();
        customers.deleteAll();
    }

    @Test
    public void testStartBetweenAndAssignedTo() {

        Opportunity o1 = this.opportunities.save(new Opportunity().setDescription("test1").setStart(parse("2017-03-10T10:00")).setValue(1));
        Opportunity o2 = this.opportunities.save(new Opportunity().setDescription("test2").setStart(parse("2017-03-10T12:00")).setValue(2));
        Opportunity o3 = this.opportunities.save(new Opportunity().setDescription("test3").setStart(parse("2017-03-10T14:00")).setValue(4));

        Worker w1 = this.workers.save(new Worker().setEmail("w1@example.com"));
        Worker w2 = this.workers.save(new Worker().setEmail("w2@example.com"));

        Set<Opportunity> found = this.opportunities.findByStartIsBetween(parse("2017-03-10T11:00"), parse("2017-03-10T15:00"));
        assertThat(found, hasSize(2));

        found = this.opportunities.findByStartIsBetweenAndWorkerEmail("2017-03-10T11:00", "2017-03-10T15:00", "w1@example.com");
        int value = this.opportunities.sumValueByStartIsBetweenAndWorkerEmail("2017-03-10T11:00", "2017-03-10T15:00", "w1@example.com");
        assertThat(found, hasSize(0));
        assertThat(value, is(0));

        w1.assignTo(o2);
        w1 = workers.save(w1);
        found = this.opportunities.findByStartIsBetweenAndWorkerEmail("2017-03-10T11:00", "2017-03-10T15:00", "w1@example.com");
        value = this.opportunities.sumValueByStartIsBetweenAndWorkerEmail("2017-03-10T11:00", "2017-03-10T15:00", "w1@example.com");
        assertThat(found, hasSize(1));
        assertThat(value, is(2));

        this.opportunities.deleteAll(Arrays.asList(o1,o2,o3));
        this.workers.deleteAll(Arrays.asList(w1,w2));
    }

    @Test
    public void findWorkersByOpportunity() {
        Opportunity opportunity = new Opportunity();

        opportunity = opportunities.save(opportunity);

        Worker worker = new Worker().setEmail("email@example.com").setPhone("123456789");

        worker.assignTo(opportunity);

        worker = workers.save(worker);

        assertEquals(worker.getPhone(), workers.findPhoneNumberByOpportunityId(opportunity.getId()));
    }
}

