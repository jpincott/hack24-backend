package com.experian.hack.backend.web;

import com.experian.hack.backend.node.Opportunity;
import com.experian.hack.backend.repository.OpportunityRepository;
import com.experian.hack.backend.repository.WorkerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkerController.class)
@RunWith(SpringRunner.class)
public class WorkerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WorkerRepository mockRepository;

    @MockBean
    private OpportunityRepository mockOpportunities;

    @Test
    public void testGetMyJobs() throws Exception {

        Mockito.when(mockRepository.findByEmail("email@example.com")).thenReturn(Optional.empty());

        mvc.perform(get("/workers/me/jobs")
                .header("email", "email@example.com"))
                .andDo(print())
                .andExpect(status().isOk());

    }
}