package com.experian.hack.backend.web;

import com.experian.hack.backend.repository.WorkerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Controller.class)
@RunWith(SpringRunner.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WorkerRepository mockRepository;

    @Test
    public void testGetMyJobs() throws Exception {

        Mockito.when(mockRepository.findByEmail("email@example.com")).thenReturn(Optional.empty());

        mvc.perform(get("/workers/me/jobs")
                .header("email", "email@example.com"))
                .andDo(print())
                .andExpect(status().isOk());

    }
}