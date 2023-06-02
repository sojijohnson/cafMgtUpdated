package com.inn.cafe.restimpl;

import com.inn.cafe.Model.Product;
import com.inn.cafe.constant.CafeConstants;
import com.inn.cafe.rest.ProductRest;
import com.inn.cafe.service.ProductService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.ProductWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@RestController
@Slf4j
public class ProductRestImpl implements ProductRest {

    @Autowired
    private ProductService productService;
    @Override
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {

       try{
           return productService.addProduct(requestMap);
       }catch(Exception ex){
          ex.printStackTrace();
       }

        return CafeUtils.getResponseEntity("productService not availabe", HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getProduct() {

       try {
           return productService.getProduct();
       }catch (Exception ex){
           ex.printStackTrace();
       }

        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {

         try{
             return  productService.updateProduct(requestMap);
         }catch(Exception ex){
             ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> delectProduct(Integer id) {
        try{
            return productService.delectProduct(id);
        }catch(Exception ex){
           ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity("id not valid",HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {

        try{
            return productService.updateStatus(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity("Status not available",HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getCategory(Integer id) {

        try{
            log.info("inside product rest ID {}" ,id);
            return productService.getCategory(id);
        }catch(Exception ex){
            ex.printStackTrace();
        }
            return new ResponseEntity(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
       // return CafeUtils.getResponseEntity("Status not available",HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
       try{
           log.info("inside product rest ID {}" ,id);

            return productService.getProductById(id);
       }catch(Exception ex){
           ex.printStackTrace();
       }
        return new ResponseEntity<>(new  ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Product>> getProductSample() {
        try {
            return productService.getProductSample();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
