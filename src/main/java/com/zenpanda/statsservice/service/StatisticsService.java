package com.zenpanda.statsservice.service;

import com.zenpanda.statsservice.dao.InMemoryStatisticsDao;
import com.zenpanda.statsservice.model.Statistics;
import com.zenpanda.statsservice.model.Transaction;
import com.zenpanda.statsservice.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;

@Slf4j
@Service
public class StatisticsService {

    @Autowired
    private InMemoryStatisticsDao inMemoryStatisticsDao;
    @Autowired
    private TimeUtil timeUtil;
    private DoubleSummaryStatistics statisticsResult;

    StatisticsService() {
        statisticsResult = new DoubleSummaryStatistics();
    }

    /**
     * For the first time, newly initialized stats is return.
     * Hence forth it returns precomputed statistics which is stored in memory
     * This is done in O(1) time & space.
     * @return
     */
    public Statistics getStatistics() {

        if (inMemoryStatisticsDao.getSize() == 0)
            return new Statistics();

        return new Statistics(statisticsResult);
    }

    /**
     * Uses the given transaction for the calculation of new resultant statistics which is stored in memory.
     * Also the updated stats is stored in the map of <seconds, stats> based upon timestamp in seconds of the transaction.
     * @param transaction
     * @return
     */
    public Statistics updateStatistics(Transaction transaction) {

        long key = timeUtil.convertMillisecondsToSeconds(transaction.getTimestamp());
        DoubleSummaryStatistics doubleSummaryStatistics;

        if (inMemoryStatisticsDao.contains(key))
            // get existing stats which is present under the key
            doubleSummaryStatistics = inMemoryStatisticsDao.get(key);
        else
            // create new stats which for the given key
            doubleSummaryStatistics = new DoubleSummaryStatistics();

        doubleSummaryStatistics.accept(transaction.getAmount());
        inMemoryStatisticsDao.add(key, doubleSummaryStatistics);
        synchronized (this) {
            statisticsResult.accept(transaction.getAmount());
        }

        return new Statistics(statisticsResult);
    }

    /**
     * This is a scheduled task which runs every second and clears the
     * stored stats from dao which  is older than 60 seconds.
     * @return
     */
    @Async
    @Scheduled(fixedRate = 1000)
    public DoubleSummaryStatistics clearStaleStatistics() {

        log.info("*** Scheduled job running to clear stale statistics ***");
        // get key for records which has passed 60 seconds window
        long staleKey = timeUtil.currentTimeInSeconds() - 60;

        if (inMemoryStatisticsDao.contains(staleKey)) {
            inMemoryStatisticsDao.remove(staleKey);
            synchronized (this) {
                statisticsResult = calculateStatisticsResult();
            }
        }

        return statisticsResult;
    }

    /**
     * Recalculates the resultant stats based upon all the stored stats from dao.
     * @return
     */
    private DoubleSummaryStatistics calculateStatisticsResult() {
        DoubleSummaryStatistics newResult = new DoubleSummaryStatistics();
        inMemoryStatisticsDao.getAll().stream().forEach(d -> newResult.combine(d));
        return newResult;
    }
}
