package com.zenpanda.statsservice.service;

import com.zenpanda.statsservice.model.Statistics;
import com.zenpanda.statsservice.model.Transaction;
import com.zenpanda.statsservice.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;

@Service
public class StatisticsService {

    DoubleSummaryStatistics statisticsResult;
    @Autowired
    TimeUtil timeUtil;

    StatisticsService() {
        statisticsResult = new DoubleSummaryStatistics();
    }

    public Statistics getStatistics() {
        return new Statistics(1.1, 2.0, 3.0, 4.0, 5l);
    }

    public void updateStatistics(Transaction transaction) {
        System.out.println("test");
    }
}
