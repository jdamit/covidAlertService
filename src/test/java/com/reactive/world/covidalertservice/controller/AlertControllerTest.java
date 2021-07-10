package com.reactive.world.covidalertservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactive.world.covidalertservice.dto.AlertStatus;
import com.reactive.world.covidalertservice.dto.SummaryData;
import com.reactive.world.covidalertservice.service.AlertService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AlertController.class)
/*@SpringBootTest
@AutoConfigureMockMvc*/
class AlertControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AlertService alertService;

    @Test
    @DisplayName(value = "Covid alert about state")
    void getAlertAboutStateTest() throws Exception {
        String url = "/india/delhi";
        AlertStatus alertStatus = new AlertStatus();
        alertStatus.setAlertLevel("GREEN");
        String alertStatusJson = objectMapper.writeValueAsString(alertStatus);

        Mockito.when(alertService.getAlertAboutState(ArgumentMatchers.anyString())).thenReturn(alertStatus);

        MvcResult response = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(response).isNotNull();
        assertThat(response.getResponse().getContentAsString()).isNotNull().isEqualTo(alertStatusJson);
    }

    @Test
    @DisplayName(value = "Over all summary")
    void getOverAllSummaryTest() throws Exception {
        String url = "/india/summary";
        SummaryData summaryData = new SummaryData();
        String summaryDataJson = objectMapper.writeValueAsString(summaryData);

        Mockito.when(alertService.getOverAllSummary()).thenReturn(summaryData);

        MvcResult response = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(response).isNotNull();
        assertThat(response.getResponse().getContentAsString()).isNotNull().isEqualToIgnoringCase(summaryDataJson);
    }

    @Test
    @DisplayName(value = "Invalid end points")
    void invalidEndPoint() throws Exception {
        String url = "/india14252";

        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}