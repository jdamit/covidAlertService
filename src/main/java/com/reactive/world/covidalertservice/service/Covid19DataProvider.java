package com.reactive.world.covidalertservice.service;

import com.reactive.world.covidalertservice.dto.CovidApiData;
import com.reactive.world.covidalertservice.dto.StateData;
import com.reactive.world.covidalertservice.dto.SummaryData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@Service
public class Covid19DataProvider {

    /*private OkHttpClient okHttpClient;

    public Covid19DataProvider(OkHttpClient okHttpClient){
        this.okHttpClient = okHttpClient;
    }*/

    @Autowired
    RestTemplate restTemplate;

    static final String URL = "https://api.rootnet.in/covid19-in/stats/latest";

    public StateData getStateData(String state) throws IOException {
        /*Request request = new Request.Builder().url(URL).build();
        Response response = okHttpClient.newCall(request).execute();*/

        CovidApiData covidApiData = restTemplate.getForObject(URL, CovidApiData.class);

        return Arrays.stream(covidApiData.getData().getRegional()).filter(
                s -> s.getLoc().equalsIgnoreCase(state)).findAny().orElse(new StateData());
    }

    public SummaryData getSummaryData() {

        CovidApiData covidApiData = restTemplate.getForObject(URL, CovidApiData.class);

        return covidApiData.getData().getSummary();
    }


}