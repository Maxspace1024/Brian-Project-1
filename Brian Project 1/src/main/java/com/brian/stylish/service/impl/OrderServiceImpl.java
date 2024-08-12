package com.brian.stylish.service.impl;

import com.brian.stylish.dao.OrdersDao;
import com.brian.stylish.dao.UserDao;
import com.brian.stylish.dao.VariantDao;
import com.brian.stylish.dto.CheckoutDTO;
import com.brian.stylish.dto.ReportPaymentDTO;
import com.brian.stylish.dto.UserDTO;
import com.brian.stylish.dto.VariantFKDTO;
import com.brian.stylish.dto.tappay.CardHolderDTO;
import com.brian.stylish.dto.tappay.PayByPrimeDTO;
import com.brian.stylish.dto.tappay.TappayResultDTO;
import com.brian.stylish.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Value("${tappay.sandbox.url}")
    private String TAPPAY_SANDBOX_URL;

    @Value("${tappay.partner.id}")
    private String TAPPAY_PARTNER_ID;

    @Value("${tappay.merchant.id}")
    private String TAPPAY_MERCHANT_ID;

    @Autowired
    private VariantDao variantDao;

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    public Integer createOrder(CheckoutDTO checkoutDTO, String email) {
        UserDTO userDTO = userDao.findUserByEmail(email);
        if (userDTO == null) {
            throw new RuntimeException("create order fail 1");
        }
        Integer newOrderId = ordersDao.createOrders(checkoutDTO.getOrder(), userDTO.getId());
        if (newOrderId == null) {
            throw new RuntimeException("create order fail 2");
        }
        Integer newRecipientId = ordersDao.createRecipient(checkoutDTO.getOrder().getRecipient(), newOrderId);
        if (newRecipientId == null) {
            throw new RuntimeException("create order fail 3");
        }
        for (var detailDTO : checkoutDTO.getOrder().getList()) {
            try {
                Integer productId = Integer.parseInt(detailDTO.getId());
                VariantFKDTO variantFKDTO = variantDao.findVariantFKDTOByFK(productId, detailDTO);
                if (variantFKDTO == null) {
                    throw new RuntimeException("create order fail 4");
                }
                Integer newOrderDetailId = ordersDao.createOrderDetail(newOrderId, productId, variantFKDTO.getId(), detailDTO.getQty());
                if (newOrderDetailId == null) {
                    throw new RuntimeException("create order fail 5");
                }
            } catch (Exception e) {
                throw e;
            }
        }

        // tappay
        CardHolderDTO cardHolderDTO = CardHolderDTO.builder()
            .phoneNumber(checkoutDTO.getOrder().getRecipient().getPhone())
            .name(checkoutDTO.getOrder().getRecipient().getName())
            .email(checkoutDTO.getOrder().getRecipient().getEmail())
            .build();

        PayByPrimeDTO payByPrimeDTO = PayByPrimeDTO.builder()
            .prime(checkoutDTO.getPrime())
            .partnerKey(TAPPAY_PARTNER_ID)
            .merchantId(TAPPAY_MERCHANT_ID)
            .amount(checkoutDTO.getOrder().getTotal())
            .details("Tappay API TEST")
            .cardholder(cardHolderDTO)
            .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", TAPPAY_PARTNER_ID);

        HttpEntity<PayByPrimeDTO> requestEntity = new HttpEntity<>(payByPrimeDTO, headers);
        ResponseEntity<TappayResultDTO> responseEntity = restTemplate.exchange(TAPPAY_SANDBOX_URL, HttpMethod.POST, requestEntity, TappayResultDTO.class);

        if (responseEntity.hasBody()) {
            TappayResultDTO tappayResultDTO = responseEntity.getBody();
            if (tappayResultDTO.getStatus() != 0) {
                throw new RuntimeException("create order fail 6");
            }
            ordersDao.updateOrderById(newOrderId, tappayResultDTO);
        }


        return newOrderId;
    }

    @Override
    public List<ReportPaymentDTO> findAllAggregatePaymentList() {
        List<ReportPaymentDTO> list = ordersDao.findAllPaymentList();
        Map<Integer, ReportPaymentDTO> map = new HashMap<>();

        for (ReportPaymentDTO dto : list) {
            if (map.containsKey(dto.getUserId())) {
                ReportPaymentDTO rpdto = map.get(dto.getUserId());
                rpdto.setTotalPayment(rpdto.getTotalPayment() + dto.getTotalPayment());
                map.put(dto.getUserId(), rpdto);
            } else {
                map.put(dto.getUserId(), dto);
            }
        }

        return map.values().stream().toList();
    }

    @Override
    public Long sendMQFindAllAggregatePaymentList(String email) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        try {
            return redisTemplate.opsForList().leftPush("msgqueue", map);
        } catch (Exception e) {
            throw e;
        }
    }
}
