package com.brian.stylish.rowmapper;

import com.brian.stylish.dto.ProductDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdcutDTORowMapper implements RowMapper<ProductDTO> {
    @Override
    public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ProductDTO.builder()
            .id(rs.getInt("id"))
            .category(rs.getString("category"))
            .title(rs.getString("title"))
            .description(rs.getString("description"))
            .price(rs.getInt("price"))
            .texture(rs.getString("texture"))
            .wash(rs.getString("wash"))
            .place(rs.getString("place"))
            .note(rs.getString("note"))
            .story(rs.getString("story"))
            .mainImage(rs.getString("main_image"))
            .build();
    }
}
