package com.brian.stylish.dao.impl;

import com.brian.stylish.dao.VariantDao;
import com.brian.stylish.dto.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class VariantDaoImpl implements VariantDao {
    @Autowired
    private NamedParameterJdbcTemplate template;

    @Override
    public Integer createVariantByProductId(Integer id, VariantCreateDTO dto) {
        // find same color/size, if not found, create a new row
        Integer colors_id = findColorIdByNameAndCode(dto);
        if (colors_id == null) {
            colors_id = createColor(dto);
        }
        Integer sizes_id = findSizeIdBySize(dto);
        if (sizes_id == null) {
            sizes_id = createSize(dto);
        }

        String sql = "INSERT INTO product_variant (id, colors_id, sizes_id, stock, product_id) VALUES" +
            "(default, :colors_id, :sizes_id, :stock, :product_id)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("colors_id", colors_id)
            .addValue("sizes_id", sizes_id)
            .addValue("stock", dto.getStock())
            .addValue("product_id", id);
        template.update(sql, params, keyholder, new String[]{"id"});

        if (keyholder.getKey() != null) {
            return keyholder.getKey().intValue();
        } else {
            return null;
        }
    }

    @Override
    public Integer createColor(VariantCreateDTO dto) {
        String sql = "INSERT INTO colors (id, name, code) VALUES (default, :name, :code)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", dto.getColor_name())
            .addValue("code", dto.getColor_code());
        template.update(sql, params, keyholder, new String[]{"id"});

        if (keyholder.getKey() != null) {
            return keyholder.getKey().intValue();
        } else {
            return null;
        }
    }

    @Override
    public Integer createSize(VariantCreateDTO dto) {
        String sql = "INSERT INTO sizes (id, size) VALUES (default, :size)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("size", dto.getSize());
        template.update(sql, params, keyholder, new String[]{"id"});

        if (keyholder.getKey() != null) {
            return keyholder.getKey().intValue();
        } else {
            return null;
        }
    }

    @Override
    public Integer findColorIdByNameAndCode(VariantCreateDTO dto) {
        String sql = "SELECT id FROM colors WHERE name = :name AND code = :code";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", dto.getColor_name())
            .addValue("code", dto.getColor_code());

        try {
            return template.queryForObject(sql, params, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer findSizeIdBySize(VariantCreateDTO dto) {
        String sql = "SELECT id FROM sizes WHERE size = :size";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("size", dto.getSize());

        try {
            return template.queryForObject(sql, params, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public VariantFKDTO findVariantFKDTOByFK(Integer productId, OrderDetailDTO dto) {
        String sql = "SELECT * FROM product_variant pv " +
            "inner join colors c on c.id = pv.colors_id " +
            "inner join sizes s on s.id = pv.sizes_id " +
            "WHERE product_id = :product_id AND name = :name AND code = :code AND size = :size ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", productId)
            .addValue("name", dto.getColor().getName())
            .addValue("code", dto.getColor().getCode())
            .addValue("size", dto.getSize());

        try {
            return template.queryForObject(sql, params, (rs, rowNum) ->
                VariantFKDTO.builder()
                    .id(rs.getInt("id"))
                    .colorsId(rs.getInt("colors_id"))
                    .sizesId(rs.getInt("sizes_id"))
                    .stock(rs.getInt("stock"))
                    .productId(rs.getInt("product_id"))
                    .build()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<String> findSizeListByProductId(Integer id) {
        String sql = "SELECT DISTINCT size FROM product_variant pv " +
            "INNER JOIN sizes s ON s.id = pv.sizes_id WHERE pv.product_id = :product_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", id);

        try {
            return template.query(sql, params, (rs, rowNum) -> rs.getString("size"));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<VariantDTO> findVariantDTOListByProductId(Integer id) {
        String sql = "SELECT * FROM product_variant pv\n" +
            "INNER JOIN sizes s ON pv.sizes_id = s.id\n" +
            "INNER JOIN colors c ON pv.colors_id = c.id\n" +
            "WHERE pv.product_id = :product_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", id);
        try {
            return template.query(sql, params, (rs, rowNum) ->
                VariantDTO.builder()
                    .color_code(rs.getString("code"))
                    .size(rs.getString("size"))
                    .stock(rs.getInt("stock"))
                    .build()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ColorDTO> findColorDTOListByProductId(Integer id) {
        String sql = "SELECT DISTINCT `name`, `code` FROM product_variant pv\n" +
            "INNER JOIN colors c ON pv.colors_id = c.id\n" +
            "WHERE pv.product_id = :product_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", id);

        try {
            return template.query(sql, params, (rs, rowNum) ->
                ColorDTO.builder()
                    .code(rs.getString("code"))
                    .name(rs.getString("name"))
                    .build()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
