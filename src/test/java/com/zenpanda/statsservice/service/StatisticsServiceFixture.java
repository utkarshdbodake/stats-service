package com.zenpanda.statsservice.service;

import com.zenpanda.statsservice.model.Statistics;
import com.zenpanda.statsservice.model.Transaction;

import java.time.Instant;
import java.util.DoubleSummaryStatistics;

public class StatisticsServiceFixture {

    static final Statistics statistics1 = new Statistics(0, 0, 0, 0, 0);

    private static final long amount1 = 10;
    private static final long timestampWithinAMinuteInMilliseonds = Instant.now().toEpochMilli() - 10000;
    static final Transaction transactionWithinAMinute = new Transaction(amount1, timestampWithinAMinuteInMilliseonds);
    static final Statistics statistics2 = new Statistics(10, 10, 10, 10, 1);

    private static final long amount2 = 20;
    static final DoubleSummaryStatistics doubleSummaryStatistics = new DoubleSummaryStatistics();
    static final Statistics statistics3 = new Statistics(30, 10, 10, 10, 3);

    static final long staleTimestamp = 20l;
    static final Transaction staleTransaction = new Transaction(10, staleTimestamp);
    static final Statistics statistics4 = new Statistics(20, 10, 10, 10, 2);

    static {
        doubleSummaryStatistics.accept(amount2);
    }
}
