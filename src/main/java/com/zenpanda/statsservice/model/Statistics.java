package com.zenpanda.statsservice.model;

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
    private Double sum;
    @NotNull
    private Double avg;
    @NotNull
    private Double max;
    @NotNull
    private Double min;
    @NotNull
    private Long count;
}
