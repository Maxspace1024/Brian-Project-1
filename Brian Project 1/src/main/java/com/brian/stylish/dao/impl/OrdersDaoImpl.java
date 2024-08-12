package com.brian.stylish.dao.impl;

import com.brian.stylish.dao.OrdersDao;
import com.brian.stylish.dto.OrderDTO;
import com.brian.stylish.dto.RecipientDTO;
import com.brian.stylish.dto.ReportPaymentDTO;
import com.brian.stylish.dto.tappay.TappayResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrdersDaoImpl implements OrdersDao {

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Override
    public Integer createOrders(OrderDTO dto, Integer userId) {
        String sql = "INSERT INTO orders (id, user_id, create_timestamp, shipping, payment, subtotal, freight, total, rec_trade_id, bank_transaction_id) VALUES " +
            "(default, :user_id, now(), :shipping, :payment, :subtotal, :freight, :total, '', '')";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId)
            .addValue("shipping", dto.getShipping())
            .addValue("payment", dto.getPayment())
            .addValue("subtotal", dto.getSubtotal())
            .addValue("freight", dto.getFreight())
            .addValue("total", dto.getTotal());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, params, keyHolder, new String[]{"id"});

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }

    @Override
    public Integer createRecipient(RecipientDTO dto, Integer ordersId) {
        String sql = "INSERT INTO recipient (id, orders_id, name, phone, email, address, time) VALUES (default, :orders_id, :name, :phone, :email, :address, :time)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("orders_id", ordersId)
            .addValue("name", dto.getName())
            .addValue("phone", dto.getPhone())
            .addValue("email", dto.getEmail())
            .addValue("address", dto.getAddress())
            .addValue("time", dto.getTime());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, params, keyHolder, new String[]{"id"});

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }

    @Override
    public Integer createOrderDetail(Integer ordersId, Integer productId, Integer variantId, Integer quantity) {
        String sql = "INSERT INTO order_details (id, orders_id, product_id, variant_id, quantity) VALUES (default, :orders_id, :product_id, :variant_id, :quantity)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("orders_id", ordersId)
            .addValue("product_id", productId)
            .addValue("variant_id", variantId)
            .addValue("quantity", quantity);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, params, keyHolder, new String[]{"id"});

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }

    @Override
    public Integer updateOrderById(Integer orderId, TappayResultDTO dto) {
        String sql = "UPDATE orders SET rec_trade_id = :rec_trade_id, bank_transaction_id = :bank_transaction_id WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        params.addValue("id", orderId)
            .addValue("rec_trade_id", dto.getRecTradeId())
            .addValue("bank_transaction_id", dto.getBankTransactionId());

        template.update(sql, params, keyHolder, new String[]{"id"});

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }

    @Override
    public List<ReportPaymentDTO> findAllPaymentList() {
        String sql = "SELECT * FROM orders";
        MapSqlParameterSource params = new MapSqlParameterSource();

        return template.query(sql, params, (rs, num) ->
            ReportPaymentDTO.builder()
                .userId(rs.getInt("user_id"))
                .totalPayment(rs.getInt("total"))
                .build()
        );
    }
}
