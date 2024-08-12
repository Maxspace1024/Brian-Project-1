package com.brian.stylish.controller;


import com.brian.stylish.dto.CampaignCreateDTO;
import com.brian.stylish.dto.CampaignDTO;
import com.brian.stylish.service.CampaignService;
import com.brian.stylish.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/1.0")
@CrossOrigin
public class MarketingController {

    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private CampaignService campaignService;

    @GetMapping("/marketing/campaigns")
    public ResponseEntity<?> marketingCampaign() {
        List<CampaignDTO> campaignDTOList = campaignService.findAllCampaignDTO();
        return responseUtils.responseData(HttpStatus.OK, campaignDTOList);
    }

    @PostMapping(value = "/campaign", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> campaign(@ModelAttribute @Validated CampaignCreateDTO campaignCreateDTO) {
        if (campaignCreateDTO.getPicture().isEmpty()) {
            throw new IllegalArgumentException("no file been uploaded");
        }

        Integer newCampaignId = campaignService.createCamapign(campaignCreateDTO);
        CampaignDTO campaignDTO = campaignService.findCampaignDTOById(newCampaignId);
        return ResponseEntity.ok(campaignDTO);
    }
}
