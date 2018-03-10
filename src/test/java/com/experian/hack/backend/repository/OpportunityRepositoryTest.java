package com.experian.hack.backend.repository;

import com.experian.hack.backend.node.Opportunity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OpportunityRepositoryTest {

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
    private OpportunityRepository repo;

    @Test
    public void shouldSaveOpportunity() {
        Opportunity golden = new Opportunity().setDescription("Once in a lifetime!");
        assertThat(golden.getId(), is(nullValue()));

        golden = repo.save(golden);
        assertThat(golden.getId(), is(notNullValue()));
    }
}