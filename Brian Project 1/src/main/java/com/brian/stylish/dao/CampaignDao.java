package com.brian.stylish.dao;

import com.brian.stylish.dto.CampaignCreateDTO;
import com.brian.stylish.dto.CampaignDTO;

import java.util.List;

public interface CampaignDao {
    Integer createCampaign(CampaignCreateDTO dto, String pictureName);

    CampaignDTO findCampaignDTOById(Integer id);

    List<CampaignDTO> findAllCampaignDTO();
}
