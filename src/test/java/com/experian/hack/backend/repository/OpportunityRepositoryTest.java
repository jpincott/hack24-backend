package com.experian.hack.backend.repository;

import com.experian.hack.backend.node.Opportunity;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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
    public void testStartBetween() {

        List<Opportunity> saved = new ArrayList<>(Arrays.asList(
                repo.save(new Opportunity().setDescription("test1").setStart(LocalDateTime.parse("2016-03-10T10:00"))),
                repo.save(new Opportunity().setDescription("test2").setStart(LocalDateTime.parse("2016-03-10T12:00"))),
                repo.save(new Opportunity().setDescription("test3").setStart(LocalDateTime.parse("2016-03-10T14:00")))
        ));

        Set<Opportunity> found = repo.findByStartIsBetween(LocalDateTime.parse("2016-03-10T11:00"), LocalDateTime.parse("2016-03-10T13:00"));
        repo.deleteAll(saved);

        assertThat(found, hasSize(1));
        assertThat(found.iterator().next().getDescription(), is("test2"));
    }

}