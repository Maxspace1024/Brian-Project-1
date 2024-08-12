package com.brian.stylish.dao;

import com.brian.stylish.dto.SignupDTO;
import com.brian.stylish.dto.UserDTO;

import java.util.List;

public interface UserDao {
    boolean checkDuplicateUser(SignupDTO dto);

    Integer createUser(SignupDTO dto);

    Integer createUserFacebook(SignupDTO dto);

    UserDTO findUserById(Integer id);

    UserDTO findUserByEmail(String email);

    List<String> findUserRoleListById(Integer userId);
}
