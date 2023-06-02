package com.inn.cafe.service;

import com.inn.cafe.Model.Product;
import com.inn.cafe.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseEntity<String> addProduct(Map<String, String> requestMap);

    ResponseEntity<List<ProductWrapper>> getProduct();

    ResponseEntity<String> updateProduct(Map<String, String> requestMap);


    ResponseEntity<String>delectProduct(Integer id);


    ResponseEntity<String>updateStatus(Map<String, String> requestMap);

    ResponseEntity<List<ProductWrapper>> getCategory(Integer id);

    ResponseEntity<ProductWrapper> getProductById(Integer id);

    ResponseEntity<List<Product>> getProductSample();
}
