package com.brian.stylish.dao;

import com.brian.stylish.dto.PageResult;
import com.brian.stylish.dto.ProductCreateDTO;
import com.brian.stylish.dto.ProductDTO;

public interface ProductDao {
    Integer createProduct(ProductCreateDTO dto, String mainImageFilename);

    ProductDTO findProductDTOById(Integer id);

    PageResult<ProductDTO> findProductListByPage(String category, Integer page);

    PageResult<ProductDTO> findProductDTOListByTitleAndPage(String title, Integer page);
}
