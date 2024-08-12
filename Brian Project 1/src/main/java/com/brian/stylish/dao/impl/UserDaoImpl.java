package com.brian.stylish.dao.impl;

import com.brian.stylish.dao.UserDao;
import com.brian.stylish.dto.SignupDTO;
import com.brian.stylish.dto.UserDTO;
import com.brian.stylish.rowmapper.UserDTORowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    NamedParameterJdbcTemplate template;

    @Override
    public boolean checkDuplicateUser(SignupDTO dto) {
        String sql = "SELECT COUNT(id) FROM `user` WHERE email = :email";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", dto.getEmail());
        Integer count = template.queryForObject(sql, params, Integer.class);

        return count > 0;
    }

    @Override
    public Integer createUser(SignupDTO dto) {
        String sql = "INSERT INTO `user` (id, provider, name, email, password, picture) VALUES (default, 'native', :name, :email, :password, '')";
        MapSqlParameterSource params = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        params.addValue("name", dto.getName());
        params.addValue("email", dto.getEmail());
        params.addValue("password", dto.getPassword());
        template.update(sql, params, keyHolder, new String[]{"id"});

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }

    @Override
    public Integer createUserFacebook(SignupDTO dto) {
        String sql = "INSERT INTO `user` (id, provider, name, email, password, picture) VALUES (default, 'facebook', :name, :email, '', '')";
        MapSqlParameterSource params = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        params.addValue("name", dto.getName());
        params.addValue("email", dto.getEmail());
        template.update(sql, params, keyHolder, new String[]{"id"});

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }

    @Override
    public UserDTO findUserById(Integer id) {
        String sql = "SELECT * FROM `user` where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try {
            return template.queryForObject(sql, params, new UserDTORowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        String sql = "SELECT * FROM `user` where email = :email";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);

        try {
            return template.queryForObject(sql, params, new UserDTORowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<String> findUserRoleListById(Integer userId) {
        String sql = "select `role` from user_role ur\n" +
            "inner join `role` r on r.id = ur.role_id\n" +
            "where ur.user_id = :user_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);

        return template.queryForList(sql, params, String.class);
    }
}
