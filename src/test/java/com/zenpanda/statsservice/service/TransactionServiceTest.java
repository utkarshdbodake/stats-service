package com.zenpanda.statsservice.service;

import com.zenpanda.statsservice.model.Transaction;
import com.zenpanda.statsservice.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock
    private TimeUtil timeUtilMock;
    @InjectMocks
    private TransactionService transactionService;

    @Before
    public void initialize() {
        when(timeUtilMock.currentTimestamp()).thenReturn(Instant.now().toEpochMilli());
//        when(timeUtilMock.convertMillisecondsToSeconds(Mockito.anyLong()))
//                .thenAnswer((InvocationOnMock invocation) -> (long)invocation.getArguments()[0] / 1000);
    }

    /**
     * Transaction is successfully validated as it falls within a minute's range.
     */
    @Test
    public void shouldBeAbleToValidateTransaction() {
        Transaction transactionWithinAMinute = TransactionServiceFixture.transactionWithinAMinute;
        boolean actualResult = transactionService.isTransactionValid(transactionWithinAMinute);
        Assert.assertTrue(actualResult);
    }

    /**
     * Transaction is invalidated as it falls prior to a minute's range.
     */
    @Test
    public void shouldBeAbleToInValidateTransaction1() {
        Transaction transactionOlderThanAMinute = TransactionServiceFixture.transactionOlderThanAMinute;
        boolean actualResult = transactionService.isTransactionValid(transactionOlderThanAMinute);
        Assert.assertFalse(actualResult);
    }

    /**
     * Transaction is invalidated as the transaction's timestamp falls in future.
     */
    @Test
    public void shouldBeAbleToInValidateTransaction2() {
        Transaction transactionOfFuture = TransactionServiceFixture.transactionOfFuture;
        boolean actualResult = transactionService.isTransactionValid(transactionOfFuture);
        Assert.assertFalse(actualResult);
    }
}
