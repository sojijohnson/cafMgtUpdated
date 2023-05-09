package com.inn.cafe.service;

import com.inn.cafe.Model.User;
import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;




public interface UserService {




     ResponseEntity<String> forgotPassword(Map<String, String> requestMap) ;


    ResponseEntity<String> signUp(User requestMap);
    ResponseEntity<String> login(Map<String, String> requestMap);


    ResponseEntity<List<UserWrapper>> getAllUser();

    ResponseEntity<String> update(Map<String, String> requestMap);

    ResponseEntity<String> resetPassword(Map<String, String> requestMap);
}
