package com.reactive.world.covidalertservice.service;

import com.reactive.world.covidalertservice.dto.AlertStatus;
import com.reactive.world.covidalertservice.dto.StateData;
import com.reactive.world.covidalertservice.dto.SummaryData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
class AlertServiceTest {

    @InjectMocks
    private AlertService alertService;

    @Mock
    private Covid19DataProvider covid19DataProvider;


    @Test
    @DisplayName(value = "When total number of confirmed cases are less than 1000")
        void getAlertAboutStateTestGreen() {
        StateData stateData = new StateData();
        stateData.setTotalConfirmed(900);

        when(covid19DataProvider.getStateData(anyString())).thenReturn(stateData);

        AlertStatus alertStatus = alertService.getAlertAboutState(anyString());

        assertThat(alertStatus.getAlertLevel()).isEqualTo("GREEN");
        assertThat(alertStatus.getMeasuresToBeTaken()).isEqualTo(Collections.singletonList("Everything is Normal !!"));
        assertThat(alertStatus.getSummaryData()).isEqualTo(stateData);

        verify(covid19DataProvider, times(1)).getStateData(anyString());

    }

    @Test
    @DisplayName(value = "When total number of confirmed cases are greater than 1000 and less than 10000")
    //@RepeatedTest(value = 4)
    void getAlertAboutStateTestOrange() {
        StateData stateData = new StateData();
        stateData.setTotalConfirmed(1200);

        when(covid19DataProvider.getStateData(anyString())).thenReturn(stateData);

        AlertStatus alertStatus = alertService.getAlertAboutState(anyString());

        assertThat(alertStatus.getAlertLevel()).isEqualTo("ORANGE");
        assertThat(alertStatus.getMeasuresToBeTaken()).isEqualTo(Arrays.asList("Only Essential services are allowed", "List of services that come under essential service"));
        assertThat(alertStatus.getSummaryData()).isEqualTo(stateData);

        verify(covid19DataProvider, times(1)).getStateData(anyString());

    }

    @Test
    @DisplayName(value = "When total number of confirmed cases are greater than 10000")
    void getAlertAboutStateTestRed() {
        StateData stateData = new StateData();
        stateData.setTotalConfirmed(12000);

        when(covid19DataProvider.getStateData(anyString())).thenReturn(stateData);

        AlertStatus alertStatus = alertService.getAlertAboutState(anyString());

        assertThat(alertStatus.getAlertLevel()).isEqualTo("RED");
        assertThat(alertStatus.getMeasuresToBeTaken()).isEqualTo(Arrays.asList("Absolute lock-down", "Only Medical and food services are allowed here"));
        assertThat(alertStatus.getSummaryData()).isEqualTo(stateData);

        verify(covid19DataProvider).getStateData(anyString());

    }

    @Test
    void getOverAllSummary() {

        SummaryData summaryData = new SummaryData();
        summaryData.setUpdateTime(ZonedDateTime.now());
        summaryData.setConfirmedButLocationUnidentified(10);
        summaryData.setConfirmedCasesForeign(1);
        summaryData.setConfirmedCasesIndian(1000);
        summaryData.setDischarged(20);
        summaryData.setDeaths(2);
        summaryData.setTotal(1011);

        when(covid19DataProvider.getSummaryData()).thenReturn(summaryData);

        SummaryData summary = alertService.getOverAllSummary();

        assertThat(summary).isEqualTo(summaryData);

    }
}