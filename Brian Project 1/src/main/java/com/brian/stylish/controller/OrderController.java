package com.brian.stylish.controller;

import com.brian.stylish.config.JwtTokenUtils;
import com.brian.stylish.dto.CheckoutDTO;
import com.brian.stylish.exception.NoTokenException;
import com.brian.stylish.exception.WrongTokenException;
import com.brian.stylish.service.OrderService;
import com.brian.stylish.utils.ResponseUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/api/1.0")
@Log4j2
public class OrderController {

    @Value("${tappay.sandbox.url}")
    private String TAPPAY_SANDBOX_URL;

    @Value("${tappay.partner.id}")
    private String TAPPAY_PARTNER_ID;

    @Value("${tappay.merchant.id}")
    private String TAPPAY_MERCHANT_ID;

    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/checkout")
    public ResponseEntity<?> checkout(@RequestHeader(value = "Authorization", required = false) String authorizationToken, @RequestBody @Validated CheckoutDTO checkoutDTO) {
        if (authorizationToken == null || !authorizationToken.startsWith("Bearer ")) {
            throw new NoTokenException("No Token");
        }
        try {
            authorizationToken = authorizationToken.replace("Bearer ", "");
            String email = jwtTokenUtils.extractUsername(authorizationToken);
            Integer newOrderId = orderService.createOrder(checkoutDTO, email);

            Map<String, Object> map = new HashMap<>();
            map.put("number", String.valueOf(newOrderId));
            return responseUtils.responseData(HttpStatus.OK, map);
        } catch (MalformedJwtException e) {
            throw new WrongTokenException("Wrong Token");
        } catch (ExpiredJwtException e) {
            throw new WrongTokenException("Expired Token");
        } catch (Exception e) {
            throw e;
        }
    }
}
