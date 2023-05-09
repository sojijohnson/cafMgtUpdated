package com.inn.cafe.restimpl;

import com.inn.cafe.Model.User;
import com.inn.cafe.constant.CafeConstants;
import com.inn.cafe.rest.UserRest;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.UserWrapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


//@NoArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@RestController

public class UserRestImpl implements UserRest {

    @Autowired
    private UserService userService;

    //CafeUtils cafeUtils;

    @Override
    public ResponseEntity<String> signUp(User requestMap) {
        // return null;

        try{
            return userService.signUp(requestMap);

        }catch(Exception ex){
            ex.printStackTrace();
        }
                  return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
       // return new ResponseEntity<String>("{\"message\":\"spmething went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
       try{
             return userService.getAllUser();
       }catch (Exception ex){
           ex.printStackTrace();
        }


        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {

       try{
           return userService.login(requestMap);
       }catch(Exception ex){
           ex.printStackTrace();
       }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {

          try{
           return   userService.update(requestMap);

          }catch(Exception ex){
               ex.printStackTrace();

          }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> resetPassword(Map<String, String> requestMap) {

       try{
           return userService.resetPassword(requestMap);

       }catch (Exception ex){
           ex.printStackTrace();
       }
        return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA,HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try{

            return userService.forgotPassword(requestMap);
        }catch (Exception ex){

            ex.printStackTrace();
        }



        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
//        return null;
   // }
}

