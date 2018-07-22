package com.zenpanda.statsservice.controller;

import com.zenpanda.statsservice.model.Transaction;
import java.time.Instant;

public class TransactionControllerFixture {

    private static final long timestampWithinAMinuteInMilliseonds = Instant.now().toEpochMilli() - 10000;
    static final Transaction transactionWithinAMinute = new Transaction(10, timestampWithinAMinuteInMilliseonds);

    private static final long timestampOlderThanAMinuteInMilliseonds = Instant.now().toEpochMilli() - 65000;
    static final Transaction transactionOlderThanAMinute =
            new Transaction(20, timestampOlderThanAMinuteInMilliseonds);
}
