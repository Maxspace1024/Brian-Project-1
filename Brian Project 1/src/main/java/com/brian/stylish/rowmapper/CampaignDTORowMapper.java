package com.brian.stylish.rowmapper;

import com.brian.stylish.dto.CampaignDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CampaignDTORowMapper implements RowMapper<CampaignDTO> {
    @Override
    public CampaignDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CampaignDTO.builder()
            .productId(rs.getInt("product_id"))
            .story(rs.getString("story"))
            .picture(rs.getString("picture"))
            .build();
    }
}
