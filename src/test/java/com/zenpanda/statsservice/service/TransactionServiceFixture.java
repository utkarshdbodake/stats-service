package com.zenpanda.statsservice.service;

import com.zenpanda.statsservice.model.Transaction;

import java.time.Instant;

public class TransactionServiceFixture {

    private static final long timestampWithinAMinuteInMilliseonds = Instant.now().toEpochMilli() - 10000;
    static final Transaction transactionWithinAMinute = new Transaction(10, timestampWithinAMinuteInMilliseonds);

    private static final long timestampOlderThanAMinuteInMilliseonds = Instant.now().toEpochMilli() - 65000;
    static final Transaction transactionOlderThanAMinute =
            new Transaction(20, timestampOlderThanAMinuteInMilliseonds);

    private static final long timestampOfFutureInMilliseonds = Instant.now().toEpochMilli() + 65000;
    static final Transaction transactionOfFuture=
            new Transaction(20, timestampOfFutureInMilliseonds);
}
