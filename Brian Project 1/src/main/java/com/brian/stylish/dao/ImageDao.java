package com.brian.stylish.dao;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageDao {
    Integer createImageByProductId(Integer id, MultipartFile file, String filename);

    List<String> findImageListByProductId(Integer id);
}
