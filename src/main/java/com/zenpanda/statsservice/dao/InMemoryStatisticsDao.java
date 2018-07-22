package com.zenpanda.statsservice.dao;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryStatisticsDao {

    private ConcurrentHashMap<Long, DoubleSummaryStatistics> secondToStatisticsMap;

    InMemoryStatisticsDao() {
        secondToStatisticsMap = new ConcurrentHashMap<>();
    }

    public int getSize() {
        return secondToStatisticsMap.size();
    }

    public boolean contains(long key) {
        return secondToStatisticsMap.containsKey(key);
    }

    public DoubleSummaryStatistics get(long key) {
        return secondToStatisticsMap.get(key);
    }

    public List<DoubleSummaryStatistics> getAll() {
        return new ArrayList<>(secondToStatisticsMap.values());
    }

    public void add(long key, DoubleSummaryStatistics value) {
        secondToStatisticsMap.put(key, value);
    }

    public void remove(long key) {
        secondToStatisticsMap.remove(key);
    }
}
