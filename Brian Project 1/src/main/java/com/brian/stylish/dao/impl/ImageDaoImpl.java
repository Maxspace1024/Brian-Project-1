package com.brian.stylish.dao.impl;

import com.brian.stylish.dao.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ImageDaoImpl implements ImageDao {

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Value("${webpage.url}")
    private String WEBPAGE_URL;

    @Override
    public Integer createImageByProductId(Integer id, MultipartFile file, String filename) {
        StringBuilder sql = new StringBuilder("INSERT INTO product_image (id, image, product_id) VALUES (default, :image, :product_id)");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", id)
                .addValue("image", WEBPAGE_URL + "img/" + filename);
        template.update(sql.toString(), params, keyHolder, new String[]{"id"});

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }

    @Override
    public List<String> findImageListByProductId(Integer id) {
        String sql = "SELECT image FROM product_image where product_id = :product_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", id);

        try {
            return template.query(sql, params, (rs, rowNum) -> rs.getString("image"));
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }
}
