package com.inn.cafe.rest;


import com.inn.cafe.Model.Product;
import com.inn.cafe.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path=("/product"))
public interface ProductRest {

    @PostMapping(path="/add")
    ResponseEntity<String>addProduct(@RequestBody(required = true) Map<String,String> requestMap);

    @GetMapping(path="/get")
    ResponseEntity<List<ProductWrapper>>getProduct();


    @PostMapping(path = "/update")
    ResponseEntity<String> updateProduct(@RequestBody Map<String,String> requestMap);

    @PostMapping(path ="/delect/{id}")
    ResponseEntity<String>delectProduct(@PathVariable Integer id);

    @PostMapping(path="/updateStatus")
     ResponseEntity<String>updateStatus(@RequestBody Map<String,String> requestMap);

    @GetMapping(path="/getCategory/{id}")
    ResponseEntity<List<ProductWrapper>>getCategory(@PathVariable Integer id);

    @GetMapping(path="/getByid/{id}")
    ResponseEntity<ProductWrapper>getProductById(@PathVariable Integer id);

    @GetMapping(path = "/gets")
    ResponseEntity<List<Product>>getProductSample();
}
