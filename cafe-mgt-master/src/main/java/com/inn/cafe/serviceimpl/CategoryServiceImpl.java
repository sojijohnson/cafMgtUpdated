package com.inn.cafe.serviceimpl;

import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.Model.Category;
import com.inn.cafe.constant.CafeConstants;
import com.inn.cafe.dao.CategoryDao;
import com.inn.cafe.service.CategoryService;
import com.inn.cafe.utils.CafeUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {


       try{
           if(jwtFilter.isAdmin()){

           if  (validateCategoryMap(requestMap,false)){
                 categoryDao.save(getCategoryFromMap(requestMap,false));
           }
                     
           }else{
               CafeUtils.getResponseEntity("UnAuthrizied access", HttpStatus.FORBIDDEN);
           }
       }catch (Exception ex){

           ex.printStackTrace();
       }

        return null;
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {

        try{
            if(!Strings.isEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
return  new ResponseEntity<List<Category>>(categoryDao.getAllCategory(),HttpStatus.OK);

            }
            return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<List<Category>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
       try{
           if(jwtFilter.isAdmin()){
               if(validateCategoryMap(requestMap,true)){
          Optional optional= categoryDao.findById(Integer.parseInt(requestMap.get("id")));



             if(!optional.isEmpty()){

                 categoryDao.save(getCategoryFromMap(requestMap,true));
          return CafeUtils.getResponseEntity("Category Updated successfully",HttpStatus.OK);
             }
              return   CafeUtils.getResponseEntity("id doesnt exsists",HttpStatus.NOT_FOUND);

               }
               return CafeUtils.getResponseEntity("invalid data",HttpStatus.BAD_REQUEST);
           }else {
               return CafeUtils.getResponseEntity("unAuthorize access",HttpStatus.FORBIDDEN);
           }


       }catch(Exception ex){

                    ex.printStackTrace();
       }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.BAD_GATEWAY);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {

        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateId){
                        return  true;
            }else if(!validateId){
                return true;
            }
        } return false;
    }


    private Category getCategoryFromMap(Map<String,String> requsteMap, Boolean isAdd){

        Category category =new Category();
        if(isAdd){
            category.setId(Integer.parseInt(requsteMap.get("id")));
        }
        category.setName(requsteMap.get("name"));
return category;

    }
}
