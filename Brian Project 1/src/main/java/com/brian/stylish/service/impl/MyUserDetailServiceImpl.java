package com.brian.stylish.service.impl;

import com.brian.stylish.dao.UserDao;
import com.brian.stylish.dto.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "myUserDetailServiceImpl")
@Log4j2
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userDao.findUserByEmail(username);
        if (userDTO == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        String[] roleArray = userDao.findUserRoleListById(userDTO.getId()).toArray(new String[0]);

        return User.withUsername(userDTO.getEmail())
            .password(userDTO.getPassword())
            .roles(roleArray)
            .build();
    }
}
