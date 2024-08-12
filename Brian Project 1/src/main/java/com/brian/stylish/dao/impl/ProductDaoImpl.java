package com.brian.stylish.dao.impl;

import com.brian.stylish.dao.ProductDao;
import com.brian.stylish.dto.PageResult;
import com.brian.stylish.dto.ProductCreateDTO;
import com.brian.stylish.dto.ProductDTO;
import com.brian.stylish.rowmapper.ProdcutDTORowMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class ProductDaoImpl implements ProductDao {

    private final static Integer PAGE_SIZE = 6;

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Value("${webpage.url}")
    private String WEBPAGE_URL;

    @Override
    public Integer createProduct(ProductCreateDTO dto, String mainImageFilename) {
        String sql = "INSERT INTO product ( id, category, title, description, price, texture, wash, place, note, story, main_image) VALUES" +
            "(default, :category, :title, :description, :price, :texture, :wash, :place, :note, :story, :main_image)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        KeyHolder keyholder = new GeneratedKeyHolder();
        params.addValue("category", dto.getCategory())
            .addValue("title", dto.getTitle())
            .addValue("description", dto.getDescription())
            .addValue("price", dto.getPrice())
            .addValue("texture", dto.getTexture())
            .addValue("wash", dto.getWash())
            .addValue("place", dto.getPlace())
            .addValue("note", dto.getNote())
            .addValue("story", dto.getStory())
            .addValue("main_image", WEBPAGE_URL + "img/" + mainImageFilename);
        template.update(sql, params, keyholder, new String[]{"id"});

        if (keyholder.getKey() != null) {
            return keyholder.getKey().intValue();
        } else {
            return null;
        }
    }

    @Override
    public ProductDTO findProductDTOById(Integer id) {
        String sql = "SELECT * FROM product where id = :product_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", id);

        try {
            return template.queryForObject(sql, params, new ProdcutDTORowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public PageResult<ProductDTO> findProductListByPage(String category, Integer page) {
        String sql = "";
        if (category.equals("all")) {
            sql = "SELECT * FROM product LIMIT :pageSize OFFSET :rowCount ";
        } else {
            sql = "SELECT * FROM product WHERE category = :category LIMIT :pageSize OFFSET :rowCount ";
        }

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pageSize", PAGE_SIZE + 1)
            .addValue("rowCount", page * PAGE_SIZE)
            .addValue("category", category);

        List<ProductDTO> result = template.query(sql, params, new ProdcutDTORowMapper());
        Integer resultSize = result.size();
        Integer nextPage = null;
        if (resultSize > PAGE_SIZE) {
            result.remove(resultSize - 1);
            nextPage = page + 1;
        }

        return PageResult.<ProductDTO>builder()
            .page(result)
            .nextPage(nextPage)
            .build();
    }

    @Override
    public PageResult<ProductDTO> findProductDTOListByTitleAndPage(String title, Integer page) {
        String sql = "SELECT * FROM product WHERE title LIKE :title LIMIT :pageSize OFFSET :rowCount";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pageSize", PAGE_SIZE + 1)
            .addValue("rowCount", page * PAGE_SIZE)
            .addValue("title", "%" + title + "%");

        List<ProductDTO> result = template.query(sql, params, new ProdcutDTORowMapper());
        Integer resultSize = result.size();
        Integer nextPage = null;
        if (resultSize > PAGE_SIZE) {
            result.remove(resultSize - 1);
            nextPage = page + 1;
        }

        return PageResult.<ProductDTO>builder()
            .page(result)
            .nextPage(nextPage)
            .build();
    }
}
