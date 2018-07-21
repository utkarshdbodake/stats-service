package com.zenpanda.statsservice.service;

import com.zenpanda.statsservice.model.Transaction;
import com.zenpanda.statsservice.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TimeUtil timeUtil;

    /**
     * If transaction timestamp is older than 60 seconds OR If transaction timestamp is
     * greater than the server's current timestamp; then return false else true.
     * @param transaction
     * @return
     */
    public boolean isTransactionValid(Transaction transaction) {

        long currentTimestamp = timeUtil.currentTimestamp();
        long transactionTimestamp = transaction.getTimestamp();
        long differenceInMilliseconds = currentTimestamp - transactionTimestamp;

        // checks if the difference in seconds lies within a minute's range.
        return differenceInMilliseconds >= 0 && differenceInMilliseconds <= 60000;
    }
}
