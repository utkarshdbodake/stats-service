package com.zenpanda.statsservice.controller;

import com.zenpanda.statsservice.model.Statistics;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public Statistics getStatistics() {

        return new Statistics(1.1, 2.0, 3.0, 4.0, 5l);
    }
}
