package com.brian.stylish.service.impl;

import com.brian.stylish.dao.ImageDao;
import com.brian.stylish.dao.ProductDao;
import com.brian.stylish.dao.VariantDao;
import com.brian.stylish.dto.PageResult;
import com.brian.stylish.dto.ProductCreateDTO;
import com.brian.stylish.dto.ProductDTO;
import com.brian.stylish.service.ProductService;
import com.brian.stylish.utils.ImageFileUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private VariantDao variantDao;

    @Autowired
    private ImageDao imageDao;

    @Override
    @Transactional
    public Integer createProduct(ProductCreateDTO dto) {
        try {
            // save the main_image and product
            String mainImageFilename = ImageFileUtils.createUuidFilename(dto.getMainImage());
            try {
                ImageFileUtils.saveFile(dto.getMainImage(), mainImageFilename);
            } catch (Exception e) {
                throw new RuntimeException(e.toString());
            }
            Integer newProductId = productDao.createProduct(dto, mainImageFilename);
            if (newProductId == null) {
                throw new RuntimeException("error: create product fail");
            }

            // create variants
            for (var variantDTO : dto.getVariants()) {
                Integer newVariantId = variantDao.createVariantByProductId(newProductId, variantDTO);
                if (newVariantId == null) {
                    throw new RuntimeException("error: create variant fail");
                }
            }

            // create images
            for (var file : dto.getImages()) {
                String imageFilename = ImageFileUtils.createUuidFilename(file);
                try {
                    ImageFileUtils.saveFile(file, imageFilename);
                } catch (Exception e) {
                    throw new RuntimeException(e.toString());
                }
                Integer newImagesId = imageDao.createImageByProductId(newProductId, file, imageFilename);
                if (newImagesId == null) {
                    throw new RuntimeException("error: create image fail");
                }
            }
            return newProductId;
        } catch (Exception e) {
            log.warn(e.toString());
            throw e;
        }
    }

    @Override
    public ProductDTO findProductDTOById(Integer id) {
        ProductDTO dto = productDao.findProductDTOById(id);
        if (dto == null) {
            return null;
        }

        dto.setSizes(variantDao.findSizeListByProductId(id));
        dto.setColors(variantDao.findColorDTOListByProductId(id));
        dto.setVariants(variantDao.findVariantDTOListByProductId(id));
        dto.setImages(imageDao.findImageListByProductId(id));
        return dto;
    }

    @Override
    public PageResult<ProductDTO> findProductListByPage(String category, Integer page) {
        PageResult<ProductDTO> result = productDao.findProductListByPage(category, page);
        List<ProductDTO> dtoList = result.getPage();
        for (var dto : dtoList) {
            Integer prodcutId = dto.getId();
            dto.setSizes(variantDao.findSizeListByProductId(prodcutId));
            dto.setColors(variantDao.findColorDTOListByProductId(prodcutId));
            dto.setVariants(variantDao.findVariantDTOListByProductId(prodcutId));
            dto.setImages(imageDao.findImageListByProductId(prodcutId));
        }
        result.setPage(dtoList);

        return result;
    }

    @Override
    public PageResult<ProductDTO> findProdcutDTOListByTitleAndPage(String title, Integer page) {
        PageResult<ProductDTO> result = productDao.findProductDTOListByTitleAndPage(title, page);
        List<ProductDTO> dtoList = result.getPage();
        for (var dto : dtoList) {
            Integer prodcutId = dto.getId();
            dto.setSizes(variantDao.findSizeListByProductId(prodcutId));
            dto.setColors(variantDao.findColorDTOListByProductId(prodcutId));
            dto.setVariants(variantDao.findVariantDTOListByProductId(prodcutId));
            dto.setImages(imageDao.findImageListByProductId(prodcutId));
        }
        result.setPage(dtoList);

        return result;
    }
}
