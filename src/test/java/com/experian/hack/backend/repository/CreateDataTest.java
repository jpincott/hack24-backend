package com.experian.hack.backend.repository;

import com.experian.hack.backend.node.Opportunity;
import com.experian.hack.backend.node.Worker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateDataTest {

    @Autowired
    private OpportunityRepository opportinuties;
    @Autowired
    private WorkerRepository workers;

    @Test
    public void shouldSaveData() {

        Worker alan = new Worker().setFirstName("Alan").setLastName("Alanson").setEmail("alan@alanson.org");
        Worker bob = new Worker().setFirstName("Bob").setLastName("Bobson").setEmail("bob@bobson.org");
        Worker clive = new Worker().setFirstName("Clive").setLastName("Cliveson").setEmail("clive@cliveson.org");

        Opportunity pub = new Opportunity().setDescription("The local pub");
        Opportunity house = new Opportunity().setDescription("Someone's house");
        Opportunity school = new Opportunity().setDescription("A school");

        alan.assignTo(pub);

        workers.save(alan);
    }

}

