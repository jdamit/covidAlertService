package com.reactive.world.covidalertservice.service;

import com.reactive.world.covidalertservice.dto.CovidApiData;
import com.reactive.world.covidalertservice.dto.StateData;
import com.reactive.world.covidalertservice.dto.SummaryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class Covid19DataProvider {

    @Autowired
    RestTemplate restTemplate;

    static final String URL = "https://api.rootnet.in/covid19-in/stats/latest";

    public StateData getStateData(String state) {

        CovidApiData covidApiData = restTemplate.getForObject(URL, CovidApiData.class); //NOSONAR
        if(covidApiData != null){
            return Arrays.stream(covidApiData.getData().getRegional()).filter(
                    s -> s.getLoc().equalsIgnoreCase(state)).findAny().orElse(new StateData());
        }
        return null;
    }

    public SummaryData getSummaryData() {

        CovidApiData covidApiData = restTemplate.getForObject(URL, CovidApiData.class); //NOSONAR

        if(covidApiData != null){
            SummaryData summary = covidApiData.getData().getSummary();
            summary.setUpdateTime(covidApiData.getLastRefreshed());
            return summary;
        }
        return null;
    }


}
