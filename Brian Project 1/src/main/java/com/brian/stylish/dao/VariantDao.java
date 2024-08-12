package com.brian.stylish.dao;

import com.brian.stylish.dto.*;

import java.util.List;

public interface VariantDao {
    Integer createVariantByProductId(Integer id, VariantCreateDTO dto);

    Integer createColor(VariantCreateDTO dto);

    Integer createSize(VariantCreateDTO dto);

    Integer findColorIdByNameAndCode(VariantCreateDTO dto);

    Integer findSizeIdBySize(VariantCreateDTO dto);

    VariantFKDTO findVariantFKDTOByFK(Integer productId, OrderDetailDTO dto);

    List<String> findSizeListByProductId(Integer id);

    List<VariantDTO> findVariantDTOListByProductId(Integer id);

    List<ColorDTO> findColorDTOListByProductId(Integer id);
}
