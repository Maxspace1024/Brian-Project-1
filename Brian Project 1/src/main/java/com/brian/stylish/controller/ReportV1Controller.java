package com.brian.stylish.controller;

import com.brian.stylish.service.OrderService;
import com.brian.stylish.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0")
public class ReportV1Controller {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ResponseUtils responseUtils;

    @GetMapping("/report/payments")
    public ResponseEntity<?> reportPayments() {
        return responseUtils.responseData(HttpStatus.OK, orderService.findAllAggregatePaymentList());
    }
}
