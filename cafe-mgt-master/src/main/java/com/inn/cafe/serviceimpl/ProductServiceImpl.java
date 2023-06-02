package com.inn.cafe.serviceimpl;

import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.Model.Category;
import com.inn.cafe.Model.Product;
import com.inn.cafe.constant.CafeConstants;
import com.inn.cafe.dao.ProductDao;
import com.inn.cafe.service.ProductService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.ProductWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
@Autowired
   private ProductDao productDao;
    @Autowired
    private JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
        try{
              if(jwtFilter.isAdmin()){
                  if(validateProductMap(requestMap, false)){

        Product product=         productDao.save(getProductFromMap(requestMap,false));

                 log.info("{}",product);
                 return CafeUtils.getResponseEntity("product added,",HttpStatus.OK);
                  }

    return CafeUtils.getResponseEntity("Invalid data", HttpStatus.ALREADY_REPORTED);
              }

        }catch(Exception ex){
            ex.printStackTrace();

        }

        return null;
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getProduct() {

       try{
            return new ResponseEntity<>(productDao.getAllProduct(),HttpStatus.OK);
       }catch(Exception ex){
           ex.printStackTrace();
       }


        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
               if(jwtFilter.isAdmin()){
                   if(validateProductMap(requestMap,true)){
            Optional<Product> optionalProduct= productDao.findById(Integer.parseInt(requestMap.get("id")));

                   if(!optionalProduct.isEmpty()){
                       Product product = getProductFromMap(requestMap,true);
                         product.setStatus(optionalProduct.get().getStatus());
                         productDao.save(product);
                         return CafeUtils.getResponseEntity("product updated",HttpStatus.OK);
                   }else{
                       return CafeUtils.getResponseEntity("product does not exist",HttpStatus.OK);
                   }
                   }else {
                       return CafeUtils.getResponseEntity("invalid data",HttpStatus.BAD_REQUEST);
                   }

               }else{
                   return CafeUtils.getResponseEntity("not admin",HttpStatus.FORBIDDEN);
               }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> delectProduct(Integer id) {

try{
    if(jwtFilter.isAdmin()){

      Optional optional= productDao.findById(id);
      if (!optional.isEmpty()){
          productDao.deleteById(id);
          return CafeUtils.getResponseEntity("delexted ",HttpStatus.OK);
      }else{
          return CafeUtils.getResponseEntity("Id does not exists",HttpStatus.NOT_FOUND);
      }
    }else{
        return CafeUtils.getResponseEntity("unauthorized user",HttpStatus.FORBIDDEN);
    }

}catch (Exception ex){
   ex.printStackTrace();

}


        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
       try{
           if(jwtFilter.isAdmin()){
                  Optional optional= productDao.findById(Integer.parseInt(requestMap.get("id")));
                  if(!optional.isEmpty()){
                      productDao.updateProductStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                      return CafeUtils.getResponseEntity("Product Status update",HttpStatus.OK);
                  }
                  return CafeUtils.getResponseEntity("id does not exist",HttpStatus.OK);
           }else{
               return CafeUtils.getResponseEntity("access denied",HttpStatus.UNAUTHORIZED);
           }

       }catch (Exception ex){
           ex.printStackTrace();
       }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getCategory(Integer id) {
       log.info("get by id {}",id);
        try{

            return new ResponseEntity<>( productDao.getCategory(id),HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        log.info("get by id {}",id);
        try{
            if(jwtFilter.isAdmin()){
            return new ResponseEntity<>(productDao.getCatById(id),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ProductWrapper(),HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Product>> getProductSample() {
        try{

            log.info("sample");
            return  new ResponseEntity<>(productDao.findAll(),HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }


    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {

        Category category =new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));
        Product product =new Product();
        if(isAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));

        }  else{
           product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));


           log.info("{}",product);

   return product;
    }

    public  Boolean validateProductMap(Map<String,String>requestMap,boolean validateId){
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id")&& validateId){
                return true;
            }else if(!validateId){return  true;}
        }
        return false;



    }

}
