package com.brian.stylish.dao;

import com.brian.stylish.dto.OrderDTO;
import com.brian.stylish.dto.RecipientDTO;
import com.brian.stylish.dto.ReportPaymentDTO;
import com.brian.stylish.dto.tappay.TappayResultDTO;

import java.util.List;

public interface OrdersDao {
    Integer createOrders(OrderDTO dto, Integer userId);

    Integer createRecipient(RecipientDTO dto, Integer ordersId);

    Integer createOrderDetail(Integer ordersId, Integer productId, Integer variantId, Integer quantity);

    Integer updateOrderById(Integer orderId, TappayResultDTO dto);

    List<ReportPaymentDTO> findAllPaymentList();
}
