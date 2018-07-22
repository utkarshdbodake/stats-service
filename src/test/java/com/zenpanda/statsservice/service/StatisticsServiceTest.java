package com.zenpanda.statsservice.service;

import com.zenpanda.statsservice.dao.InMemoryStatisticsDao;
import com.zenpanda.statsservice.model.Statistics;
import com.zenpanda.statsservice.model.Transaction;
import com.zenpanda.statsservice.util.TimeUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {

    @Mock
    private TimeUtil timeUtil;
    @Mock
    private InMemoryStatisticsDao inMemoryStatisticsDao;
    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    public void shouldBeAbleToGetNewlyInitializedStatistics() {
        when(inMemoryStatisticsDao.getSize()).thenReturn(0);
        Statistics actualResult = statisticsService.getStatistics();
        Assert.assertEquals(StatisticsServiceFixture.statistics1, actualResult);
    }

    /**
     * Gets statistics from the object which is already stored in memory.
     */
    @Test
    public void shouldBeAbleToGetPrecomputedStatistics() {
        when(inMemoryStatisticsDao.getSize()).thenReturn(1);
        when(inMemoryStatisticsDao.contains(Mockito.anyLong()))
                .thenReturn(false);

        Transaction transaction = StatisticsServiceFixture.transactionWithinAMinute;
        statisticsService.updateStatistics(transaction);
        statisticsService.updateStatistics(transaction);


        Statistics actualResult = statisticsService.getStatistics();
        Assert.assertEquals(StatisticsServiceFixture.statistics4, actualResult);
    }

    /**
     * Creates and updates resultant statistics with new stats for the given second in transaction.
     */
    @Test
    public void shouldUpdateStatisticsWithGivenTransaction1() {
        when(inMemoryStatisticsDao.contains(Mockito.anyLong()))
                .thenReturn(false);

        Transaction transaction = StatisticsServiceFixture.transactionWithinAMinute;
        Statistics actualResult = statisticsService.updateStatistics(transaction);

        verify(inMemoryStatisticsDao, times(1)).contains(anyLong());
        verify(inMemoryStatisticsDao, times(0)).get(anyLong());
        verify(inMemoryStatisticsDao, times(1)).add(anyLong(), any());
        verify(timeUtil, times(1)).convertMillisecondsToSeconds(anyLong());
        Assert.assertEquals(StatisticsServiceFixture.statistics2, actualResult);
    }

    /**
     * Updates resultant statistics with stats for the given second in transaction.
     */
    @Test
    public void shouldUpdateStatisticsWithGivenTransaction2() {
        when(inMemoryStatisticsDao.contains(Mockito.anyLong()))
                .thenReturn(true);
        when(inMemoryStatisticsDao.get(Mockito.anyLong()))
                .thenReturn(StatisticsServiceFixture.doubleSummaryStatistics);

        Transaction transaction = StatisticsServiceFixture.transactionWithinAMinute;

        statisticsService.updateStatistics(transaction);
        statisticsService.updateStatistics(transaction);
        Statistics actualResult = statisticsService.updateStatistics(transaction);

        verify(inMemoryStatisticsDao, times(3)).contains(anyLong());
        verify(inMemoryStatisticsDao, times(3)).get(anyLong());
        verify(inMemoryStatisticsDao, times(3)).add(anyLong(), any());
        verify(timeUtil, times(3)).convertMillisecondsToSeconds(anyLong());
        Assert.assertEquals(StatisticsServiceFixture.statistics3, actualResult);
    }

    /**
     * Clears the stale statistics and recomputes the result successfully.
     */
    @Test
    public void shouldClearStaleStatisticsSuccessfully() {
        when(inMemoryStatisticsDao.getSize())
                .thenReturn(3);
        when(timeUtil.currentTimeInSeconds())
                .thenReturn(StatisticsServiceFixture.staleTimestamp + 60);
        when(inMemoryStatisticsDao.contains(Mockito.anyLong()))
                .thenReturn(true);
        when(inMemoryStatisticsDao.get(Mockito.anyLong()))
                .thenReturn(StatisticsServiceFixture.doubleSummaryStatistics);

        Transaction transaction1 = StatisticsServiceFixture.transactionWithinAMinute;
        Transaction staleTransaction = StatisticsServiceFixture.staleTransaction;
        statisticsService.updateStatistics(transaction1);
        statisticsService.updateStatistics(transaction1);
        Statistics oldStatistics = statisticsService.updateStatistics(staleTransaction);

        List<DoubleSummaryStatistics> list = new ArrayList<>();
        DoubleSummaryStatistics d1 = new DoubleSummaryStatistics();
        d1.accept(transaction1.getAmount());
        list.add(d1);
        list.add(d1);
        when(inMemoryStatisticsDao.getAll())
                .thenReturn(list);

        statisticsService.clearStaleStatistics();
        Statistics newStatistics = statisticsService.getStatistics();

        verify(timeUtil, times(1)).currentTimeInSeconds();
        verify(inMemoryStatisticsDao, times(4)).contains(anyLong());
        verify(inMemoryStatisticsDao, times(1)).remove(anyLong());
        verify(inMemoryStatisticsDao, times(1)).getAll();
        Assert.assertEquals(StatisticsServiceFixture.statistics3, oldStatistics);
        Assert.assertEquals(StatisticsServiceFixture.statistics4, newStatistics);
    }
}
