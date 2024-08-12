package com.brian.stylish.controller;

import com.brian.stylish.service.OrderService;
import com.brian.stylish.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/2.0")
public class ReportV2Controller {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ResponseUtils responseUtils;

    @GetMapping("/report/payments")
    public ResponseEntity<?> reportPayments(@RequestParam String email) {
        try {
            orderService.sendMQFindAllAggregatePaymentList(email);
            return responseUtils.responseData(HttpStatus.OK, "deal with request, wait a minute");
        } catch (Exception e) {
            return responseUtils.responseError(HttpStatus.INTERNAL_SERVER_ERROR, "server error");
        }
    }
}