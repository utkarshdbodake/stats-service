package com.zenpanda.statsservice.dao;

import org.junit.Assert;
import org.junit.Test;

import java.util.DoubleSummaryStatistics;

public class InMemoryStatisticsDaoTest {

    /**
     * Should be able to insert a single record successfully.
     */
    @Test
    public void shouldBeAbleToInsert() {
        InMemoryStatisticsDao inMemoryStatisticsDao = new InMemoryStatisticsDao();
        inMemoryStatisticsDao.add(InMemoryStatisticsDaoFixture.keyInSeconds1, InMemoryStatisticsDaoFixture.doubleSummaryStatistics1);
        Assert.assertEquals(
                InMemoryStatisticsDaoFixture.doubleSummaryStatistics1,
                inMemoryStatisticsDao.get(InMemoryStatisticsDaoFixture.keyInSeconds1)
        );
        Assert.assertEquals(1, inMemoryStatisticsDao.getAll().size());
    }

    /**
     * Should be able to delete a single record successfully.
     */
    @Test
    public void shouldBeAbleToDelete() {
        InMemoryStatisticsDao inMemoryStatisticsDao = new InMemoryStatisticsDao();
        inMemoryStatisticsDao.add(InMemoryStatisticsDaoFixture.keyInSeconds1, InMemoryStatisticsDaoFixture.doubleSummaryStatistics1);
        inMemoryStatisticsDao.remove(InMemoryStatisticsDaoFixture.keyInSeconds1);
        Assert.assertEquals(0, inMemoryStatisticsDao.getAll().size());
    }

    /**
     * Should be able to get a single record successfully.
     */
    @Test
    public void shouldBeAbleToGet() {
        InMemoryStatisticsDao inMemoryStatisticsDao = new InMemoryStatisticsDao();
        inMemoryStatisticsDao.add(InMemoryStatisticsDaoFixture.keyInSeconds1, InMemoryStatisticsDaoFixture.doubleSummaryStatistics1);
        DoubleSummaryStatistics actualResult = inMemoryStatisticsDao.get(InMemoryStatisticsDaoFixture.keyInSeconds1);
        Assert.assertEquals(InMemoryStatisticsDaoFixture.doubleSummaryStatistics1, actualResult);
    }

    /**
     * Should be able to get all the records successfully.
     */
    @Test
    public void shouldBeAbleToGetAll() {
        InMemoryStatisticsDao inMemoryStatisticsDao = new InMemoryStatisticsDao();
        inMemoryStatisticsDao.add(InMemoryStatisticsDaoFixture.keyInSeconds1, InMemoryStatisticsDaoFixture.doubleSummaryStatistics1);
        inMemoryStatisticsDao.add(InMemoryStatisticsDaoFixture.keyInSeconds2, InMemoryStatisticsDaoFixture.doubleSummaryStatistics2);
        int actualResult = inMemoryStatisticsDao.getAll().size();
        Assert.assertEquals(2, actualResult);
    }

    /**
     * When the search key is present in the map.
     */
    @Test
    public void shouldBeAbleToSearch1() {
        InMemoryStatisticsDao inMemoryStatisticsDao = new InMemoryStatisticsDao();
        inMemoryStatisticsDao.add(InMemoryStatisticsDaoFixture.keyInSeconds1, InMemoryStatisticsDaoFixture.doubleSummaryStatistics1);
        boolean actualResult = inMemoryStatisticsDao.contains(InMemoryStatisticsDaoFixture.keyInSeconds1);
        Assert.assertTrue(actualResult);
    }

    /**
     * When the search key is not present in the map.
     */
    @Test
    public void shouldBeAbleToSearch2() {
        InMemoryStatisticsDao inMemoryStatisticsDao = new InMemoryStatisticsDao();
        inMemoryStatisticsDao.add(InMemoryStatisticsDaoFixture.keyInSeconds1, InMemoryStatisticsDaoFixture.doubleSummaryStatistics1);
        boolean actualResult = inMemoryStatisticsDao.contains(InMemoryStatisticsDaoFixture.keyInSeconds2);
        Assert.assertFalse(actualResult);
    }
}
