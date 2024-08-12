package com.brian.stylish.controller;

import com.brian.stylish.dto.MessageWrapper;
import com.brian.stylish.dto.PageResult;
import com.brian.stylish.dto.ProductDTO;
import com.brian.stylish.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/1.0")
@CrossOrigin
public class APIController {

    private static final List<String> categoryType = List.of("all", "men", "women", "accessories");

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{category}")
    public ResponseEntity<Object> productPaging(
        @PathVariable String category,
        @RequestParam(value = "paging", defaultValue = "0") String paging
    ) {
        if (!categoryType.contains(category)) {
            return ResponseEntity.badRequest().body(
                MessageWrapper.builder()
                    .error("error request path")
                    .build()
            );
        }

        try {
            Integer pagingNumber = Integer.parseInt(paging);
            PageResult<ProductDTO> pageResult = productService.findProductListByPage(category, pagingNumber);
            return ResponseEntity.ok().body(
                MessageWrapper.builder()
                    .data(pageResult.getPage())
                    .nextPage(pageResult.getNextPage())
                    .build()
            );
        } catch (NumberFormatException e) {
            // if user input non-digit query string, parseInt will fail and we should deal the error
            return ResponseEntity.badRequest().body(
                MessageWrapper.builder()
                    .error(e.toString())
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                MessageWrapper.builder()
                    .error(e.toString())
                    .build()
            );
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<Object> productSearch(
        @RequestParam String keyword,
        @RequestParam(value = "paging", defaultValue = "0") String paging
    ) {
        try {
            Integer pagingNumber = Integer.parseInt(paging);
            PageResult<ProductDTO> pageResult = productService.findProdcutDTOListByTitleAndPage(keyword, pagingNumber);
            return ResponseEntity.ok().body(
                MessageWrapper.builder()
                    .data(pageResult.getPage())
                    .nextPage(pageResult.getNextPage())
                    .build()
            );
        } catch (NumberFormatException e) {
            // if user input non-digit query string, parseInt will fail and we should deal the error
            return ResponseEntity.badRequest().body(
                MessageWrapper.builder()
                    .error(e.toString())
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                MessageWrapper.builder()
                    .error(e.toString())
                    .build()
            );
        }
    }

    @GetMapping("/products/details")
    public ResponseEntity<Object> details(@RequestParam Integer id) {
        try {
            ProductDTO dto = productService.findProductDTOById(id);
            if (dto != null) {
                return ResponseEntity.ok().body(
                    MessageWrapper.builder()
                        .data(dto)
                        .build()
                );
            } else {
                return ResponseEntity.badRequest().body(
                    MessageWrapper.builder()
                        .error("product not found")
                        .build()
                );
            }
        } catch (NumberFormatException e) {
            // if user input non-digit query string, parseInt will fail and we should deal the error
            return ResponseEntity.badRequest().body(
                MessageWrapper.builder()
                    .error(e.toString())
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                MessageWrapper.builder()
                    .error(e.toString())
                    .build()
            );
        }
    }
}
