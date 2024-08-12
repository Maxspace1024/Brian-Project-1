package com.brian.stylish.dao.impl;

import com.brian.stylish.dao.CampaignDao;
import com.brian.stylish.dto.CampaignCreateDTO;
import com.brian.stylish.dto.CampaignDTO;
import com.brian.stylish.rowmapper.CampaignDTORowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CampaignDaoImpl implements CampaignDao {

    @Value("${webpage.url}img/")
    private String WEBPAGE_URL;

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Override
    public Integer createCampaign(CampaignCreateDTO dto, String pictureName) {
        String sql = "INSERT INTO campaign (id, product_id, picture, story) VALUES (default, :product_id, :picture, :story)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        params.addValue("product_id", dto.getProductId())
            .addValue("picture", WEBPAGE_URL + pictureName)
            .addValue("story", dto.getStory());
        template.update(sql, params, keyHolder, new String[]{"id"});

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }

    @Override
    public CampaignDTO findCampaignDTOById(Integer id) {
        String sql = "SELECT * FROM campaign WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return template.queryForObject(sql, params, new CampaignDTORowMapper());
    }

    @Override
    public List<CampaignDTO> findAllCampaignDTO() {
        String sql = "SELECT * FROM campaign";
        MapSqlParameterSource params = new MapSqlParameterSource();

        return template.query(sql, params, new CampaignDTORowMapper());
    }
}
