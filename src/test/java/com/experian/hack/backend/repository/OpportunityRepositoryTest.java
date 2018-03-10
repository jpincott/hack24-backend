package com.experian.hack.backend.repository;

import com.experian.hack.backend.node.Opportunity;
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
public class OpportunityRepositoryTest {

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