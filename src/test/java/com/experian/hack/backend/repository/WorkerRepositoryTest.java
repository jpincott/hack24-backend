package com.experian.hack.backend.repository;

import com.experian.hack.backend.node.Worker;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Autowired
    private WorkerRepository repo;

    @Test
    public void shouldFindByEmail() {

        String email = "email@example.com";
        repo.save(new Worker().setEmail(email));

        Optional<Worker> found = repo.findByEmail(email);
        assertThat(found.isPresent(), is(true));
        assertThat(found.get().getEmail(), is(email));
        assertThat(found.get().getJobs(), hasSize(0));

        found.ifPresent(repo::delete);
    }
}