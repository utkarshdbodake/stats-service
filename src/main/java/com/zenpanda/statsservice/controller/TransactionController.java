package com.zenpanda.statsservice.controller;

import com.zenpanda.statsservice.model.Transaction;
import com.zenpanda.statsservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity saveTransaction(@RequestBody Transaction transaction) {

        // If transaction is older than 60 seconds, then send http status code 204
        if (!transactionService.isTransactionValid(transaction))
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
