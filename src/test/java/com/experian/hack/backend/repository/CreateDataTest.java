package com.experian.hack.backend.repository;

import com.experian.hack.backend.node.Customer;
import com.experian.hack.backend.node.Opportunity;
import com.experian.hack.backend.node.Worker;
import com.github.javafaker.Faker;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.util.IterableUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDateTime.parse;


//@Ignore
@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateDataTest {

    @Autowired
    private OpportunityRepository opportunities;
    @Autowired
    private WorkerRepository workers;
    @Autowired
    private CustomerRepository customers;

    public void deleteGraph() {
        workers.deleteAll();
        opportunities.deleteAll();
        customers.deleteAll();
    }

    public void createCustomers() {
        Faker faker = new Faker(Locale.UK, new Random(23081978L));
        customers.deleteAll();
        for (int i = 10; i-- > 0; ) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            customers.save(new Customer()
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setEmail(String.format("%s.%s@%s", firstName, lastName, faker.internet().domainName()))
                    .setPhone("+447966064531")
            );
        }
    }

    public void createOpportunities() {
        Faker faker = new Faker(Locale.UK, new Random(11032018L));
        Date start = Date.from(LocalDateTime.parse("2018-03-01T00:00").toInstant(ZoneOffset.UTC));
        opportunities.deleteAll();
        for (int i = 200; i-- > 0; ) {
            opportunities.save(new Opportunity()
                    .setDescription(String.format("%s %s",
                            faker.options().option("light", "medium", "heavy"),
                            faker.options().option("soiling", "odour", "flooding")
                    ))
                    .setStart(faker.date().future(28, TimeUnit.DAYS, start).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .setLocation(faker.address().fullAddress())
                    .setValue(faker.number().numberBetween(10, 15))
                    .setLatitude(faker.address().latitude())
                    .setLongitude(faker.address().longitude())
                    .setClearance(faker.number().numberBetween(0, 5))
            );
        }
    }

    public void createWorkers() {
        Faker faker = new Faker(Locale.UK, new Random(260685L));
        workers.deleteAll();
        for (int i = 15; i-- > 0; ) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            workers.save(new Worker()
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setEmail(String.format("%s.%s@%s", firstName, lastName, faker.internet().domainName()))
                    .setPhone("+447966064531")
                    .setLatitude(faker.address().latitude())
                    .setLongitude(faker.address().longitude())
                    .setClearance(faker.number().numberBetween(0, 5)));
        }
    }

    public void linkOpportunitiesToCustomers() {
        Random random = new Random(23052018L);
        List<Customer> customers = IterableUtils.toList(this.customers.findAll());
        opportunities.findAll().forEach(c -> customers.get(random.nextInt(customers.size())).create(c));
        this.customers.saveAll(customers);
    }

    public void linkOpportunitiesToWorkers() {
        Random random = new Random(3081981L);
        List<Worker> workers = IterableUtils.toList(this.workers.findAll());
        opportunities.findAll().forEach(o -> workers.get(random.nextInt(workers.size())).assignTo(o));
        this.workers.saveAll(workers);
    }

    public void createNodes() {
        createCustomers();
        createOpportunities();
        createWorkers();
    }

    public void createRelationships() {
        linkOpportunitiesToCustomers();
        linkOpportunitiesToWorkers();
    }

    @Test
    public void createGraph() {
        deleteGraph();
        createNodes();
        createRelationships();
    }
}

