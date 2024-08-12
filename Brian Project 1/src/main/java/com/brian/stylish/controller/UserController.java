package com.brian.stylish.controller;

import com.brian.stylish.config.JwtTokenUtils;
import com.brian.stylish.dto.AccessDTO;
import com.brian.stylish.dto.SigninDTO;
import com.brian.stylish.dto.SignupDTO;
import com.brian.stylish.dto.UserDTO;
import com.brian.stylish.service.UserService;
import com.brian.stylish.utils.ResponseUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RequestMapping("/api/1.0/user")
@Log4j2
public class UserController {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/signup")
    public ResponseEntity<Object> signup(@RequestBody @Validated SignupDTO signupDTO, BindingResult result) {
        if (result.hasErrors()) {
            // Client Error Response: 400
            return responseUtils.responseError(HttpStatus.BAD_REQUEST, getBindingErrorMessage(result));
        }

        try {
            if (userService.checkDuplicateUser(signupDTO)) {
                // Email Already Exists: 403
                return responseUtils.responseError(HttpStatus.FORBIDDEN, "Email Already Exists");
            }

            Integer userId = userService.createUser(signupDTO);
            UserDTO userDto = userService.findUserDTOByIdNoPassword(userId);
            AccessDTO accessDto = jwtTokenUtils.createAccessDTO(userDto);
            //Success Response: 200
            return responseUtils.responseData(HttpStatus.OK, accessDto);
        } catch (Exception e) {
            // Server Error Response: 500
            return responseUtils.responseError(HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> signin(HttpSession session, @RequestBody @Validated SigninDTO signinDTO, BindingResult result) {
        // Client Error Response: 400
        if (result.hasErrors()) {
            return responseUtils.responseError(HttpStatus.BAD_REQUEST, getBindingErrorMessage(result));
        }
        log.info(signinDTO.getProvider());
        if (!"native".equals(signinDTO.getProvider()) && !"facebook".equals(signinDTO.getProvider())) {
            return responseUtils.responseError(HttpStatus.BAD_REQUEST, "invalid provider");
        } else if ("native".equals(signinDTO.getProvider()) && (signinDTO.getEmail() == null || signinDTO.getPassword() == null)) {
            return responseUtils.responseError(HttpStatus.BAD_REQUEST, "email and password should not be null");
        } else if ("facebook".equals(signinDTO.getProvider()) && (signinDTO.getAccessToken() == null)) {
            return responseUtils.responseError(HttpStatus.BAD_REQUEST, "access token is null");
        }

        try {
            UserDTO userDto = userService.userLogin(signinDTO);
            if (userDto != null) {
                AccessDTO accessDto = jwtTokenUtils.createAccessDTO(userDto);
                //Success Response: 200
                return responseUtils.responseData(HttpStatus.OK, accessDto);
            } else {
                // Sign In Failed: 403
                return responseUtils.responseError(HttpStatus.FORBIDDEN, "login fail");
            }
        } catch (Exception e) {
            // Server Error Response: 500
            return responseUtils.responseError(HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> profile(@RequestHeader(name = "Authorization", required = false) String token) {
        try {
            if (token == null) {
                // Client Error (No token) Response: 401
                return responseUtils.responseError(HttpStatus.UNAUTHORIZED, "No Token");
            }
            token = token.replace("Bearer ", "");
            UserDTO dto = jwtTokenUtils.parseTokenToUserProfile(token);
            // Success Response: 200
            return responseUtils.responseData(HttpStatus.OK, dto);
        } catch (ExpiredJwtException e) {
            return responseUtils.responseError(HttpStatus.UNAUTHORIZED, e.toString());
        } catch (MalformedJwtException e) {
            // Client Error (Wrong token) Response: 403
            return responseUtils.responseError(HttpStatus.FORBIDDEN, e.toString());
        } catch (Exception e) {
            // Server Error Response: 500
            return responseUtils.responseError(HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    private static String getBindingErrorMessage(BindingResult result) {
        StringBuilder sb = new StringBuilder();
        for (var e : result.getFieldErrors()) {
            sb.append(e.getDefaultMessage());
            sb.append(",");
        }

        return sb.toString();
    }
}
