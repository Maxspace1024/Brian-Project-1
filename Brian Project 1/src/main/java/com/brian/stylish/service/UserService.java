package com.brian.stylish.service;

import com.brian.stylish.dto.SigninDTO;
import com.brian.stylish.dto.SignupDTO;
import com.brian.stylish.dto.UserDTO;

public interface UserService {
    Integer createUser(SignupDTO dto);

    Boolean checkDuplicateUser(SignupDTO dto);

    UserDTO userLogin(SigninDTO dto);

    UserDTO userLoginNative(SigninDTO dto);

    UserDTO userLoginFackbook(SigninDTO dto);

    UserDTO findUserDTOById(Integer id);

    UserDTO findUserDTOByIdNoPassword(Integer id);
}
