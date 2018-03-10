package com.experian.hack.backend.repository;

import com.experian.hack.backend.node.Worker;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WorkerRepositoryTest {

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public SessionFactory getSessionFactory() {
            return new SessionFactory( "com.experian.hack.backend");
        }

        @Bean
        public Neo4jTransactionManager transactionManager() {
            return new Neo4jTransactionManager(getSessionFactory());
        }
    }

    @Autowired
    private WorkerRepository repo;

    @After
    public void cleanUp() {
        repo.deleteAll();
    }

    @Test
    public void shouldSaveWorker() {
        Worker alan = new Worker().setFirstName("Alan").setLastName("Alanson").setEmail("alan@alanson.org");
        assertThat(alan.getId(), is(nullValue()));

        alan = repo.save(alan);
        assertThat(alan.getId(), is(notNullValue()));
    }

    @Test
    public void shouldFindByEmail() {

        repo.save(new Worker().setEmail("me@me.com"));

        String email = "me@me.com";

        Optional<Worker> worker = repo.findByEmail(email);

        assertThat(worker.isPresent(), is(true));
        assertThat(worker.get().getEmail(), is(email));
    }
}