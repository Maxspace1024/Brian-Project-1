package com.brian.stylish.service;

import com.brian.stylish.dto.PageResult;
import com.brian.stylish.dto.ProductCreateDTO;
import com.brian.stylish.dto.ProductDTO;

public interface ProductService {
    Integer createProduct(ProductCreateDTO dto);

    ProductDTO findProductDTOById(Integer id);

    PageResult<ProductDTO> findProductListByPage(String category, Integer page);

    PageResult<ProductDTO> findProdcutDTOListByTitleAndPage(String title, Integer page);
}
