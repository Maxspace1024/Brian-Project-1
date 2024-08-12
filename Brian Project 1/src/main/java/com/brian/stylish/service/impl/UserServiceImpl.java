package com.brian.stylish.service.impl;

import com.brian.stylish.dao.UserDao;
import com.brian.stylish.dto.MeDTO;
import com.brian.stylish.dto.SigninDTO;
import com.brian.stylish.dto.SignupDTO;
import com.brian.stylish.dto.UserDTO;
import com.brian.stylish.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private static final String GRAPH_API_URL = "https://graph.facebook.com/me?fields=id,name,email&access_token=";

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer createUser(SignupDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return userDao.createUser(dto);
    }

    @Override
    public Boolean checkDuplicateUser(SignupDTO dto) {
        return userDao.checkDuplicateUser(dto);
    }

    @Override
    public UserDTO userLogin(SigninDTO dto) {
        UserDTO userDto = null;
        if ("native".equals(dto.getProvider())) {
            userDto = userLoginNative(dto);
        } else if ("facebook".equals(dto.getProvider())) {
            userDto = userLoginFackbook(dto);
        }
        return userDto;
    }

    @Override
    public UserDTO userLoginNative(SigninDTO dto) {
        UserDTO userDto = userDao.findUserByEmail(dto.getEmail());
        if (userDto != null && passwordEncoder.matches(dto.getPassword(), userDto.getPassword())) {
            userDto.setPassword(null);
            return userDto;
        } else {
            return null;
        }
    }

    @Override
    public UserDTO userLoginFackbook(SigninDTO dto) {
        UserDTO userDto = null;
        String accessToken = dto.getAccessToken();

        // use graph api to authenticate the FB access token
        String respString = "";
        try {
            respString = restTemplate.getForObject(GRAPH_API_URL + accessToken, String.class);
        } catch (HttpClientErrorException e) {
            respString = e.getResponseBodyAsString();
            return null;
        }

        try {
            MeDTO meDto = objectMapper.readValue(respString, MeDTO.class);
            if (meDto.getError() != null) {
                return null;
            }
            // if userDto is null, means this is a new user
            // or userDto is not null, just return it
            userDto = userDao.findUserByEmail(meDto.getEmail());
            if (userDto == null) {
                SignupDTO signupDTO = new SignupDTO(meDto.getName(), meDto.getEmail(), "");
                Integer newUserId = userDao.createUserFacebook(signupDTO);
                userDto = userDao.findUserById(newUserId);
            }
            userDto.setPassword(null);
            return userDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserDTO findUserDTOById(Integer id) {
        return userDao.findUserById(id);
    }

    @Override
    public UserDTO findUserDTOByIdNoPassword(Integer id) {
        UserDTO dto = userDao.findUserById(id);
        if (dto != null) {
            dto.setPassword(null);
            return dto;
        } else {
            return null;
        }
    }
}
