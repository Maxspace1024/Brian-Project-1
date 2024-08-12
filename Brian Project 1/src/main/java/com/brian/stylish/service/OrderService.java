package com.brian.stylish.service;

import com.brian.stylish.dto.CheckoutDTO;
import com.brian.stylish.dto.ReportPaymentDTO;

import java.util.List;

public interface OrderService {
    Integer createOrder(CheckoutDTO dto, String email);

    List<ReportPaymentDTO> findAllAggregatePaymentList();

    Long sendMQFindAllAggregatePaymentList(String email);
}
