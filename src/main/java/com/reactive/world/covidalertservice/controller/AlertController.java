package com.reactive.world.covidalertservice.controller;

import com.reactive.world.covidalertservice.dto.AlertStatus;
import com.reactive.world.covidalertservice.dto.SummaryData;
import com.reactive.world.covidalertservice.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping(path = "/india/")
public class AlertController {

    @Autowired
    AlertService alertService;

    @GetMapping(path = "/{state}")
    public AlertStatus getAlertAboutState(@PathVariable String state) {
        return alertService.getAlertAboutState(state);
    }

    @GetMapping("/summary")
    public SummaryData getOverAllSummary() {
        return alertService.getOverAllSummary();
    }
}
