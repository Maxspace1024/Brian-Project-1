package com.brian.stylish.controller;

import com.brian.stylish.dto.ProductCreateDTO;
import com.brian.stylish.dto.ProductDTO;
import com.brian.stylish.service.ProductService;
import com.brian.stylish.utils.ImageFileUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/img/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            byte[] fileBytes = ImageFileUtils.getFile(filename);
            if (fileBytes.length == 0) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileBytes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/api/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> postProduct(@ModelAttribute ProductCreateDTO dto) {
        Integer newId = productService.createProduct(dto);
        return ResponseEntity.ok()
            .body(productService.findProductDTOById(newId));
    }
}
