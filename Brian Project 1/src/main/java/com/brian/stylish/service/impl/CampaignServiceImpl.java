package com.brian.stylish.service.impl;

import com.brian.stylish.dao.CampaignDao;
import com.brian.stylish.dao.ProductDao;
import com.brian.stylish.dto.CampaignCreateDTO;
import com.brian.stylish.dto.CampaignDTO;
import com.brian.stylish.dto.ProductDTO;
import com.brian.stylish.service.CampaignService;
import com.brian.stylish.utils.ImageFileUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Log4j2
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private CampaignDao campaignDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    public Integer createCamapign(CampaignCreateDTO dto) {
        MultipartFile pictureFile = dto.getPicture();
        if (pictureFile == null) {
            throw new RuntimeException("the picture is null");
        }
        String uuidName = ImageFileUtils.createUuidFilename(pictureFile);
        ProductDTO productDTO = productDao.findProductDTOById(dto.getProductId());
        if (productDTO == null) {
            throw new RuntimeException("the product does not exist");
        }
        try {
            ImageFileUtils.saveFile(pictureFile, uuidName);
            Integer newCampaignId = campaignDao.createCampaign(dto, uuidName);
            if (newCampaignId == null) {
                ImageFileUtils.deleteFile(uuidName);
                throw new RuntimeException("error create campaign record fail");
            }

            try {
                log.info(String.format("campaigns: delete cache"));
                redisTemplate.delete("campaigns");
            } catch (Exception e) {
                log.info(String.format("campaigns: delete cache fail"));
            }
            return newCampaignId;
        } catch (IOException e) {
            throw new RuntimeException("error save picture");
        }
    }

    @Override
    public CampaignDTO findCampaignDTOById(Integer id) {
        return campaignDao.findCampaignDTOById(id);
    }

    @Override
    public List<CampaignDTO> findAllCampaignDTO() {
        List<CampaignDTO> campaignDTOList = null;
        long startTick = System.currentTimeMillis();
        try {
            log.info(String.format("campaigns: try cache"));
            campaignDTOList = (List<CampaignDTO>) redisTemplate.opsForValue().get("campaigns");
            if (campaignDTOList == null) {
                log.info(String.format("campaigns: cache miss, do db query"));
                campaignDTOList = campaignDao.findAllCampaignDTO();
                redisTemplate.opsForValue().set("campaigns", campaignDTOList);
            }
        } catch (Exception e) {
            log.info(String.format("campaigns: exception occurs, do db query"));
            e.printStackTrace();
            campaignDTOList = campaignDao.findAllCampaignDTO();
        }
        long endTick = System.currentTimeMillis();
        log.info(String.format("campaigns: Elapsed Time %f s", (float) (endTick - startTick) / 1000.0));

        return campaignDTOList;
    }
}
