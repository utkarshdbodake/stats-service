package com.zenpanda.statsservice.model;

import java.util.DoubleSummaryStatistics;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Statistics {

    @NotNull
    private double sum;
    @NotNull
    private double avg;
    @NotNull
    private double max;
    @NotNull
    private double min;
    @NotNull
    private long count;

    public Statistics(DoubleSummaryStatistics doubleSummaryStatistics) {
        sum = doubleSummaryStatistics.getSum();
        avg = doubleSummaryStatistics.getAverage();
        max = doubleSummaryStatistics.getMax();
        min = doubleSummaryStatistics.getMin();
        count = doubleSummaryStatistics.getCount();
    }
}
