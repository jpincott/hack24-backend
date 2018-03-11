package com.experian.hack.backend.web;

import java.util.Optional;

import com.experian.hack.backend.repository.OpportunityRepository;
import com.experian.hack.backend.repository.WorkerRepository;
import esendex.sdk.java.model.domain.request.SmsMessageRequest;
import esendex.sdk.java.service.BasicServiceFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @MockBean(answer = Answers.RETURNS_DEEP_STUBS)
    private BasicServiceFactory serviceFactory;

    @Test
    public void testGetMyJobs() throws Exception {

        Mockito.when(mockRepository.findByEmail("email@example.com")).thenReturn(Optional.empty());

        mvc.perform(get("/workers/me/jobs")
                .header("email", "email@example.com")
                .param("start", "2018-03-11T00:00")
                .param("end", "2018-03-12T00:00")
        )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void testSwapRequest() throws Exception {

        Mockito.when(mockRepository.findPhoneNumberByOpportunityId(1L)).thenReturn("123456789");

        mvc.perform(post("/workers/me/swaprequest/1")
                .header("email", "email@example.com")
        )
                .andDo(print())
                .andExpect(status().isOk());

        ArgumentCaptor<SmsMessageRequest> argumentCaptor = ArgumentCaptor.forClass(SmsMessageRequest.class);

        Mockito.verify(serviceFactory.getMessagingService()).sendMessage(
                eq("email@example.com"),
                argumentCaptor.capture()
        );

        assertEquals("123456789", argumentCaptor.getValue().getTo());
    }
}