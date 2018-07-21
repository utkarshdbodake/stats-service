package com.zenpanda.statsservice.controller;

import com.zenpanda.statsservice.model.Statistics;
import com.zenpanda.statsservice.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public Statistics getStatistics() {
        return statisticsService.getStatistics();
    }
}
