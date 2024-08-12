package com.brian.stylish.service;

import com.brian.stylish.dto.CampaignCreateDTO;
import com.brian.stylish.dto.CampaignDTO;

import java.util.List;

public interface CampaignService {
    Integer createCamapign(CampaignCreateDTO dto);

    CampaignDTO findCampaignDTOById(Integer id);

    List<CampaignDTO> findAllCampaignDTO();
}
