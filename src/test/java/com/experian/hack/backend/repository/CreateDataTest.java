package com.experian.hack.backend.repository;

import com.experian.hack.backend.node.Customer;
import com.experian.hack.backend.node.Opportunity;
import com.experian.hack.backend.node.Worker;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
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
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDateTime.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateDataTest {

    @Autowired
    private OpportunityRepository opportunities;
    @Autowired
    private WorkerRepository workers;
    @Autowired
    private CustomerRepository customers;

    @Test
    @Ignore
    public void shouldSaveData() {

        Worker alan = new Worker().setFirstName("Alan").setLastName("Alanson").setEmail("alan@alanson.org");
        Worker bob = new Worker().setFirstName("Bob").setLastName("Bobson").setEmail("bob@bobson.org");
        Worker clive = new Worker().setFirstName("Clive").setLastName("Cliveson").setEmail("clive@cliveson.org");

        Opportunity pub = new Opportunity().setDescription("The local pub").setStart(parse("2018-03-10T10:00"));
        Opportunity house = new Opportunity().setDescription("Someone's house").setStart(parse("2018-03-10T12:00"));
        Opportunity school = new Opportunity().setDescription("A school").setStart(parse("2018-03-10T14:00"));

        Customer zoe = new Customer().setFirstName("zoe").setEmail("zoe@example.com");
        Customer yolanda = new Customer().setFirstName("yolanda").setEmail("yolanda@example.com");

        zoe.create(pub);
        yolanda.create(house);
        yolanda.create(school);

        customers.save(zoe);
        customers.save(yolanda);

        alan.assignTo(pub);
        bob.assignTo(house);
        clive.assignTo(school);

        workers.save(alan);
        workers.save(bob);
        workers.save(clive);
    }

    @Test
//    @Ignore
    public void shouldDeleteAll() {
        workers.deleteAll();
        opportunities.deleteAll();
        customers.deleteAll();
    }

    @Test
    @Ignore
    public void shouldSaveData2() {

        Worker me = new Worker().setFirstName("me").setLastName("me").setEmail("me@me.com");

        me.assignTo(new Opportunity().setDescription("Office 1").setStart(LocalDateTime.parse("2018-03-10T10:00")));
        me.assignTo(new Opportunity().setDescription("Office 2").setStart(LocalDateTime.parse("2018-03-11T10:00")));
        me.assignTo(new Opportunity().setDescription("Office 3").setStart(LocalDateTime.parse("2018-03-12T10:00")));

        workers.save(me);
    }

    @Test
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
                    .setPhone(faker.phoneNumber().cellPhone())
            );
        }
    }

    @Test
    public void createOpportunities() {
        Faker faker = new Faker(Locale.UK, new Random(11032018L));
        Date start = Date.from(LocalDateTime.parse("2018-03-11T00:00").toInstant(ZoneOffset.UTC));
        opportunities.deleteAll();
        for (int i = 50; i-- > 0; ) {
            opportunities.save(new Opportunity()
                    .setDescription(faker.options().option("light", "medium", "heavy"))
                    .setStart(faker.date().future(14, TimeUnit.DAYS, start).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .setLocation(faker.address().fullAddress())
            );
        }
    }

    @Test
    public void createWorkers() {
        Faker faker = new Faker(Locale.UK, new Random(260685L));
        workers.deleteAll();
        for (int i = 10; i-- > 0; ) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            workers.save(new Worker()
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setEmail(String.format("%s.%s@%s", firstName, lastName, faker.internet().domainName()))
                    .setPhone(faker.phoneNumber().cellPhone()));
        }
    }

    @Test
    public void linkOpportunitiesToCustomers() {
        Random random = new Random(23052018L);
        List<Customer> customers = IterableUtils.toList(this.customers.findAll());
        opportunities.findAll().forEach(c -> customers.get(random.nextInt(customers.size())).create(c));
        this.customers.saveAll(customers);
    }

    @Test
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
        shouldDeleteAll();
        createNodes();
        createRelationships();
    }
}

